/**
 * @author Girish Raman
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.dao.GovtRequestsDAO;
import com.bankapp.model.GovtRequestsModel;
import com.bankapp.model.PIIAccessInfoModel;

@Service
public class GovtRequestsServiceImpl implements GovtRequestsService {

	@Autowired
	GovtRequestsDAO govtRequestsDAO;

	@Override
	public List<GovtRequestsModel> getGovtRequestsList() {
		return govtRequestsDAO.getGovtRequestsList();
	}

	@Override
	public boolean update(String[] govtActionModel) {

		try {
			for (int i = 0; i < govtActionModel.length; i++) {
				String[] temp = govtActionModel[i].split(" ");
				govtRequestsDAO.updateGovtAction(temp[1].trim(), temp[0].trim());
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	@Override
	public String isPiiInfoPresent(String username) {
		return govtRequestsDAO.isPiiInfoPresent(username);
	}

	@Override
	public void insertPersonalInfo(PIIAccessInfoModel pii) {	
		govtRequestsDAO.insertPersonalInfo(pii);
	}

}