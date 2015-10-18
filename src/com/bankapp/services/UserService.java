package com.bankapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bankapp.model.UserInfo;

@Service("userService")
public interface UserService {
	
	public void insertData(UserInfo user);
	 public List<UserInfo> getUserList(); 
	 public int addNewExternalUuser(UserInfo userInfo,String role);
}