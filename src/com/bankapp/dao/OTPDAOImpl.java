
package com.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPDAOImpl implements OTPDAO {

	private Connection conn = null;
	@Autowired
	DataSource dataSource;

	@Override
	public void storeOTP(String userName, String otp) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String selectQuery = "SELECT COUNT(username) FROM tbl_otp WHERE username = ?";
		try {
			int count = jdbcTemplate.queryForObject(selectQuery, new Object[] { userName }, Integer.class);
			if (count == 0) {
				String query = "INSERT INTO tbl_otp (username, otp) VALUES (?,?)";
				jdbcTemplate.update(query, new Object[] { userName, otp });
				jdbcTemplate.execute(query);
			} else {

				String query = "UPDATE tbl_otp SET otp = ? WHERE username = ?";
				try {
					conn = dataSource.getConnection();
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, otp);
					ps.setString(2, userName);
					ps.executeUpdate();
					ps.close();

				} catch (SQLException e) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}