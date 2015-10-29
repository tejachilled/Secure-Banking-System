
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

	@Override
	public void storeOTP(String userName, Long otp) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String selectQuery = "SELECT COUNT(username) FROM tbl_otp WHERE username = ?";
		Date startDate = new Date(); // current time
		Date endDate = new Date(startDate.getTime() + (10 * 60 * 1000)); // current time + 10 minutes
		//long endDate = startDate.getTime() + (10 * 60 * 1000); // current time + 10 minutes
		try {
			int count = jdbcTemplate.queryForObject(selectQuery, new Object[] { userName }, Integer.class);
			if (count == 0) {
				String query = "INSERT INTO tbl_otp (username, otp,startDate,endDate) VALUES (?,?,?,?)";
				jdbcTemplate.update(query, new Object[] { userName, otp, startDate.getTime(), endDate.getTime()});
				jdbcTemplate.execute(query);
			} else {
				String query = "UPDATE tbl_otp SET otp = ?, startDate = ?, endDate=? WHERE username = ?";
				try {
					conn = dataSource.getConnection();
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setLong(1, otp);
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
	public boolean checkOTP(String userName, Long userOTP) throws CustomException {
		String sql = "select * from tbl_otp where username= ?";
		Long otp = 0L;
		long startTime = 0L, endTime = 0L;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				otp = rs.getLong("otp");
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
		if (endTime < currentTime.getTime()) {
			System.out.println("end time" + endTime);
			System.out.println("start time" + currentTime.getTime());			
			throw new CustomException("OTP was expired");
		}
		
		return true;
	}

}