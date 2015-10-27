package com.bankapp.model;

import java.util.List;

/**
 * @author ravi
 *
 */
public class UserInfo {
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String emaiID;
	private Long phoneNumber;
	private String address1;
	private String address2;
	private String role;
	private String ssn;
	private List<Useraccounts> account;
	private String sq1;
	private String sq2;
	private String sq3;
	private boolean isFirstLogin;
	
	public UserInfo(){
		
	}
	public UserInfo(String userName,String password,String role,boolean isFirstLogin){
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.setFirstLogin(isFirstLogin);
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the emaiID
	 */
	public String getEmaiID() {
		return emaiID;
	}

	/**
	 * @param emaiID the emaiID to set
	 */
	public void setEmaiID(String emaiID) {
		this.emaiID = emaiID;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the phoneNumber
	 */
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	/**
	 * @return the account
	 */
	public List<Useraccounts> getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(List<Useraccounts> account) {
		this.account = account;
	}
	/**
	 * @return the sq1
	 */
	public String getSq1() {
		return sq1;
	}
	/**
	 * @param sq1 the sq1 to set
	 */
	public void setSq1(String sq1) {
		this.sq1 = sq1;
	}
	/**
	 * @return the sq2
	 */
	public String getSq2() {
		return sq2;
	}
	/**
	 * @param sq2 the sq2 to set
	 */
	public void setSq2(String sq2) {
		this.sq2 = sq2;
	}
	/**
	 * @return the sq3
	 */
	public String getSq3() {
		return sq3;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [userName=").append(userName).append(", password=").append(password)
				.append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", emaiID=")
				.append(emaiID).append(", phoneNumber=").append(phoneNumber).append(", address1=").append(address1)
				.append(", address2=").append(address2).append(", role=").append(role).append(", ssn=").append(ssn)
				.append(", account=").append(account).append(", sq1=").append(sq1).append(", sq2=").append(sq2)
				.append(", sq3=").append(sq3).append("]");
		return builder.toString();
	}
	/**
	 * @param sq3 the sq3 to set
	 */
	public void setSq3(String sq3) {
		this.sq3 = sq3;
	}
	/**
	 * @return the isFirstLogin
	 */
	public boolean isFirstLogin() {
		return isFirstLogin;
	}
	/**
	 * @param isFirstLogin the isFirstLogin to set
	 */
	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	
}
