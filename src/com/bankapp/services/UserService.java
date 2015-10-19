package com.bankapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bankapp.model.UserInfo;

@Service("userService")
public interface UserService {
	
	public void insertData(UserInfo user);
	 public List<UserInfo> getUserList(); 
	 public Long addNewExternalUuser(UserInfo userInfo,String role,String accountType);
	public String getUserRoleType(String username);
	public UserInfo getUserInfobyUserName(String userName);
	public void updateUserInfo(UserInfo ui);
	public void deleteUserInfo(UserInfo ui);
}