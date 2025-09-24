package com.moodshop.kokone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.Company1DAO;
import com.moodshop.kokone.dao.ProductDAO;
import com.moodshop.kokone.dao.ProductOptionDAO;
import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;

@Service("mainService")
public class MainMoodShopServiceImpl implements MainMoodShopService {
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductOptionDAO productOptionDAO;

	@Autowired
	private Company1DAO company1DAO;

	@Override
	public ProductVO CategoryMood(String product_category, String product_mood) {

		return productDAO.selectByCategoryAndMood(product_category, product_mood);
	}

	@Override
	public ProductVO Category(String product_category) {

		return productDAO.selectByCategory(product_category);
	}

	@Override
	public List<ProductVO> MainGetProducts() {
		return productDAO.selectAll();
	}

	@Override
	public List<ProductVO> HeaderProductCategory(String product_category) {
		// TODO Auto-generated method stub
		return productDAO.selectByCategory2(product_category);
	}

	@Override
	public List<ProductVO> HeaderProductMood(String product_category, String product_mood) {
		// TODO Auto-generated method stub
		return productDAO.selectByCategoryMood(product_category, product_mood);
	}

	@Override
	public List<ProductVO> getProductById(String product_id) {
		// TODO Auto-generated method stub
		return productDAO.getProductById(product_id);
	}

	@Override
	public List<ProductVO> RatingSelect(String Product_id) {
		// TODO Auto-generated method stub
		return productDAO.RatingSelect(Product_id);
	}

	@Override
	public List<ProductOptionVO> getProductOptionById(String product_id) {
		// TODO Auto-generated method stub
		return productDAO.getProductOptionById(product_id);
	}


	@Override
	public List<ProductVO> getProductBySearched(String search) {
		// TODO Auto-generated method stub
		return productDAO.getProductBySearched(search);
	}

	@Override
	public ProductOptionVO getProductOptionByIdOption(String product_id, String option_color) {
		// TODO Auto-generated method stub
		return productOptionDAO.getProductOptionByIdOption(product_id, option_color);
	}

	@Override
	public ProductOptionVO getProductionOptionByIdAllOption(String product_id, String option_color,
			String option_size) {
		// TODO Auto-generated method stub
		return productOptionDAO.getProductionOptionByIdAllOption(product_id, product_id, option_size);
	}

	@Override
	public ProductOptionVO getProductOptionByIdOptionSize(String product_id, String option_size) {
		// TODO Auto-generated method stub
		return productOptionDAO.getProductOptionByIdOptionSize(product_id, option_size);

	}

	@Override
	public ProductOptionVO getProductOptionByIdBasket(String product_id, String option_color) {
		// TODO Auto-generated method stub
		return productOptionDAO.getProductOptionByIdBasket(product_id, option_color);
	}

	@Override
	public String get_companyid_by_managerid(String managerId) {
		// TODO Auto-generated method stub
		return company1DAO.get_companyid_by_managerid(managerId);
	}


	


}
