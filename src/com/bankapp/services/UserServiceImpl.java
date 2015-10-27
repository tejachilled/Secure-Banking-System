/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.dao.UserDAO;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserAccountExist;
import com.bankapp.userexceptions.UserNameExists;

/**
 * @author manikandan_eshwar
 *
 */
public class UserServiceImpl implements UserService {

	@Autowired
	 UserDAO userdao;
	
	@Autowired 
	PasswordEncoder encoder;

	 @Override
	 public void insertData(UserInfo user) {
	  userdao.insert(user);
	 }

	@Override
	public List<UserInfo> getExternalUserList() {
		// TODO Auto-generated method stub
		return userdao.getExternalUserList();
	}

	@Override
	public Long addNewExternalUuser(UserInfo UserInfo,String role,String accountType) throws UserAccountExist, UserNameExists, CustomException
	{
		Useraccounts account=new Useraccounts();
		account.setBalance(500.0);
		account.setUsername(UserInfo.getUserName());
		account.setAccountType(accountType);
		UserInfo.setRole(role);
		return userdao.registerNewUserAccount(UserInfo,account);
	}

	@Override
	public void addNewInternaluser(UserInfo userInfo, String role) throws CustomException, UserNameExists {
		userdao.registerNewInternalUser(userInfo,role);
	}

	@Override
	@Transactional
	public UserInfo getUserInfobyUserName(String username)
	{   
		UserInfo ui = userdao.findUserByUsername(username);
		if(ui != null)
		{ 
			return ui;
		}
		return null;
	}

	@Override
	//update address or emailID of user with given values
		@Transactional
		public void updateUserInfo(UserInfo userInfo)
		{   
		userdao.updateUserInfo(userInfo);
		}
	@Override
	@Transactional
	public void updateInternalUserInfo(UserInfo ui) {
		// TODO Auto-generated method stub
		userdao.updateInternalUserInfo(ui);
	}

	@Override
	@Transactional
	public void deleteUserInfo(UserInfo userInfo)
	{   
		userdao.deleteUserInfo(userInfo);
	}

	@Override
	public UserInfo getUserAndAccuntInfobyUserName(String userName) {
		// TODO Auto-generated method stub
		return userdao.getUserAndAccuntInfobyUserName(userName);
	}

	@Override
	public String getUserRoleType(String username) {
		// TODO Auto-generated method stub
		return userdao.getUserRole(username);
	}

	@Override
	public String setRoleToDisplayUI(String role) {
		if(role.contains("SA")){
			role = "Administrator";
		}else if(role.contains("RE")){
			role = "Regular employee";
		}else if(role.contains("SM")){
			role = "System Manager";
		}else if(role.contains("U")){
			role = "Customer";
		}else if(role.contains("M")){
			role  = "Merchant";
		}else{
			role =  "******";
		}
		System.out.println("Role to display : "+role);
		return role;
	}
	public boolean checkAccountExists(Long accountid){
		return userdao.checkAccountExists(accountid);
	}

	@Override
	public boolean isFirstLogin(String name) {
		// TODO Auto-generated method stub
		return userdao.isFirstLogin(name);
	}

	@Override
	public void changePassword(String confirmPassword, String userName) {
		// TODO Auto-generated method stub
		userdao.updateLoginTable(encoder.encode(confirmPassword),userName);
	}



}
