package com.moodshop.kokone.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.moodshop.kokone.vo.ProductOptionVO;

@Mapper
@Repository("productOptionDAO")
public interface ProductOptionDAO {

	ProductOptionVO getProductOptionByIdOption(@Param("product_id") String product_id,
			@Param("option_color") String option_color);

	ProductOptionVO getProductionOptionByIdAllOption(String product_id, String product_id2);

	ProductOptionVO getProductionOptionByIdAllOption(String product_id, String product_id2, String option_size);

	ProductOptionVO getProductOptionByIdOptionSize(String product_id, String option_size);

	ProductOptionVO getProductOptionByIdBasket(@Param("product_id") String product_id, @Param("option_color") String option_color);

	// 이제 부터 관리자 백업
	ArrayList<ProductOptionVO> MSelectProductD(List<String> product_id);

	void MDeleteProductDAll(List<String> product_id_list);

	void MInsertProductD(ProductOptionVO insertvo);

	void MUpdateProductD(ProductOptionVO updatevo);

	ProductOptionVO MSelectProductDOne(@Param("option_id") String option_id, @Param("product_id") String product_id);

	ArrayList<ProductOptionVO> MSelectProductDAll(String product_id);

	int MDeleteProductD(ProductOptionVO deletevo);
}
