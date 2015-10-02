/**
 * 
 */
package com.bankapp.model;

/**
 * @author manikandan_eshwar
 *
 */
public class UserModel {

	private int userID;
	private String firstName;
	private String lastName;

	public int getUserID() {
		return userID;
	}
   
	 public void setUserID(int userID) {
		 this.userID = userID;
	 }
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
		
}
