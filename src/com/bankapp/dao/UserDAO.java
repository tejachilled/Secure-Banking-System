/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.UserInfo;

/**
 * @author manikandan_eshwar
 *
 */
public interface UserDAO {
	public void insert(UserInfo user);
	public List<UserInfo> getUserList();
	public UserInfo findUserByUsername(String username);
}
