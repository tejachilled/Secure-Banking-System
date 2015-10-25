
package com.bankapp.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PIIRequestDAOImpl implements PIIRequestDAO {

	@Autowired
	DataSource dataSource;

	@Override
	public void sendNewRequest(String externalUserName, String internalUserName) {
		String query = "INSERT INTO tbl_authorizations_government (external_username, internal_username) VALUES (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, new Object[] { externalUserName, internalUserName });
		jdbcTemplate.execute(query);
	}

}