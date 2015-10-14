package com.bankapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.GovtActionModel;
import com.bankapp.model.GovtRequestsModel;
import com.bankapp.services.GovtRequestsServiceImpl;

@Controller
public class GovtRequestsController {

	@Autowired
	GovtRequestsServiceImpl govtRequestsService;

	/*
	 * @RequestMapping("/govtAction") public ModelAndView
	 * govtAction(@ModelAttribute("govtAction") GovtActionModel govtActionModel)
	 * { govtRequestsService.update(govtActionModel, "a"); return new
	 * ModelAndView("govt"); }
	 */

	@RequestMapping("/govt")
	public ModelAndView getGovernmentRequests(@ModelAttribute("govtAction") GovtActionModel govtActionModel) {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Government User Here");
		model.setViewName("govt");
		List<GovtRequestsModel> govtRequestsList = govtRequestsService.getGovtRequestsList();
		model.addObject("govtRequestsList", govtRequestsList);
		//govtActionModel.setCheckboxList(govtRequestsList);
		return model;
	}

	@RequestMapping("/acceptRequests")
	public ModelAndView acceptedRequests(@ModelAttribute("govtAction") GovtActionModel govtActionModel,
			ModelAndView model) {
		model.setViewName("redirect:/govt");
		if (govtRequestsService.update(govtActionModel.getCheckboxList(), "a")) {
			model.getModelMap().addAttribute("error", false);
			return model;
		}
		model.getModelMap().addAttribute("error", true);
		return model;
	}
}
