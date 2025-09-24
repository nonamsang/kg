package com.moodshop.kokone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moodshop.kokone.service.ImageService;

@Controller
public class ImageController {

	@Autowired
	private ImageService imageService;

	
	/*	@RequestMapping("MainProductName.do")
		public String redirectByProduct(@RequestParam("product_name") String product_name) {
			return product_name;
	
		}*/
	
}
