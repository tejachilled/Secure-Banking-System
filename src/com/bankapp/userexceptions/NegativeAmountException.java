package com.bankapp.userexceptions;

public class NegativeAmountException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6524197060789989442L;

	public NegativeAmountException() {
		// TODO Auto-generated constructor stub
	}

	public NegativeAmountException(String message){
		super(message);
	}
}
