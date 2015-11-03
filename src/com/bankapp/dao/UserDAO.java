
/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserAccountExist;
import com.bankapp.userexceptions.UserNameExists;

/**
 * @author manikandan_eshwar
 *
 */
public interface UserDAO {
	public void insert(UserInfo user);
	public List<UserInfo> getExternalUserList();
	public UserInfo findUserByUsername(String username);
	public Long registerNewUserAccount(UserInfo userInfo, Useraccounts account) throws UserAccountExist, UserNameExists, CustomException;
	public String getUserRole(String username);
	public void updateUserInfo(UserInfo userInfo);
	public void deleteUserInfo(UserInfo userInfo);
	public UserInfo getUserAndAccuntInfobyUserName(String userName);
	public void updateInternalUserInfo(UserInfo userInfo);
	public void registerNewInternalUser(UserInfo userInfo, String role) throws CustomException, UserNameExists;
	public boolean checkAccountExists(Long accountid);
	public boolean isFirstLogin(String name);
	public void updateLoginTable(String encode, String userName);
	public void insertPubKey(String userName, String pubKey);
	public String getPubKey(String userName);
}