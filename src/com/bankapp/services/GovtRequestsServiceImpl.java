/**
 * @author Girish Raman
 */
package com.bankapp.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.bankapp.dao.GovtRequestsDAO;
import com.bankapp.model.GovtRequestsModel;

public class GovtRequestsServiceImpl implements GovtRequestsService {

	@Autowired
	 GovtRequestsDAO govtRequestsDAO;

	@Override
	public List<GovtRequestsModel> getGovtRequestsList() {
		return govtRequestsDAO.getGovtRequestsList();
	}

}