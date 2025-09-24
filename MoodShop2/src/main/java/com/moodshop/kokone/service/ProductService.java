package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;

public interface ProductService{
ArrayList<ProductVO> getAllLastProduct();

ProductVO SelectedProduct(String Product_id);

List<Map<String, Object>> getWishChartData(String managerId);

List<ProductVO> getAllProductList();

// 이제 부터 관리자 백업(관리자는 앞에 대문자 M.표시
// �ش� ȸ���� ��ǰ ��� ��ȸ(������)
ArrayList<ProductVO> MSelectProduct(String company_name);

// ��ǰ ��� ������ ��
List<ProductVO> MSelectProducts(@Param("company_name") String company_name,
		@Param("product_id_list") List<String> product_idList);

// ������ ��ǰ ��� ����(������)
int MUpdateProduct(List<ProductVO> productList);

// ������ ��ǰ ��� ����(������)
int MDeleteProduct(String company_name, List<String> product_id_list);

// �ش��ϴ� ��ǰ�� ��ǰ �ɼ��� ���� �������� ��
ArrayList<ProductOptionVO> MSelectProductD(List<String> product_id);

// ��ǰ ������ ���(������)
void MInsertProduct(ProductVO insertvo);

// ��ǰ ����� �̹��� ���(������)
void MUpdateImage(ProductVO imagevo);

void MInsertProductD(ProductOptionVO insertvo);

// 상품옵션 하나만 수정
void MUpdateProductD(ProductOptionVO updatevo);

// 상품옵션 하나만 삭제
int MDeleteProductD(ProductOptionVO deletevo);

// 상품 옵션 한가지만 선택
ProductOptionVO MSelectProductDOne(@Param("option_id") String option_id, @Param("product_id") String product_id);

// 상품중 하나 선택(관리자)
ProductVO MselectProDetail(@Param("company_name") String company_name, @Param("product_id") String product_id);

// 해당하는 상품아이디의 상품 옵션 전체 출력
ArrayList<ProductOptionVO> MSelectProductDAll(String product_id);

// 상품 무드기준으로 조회
ArrayList<ProductVO> MAlinProductM(String company_name);

// 상품 카테고리기준으로 조회
ArrayList<ProductVO> MAlinProductC(String company_name);
}