package com.fintecho.littleforest.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintecho.littleforest.service.admin.AdminStatsService;
import com.fintecho.littleforest.vo.admin.AdminCountsVO;

@RestController
public class AdminStatsApiController {

	private final AdminStatsService service;

	public AdminStatsApiController(AdminStatsService service) {
		this.service = service;
	}

	@GetMapping("/admin/stats/counts")
	public AdminCountsVO getCounts() {
		return service.getCounts();
	}
}
