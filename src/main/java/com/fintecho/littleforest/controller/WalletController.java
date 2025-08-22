package com.fintecho.littleforest.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fintecho.littleforest.service.PaymentService;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.service.WalletService;
import com.fintecho.littleforest.vo.PaymentVO;
import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.WalletVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
    @Autowired
    UserService userService; 
    
    @Autowired
    PaymentService paymentService; 
	
    @RequestMapping("/wallet")
    public String walletlist(HttpSession session, Model model) {
    	
    		int testUserId =5; //db 테스트용 유저 번호 
    		//로그인한 사용자의 user_seq 기준으로 사용자 정보 조회
    		// int user_Id = loginUser.getUser_id();

    		// 유저 정보 조회 후 model에 추가
        UserVO userInfo = userService.getInfo(testUserId);
        model.addAttribute("userInfo", userInfo);
		
        // 모달에 표시할 보유 포인트
        model.addAttribute("pointBalance", userInfo.getPoint());
		/*
		 * if (userInfo == null) { // 사용자 정보를 찾을 수 없는 경우 메인페이지로 return
		 * "redirect:/mainpage"; }
		 */
   
    		// user_id로 지갑 목록 전체 조회 (전체 계좌 정보)
        List<WalletVO> walletList = walletService.getWalletsByUserId(testUserId);
        model.addAttribute("walletList", walletList);
        
        //총 잔액 계산
        int totalBalance = walletList.stream()
                .mapToInt(WalletVO::getAccount_Balance)
                .sum();
  	    model.addAttribute("totalBalance", totalBalance);
        
        // 통장 인덱스 walletList에서 중복제거하기 위한 Thymeleaf 사용중이라 따로 만들어야함
        // 중복 제거된 은행명 리스트 (탭생성용)
        Set<String> bankNames = walletList.stream()
                .map(WalletVO::getBank_Name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        											//순서유지 
        // Set <String > bankNames =[농협, 기업, 신한]
        model.addAttribute("bankNames", bankNames);
        
     // 첫 탭 기본 선택 (첫 번째 은행명)
		/*
		 * if (!bankNames.isEmpty()) { model.addAttribute("defaultBank",
		 * bankNames.iterator().next()); }
		 */

        model.addAttribute("showPassbook", false);
        
    		return "walletlist";
    } 


    @RequestMapping("/payment")
    public String paymentlist(HttpSession session, Model model) {
        int testUserId = 5;

        // 유저 정보
        UserVO userInfo = userService.getInfo(testUserId);
        model.addAttribute("userInfo", userInfo);

        // 지갑 내역
        List<PaymentVO> paymentList = paymentService.getPaymentListByUserId(testUserId);
       // log.info("결제 내역 수: {}", paymentList.size()); db에 값 없을때 대비
        model.addAttribute("paymentList", paymentList);

        // ⬇ 총잔액 계산 추가 (walletService 이용)
        List<WalletVO> walletList = walletService.getWalletsByUserId(testUserId);
        int totalBalance = walletList.stream()
        		.mapToInt(WalletVO::getAccount_Balance)
        		.sum();
        model.addAttribute("totalBalance", totalBalance);

        return "payment";
    }


}
