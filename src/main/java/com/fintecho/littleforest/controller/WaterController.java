package com.fintecho.littleforest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fintecho.littleforest.service.GrowingTreeService;
import com.fintecho.littleforest.vo.GrowingTreeVO;

import jakarta.servlet.http.HttpSession;

@RestController
public class WaterController {

	@Autowired
	GrowingTreeService growingTreeService;
	    @GetMapping("/water")
	    public ResponseEntity<String> water0(){
	    	int user_Id=5;
	    	GrowingTreeVO treevo=growingTreeService.getAllStock(user_Id);
	    	int stock=treevo.getWater_Stock();
	    	if(stock==0) {
	    		return ResponseEntity.status(400).body("보유 물이 없습니다");
	    	}
	    	return ResponseEntity.ok("성공");
	    }
	    @PostMapping("/water2")
	    public ResponseEntity<Map<String,Object>> water(@RequestBody WaterRequest request,HttpSession session){
	    	//int user_seq=(int) session.getAttribute("user_seq"); 
	    	try {
	    	int user_Id=5;//임시 하드코딩
	    	GrowingTreeVO treevo=growingTreeService.getAllStock(user_Id);
	    	int count=treevo.getWater_Count();
	    	int tree_Level=treevo.getTree_Level();
	    	treevo.setWater_Count((count)+1);
	    	growingTreeService.updatewater(user_Id);
	    	growingTreeService.updatelevel(tree_Level,user_Id);
	    	GrowingTreeVO reload=growingTreeService.getAllStock(user_Id);
	    	Map<String, Object> response=new HashMap<>();
	    	response.put("exp",reload.getWater_Count());
	    	response.put("treeLevel", reload.getTree_Level());
	    	return ResponseEntity.ok(response);
	    	}catch(Exception e) {
	    		Map<String, Object> error=new HashMap<>();
	    		error.put("error", e.getMessage());
	    		return ResponseEntity.status(500).body(error);
	    	}
	    }
	    public static class WaterRequest{
	    	private int water_Stock;

			public int getWater_Stock() {
				return water_Stock;
			}

			public void setWater_Stock(int water_Stock) {
				this.water_Stock = water_Stock;
			}
	    }
}


