package com.bankapp.model;

/**
 * @author Girish Raman
 */
public class GovtRequestsModel {

	String externalUserName, internalUserName;
	char status;

	public String getExternalUserName() {
		return externalUserName;
	}

	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}

	public String getInternalUserName() {
		return internalUserName;
	}

	public void setInternalUserName(String internalUserName) {
		this.internalUserName = internalUserName;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String toString() {
		return externalUserName + " " + internalUserName;
	}
}