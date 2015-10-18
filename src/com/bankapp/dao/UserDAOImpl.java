package com.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private static final String EXTERNAL_USER = "E"; 
	private static final String INTERNAL_MERCHANT = "M";
	
	public void insert(UserInfo user){
 
		String sql = "INSERT INTO test_table "
				+ "(firstname,lastname) VALUES (?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { user.getFirstName(), user.getLastName()});

	}

	public List<UserInfo> getUserList() {
		List userList = new ArrayList();

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
		
		if(user!=null && ( user.getRole().equalsIgnoreCase(INTERNAL_USER) ||  user.getRole().equalsIgnoreCase(INTERNAL_MERCHANT))){
			sql = "select * from tbl_internal_users where user_name= ?";
			user = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[] { username },
					new UserRowMapper());
			user.setPassword(password);
		}else if(user!=null && user.getRole().equalsIgnoreCase(EXTERNAL_USER)){
			sql = "select * from tbl_external_users where user_name= ?";
			user = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[] { username },
					new UserRowMapper());
			user.setPassword(password);
		}
		System.out.println(user);
		return user;
	}

	@Override
	public int registerNewUserAccount(UserInfo userInfo, Useraccounts account) {
		String sql = "Insert into login";
		
		
		return 0;
	}

}
