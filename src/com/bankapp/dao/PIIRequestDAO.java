/**
 * @author Girish Raman
 */

package com.bankapp.dao;

public interface PIIRequestDAO {

	void sendNewRequest(String externalUserName, String internalUserName);

}