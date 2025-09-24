
//김동주 작성 + Company1Mapper.xml 추가
package com.moodshop.kokone.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.moodshop.kokone.vo.Company1VO;

@Mapper
public interface Company1DAO {
	// 여기 수정
	List<Company1VO> getCompanyByManagerId(String managerId);

	// 김동주 추가
	List<Map<String, Object>> selectCompanyListByManagerId(String managerId);

	String get_companyid_by_managerid(String managerId);
}
