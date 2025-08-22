package com.fintecho.littleforest.controller.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fintecho.littleforest.dto.ChatRoomDTO;
import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.mapper.admin.AdminChatMapper;
import com.fintecho.littleforest.mapper.admin.AdminChatMessageMapper;
import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.admin.ChatMessageVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatPageController {
	
	@Autowired
	private AdminChatMapper adminchatmapper;
	@Autowired
	private AdminChatMessageMapper adminchatmessagemapper;
	@Autowired
	private UserMapper usermapper;
	
	@GetMapping("/admin/chat")
	public String page(@RequestParam(value = "roomId", required = false, defaultValue = "0")
			Integer roomId,HttpSession session, Model model) {
		
		if(session.getAttribute("user_Id") == null) {
			session.setAttribute("user_Id", 1);
		}
				
		int id = (int) session.getAttribute("user_Id");
		System.out.println("id=" + id);
		
		ArrayList<ChatRoomDTO> chatroomdto = new ArrayList<>();
		ArrayList<ChatMessageVO> chatmessagevo = new ArrayList<>();
		UserVO otheruservo = new UserVO();
		
		chatroomdto = adminchatmapper.getChatRoomDTO(id);
		System.out.println("asd");
		System.out.println(chatroomdto.get(0).getId());
		if(roomId == 0) {
			chatmessagevo = adminchatmessagemapper.getAllChatMessage(chatroomdto.get(0).getId());
		}
		else {
			chatmessagevo = adminchatmessagemapper.getAllChatMessage(roomId);
		}
		
		
		model.addAttribute("profile_Path", "/img/" + otheruservo.getProfile_Photo());
		model.addAttribute("chatroom", chatroomdto);
		model.addAttribute("chat", chatmessagevo);
		
		model.addAttribute("title", "관리자 채팅");
		model.addAttribute("page", "chat"); // 사이드바 active에 쓰면 좋음
		return "admin/chat";
	}	
	
}
