/**
 * 
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.model.Transaction;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionRowMapper implements RowMapper<Transaction> {

	@Override
	public Transaction mapRow(ResultSet rs, int line) throws SQLException {
		TransactionExtractor ts = new TransactionExtractor();	
		return ts.extractData(rs);
	}
}
