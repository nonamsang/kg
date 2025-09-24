package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodshop.kokone.dao.Company1DAO;
import com.moodshop.kokone.dao.ProductDAO;
import com.moodshop.kokone.dao.ProductOptionDAO;
import com.moodshop.kokone.dao.WishListDAO;
import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
	//��ǰ DAO
    @Resource(name = "productDAO")
    private ProductDAO productDAO;
	// 김동주 추가 DAO
	@Resource(name = "company1DAO")
	private Company1DAO company1DAO;

	@Autowired
	private WishListDAO wishlistDAO;

	@Resource(name = "productOptionDAO")
	private ProductOptionDAO productOptionDAO;
	
	@Autowired
    private SqlSession sqlSession;

    @Override
    public List<ProductVO> getAllProductList() {
    	return sqlSession.selectList("com.moodshop.kokone.dao.ProductDAO.getAllProductList");

    }
    //��� ��ǰ ��ȸ
    @Override
    public ArrayList<ProductVO> getAllLastProduct() {
        return productDAO.getAllLastProduct();
    }
    //�ϳ��� ��ǰ ����
    @Override
    public ProductVO SelectedProduct(String product_id) {
        return productDAO.SelectedProduct(product_id);
    }
    
	// 김동주 추가
	@Override
	public List<Map<String, Object>> getWishChartData(String managerId) {
		// 1. 회사 목록 조회 (회사 ID + 이름)
		List<Map<String, Object>> companyList = company1DAO.selectCompanyListByManagerId(managerId);

		List<Map<String, Object>> allItems = new ArrayList<>();

		for (Map<String, Object> company : companyList) {
			String companyName = (String) company.get("COMPANY_NAME");

			// 2. 회사별 상품 목록 조회 (상품 ID + 이름)
			List<Map<String, Object>> productList = productDAO.selectProductListByCompanyName(companyName);

			for (Map<String, Object> product : productList) {
				String productId = (String) product.get("PRODUCT_ID");
				String productName = (String) product.get("PRODUCT_NAME");

				// 3. 해당 상품에 대한 찜 수 조회
				int wishCount = wishlistDAO.countWishlistByProductId(productId);

				if (wishCount > 0) {
					Map<String, Object> item = new HashMap<>();
					item.put("label", companyName + " - " + productName);
					item.put("count", wishCount);
					allItems.add(item);
				}
			}
		}

		// 4. count 기준 정렬
		allItems.sort((a, b) -> ((Integer) b.get("count")).compareTo((Integer) a.get("count")));

		// 5. Top 5 + 기타 분리
		List<Map<String, Object>> top5 = new ArrayList<>();
		int etcCount = 0;

		for (int i = 0; i < allItems.size(); i++) {
			if (i < 5) {
				top5.add(allItems.get(i));
			} else {
				etcCount += (Integer) allItems.get(i).get("count");
			}
		}

		if (etcCount > 0) {
			Map<String, Object> etcItem = new HashMap<>();
			etcItem.put("label", "기타 품목");
			etcItem.put("count", etcCount);
			top5.add(etcItem);
		}

		return top5;
	}
	// 관리자 백업(관리자는 대문자 M표시)
	@Override
	public ArrayList<ProductVO> MSelectProduct(String company_id) {
		return productDAO.MSelectProduct(company_id);
	}

	// üũ�ڽ��� ��ǰ �������� ������ �����ϴ� ��(������)
	@Override
	public List<ProductVO> MSelectProducts(String companyId, List<String> productIdList) {
		// TODO Auto-generated method stub
		return productDAO.MSelectProducts(companyId, productIdList);
	}

	// üũ �ڽ� ������ �� ����(������)
	@Override
	@Transactional
	public int MUpdateProduct(List<ProductVO> productList) {
		int count = 0;
		for (ProductVO product : productList) {
			count += productDAO.MUpdateProduct(product);
		}
		return count;
	}

	// üũ �ڽ� ������ �� ����(������)
	@Override
	@Transactional
	public int MDeleteProduct(String company_name, List<String> product_id_list) {
		productOptionDAO.MDeleteProductDAll(product_id_list);

		Map<String, Object> paramMap = new HashMap<>();


		paramMap.put("company_name", company_name);
		paramMap.put("product_id_list", product_id_list);
		productDAO.MDeleteWishlist(product_id_list);
		/* product_OptionDAO.MDeleteProductDAll(Product_id()); */
		return productDAO.MDeleteProduct(paramMap);
	}

	//
	@Override
	public ArrayList<ProductOptionVO> MSelectProductD(List<String> product_id) {
		return productOptionDAO.MSelectProductD(product_id);
	}

	// ��ǰ ���(������)
	@Override
	public void MInsertProduct(ProductVO insertvo) {
		// TODO Auto-generated method stub
		productDAO.MInsertProduct(insertvo);
	}

	// ��ǰ ��� �� �̹����� ����α�(������)
	@Override
	public void MUpdateImage(ProductVO imagevo) {
		productDAO.MUpdateImage(imagevo);

	}

	// 상품 옵션 등록
	@Override
	public void MInsertProductD(ProductOptionVO insertvo) {
		productOptionDAO.MInsertProductD(insertvo);

	}

	// 상품 옵션 수정
	@Override
	public void MUpdateProductD(ProductOptionVO updatevo) {
		productOptionDAO.MUpdateProductD(updatevo);

	}

	// 상품 옵션 하나만 선택
	@Override
	public ProductOptionVO MSelectProductDOne(String option_id, String product_id) {
		// TODO Auto-generated method stub
		return productOptionDAO.MSelectProductDOne(option_id, product_id);
	}

	// 상품중 하나만 선택
	@Override
	public ProductVO MselectProDetail(String compnay_id, String product_id) {
		// TODO Auto-generated method stub
		return productDAO.MselectProDetail(compnay_id, product_id);
	}

	// 해당하는 상품아이디의 상품옵션 전체 출력
	@Override
	public ArrayList<ProductOptionVO> MSelectProductDAll(String product_id) {
		// TODO Auto-generated method stub
		return productOptionDAO.MSelectProductDAll(product_id);
	}

	// 상품 옵션 삭제
	@Override
	public int MDeleteProductD(ProductOptionVO deletevo) {
		// TODO Auto-generated method stub
		return productOptionDAO.MDeleteProductD(deletevo);
	}

	// 상품 MOOD로 정렬
	@Override
	public ArrayList<ProductVO> MAlinProductM(String company_id) {
		// TODO Auto-generated method stub
		return productDAO.MAlinProductM(company_id);
	}

	// 상품 CATEGORY로 정렬
	@Override
	public ArrayList<ProductVO> MAlinProductC(String company_id) {
		// TODO Auto-generated method stub
		return productDAO.MAlinProductC(company_id);
	}
}