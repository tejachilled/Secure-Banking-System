package com.bankapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.GovtActionModel;
import com.bankapp.model.GovtRequestsModel;
import com.bankapp.services.GovtRequestsService;

@Controller
public class GovtRequestsController {

	@Autowired
	GovtRequestsService govtRequestsService;

	private static final Logger logger = Logger.getLogger(GovtRequestsController.class);

	/*
	 * Home page and the only page of a Govt user. It displays the list of
	 * requests from Internal Users for viewing the SSN of External Users.
	 */
	@RequestMapping("/govt")
	public ModelAndView getGovernmentRequests(@ModelAttribute("govtAction") GovtActionModel govtActionModel) {
		logger.info("Government User Logged in.");
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Welcome Govt User!");
		model.addObject("bank_name", "SunDevilBank");
		model.setViewName("govt");
		List<GovtRequestsModel> govtRequestsList = govtRequestsService.getGovtRequestsList();
		model.addObject("govtRequestsList", govtRequestsList);
		return model;
	}

	/*
	 * Called when a Govt User wants to accept one or more of the requests.
	 */
	@RequestMapping("/acceptRequests")
	public ModelAndView acceptedRequests(@ModelAttribute("govtAction") GovtActionModel govtActionModel,
			ModelAndView model) {
		logger.info("Government User accepted request(s)");
		model.setViewName("redirect:/govt");
		if (govtRequestsService.update(govtActionModel.getCheckboxList())) {
			model.addObject("error", false);
			return model;
		}
		model.addObject("error", true);
		return model;
	}
}