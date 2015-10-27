
package com.bankapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bankapp.jdbc.PIIAccessInfoRowMapper;
import com.bankapp.model.PIIAccessInfoModel;

@Service
public class PIIAccessInfoDAOImpl implements PIIAccessInfoDAO {

	@Autowired
	DataSource dataSource;

	@Override
	public List<PIIAccessInfoModel> getPIIAccessInfoList(String userName) {
		List<PIIAccessInfoModel> piiAccessInfoList = new ArrayList<PIIAccessInfoModel>();
		String query = "SELECT govt.user_name_e, govt.ssn_e FROM tbl_government govt, tbl_authorizations_government auth WHERE auth.status='a' AND auth.internal_username='"+userName+"' AND govt.user_name_e=auth.external_username";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		piiAccessInfoList = jdbcTemplate.query(query, new PIIAccessInfoRowMapper());
		return piiAccessInfoList;
	}

}