/**
 * 
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bankapp.model.UserModel;

/**
 * @author manikandan_eshwar
 *
 */
public class UserExtractor implements ResultSetExtractor<UserModel> {

	 public UserModel extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException {
	  
	  UserModel user = new UserModel();
	  
	  user.setUserID(resultSet.getInt(1));
	  user.setFirstName(resultSet.getString(2));
	  user.setLastName(resultSet.getString(3));

	  return user;
	 }

	}


