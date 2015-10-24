/**
 * 
 */
package com.bankapp.model;

/**
 * @author manikandan_eshwar
 *
 */
public class Transfer {

	private long toAccountNo;
	/**
	 * @return the toAccountNo
	 */
	public long getToAccountNo() {
		return toAccountNo;
	}
	/**
	 * @param toAccountNo the toAccountNo to set
	 */
	public void setToAccountNo(long toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	/**
	 * @return the amountInvolved
	 */
	public Double getAmountInvolved() {
		return amountInvolved;
	}
	/**
	 * @param amountInvolved the amountInvolved to set
	 */
	public void setAmountInvolved(Double amountInvolved) {
		this.amountInvolved = amountInvolved;
	}
	private Double amountInvolved;
	

}
