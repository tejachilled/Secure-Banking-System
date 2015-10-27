
package com.bankapp.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPDAOImpl implements OTPDAO {

	@Autowired
	DataSource dataSource;

	public void sendNewRequest(String externalUserName, String internalUserName) {
		String query = "INSERT INTO tbl_authorizations_government (external_username, internal_username) VALUES (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, new Object[] { externalUserName, internalUserName });
		jdbcTemplate.execute(query);
	}

	@Override
	public void storeOTP(String userName, String otp) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String selectQuery = "SELECT COUNT(username) FROM tbl_otp WHERE username = ?";
		int count = jdbcTemplate.queryForObject(selectQuery, new Object[] { userName }, Integer.class);
		if (count == 0) {
			String query = "INSERT INTO tbl_otp (username, otp) VALUES (?,?)";
			jdbcTemplate.update(query, new Object[] { userName, otp });
			jdbcTemplate.execute(query);
		} else {
			String query = "UPDATE tbl_otp SET otp = ? WHERE username = ?";
			jdbcTemplate.update(query, new Object[] { otp, userName });
			jdbcTemplate.execute(query);
		}
	}

}