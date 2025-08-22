package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.UserVO;

@Mapper
public interface UserMapper {
//해당 사원번호의 컬럼 전체 조회
	UserVO getinfrom(int user_Id);

//비료구매할때 포인트 차감용 업데이트
	void updatepoint(@Param("totalprice") int totalprice, @Param("user_Id") int user_Id);

	void update_100(@Param("user_Id") int user_Id);

//로그인 //닉네임으로 사용자 한 명 정보 가져오기
	UserVO findByOauthID(@Param("oauth_id") String oauth_id);

	int checkEmailDupl(String email);

	// 회원가입
	void insertUser(UserVO user);

	UserVO getInfo(int id); // 회원정보 조회
	// 포인트 락 조회

	Integer selectPointForUpdate(int id);

	// 포인트 차감(보유 초과 방지)
	int deductPoint(int id, int amount);

	// 포인트 증가(추가)
	int addPoint(int id, int amount);

	/*
	 * UserVO getInfo(@Param("id") int id); Integer
	 * selectPointForUpdate(@Param("id") int id); int deductPoint(@Param("id") int
	 * id, @Param("amount") int amount);
	 */

}
