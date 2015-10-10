
package com.bankapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.jdbc.GovtRequestRowMapper;
import com.bankapp.model.GovtRequestsModel;

public class GovtRequestsDAOImpl implements GovtRequestsDAO {

	@Autowired
	 DataSource dataSource;

	 public List<GovtRequestsModel> getGovtRequestsList() {
		  List govtRequestsList = new ArrayList<GovtRequestsModel>();
		  String query = "SELECT * FROM tbl_authorizations_government";
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  govtRequestsList = jdbcTemplate.query(query, new GovtRequestRowMapper());
		  return govtRequestsList;
	 }

}