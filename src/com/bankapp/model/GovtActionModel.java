package com.bankapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Girish Raman
 */
public class GovtActionModel {
	
	List<String> checkboxList = new ArrayList<>();

	public List<String> getCheckboxList() {
		return checkboxList;
	}
	
	public void setCheckboxList(List<String> checkboxList) {
		this.checkboxList = checkboxList;
	}
}