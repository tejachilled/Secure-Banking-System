/**
 * @author Girish Raman
 */
package com.bankapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.dao.PIIRequestDAO;

@Service
public class PIIRequestServiceImpl implements PIIRequestService {

	@Autowired
	PIIRequestDAO piiRequestDAO;

	@Override
	public boolean sendNewRequest(String externalUserName, String internalUserName) {
		try {
			piiRequestDAO.sendNewRequest(externalUserName.trim(), internalUserName.trim());
		} catch (Exception e) {
			return true;
		}
		return false;
	}
}