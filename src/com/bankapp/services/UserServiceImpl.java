/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.dao.UserDAO;
import com.bankapp.model.UserInfo;

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

}
