package com.moodshop.kokone.controller;

//ProductAPIController.java
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodshop.kokone.service.ProductService;
import com.moodshop.kokone.vo.ProductAPIVO;
import com.moodshop.kokone.vo.ProductVO;

@Controller
public class ProductAPIController {
	
	@Resource(name = "ProductService")
	private ProductService productService;

    // 상품 페이지 이동
    @RequestMapping("apiProduct.do")
    public String apiProductPage() {
        return "apiProduct";
    }
    @RequestMapping("getProductList.do")
    public void getProductList(HttpServletResponse response) throws IOException {
        List<ProductVO> productList = productService.getAllProductList();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(productList);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResult);
    }

    // API 데이터 호출
    @RequestMapping("getAPIProductList.do")
    public void getAPIProductList(HttpServletResponse response) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.escuelajs.co/api/v1/products";
        String result = restTemplate.getForObject(apiUrl, String.class);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(result);
    }

    // 상품 상세 페이지
    @RequestMapping("productDetail.do")
    public String productDetailPage(@RequestParam("productId") int productId, Model model) {
        String apiUrl = "https://api.escuelajs.co/api/v1/products/" + productId;
        RestTemplate restTemplate = new RestTemplate();
        ProductAPIVO product = restTemplate.getForObject(apiUrl, ProductAPIVO.class);
        model.addAttribute("product", product);
        return "productDetail";
    }
}

