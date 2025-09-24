package com.moodshop.kokone.service;

import java.util.ArrayList;

import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.UserVO;

public interface LoginService {

ArrayList<UserVO> getAllUser();

UserVO getUserVO(String user_id);	

ManagerVO getManagerVO(String manager_id);

boolean checkUserId(String user_id);

boolean checkManagerId(String manager_id);

boolean checkUserNick(String userNickName);

boolean checkManagerNick(String managerNickName);

void insertAdmin(ManagerVO managervo);

void insertUser(UserVO uservo);

UserVO findUserId(String name, String tel);

ManagerVO findManagerId(String name, String tel);

UserVO findUserPw(String name, String tel, String user_id);

ManagerVO findManagerPw(String name, String tel, String manager_id);

void updateUserPassword(String user_id, String newPassword);

void updateManagerPassword(String manager_id, String newPassword);
}
