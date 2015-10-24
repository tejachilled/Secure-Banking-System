/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
