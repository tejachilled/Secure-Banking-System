package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.model.Useraccounts;

public class UseraccountsRowMapper implements RowMapper<Useraccounts> {

	
	@Override
	public Useraccounts mapRow(ResultSet resultSet, int line) throws SQLException {
		UseraccountsExtracter userAccountsExtractor = new UseraccountsExtracter();
		return userAccountsExtractor.extractData(resultSet);
	}

}
