package com.moodshop.kokone.service;

import java.util.ArrayList;

import com.moodshop.kokone.vo.UserVO;

public interface UserService {
	ArrayList<UserVO> getAllUser(); //회원 전체 조회 
	

    // 회원 1명 조회
    UserVO getUserVO(String user_id);

    // 회원 정보 수정
    void updateUser(UserVO user);

    // 회원 삭제
    void deleteUser(String user_id);
}
