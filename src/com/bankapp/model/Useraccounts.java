package com.bankapp.model;


// default package
// Generated Oct 26, 2014 3:05:06 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Useraccounts {

	private Integer accountno;
	private String username;
	private String routingno;
	private String wiringno;
	private Date accountopendate;
	private double balance;

	public Useraccounts() {
	}

	public Useraccounts(Integer accountno) {
		this.accountno = accountno;
	}

	public Useraccounts(Integer accountno, String username, String routingno,
			String wiringno, Date accountopendate, double balance) {
		this.accountno = accountno;
		this.username = username;
		this.routingno = routingno;
		this.wiringno = wiringno;
		this.accountopendate = accountopendate;
		this.balance = balance;

	}


	public Integer getAccountno() {
		return this.accountno;
	}

	public void setAccountno(Integer accountno) {
		this.accountno = accountno;
	}

	public String getRoutingno() {
		return this.routingno;
	}

	public void setRoutingno(String routingno) {
		this.routingno = routingno;
	}

	public String getWiringno() {
		return this.wiringno;
	}

	public void setWiringno(String wiringno) {
		this.wiringno = wiringno;
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

	
}
