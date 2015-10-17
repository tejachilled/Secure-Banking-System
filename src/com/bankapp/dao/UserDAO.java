/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;

/**
 * @author manikandan_eshwar
 *
 */
public interface UserDAO {
	public void insert(UserInfo user);
	public List<UserInfo> getUserList();
	public UserInfo findUserByUsername(String username);
	public int registerNewUserAccount(UserInfo userInfo, Useraccounts account);
}
