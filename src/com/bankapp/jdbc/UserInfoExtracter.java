package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.bankapp.model.UserInfo;

public class UserInfoExtracter {

	public UserInfo extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		UserInfo user = new UserInfo();
		user.setUserName(resultSet.getString("user_name"));
		user.setFirstName(resultSet.getString("first_name"));
		user.setLastName(resultSet.getString("last_name"));
		user.setEmaiID(resultSet.getString("email_id"));
		user.setPhoneNumber(resultSet.getLong("phone_number"));
		user.setAddress1(resultSet.getString("add_l1"));
		user.setAddress2(resultSet.getString("add_l2"));
		user.setRole(resultSet.getString("role").toUpperCase());
		user.setSq1(resultSet.getString("sa1"));
		user.setSq2(resultSet.getString("sa2"));
		user.setSq3(resultSet.getString("sa3"));
		return user;
	}

}
