package com.moodshop.kokone.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.ManagerDAO;
import com.moodshop.kokone.dao.UserDAO;
import com.moodshop.kokone.util.PasswordUtil;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.UserVO;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Resource(name="managerDAO")
	private ManagerDAO managerDAO;
	
	@Override
	public ArrayList<UserVO> getAllUser() {
		// TODO Auto-generated method stub
		return userDAO.getAllUser();
	}

	@Override
	public UserVO getUserVO(String user_id) {
		// TODO Auto-generated method stub
		return userDAO.getUserVO(user_id);
	}
	
	@Override
	public ManagerVO getManagerVO(String manager_id) {
		return managerDAO.getManagerVO(manager_id);
	}
	
	@Override
	public boolean checkUserId(String user_id) {
		int count = userDAO.checkUserId(user_id);
		return count > 0;
	}
	
	@Override
	public boolean checkManagerId(String manager_id) {
		int count = managerDAO.checkManagerId(manager_id);
		return count > 0;
	}
	
	@Override
	public boolean checkUserNick(String nickname) {
		int count = userDAO.checkUserNick(nickname);
		return count > 0;
	}
	
	@Override
	public boolean checkManagerNick(String nickname) {
		int count = managerDAO.checkManagerNick(nickname);
		return count > 0;
	}
	
	@Override
	public void insertAdmin(ManagerVO managervo) {
		String rawPassword = managervo.getPassword();
		String hashedPassword = PasswordUtil.toSHA256(rawPassword);
		managervo.setPassword(hashedPassword);
		managerDAO.insertAdmin(managervo);
	}
	
	@Override
	public void insertUser(UserVO uservo) {
		String rawPassword = uservo.getPassword();
		String hashedPassword = PasswordUtil.toSHA256(rawPassword);
		uservo.setPassword(hashedPassword);
		userDAO.insertUser(uservo);
	}
	
	@Override
	public UserVO findUserId(String name, String tel) {
		return userDAO.findUserId(name, tel);
	}
	
	@Override
	public ManagerVO findManagerId(String name, String tel) {
		return managerDAO.findManagerId(name, tel);
	}
	
	@Override
	public UserVO findUserPw(String name, String tel, String user_id) {
		return userDAO.findUserPw(name, tel, user_id);
	}
	
	@Override
	public ManagerVO findManagerPw(String name, String tel, String manager_id) {
		return managerDAO.findManagerPw(name, tel, manager_id);
	}
	
	@Override
	public void updateUserPassword(String user_id, String newPassword) {
		newPassword = PasswordUtil.toSHA256(newPassword);
		userDAO.updateUserPw(user_id, newPassword);
	}
	
	@Override
	public void updateManagerPassword(String manager_id, String newPassword) {
		newPassword = PasswordUtil.toSHA256(newPassword);
		managerDAO.updateManagerPw(manager_id, newPassword);
	}
}
