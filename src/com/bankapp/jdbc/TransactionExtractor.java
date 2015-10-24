/**
 * 
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bankapp.model.Transaction;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionExtractor implements ResultSetExtractor<Transaction>{
	
	@Override
	public Transaction extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Transaction trans = new Transaction();
		trans.setTransactionID(rs.getString(1));
		trans.setAccountId(rs.getLong(2));
		trans.setType(rs.getString(3));
		trans.setIsCritical(rs.getString(4));
		trans.setDateInitiated(rs.getDate(5));
		trans.setDataApproved(rs.getDate(6));
		return trans;
	}

	

}
