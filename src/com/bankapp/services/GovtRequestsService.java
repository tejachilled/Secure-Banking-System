package com.bankapp.services;

import java.util.List;

import com.bankapp.model.GovtRequestsModel;
import com.bankapp.model.PIIAccessInfoModel;

public interface GovtRequestsService {

	List<GovtRequestsModel> getGovtRequestsList();

	boolean update(String[] govtActionModel);
	
	public Boolean isPiiInfoPresent(String username);
	
	public void insertPersonalInfo(PIIAccessInfoModel pii);

}