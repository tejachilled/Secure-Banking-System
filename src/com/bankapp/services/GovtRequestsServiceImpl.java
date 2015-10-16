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

	@Override
	public boolean update(String[] govtActionModel, String type) {
				
		try{
		for (int i=0;i<govtActionModel.length;i++) {
			String[] temp = govtActionModel[i].split(" ");
			govtRequestsDAO.updateGovtAction(temp[1].trim(),
					temp[0].trim(), type);
		}
		}
		catch(Exception ex){
			return false;
		}
		return true;
	}

}