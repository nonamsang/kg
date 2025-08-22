package com.fintecho.littleforest.service;

import com.fintecho.littleforest.vo.UserVO;

public interface UserService {

	UserVO getinform(int user_Id);

// 닉네임으로 회원 조회 (로그인)
	UserVO findByOauthID(String oauth_id);

// 회원가입
	void joinUser(UserVO user);

// 이메일 중복 체크
	boolean isEmailDuplicate(String email);

// Oauth ID 중복 체크
	boolean isOauthIdDuplicate(String oauthId);
	
	UserVO getInfo(int id);

}
