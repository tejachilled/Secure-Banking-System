
package com.bankapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.jdbc.UserRowMapper;
import com.bankapp.model.UserModel;

public class UserDAOImpl implements UserDAO {

	@Autowired
	 DataSource dataSource;

	 public void insert(UserModel user) {

	  String sql = "INSERT INTO test_table "
	    + "(firstname,lastname) VALUES (?, ?)";

	  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	  jdbcTemplate.update(
	    sql,
	    new Object[] { user.getFirstName(), user.getLastName()});

	 }

	 public List<UserModel> getUserList() {
		  List userList = new ArrayList();
		  String sql = "select * from test_table ";
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  userList = jdbcTemplate.query(sql, new UserRowMapper());
		  return userList;
	 }

}