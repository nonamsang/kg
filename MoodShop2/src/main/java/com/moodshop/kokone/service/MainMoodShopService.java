package com.moodshop.kokone.service;

import java.util.List;

import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;

public interface MainMoodShopService {
	ProductVO CategoryMood(String category, String mood);

	ProductVO Category(String category);

	List<ProductVO> MainGetProducts();

	List<ProductVO> HeaderProductCategory(String product_category);

	List<ProductVO> HeaderProductMood(String product_category, String product_mood);

	List<ProductVO> RatingSelect(String Product_id);

	List<ProductVO> getProductById(String product_id);

	List<ProductOptionVO> getProductOptionById(String product_id);

	List<ProductVO> getProductBySearched(String search);

	ProductOptionVO getProductOptionByIdOption(String product_id, String option_color);

	ProductOptionVO getProductionOptionByIdAllOption(String product_id, String option_color, String option_size);

	ProductOptionVO getProductOptionByIdOptionSize(String product_id, String option_size);

	ProductOptionVO getProductOptionByIdBasket(String product_id, String option_color);

	String get_companyid_by_managerid(String managerId);


}
