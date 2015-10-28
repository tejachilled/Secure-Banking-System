package com.bankapp.test;

public class AddressValidation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(validateAddress(" Street east University drive apt #123"));
	}
	public static boolean validateAddress( String address )
	   {
	      return address.matches( 
	         "^[\\d]+[A-Za-z0-9\\s,\\.\\#]+?[\\d\\-]+|^[A-Za-z0-9\\s,\\.\\#]+?$" );
	   } 

}
