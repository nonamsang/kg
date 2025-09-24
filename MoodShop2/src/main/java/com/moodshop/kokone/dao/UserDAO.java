package com.moodshop.kokone.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moodshop.kokone.vo.UserVO;

public interface UserDAO {

ArrayList<UserVO> getAllUser();

UserVO getUserVO(String user_id);

int checkUserId(String user_id);

int checkUserNick(String nickname);

void insertUser(UserVO uservo);

UserVO findUserId(@Param("name")String name, @Param("tel")String tel);

UserVO findUserPw(@Param("name")String name, @Param("tel")String tel, @Param("user_id")String user_id);

void updateUserPw(@Param("user_id")String user_id, @Param("newPassword")String newPassword);

// 회원 정보 수정
void updateUser(UserVO user);

// 회원 삭제
void deleteUser(String user_id);

// 아래가 김동주가 추가한 것
List<UserVO> getUserInfoByUserId(String user_id);
}
