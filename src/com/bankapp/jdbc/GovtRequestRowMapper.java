/**
 * @author Girish Raman
 */

package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.model.GovtRequestsModel;

public class GovtRequestRowMapper implements RowMapper<GovtRequestsModel> {

	@Override
	public GovtRequestsModel mapRow(ResultSet resultSet, int line) throws SQLException {
		GovtRequestExtractor govtRequestExtractor = new GovtRequestExtractor();
		return govtRequestExtractor.extractData(resultSet);
	}

}