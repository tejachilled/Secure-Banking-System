package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.bankapp.model.Useraccounts;

public class UseraccountsExtracter {

	
	
	public Useraccounts extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		Useraccounts userAccounts = new Useraccounts();
		userAccounts.setAccountno(resultSet.getLong("account_id"));
		userAccounts.setUsername(resultSet.getString("user_name"));
		userAccounts.setAccountType(resultSet.getString("type"));
		userAccounts.setBalance(resultSet.getDouble("balance"));
		userAccounts.setAccountopendate(resultSet.getDate("account_open_date"));
		return userAccounts;
	}

}
