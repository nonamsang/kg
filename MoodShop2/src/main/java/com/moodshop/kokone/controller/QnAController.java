package com.moodshop.kokone.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodshop.kokone.service.LoginService;
import com.moodshop.kokone.service.QnABoardService;
import com.moodshop.kokone.vo.AnswerVO;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.QnABoardVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class QnAController {

	@Resource(name = "QnABoardService")
	private QnABoardService qnaboardService;

	@Resource(name = "loginService")
	private LoginService loginService;

	@RequestMapping("gotoQnA.do")
	public String gotoQnA() {
		return "redirect:/qnaAllList.do";

	}
	
	@RequestMapping("gotoMyQnA.do")
	public String gotoMyQnA() {
		return "redirect:/myQnaList.do";

	}

	@RequestMapping("qnaAllList.do")
	public String Alllist(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		int perPage = 10;
		List<QnABoardVO> qnaallList = qnaboardService.getQnaAllList();

		int totalCount = qnaallList.size();
		int totalPage = (int) Math.ceil((double) totalCount / perPage);

		int start = (page - 1) * perPage;
		int end = Math.min(start + perPage, totalCount);
		List<QnABoardVO> pagedList = qnaallList.subList(start, end);

		// ✅ 답변 여부 Map 생성
		Map<String, Boolean> answerMap = new HashMap<>();
		for (QnABoardVO qna : pagedList) {
			String qId = qna.getQId();
			boolean hasAnswer = qnaboardService.hasAnswer(qId);
			answerMap.put(qId, hasAnswer);
		}

		// ✅ 모델에 추가
		model.addAttribute("qnaAllList", pagedList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("answerMap", answerMap); // ✅ 이 줄이 없으면 JSP에서 못 씀!

		return "QnAList";
	}

	@RequestMapping("MainQandA.do")
	public String goToMyQna(HttpSession session) {
		// 세션에서 로그인된 사용자 정보 확인
		UserVO user = (UserVO) session.getAttribute("userVO");
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");

		// 사용자 또는 관리자가 로그인 되어 있는지 확인
		if (user != null || manager != null) {
			return "redirect:myQnaList.do?page=1"; // 사용자든 뭐든 일단 세션 자체를 넘기도록 설정
		}

		// 로그인되지 않은 상태일 경우 로그인 페이지로 리다이렉트
		return "redirect:MyLogin.do";
	}

	@RequestMapping("myQnaList.do")
	public String myQnaList(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
	    UserVO user = (UserVO) session.getAttribute("userVO");
	    ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");

	    List<QnABoardVO> qnaList = new ArrayList<>();
	    int totalPage = 1;
	    String userType = "";

	    if (user != null) {
	        String userId = user.getUser_id();
	        qnaList = qnaboardService.getUserQnaList(userId, page); // 해당 유저의 질문만
	        totalPage = qnaboardService.getTotalPagesForUser(userId);
	        userType = "user";
	    } else if (manager != null) {
	        String managerId = manager.getManager_id();
	        qnaList = qnaboardService.getAdminQnaList(managerId, page); // 해당 관리자의 답변이 있는 질문만
	        totalPage = qnaboardService.getTotalPagesForManager(managerId);
	        userType = "manager";
	    } else {
	        return "redirect:MyLogin.do"; // 로그인 안 했으면 로그인 페이지로
	    }

	    // ✅ 각 질문에 대해 답변 존재 여부 확인
	    Map<String, Boolean> answerMap = new HashMap<>();
	    Map<String, Boolean> editAllowedMap = new HashMap<>(); // 수정 가능 여부 체크

	    for (QnABoardVO qna : qnaList) {
	        if (qna != null && qna.getQId() != null) {
	            String qId = qna.getQId();
	            boolean hasAnswer = qnaboardService.hasAnswer(qId);
	            answerMap.put(qId, hasAnswer); // 답변 여부 확인

	            // 유저의 경우: 답변이 있을 경우 수정 불가
	            if (user != null) {
	                if (hasAnswer) {
	                    editAllowedMap.put(qId, false);  // 답변이 있으면 수정 불가
	                } else {
	                    editAllowedMap.put(qId, true);   // 답변이 없으면 수정 가능
	                }
	            } else if (manager != null) {
	                // 관리자의 경우: 24시간 경과 여부로 수정 가능 여부 판단
	                if (hasAnswer) {
	                    List<AnswerVO> answers = qnaboardService.getAnswersByQnaId(qId);
	                    AnswerVO answer = answers.get(0); // 답변 고려
	                    java.util.Date answerDate = answer.getaDate(); // Date 타입

	                    if (answerDate != null) {
	                        long nowMillis = System.currentTimeMillis();
	                        long answerMillis = answerDate.getTime();
	                        long diffMillis = nowMillis - answerMillis;
	                        long diffHours = diffMillis / (1000 * 60 * 60); // ms → 시간

	                        if (diffHours >= 24) {
	                            editAllowedMap.put(qId, false);  // 24시간 경과 시 수정 불가
	                        } else {
	                            editAllowedMap.put(qId, true);  // 수정 가능
	                        }
	                    }
	                }
	            }
	        }
	    }

	    model.addAttribute("qnaAllList", qnaList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPage", totalPage);
	    model.addAttribute("userType", userType);
	    model.addAttribute("answerMap", answerMap); // 답변 존재 여부 전달
	    model.addAttribute("editAllowedMap", editAllowedMap); // 수정 가능 여부 전달

	    return "MyQnAList";
	}





	@RequestMapping("MainLetter.do")
	public String handleQnaModify(@RequestParam("id") String qId,
	            @RequestParam(value = "action", defaultValue = "view") String action,
	            @RequestParam(value = "from", defaultValue = "list") String from, HttpSession session, Model model) {

	    // 세션에서 사용자 정보 가져오기
	    UserVO user = (UserVO) session.getAttribute("userVO");
	    ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
	    
	    if (user == null && manager == null) {
	        return "redirect:MyLogin.do";
	    }
	    
	    QnABoardVO qna = qnaboardService.getQnaById(qId);  // 질문 정보 가져오기
	    List<AnswerVO> answers = qnaboardService.getAnswersByQnaId(qId);  // 해당 질문에 대한 답변 가져오기
	    boolean hasAnswer = !answers.isEmpty();  // 답변이 있는지 여부 확인
	    
	    // 현재 접속자의 ID
	    String currentUserId = (user != null) ? user.getUser_id() : manager.getManager_id();

	    model.addAttribute("qna", qna);
	    model.addAttribute("answers", answers);
	    model.addAttribute("hasAnswer", hasAnswer);

	    // 수정 요청인 경우
	    if ("modify".equals(from)) {
	        if (user != null) {
	            // 일반 사용자는 자신의 질문만 수정 가능
	            if (!qna.getUserId().equals(currentUserId)) {
	                return "redirect:/accessDenied";
	            }
	            model.addAttribute("role", "USER");
	            return "QnaModifyForm";
	        }

	        if (manager != null) {
	            // 관리자는 답변이 있어야만 수정 가능
	            if (!hasAnswer) {
	                return "redirect:/accessDenied";
	            }
	            model.addAttribute("role", "ADMIN");
	            model.addAttribute("answer", answers.get(0)); // 첫 번째 답변
	            return "QnaModifyForm";
	        }
	    }

	    // 일반 조회 요청
	    if (manager != null && !hasAnswer) {
	        // 관리자 + 미답변: 답변 작성 페이지로 이동
	        model.addAttribute("role", "ADMIN");
	        return "WriteA";
	    }

	    // 기본: 질문 보기
	    return "QnaView";
	}



	@RequestMapping(value = "QnaModify.do", method = RequestMethod.POST)
	public String handleQnaModifySubmit(QnABoardVO qna, HttpSession session) {
		// 로그인 확인 (보안용)
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do";
		}

		// 기존 글의 작성자와 현재 로그인 사용자가 일치하는지 확인 (추가 보안)
		QnABoardVO original = qnaboardService.getQnaById(qna.getqId());
		if (!user.getUser_id().equals(original.getUserId())) {
			return "redirect:/accessDenied";
		}

		// 수정 처리
		qnaboardService.modifyQna(qna);

		// 성공 후 상세보기로 리다이렉트
		return "redirect:myQnaList.do";
	}
	
	@RequestMapping("MainWrite.do")
	public String goWriteForm(HttpSession session) {
	    UserVO user = (UserVO) session.getAttribute("userVO");

	    if (user == null) {
	        return "redirect:/accessDenied";  // 사용자만 작성 가능
	    }

	    return "WriteQ"; // 질문 작성 폼으로 이동
	}
	@RequestMapping("QWriteCom.do")
	public String handleQuestionWrite(HttpSession session,
	                                  @RequestParam("qTitle") String qTitle,
	                                  @RequestParam("qContent") String qContent) {

	    UserVO user = (UserVO) session.getAttribute("userVO");
	    if (user == null) {
	        return "redirect:/accessDenied";
	    }

	    QnABoardVO Qna = new QnABoardVO();
	    Qna.setUserId(user.getUser_id());
	    Qna.setQTitle(qTitle);
	    Qna.setQContent(qContent);
	    Qna.setQDate(new Date()); // java.util.Date 또는 LocalDateTime 사용

	    qnaboardService.insertQna(Qna); // 서비스 통해 저장

	    return "redirect:/myQnaList.do"; // 목록 페이지로 이동
	}
	@RequestMapping(value = "AnswerWrite.do", method = RequestMethod.POST)
	public String handleAnswerWrite(HttpSession session,
	                                @RequestParam("qId") String qId,
	                                @RequestParam("aTitle") String aTitle,
	                                @RequestParam("aContent") String aContent) {

	    ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
	    if (manager == null) {
	        return "redirect:/accessDenied";
	    }

	    // AnswerVO 객체 생성 및 데이터 설정
	    AnswerVO answer = new AnswerVO();
	    answer.setQId(qId);
	    answer.setaTitle(aTitle);
	    answer.setaContent(aContent);
	    answer.setManagerId(manager.getManager_id());  // 작성자 ID 저장

	    // 답변 저장
	    qnaboardService.insertAnswer(answer);

	    // 작성 후 Q&A 목록 페이지로 리다이렉트
	    return "redirect:/qnaAllList.do";
	}
}
