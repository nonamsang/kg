package com.fintecho.littleforest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminStatsPageController {

	@GetMapping("/admin/stats")
	public String statsPage(Model model) {
		model.addAttribute("title", "회원통계");
		model.addAttribute("page", "stats");
		return "admin/stats";
	}
}
