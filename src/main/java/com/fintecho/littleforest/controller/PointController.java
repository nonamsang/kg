package com.fintecho.littleforest.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fintecho.littleforest.service.EventService;
import com.fintecho.littleforest.service.PaymentService;
import com.fintecho.littleforest.service.PointService;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.service.WalletService;
import com.fintecho.littleforest.vo.PaymentVO;
import com.fintecho.littleforest.vo.PointVO;
import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.WalletVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PointController {

    private final UserService userService;
    private final WalletService walletService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final EventService eventService;

    // 포트원 가맹점 식별코드 (JS SDK에서 사용)
    @Value("${iamport.merchant_code}")
    private String merchantCode;

    /** 결제내역/포인트 탭 페이지 (단일 페이지) */
    @GetMapping("/pay")
    public String payPage(@RequestParam(defaultValue = "points") String tab,
                          HttpSession session, Model model) {

        int testUserId = 1; // 로그인 연동 후 교체
        UserVO userInfo = userService.getInfo(testUserId);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("merchantCode", merchantCode);
        model.addAttribute("tab", tab);
         
        // 결제내역(왼쪽 탭에서 사용)
        List<PaymentVO> paymentList = paymentService.getPaymentListByUserId(testUserId);
        model.addAttribute("paymentList", paymentList);

        // (참고) 총 잔액(지갑) 필요 시
        List<WalletVO> walletList = walletService.getWalletsByUserId(testUserId);
        int totalBalance = walletList.stream().mapToInt(WalletVO::getAccount_Balance).sum();
        model.addAttribute("totalBalance", totalBalance);
        
        //포인트 내역
        List<PointVO> pointList = pointService.getPointByUserId(testUserId);
        model.addAttribute("pointList", pointList);

        // 은행 탭 등 쓸 일 있으면…
        Set<String> bankNames = walletList.stream()
                .map(WalletVO::getBank_Name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        model.addAttribute("bankNames", bankNames);

        return "pay"; // templates/pay.html
    }

    /** 카카오페이(포트원) 결제 완료 콜백 → 서버 검증 + 포인트 적립 */
    @PostMapping("/points/charge/complete")
    @ResponseBody
    public String chargeComplete(@RequestParam("imp_uid") String impUid,
                                 @RequestParam("merchant_uid") String merchantUid,
                                 @RequestParam("amount") int amount) {
        int testUserId = 1; // TODO: 로그인 유저
        int newBalance = pointService.verifyAndChargeByKakaoPay(testUserId, impUid, merchantUid, amount);
        // 새 포인트 잔액 숫자만 반환(프런트에서 표시 교체)
        return String.valueOf(newBalance);
    }

    /** 즉시 적립(이벤트 버튼) */
    @PostMapping("/points/event")
    @ResponseBody
    public String earnPoint(@RequestParam int amount, @RequestParam(required = false) String memo) {
        int testUserId = 1; 
        int newBalance = pointService.earn(testUserId, amount, memo);
        return String.valueOf(newBalance);
    }

    /** 포인트 선물 */
    @PostMapping("/points/gift")
    @ResponseBody
    public String gift(@RequestParam("to_user_id") int toUserId,
                       @RequestParam int amount,
                       @RequestParam(required = false) String memo) {
        int fromUserId = 1; // TODO
        int newBalance = pointService.gift(fromUserId, toUserId, amount, memo);
        return String.valueOf(newBalance);
    }
    
    /** 하루 1회 적립 API */
 // 하루 1회 적립 엔드포인트
    @PostMapping("/points/event/daily")
    @ResponseBody
    public ResponseEntity<String> claimDaily(
            @RequestParam("code") String code,                         // 어떤 이벤트인지 (예: DAILY_TIP, COMMUNITY)
            @RequestParam(value = "amount", required = false) Integer amount, // 금액은 선택(안 보내도 됨)
            @RequestParam(value = "memo",   required = false) String memo) {  // 메모도 선택

        int userId = 1; // 로그인 사용자 ID

        // amount를 안 보내도 동작하도록 “코드별 기본값”을 잡아줌
        //    → 프런트에서 누락돼도 400이 안 나고 정상 처리됨
        int amt = (amount != null) ? amount
                 : switch (code) {
                     case "DAILY_TIP" -> 15;  // 생활실천팁 기본 15P
                     case "COMMUNITY" -> 5;   // 커뮤니티 방문 기본 5P
                     default          -> throw new IllegalArgumentException("UNKNOWN_CODE");
                   };

        // 서비스 호출(여기서 이미 오늘 받았으면 예외 던짐)
        int newBalance = eventService.claimDaily(userId, code, amt, memo);

        // 정상이면 200 OK + 새 잔액 문자열
        return ResponseEntity.ok(String.valueOf(newBalance));
    }

    // “오늘 이미 받음” 상황을 HTTP 409(CONFLICT)로 명확히 내려줌
//        → 프런트는 res.status === 409 로 케이스 분기 가능(문자열 비교보다 안전)
    @ExceptionHandler(EventService.AlreadyClaimedTodayException.class)
    public ResponseEntity<String> alreadyClaimed() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("ALREADY_CLAIMED");
    }

    /** 커뮤니티 방문하며 적립 + 이동(하루 1회) */
	/*
	 * @GetMapping("/points/event/community-visit") public String
	 * communityVisitOnce() { int testUserId = 1; try {
	 * eventService.claimDaily(testUserId, "COMMUNITY_VISIT", 5, "커뮤니티 방문"); } catch
	 * (EventService.AlreadyClaimedTodayException ignored) {} return
	 * "redirect:/community"; }
	 */
    
    
}

