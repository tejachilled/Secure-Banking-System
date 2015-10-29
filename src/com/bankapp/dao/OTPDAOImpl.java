
package com.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bankapp.userexceptions.CustomException;

@Service
public class OTPDAOImpl implements OTPDAO {

	private Connection conn = null;
	@Autowired
	DataSource dataSource;
	static final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

	@Override
	public void storeOTP(String userName, String otp) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String selectQuery = "SELECT COUNT(username) FROM tbl_otp WHERE username = ?";
		Date startDate = new Date(); // current time
		Date endDate = new Date(startDate.getTime() + (10 * ONE_MINUTE_IN_MILLIS)); // current
																					// time+10
																					// mins
		try {
			int count = jdbcTemplate.queryForObject(selectQuery, new Object[] { userName }, Integer.class);
			if (count == 0) {
				String query = "INSERT INTO tbl_otp (username, otp,startDate,endDate) VALUES (?,?,?,?)";
				jdbcTemplate.update(query, new Object[] { userName, otp, startDate.getTime(), endDate.getTime() });
				jdbcTemplate.execute(query);
			} else {
				String query = "UPDATE tbl_otp SET otp = ?, startDate = ?, endDate=? WHERE username = ?";
				try {
					conn = dataSource.getConnection();
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, otp);
					ps.setLong(2, startDate.getTime());
					ps.setLong(3, endDate.getTime());
					ps.setString(4, userName);
					ps.executeUpdate();
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean checkOTP(String userName, String userOTP) throws CustomException {
		String sql = "select * from tbl_otp where username= ?";
		String otp = "";
		long startTime = 0L, endTime = 0L;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				otp = rs.getString("otp");
				startTime = rs.getLong("startDate");
				endTime = rs.getLong("endDate");
			}
			ps.close();

		} catch (SQLException e) {
			throw new CustomException(e.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					new CustomException(e.getMessage());
				}
			}
		}

		if (!userOTP.equals(otp)) {
			throw new CustomException("OTP doesn't match");
		}

		Date currentTime = new Date();
		if (currentTime.getTime() - endTime < 0) {
			throw new CustomException("OTP was expired");
		}

		return true;
	}

}