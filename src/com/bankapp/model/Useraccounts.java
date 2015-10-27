package com.bankapp.model;


// default package
// Generated Oct 26, 2014 3:05:06 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.springframework.context.annotation.Scope;

@Scope("sessions")
public class Useraccounts {

	private long accountno;
	private String username;
	private Date accountopendate;
	private double balance;
	private String accountType;

	public Useraccounts() {
	}

	public Useraccounts(Integer accountno) {
		this.accountno = accountno;
	}

	public Useraccounts(Integer accountno, String username, Date accountopendate, double balance, String accountType) {
		this.accountno = accountno;
		this.username = username;
		this.accountopendate = accountopendate;
		this.balance = balance;
		this.accountType = accountType;

	}


	public long getAccountno() {
		return this.accountno;
	}

	public void setAccountno(long accountno) {
		this.accountno = accountno;
	}

	public Date getAccountopendate() {
		return this.accountopendate;
	}

	public void setAccountopendate(Date accountopendate) {
		this.accountopendate = accountopendate;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Useraccounts [accountno=").append(accountno).append(", username=").append(username)
				.append(", accountopendate=").append(accountopendate).append(", balance=").append(balance)
				.append(", accountType=").append(accountType).append("]");
		return builder.toString();
	}

	
}
