/**
 * 
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.model.UserInfo;

/**
 * @author manikandan_eshwar
 *
 */
public class UserRowMapper implements RowMapper<UserInfo> {

	@Override
	public UserInfo mapRow(ResultSet resultSet, int line) throws SQLException {
		UserInfoExtracter userExtractor = new UserInfoExtracter();
		return userExtractor.extractData(resultSet);
	}

}

