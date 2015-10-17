package com.bankapp.bean;

/**
* @author manikandan_eshwar
*
*/
public class Transaction {

	private long transactionID;
	private long accountId;
	private String type;
	private Double amount;
	private String isCritical;
	private String dateInitiated;
	private String dataApproved;
	
	
	/**
	 * @return the transactionID
	 */
	public long getTransactionID() {
		return transactionID;
	}
	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(long transactionID) {
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
	public String getDateInitiated() {
		return dateInitiated;
	}
	/**
	 * @param dateInitiated the dateInitiated to set
	 */
	public void setDateInitiated(String dateInitiated) {
		this.dateInitiated = dateInitiated;
	}
	/**
	 * @return the dataApproved
	 */
	public String getDataApproved() {
		return dataApproved;
	}
	/**
	 * @param dataApproved the dataApproved to set
	 */
	public void setDataApproved(String dataApproved) {
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
	
	
	
	

}
