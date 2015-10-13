/**
 * @author Girish Raman
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.dao.GovtRequestsDAO;
import com.bankapp.model.GovtActionModel;
import com.bankapp.model.GovtRequestsModel;

public class GovtRequestsServiceImpl implements GovtRequestsService {

	@Autowired
	 GovtRequestsDAO govtRequestsDAO;

	@Override
	public List<GovtRequestsModel> getGovtRequestsList() {
		return govtRequestsDAO.getGovtRequestsList();
	}
	
	@Override
	public void update(GovtActionModel govtActionModel, String string){
		List<String> list = govtActionModel.getCheckboxList();
		for(String s : list){
			String[] parts = s.split("_");
			govtRequestsDAO.updateGovtAction(parts[0], parts[1], string);
		}
	}

}