package com.bankapp.model;


// default package
// Generated Oct 26, 2014 3:05:06 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Useraccounts {

	private Integer accountno;
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


	public Integer getAccountno() {
		return this.accountno;
	}

	public void setAccountno(Integer accountno) {
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

	
}
