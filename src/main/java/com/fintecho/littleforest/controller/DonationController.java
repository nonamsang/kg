package com.fintecho.littleforest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.DonationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {
	
	 private final DonationService donationService;
	 
	 @PostMapping("/point")
	 @ResponseBody
	 public ResponseEntity<?> donateByPoint(
	            // 로그인 세션/시큐리티 연동 전까지 테스트용 파라미터/하드코딩
	            @RequestParam int amount,
	            @RequestParam(required = false) String description) {

	        int testUserId = 1; // 로그인 유저로 교체
	        try {
	            int newBalance = donationService.donateByPoint(testUserId, amount, description);
	            return ResponseEntity.ok(Map.of("ok", true, "newBalance", newBalance));
	        } catch (IllegalArgumentException e) {
	            String code = e.getMessage();
	            String msg = switch (code) {
	                case "MIN_AMOUNT"   -> "1,000원 이상부터 기부가 가능합니다.";
	                case "INSUFFICIENT" -> "보유 포인트가 부족합니다.";
	                default             -> "요청이 올바르지 않습니다.";
	            };
	            return ResponseEntity.badRequest().body(msg);
	        } catch (Exception e) {
	        	System.out.println(e);
	            return ResponseEntity.internalServerError().body(e);
	        }
	    }
	}