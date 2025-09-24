package com.moodshop.kokone.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.UserDAO;
import com.moodshop.kokone.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name = "userDAO")
	private UserDAO userDAO;
	
	@Override
	public ArrayList<UserVO> getAllUser(){
		return userDAO.getAllUser();
	}

	@Override
    public UserVO getUserVO(String user_id) {
        return userDAO.getUserVO(user_id);
    }

    @Override
    public void updateUser(UserVO user) {
		String rawPassword = user.getPassword();
		String hashedPassword = toSHA256(rawPassword);
		user.setPassword(hashedPassword);
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(String user_id) {
        userDAO.deleteUser(user_id);
    }

	private String toSHA256(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();

			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 암호화 실패", e);
		}
	}

}
