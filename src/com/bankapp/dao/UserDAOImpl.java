package com.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.jdbc.UserRowMapper;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;

public class UserDAOImpl implements UserDAO {

	@Autowired
	DataSource dataSource;
	private static final String INTERNAL_USER = "I"; 
	private static final String EXTERNAL_USER = "U"; 
	private static final String MERCHANT = "M";
	
	public void insert(UserInfo user){
		String sql = "INSERT INTO test_table "
				+ "(firstname,lastname) VALUES (?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { user.getFirstName(), user.getLastName()});

	}

	public List<UserInfo> getUserList() {
		List<UserInfo> userList = new ArrayList<>();

		String sql = "select * from test_table ";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new UserRowMapper());
		return userList;
	}

	@Override
	public UserInfo findUserByUsername(String username) {
		String sql = "select * from tbl_login where user_name= ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Connection conn = null;
		UserInfo user = null;
		String password = "";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserInfo(
					rs.getString("user_name"),
					password = rs.getString("pwd_hash"), 
					rs.getString("user_type")
				);
			}
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
		if(user!=null && ( user.getRole().equalsIgnoreCase(INTERNAL_USER))){
			sql = "select * from tbl_internal_users where user_name= ?";
			user = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[] { username },
					new UserRowMapper());
			user.setPassword(password);
		}else if(user!=null && user.getRole().equalsIgnoreCase(EXTERNAL_USER) 
				||  user.getRole().equalsIgnoreCase(MERCHANT)){
			sql = "select * from tbl_external_users where user_name= ?";
			user = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[] { username },
					new UserRowMapper());
			user.setPassword(password);
		}
		System.out.println(user);
		return user;
	}

	@Override
	public Long registerNewUserAccount(UserInfo userInfo, Useraccounts account) {
		Connection conn = null;
		String sql = "Insert into tbl_login(user_name, pwd_hash, user_type, first_time) values(?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userInfo.getUserName());
			ps.setString(2, userInfo.getPassword());
			ps.setString(3, userInfo.getRole());
			ps.setBoolean(4, true);
			ps.executeUpdate();
		}
		catch (SQLIntegrityConstraintViolationException e) {
		    // Duplicate entry
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
 
		}
		sql = "insert into tbl_external_users(user_name, first_name, last_name, email_id, phone_number, add_l1, add_l2, role) values(?,?,?,?,?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userInfo.getUserName());
			ps.setString(2, userInfo.getFirstName());
			ps.setString(3, userInfo.getLastName());
			ps.setString(4, userInfo.getEmaiID());
			ps.setLong(5, userInfo.getPhoneNumber());
			ps.setString(6, userInfo.getAddress1());
			ps.setString(7, userInfo.getAddress2());
			if(userInfo.getRole().equalsIgnoreCase(EXTERNAL_USER)){
			ps.setString(8, "ROLE_U");
			}else if(userInfo.getRole().equalsIgnoreCase(MERCHANT)){
				ps.setString(8, "ROLE_M");
			}
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
 
		}
		Long uniqueAccountNumber = getUniqueAccountNumber();
		sql = "insert into tbl_accounts(account_id, user_name, type, balance, account_open_date) values(?,?,?,?,?)";
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, uniqueAccountNumber);
			ps.setString(2, userInfo.getUserName());
			ps.setString(3, account.getAccountType());
			ps.setDouble(4, account.getBalance());
			ps.setTimestamp(5, getTodaysDate());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
					
				} catch (SQLException e) {}
			}
		}
		return uniqueAccountNumber;
	}
	
	private Long getUniqueAccountNumber() {
		 SimpleDateFormat simpleDateFormat =
		            new SimpleDateFormat("MMddkkmmss");
		String dateAsString = simpleDateFormat.format(new java.sql.Timestamp(getTodaysDate().getTime()));
		return Long.valueOf(dateAsString).longValue();
	}

	private Timestamp getTodaysDate(){
		java.util.Date today = new java.util.Date();
		return  new java.sql.Timestamp(today.getTime());
	}

	@Override
	public String findUserRoleType(String username) {
		SimpleDateFormat simpleDateFormat =
	            new SimpleDateFormat("MMddkkmmss");	
		return  simpleDateFormat.format(getTodaysDate());
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		
		
	}

	@Override
	public void deleteUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		
	}

}
