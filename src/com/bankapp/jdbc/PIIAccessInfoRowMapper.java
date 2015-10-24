/**
 * @author Girish Raman
 */

package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.model.PIIAccessInfoModel;

public class PIIAccessInfoRowMapper implements RowMapper<PIIAccessInfoModel> {

	@Override
	public PIIAccessInfoModel mapRow(ResultSet resultSet, int line) throws SQLException {
		PIIAccessInfoExtractor piiAccessInfoExtractor = new PIIAccessInfoExtractor();
		return piiAccessInfoExtractor.extractData(resultSet);
	}

}