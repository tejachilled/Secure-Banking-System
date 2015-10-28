
package com.bankapp.model;

import java.util.Date;

/**
* @author manikandan_eshwar
*
*/
public class Transaction {

	private String transactionID;
	private long accountId;
	private String type;
	private Double amount;
	private String isCritical;
	private Date dateInitiated;
	private Date dataApproved;
	private String remark;
	private String accType;
	
	/**
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}
	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the dateInitiated
	 */
	public Date getDateInitiated() {
		return dateInitiated;
	}
	/**
	 * @param dateInitiated the dateInitiated to set
	 */
	public void setDateInitiated(Date dateInitiated) {
		this.dateInitiated = dateInitiated;
	}
	/**
	 * @return the dataApproved
	 */
	public Date getDataApproved() {
		return dataApproved;
	}
	/**
	 * @param dataApproved the dataApproved to set
	 */
	public void setDataApproved(Date dataApproved) {
		this.dataApproved = dataApproved;
	}
	/**
	 * @return the isCritical
	 */
	public String getIsCritical() {
		return isCritical;
	}
	/**
	 * @param isCritical the isCritical to set
	 */
	public void setIsCritical(String isCritical) {
		this.isCritical = isCritical;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	
	
	

}
