
package com.bankapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bankapp.jdbc.GovtRequestRowMapper;
import com.bankapp.jdbc.PIIAccessInfoRowMapper;
import com.bankapp.model.GovtRequestsModel;
import com.bankapp.model.PIIAccessInfoModel;
import com.bankapp.model.UserInfo;

@Service
public class GovtRequestsDAOImpl implements GovtRequestsDAO {

	@Autowired
	DataSource dataSource;

	@Override
	public List<GovtRequestsModel> getGovtRequestsList() {
		List<GovtRequestsModel> govtRequestsList = new ArrayList<GovtRequestsModel>();
		String query = "SELECT external_username, internal_username, status FROM tbl_authorizations_government";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		govtRequestsList = jdbcTemplate.query(query, new GovtRequestRowMapper());
		return govtRequestsList;
	}

	@Override
	public void updateGovtAction(String internalUserName, String externalUserName) {
		String query = "UPDATE tbl_authorizations_government SET status = 'a' WHERE external_username= ? AND internal_username= ? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, new Object[] { externalUserName, internalUserName });
		jdbcTemplate.execute(query);
	}

	@Override
	public String isPiiInfoPresent(String username) {
		List<PIIAccessInfoModel> piiAccessInfoList = new ArrayList<PIIAccessInfoModel>();
		String query = "SELECT user_name_e, ssn_e FROM tbl_government";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		piiAccessInfoList = jdbcTemplate.query(query, new PIIAccessInfoRowMapper());
		
		String piiVal = "";
		for(PIIAccessInfoModel pii:piiAccessInfoList) {
			if(pii.getUserName().equals(username))  {
				piiVal = pii.getPii();
			}
		}
		return piiVal;
	}

	@Override
	public void insertPersonalInfo(PIIAccessInfoModel pii) {
			String sql = "INSERT INTO tbl_government "
					+ "(user_name_e,ssn_e) VALUES (?, ?)";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					sql,
					new Object[] { pii.getUserName(), pii.getPii()});
		
	}

	
}