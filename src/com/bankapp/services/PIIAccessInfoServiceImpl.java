/**
 * @author Girish Raman
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.dao.PIIAccessInfoDAO;
import com.bankapp.model.PIIAccessInfoModel;

@Service
public class PIIAccessInfoServiceImpl implements PIIAccessInfoService {

	@Autowired
	PIIAccessInfoDAO piiAccessInfoDAO;

	@Override
	public List<PIIAccessInfoModel> getPIIAccessInfoList(String userName) {
		return piiAccessInfoDAO.getPIIAccessInfoList(userName);
	}
}