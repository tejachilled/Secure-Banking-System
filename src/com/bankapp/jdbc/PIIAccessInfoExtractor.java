/**
 * @author Girish Raman
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bankapp.model.PIIAccessInfoModel;

public class PIIAccessInfoExtractor implements ResultSetExtractor<PIIAccessInfoModel> {

	public PIIAccessInfoModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {

		PIIAccessInfoModel piiAccessInfoRequest = new PIIAccessInfoModel();
		piiAccessInfoRequest.setUserName(resultSet.getString(1));
		piiAccessInfoRequest.setPii(resultSet.getString(2));
		return piiAccessInfoRequest;

	}

}