/**
 * @author Girish Raman
 */

package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.PIIAccessInfoModel;

public interface PIIAccessInfoDAO {

	public List<PIIAccessInfoModel> getPIIAccessInfoList(String userName);

}