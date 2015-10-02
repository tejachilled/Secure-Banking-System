/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.UserModel;

/**
 * @author manikandan_eshwar
 *
 */
public interface UserDAO {
	public void insert(UserModel user);
	public List<UserModel> getUserList();
}
