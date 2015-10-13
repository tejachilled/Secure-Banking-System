package com.bankapp.services;

import java.util.List;

import com.bankapp.model.GovtActionModel;
import com.bankapp.model.GovtRequestsModel;

public interface GovtRequestsService {

	List<GovtRequestsModel> getGovtRequestsList();
	void update(GovtActionModel govtActionModel, String string);

}