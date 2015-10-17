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
		user.setCity(resultSet.getString("city"));
		user.setState(resultSet.getString("state"));
		user.setZipcode(resultSet.getInt("zipcode"));
		user.setRole("ROLE_"+resultSet.getString("role").toUpperCase());
		return user;
	}

}
