package com.bankapp.services;

import java.util.List;

import com.bankapp.model.PIIAccessInfoModel;

public interface PIIAccessInfoService {

	List<PIIAccessInfoModel> getPIIAccessInfoList(String userName);

}