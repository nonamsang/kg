package com.moodshop.kokone.dao;

import org.apache.ibatis.annotations.Param;

import com.moodshop.kokone.vo.ManagerVO;

public interface ManagerDAO {

ManagerVO getManagerVO(String manager_id);

int checkManagerId(String manager_id);

int checkManagerNick(String nickname);

void insertAdmin(ManagerVO managervo);

ManagerVO findManagerId(@Param("name")String name, @Param("tel")String tel);

ManagerVO findManagerPw(@Param("name")String name, @Param("tel")String tel, @Param("manager_id")String manager_id);

void updateManagerPw(@Param("manager_id")String manager_id, @Param("newPassword")String newPassword);
}
