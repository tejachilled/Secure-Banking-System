/**
 * 
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.model.UserModel;

/**
 * @author manikandan_eshwar
 *
 */
public class UserRowMapper implements RowMapper<UserModel> {

	 @Override
	 public UserModel mapRow(ResultSet resultSet, int line) throws SQLException {
	  UserExtractor userExtractor = new UserExtractor();
	  return userExtractor.extractData(resultSet);
	 }

	}

