package com.fintecho.littleforest.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fintecho.littleforest.service.GrowingTreeService;
import com.fintecho.littleforest.service.GrownTreeService;
import com.fintecho.littleforest.vo.GrowingTreeVO;
import com.fintecho.littleforest.vo.GrownTreeVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class GrownTreeController {

	@Autowired
	private GrownTreeService grownTreeService;
	
	@Autowired
	private GrowingTreeService growingTreeService;
	
	@GetMapping("/grown")
	public String growntree(ArrayList<GrownTreeVO> vo,Model model, HttpSession session) {
		int user_Id=5;
		//int userId = (Integer) session.getAttribute("userId");
		vo=grownTreeService.grownSelect(user_Id);
		model.addAttribute("tree", vo);
		return "grown";
		
	}
	@GetMapping("/more")
	public String more(GrownTreeVO grownvo,HttpSession session) {
		int user_Id=4;
		GrowingTreeVO treeVO=growingTreeService.getAllStock(user_Id);
		int growing_Tree_Id=treeVO.getId();
		grownvo.setUser_Id(user_Id);
		grownvo.setGrowing_Tree_Id(growing_Tree_Id);
		
		grownTreeService.grownInsert(grownvo);
		return "redirect:/grown";
		
		
	}
}
