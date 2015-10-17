/**
 * 
 */
package com.bankapp.model;

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
	private String city;
	private String state;
	private Integer zipcode;
	private String role;
	
	public UserInfo(){
		
	}
	public UserInfo(String userName,String password,String role){
		this.userName = userName;
		this.password = password;
		this.role = role;
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

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zipcode
	 */
	public Integer getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [userName=").append(userName).append(", password=").append(password)
				.append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", emaiID=")
				.append(emaiID).append(", phoneNumber=").append(phoneNumber).append(", address1=").append(address1)
				.append(", address2=").append(address2).append(", city=").append(city).append(", state=").append(state)
				.append(", zipcode=").append(zipcode).append(", role=").append(role).append("]");
		return builder.toString();
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
