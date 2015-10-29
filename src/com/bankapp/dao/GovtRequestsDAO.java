/**
 * @author Girish Raman
 */

package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.GovtRequestsModel;
import com.bankapp.model.PIIAccessInfoModel;

public interface GovtRequestsDAO {

	/**
	 * This function gets the list of all PII requests from the database and
	 * gives it as a @return List<GovtRequestsModel> object.
	 */
	public List<GovtRequestsModel> getGovtRequestsList();

	/**
	 * 
	 * This function sets updates the status as accepted ('a') for the row with
	 * internal user name @param internalUserName and external username @param
	 * externalUserName
	 * 
	 */
	void updateGovtAction(String internalUserName, String externalUserName);

	public String isPiiInfoPresent(String username);
	
	public void insertPersonalInfo(PIIAccessInfoModel pii);
}