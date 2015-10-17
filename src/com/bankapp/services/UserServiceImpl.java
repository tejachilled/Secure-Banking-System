/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.dao.UserDAO;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;

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
	public List<UserInfo> getUserList() {
		// TODO Auto-generated method stub
		return userdao.getUserList();
	}

	@Override
	public int addNewExternalUuser(UserInfo UserInfo,String role)
	{
		Useraccounts account=new Useraccounts();
		account.setBalance(500.0);
		account.setUsername(UserInfo.getUserName());
		UserInfo.setRole(role);
		return userdao.registerNewUserAccount(UserInfo,account);
	}	

}
