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
		trans.setTransactionID(rs.getString("transaction_id"));
		trans.setAccountId(rs.getLong("account_id"));
		trans.setAmount(rs.getDouble("amount"));
		trans.setType(rs.getString("transaction_type"));
		trans.setIsCritical(rs.getString("isCritical"));
		//trans.setRemark(rs.getString("remark")); //alter table
		trans.setDateInitiated(rs.getDate("date_of_transaction_initiation"));
		//trans.setDataApproved(rs.getDate("date_of_transaction_approval")); make value Nullable in db
		return trans;
	}

	

}
