package com.fintecho.littleforest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fintecho.littleforest.mapper.MerchantMapper;
import com.fintecho.littleforest.mapper.ProductMapper;
import com.fintecho.littleforest.mapper.WalletMapper;
import com.fintecho.littleforest.vo.ProductVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ShopController {

	@Autowired
	ProductMapper productmapper;
	@Autowired
	MerchantMapper merchantmapper;
	@Autowired
	WalletMapper walletmapper;

	@GetMapping("/shop")
	public String product(Model model) {

		List<ProductVO> products = productmapper.getAllProductByMerchantId(4);

		model.addAttribute("products", products);

		return "shop/shopmain";
	}
}