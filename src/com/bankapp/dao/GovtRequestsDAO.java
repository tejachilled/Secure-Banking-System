/**
 * @author Girish Raman
 */

package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.GovtRequestsModel;

public interface GovtRequestsDAO {

	public List<GovtRequestsModel> getGovtRequestsList();
	void updateGovtAction(String internalUserName, String externalUserName, String status);	

}