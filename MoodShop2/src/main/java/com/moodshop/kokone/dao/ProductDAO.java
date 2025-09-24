package com.moodshop.kokone.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;

public interface ProductDAO {
 ArrayList<ProductVO> getAllLastProduct();
 
 ProductVO SelectedProduct(String Product_id);

	List<ProductVO> RatingSelect(String Product_id);

	ProductVO selectByCategoryAndMood(String product_category, String product_mood);

	ProductVO selectByCategory(String product_category);

	List<ProductVO> selectAll();

	List<ProductVO> selectByCategory2(String product_category);

	List<ProductVO> selectByCategoryMood(@Param("product_category") String product_category,
			@Param("product_mood") String product_mood);

	List<ProductVO> getProductById(String product_id);

	List<ProductOptionVO> getProductOptionById(String product_id);

	List<ProductVO> getProductBySearched(String search);
	
	List<ProductVO> getAllProductList();

	// ----- 김동주가 추가한 것

	List<ProductVO> getProductsByCompanyName(String companyName);

	Integer getStock(Map<String, Object> param);

	void decreaseStock(@Param("productId") String productId, @Param("productColor") String productColor,
			@Param("productCount") int productCount);

	// 김동주가 추가한 것
	List<Map<String, Object>> selectProductListByCompanyName(String companyName);

	// 이제부터 관리자 백업

	// 회사아이디가 일치하는 모든 상품 조회
	ArrayList<ProductVO> MSelectProduct(String company_name);

	// ��ǰ ��� ������ ��
	List<ProductVO> MSelectProducts(@Param("company_name") String company_name,
			@Param("product_id_list") List<String> product_idList);

	// ������ ��ǰ ��� ����(������)
	int MUpdateProduct(ProductVO product);

	// ������ ��ǰ ��� ����(������)
	int MDeleteProduct(Map<String, Object> paramMap);

	// ��ǰ ���(������)
	void MInsertProduct(ProductVO insertvo);

	// ��ǰ ����� �̹����� ���(������)
	void MUpdateImage(ProductVO imagevo);

	ArrayList<ProductVO> MSelectProDet(String company_name, String product_id);

	ProductVO MselectProDetail(@Param("company_name") String company_name, @Param("product_id") String product_id);

	ArrayList<ProductVO> MAlinProductM(String company_name);

	ArrayList<ProductVO> MAlinProductC(String company_name);

	void MDeleteWishlist(List<String> product_id_list);

	String getOptionColorById(String optionId);

}
