package com.bankapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bankapp.model.UserInfo;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserAccountExist;
import com.bankapp.userexceptions.UserNameExists;

@Service("userService")
public interface UserService {

	public void insertData(UserInfo user);
	public List<UserInfo> getExternalUserList(); 
	public Long addNewExternalUuser(UserInfo userInfo,String role,String accountType) throws UserAccountExist, UserNameExists, CustomException;

	public UserInfo getUserInfobyUserName(String userName);
	public void updateUserInfo(UserInfo ui);
	public void deleteUserInfo(UserInfo ui);
	public UserInfo getUserAndAccuntInfobyUserName(String userName);
	public String getUserRoleType(String username);
	public boolean checkAccountExists(Long accountid);
}