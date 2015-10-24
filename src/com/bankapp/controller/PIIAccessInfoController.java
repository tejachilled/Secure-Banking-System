package com.bankapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.GovtActionModel;
import com.bankapp.model.PIIAccessInfoModel;
import com.bankapp.services.PIIAccessInfoServiceImpl;

@Controller
public class PIIAccessInfoController {

	@Autowired
	PIIAccessInfoServiceImpl piiAccessInfoService;

	@RequestMapping("/piiaccessinfo")
	public ModelAndView getGovernmentRequests(@ModelAttribute("govtAction") GovtActionModel govtActionModel) {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Welcome to Richie Rich Bank!");
		model.addObject("bank_name", "Richie Rich Bank");
		model.setViewName("piiaccessinfo");
		List<PIIAccessInfoModel> piiAccessInfoList = piiAccessInfoService.getPIIAccessInfoList();
		model.addObject("piiAccessInfoList", piiAccessInfoList);
		return model;
	}
}