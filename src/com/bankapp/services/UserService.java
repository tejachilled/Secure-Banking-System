package com.bankapp.services;

import java.util.List;

import com.bankapp.model.UserModel;

public interface UserService {
	public void insertData(UserModel user);
	 public List<UserModel> getUserList();  
}