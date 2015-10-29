package com.bankapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.GovtActionModel;
import com.bankapp.model.PIIAccessInfoModel;
import com.bankapp.model.PIIRequestModel;
import com.bankapp.services.PIIAccessInfoServiceImpl;
import com.bankapp.services.PIIRequestServiceImpl;

@Controller
public class PIIAccessInfoController {

	@Autowired
	PIIAccessInfoServiceImpl piiAccessInfoService;
	
	@Autowired
	PIIRequestServiceImpl piiRequestService;

	private static final Logger logger = Logger.getLogger(PIIAccessInfoController.class);

	@RequestMapping("/piiaccessinfo")
	public ModelAndView getGovernmentRequests(@ModelAttribute("govtAction") GovtActionModel govtActionModel) {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Welcome to SunDevilBank!");
		model.addObject("bank_name", "SunDevilBank");
		model.setViewName("piiaccessinfo");
		List<PIIAccessInfoModel> piiAccessInfoList = piiAccessInfoService
				.getPIIAccessInfoList(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addObject("piiAccessInfoList", piiAccessInfoList);
		return model;
	}

	@RequestMapping("/newPiiRequest")
	public ModelAndView newPiiRequestPage(@ModelAttribute("piiRequestModel") PIIRequestModel piiRequestModel) {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Welcome to SunDevilBank!");
		model.addObject("bank_name", "SunDevilBank");
		model.setViewName("newPiiRequest");
		model.addObject("piiRequestModel", piiRequestModel);
		return model;
	}

	@RequestMapping("/sendPiiAccessRequest")
	public String sendNewPiiRequest(@ModelAttribute("piiRequest") PIIRequestModel piiRequestModel) {
		logger.info("New PII Access request sent by internal user '"
				+ SecurityContextHolder.getContext().getAuthentication().getName() + "' for external user '"
				+ piiRequestModel.getUserNameExternal() + "'");
		if (piiRequestModel != null)
			piiRequestService.sendNewRequest(piiRequestModel.getUserNameExternal(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		return "redirect:/piiaccessinfo";
	}

}