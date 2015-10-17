/**
 * @author Girish Raman
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bankapp.model.GovtRequestsModel;

public class GovtRequestExtractor implements ResultSetExtractor<GovtRequestsModel> {

	public GovtRequestsModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {

		GovtRequestsModel govtRequest = new GovtRequestsModel();
		govtRequest.setStatus(resultSet.getString(3).charAt(0));
		govtRequest.setExternalUserName(resultSet.getString(1));
		govtRequest.setInternalUserName(resultSet.getString(2));
		return govtRequest;

	}

}