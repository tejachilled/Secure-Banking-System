package com.bankapp.controller;

import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.bankapp.model.UserInfo;
import com.bankapp.services.CertService;
import com.bankapp.services.EmailService;
import com.bankapp.services.UserService;
import com.bankapp.services.UserValidator;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserAccountExist;
import com.bankapp.userexceptions.UserNameExists;

@Controller
public class InternalUserController {
	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserValidator userValidator;

	@Autowired
	EmailService emailService;
	
	@Autowired
	CertService certService;

	private static final Logger logger = Logger.getLogger(InternalUserController.class);

	@RequestMapping(value = "/register")
	public String registerAUser(ModelMap model) {
		model.addAttribute("extUser", new UserInfo());
		return "addExternalUserAccount";
	}

	@Transactional
	@RequestMapping(value = "/addExtUser", method = RequestMethod.POST)
	public String submitForm(ModelMap model, @ModelAttribute("extUser") @Validated UserInfo UserInfo,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		String role = request.getParameter("role").toString();
		String accountType = request.getParameter("accountType").toString();
		System.out.println("submitForm: account type :" + accountType);
		userValidator.validate(UserInfo, result);
		if (result.hasErrors()) {
			System.out.println("error");
			return "addExternalUserAccount";
		}
		Long accno = 0L;
		try {
			logger.info("Adding an external user - " + UserInfo.getUserName() + " with role- " + role);
			final String tempPwd = emailService.generatePassword();
			UserInfo.setPassword(encoder.encode(tempPwd));
			System.out.println("sec answer 3 : " + UserInfo.getSq3());
			accno = userService.addNewExternalUuser(UserInfo, role, accountType);
			emailService.Send(UserInfo.getUserName(), tempPwd, UserInfo.getEmaiID());
			model.addAttribute("accno", accno);
			certService.initCertPfx(UserInfo.getUserName(), UserInfo.getUserName());
			logger.info("Added customer successfully!");
		} catch (UserAccountExist exception) {
			model.addAttribute("exception", exception.getMessage());
		} catch (CustomException exception) {
			model.addAttribute("exception", exception.getMessage());
		} catch (UserNameExists exception) {
			model.addAttribute("exception", exception.getMessage());
		} catch (MessagingException exception) {
			model.addAttribute("exception", exception.getMessage());
		}

		return "addExternalUserAccount";
	}

	// Manager functionalities
	@RequestMapping(value = "/ViewEmpProfile", method = RequestMethod.GET)
	public String viewEmpProfile(Model model) {
		model.addAttribute("accessInfo", new UserInfo());
		return "viewEmpProfile";
	}

	@RequestMapping(value = "/ViewEmpProfile", method = RequestMethod.POST, params = "Delete")
	public String delEmpProfile(@ModelAttribute("accessInfo") @Validated UserInfo userInfo, HttpServletRequest request,
			BindingResult result, SessionStatus status, Model model) {
		String role = request.getParameter("role").toString();
		System.out.println("in dele method with user role : " + role);
		if (role != null) {
			logger.info("Deleting user");
			userInfo.setRole(role);
			userService.deleteUserInfo(userInfo);
			model.addAttribute("success", "Successfully deleted!");
			logger.info("Successfull");
		}
		return "viewEmpProfile";
	}

	@RequestMapping(value = "/ViewEmpProfile", method = RequestMethod.POST)
	public String viewEmpProfile(@ModelAttribute("accessInfo") @Validated UserInfo userInfo, BindingResult result,
			SessionStatus status, Model model) {
		model.addAttribute("accessInfo", userInfo);
		model.addAttribute("usernameerror", null);
		if (userInfo.getUserName() != null) {
			logger.info("Viewing customer with username - " + userInfo.getUserName());
			if (!(userInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$")) {
				model.addAttribute("usernameerror", "Please enter a valid username");
				return "viewEmpProfile";
			} else {
				UserInfo ui = userService.getUserInfobyUserName(userInfo.getUserName());
				// validate if reasonable request and username exists
				if (ui == null) {
					model.addAttribute("usernameerror", "Specified username does not exist");
					return "viewEmpProfile";
				} else {
					if (ui.getRole().equalsIgnoreCase("ROLE_U") || ui.getRole().equalsIgnoreCase("ROLE_M")) {
						ui.setRole(userService.setRoleToDisplayUI(ui.getRole()));
						model.addAttribute("accessInfo", ui);
						return "viewEmpProfile";
					} else {
						model.addAttribute("usernameerror", "Not a valid customer");
						return "viewEmpProfile";
					}
				}
			}
		} else {
			model.addAttribute("usernameerror", "Please enter the username");
			return "viewEmpProfile";
		}
	}

	// Edit Employees
	@RequestMapping(value = "/EditEmpProfile", method = RequestMethod.GET)
	public String editEmpProfile(Model model) {
		model.addAttribute("accessInfo", new UserInfo());
		return "editEmpProfile";
	}

	@RequestMapping(value = "/EditEmpProfile", method = RequestMethod.POST)
	public String editEmpProfile(@ModelAttribute("accessInfo") @Validated UserInfo UserInfo, BindingResult result,
			SessionStatus status, Model model) {
		// add objects to model
		model.addAttribute("accessInfo", UserInfo);
		System.out.println("editEmpProfile: " + UserInfo.toString());
		model.addAttribute("usernameerror", null);
		model.addAttribute("addresserror", null);
		model.addAttribute("phoneNumber", null);
		model.addAttribute("emailid", null);

		// validate input format
		if (UserInfo.getUserName() != null) {
			if (UserInfo.getFirstName() != null) {
				if (UserInfo.getAddress1() == null || UserInfo.getAddress1().length() == 0) {
					model.addAttribute("addresserror", "Please enter a valid address having characters numbers and #");
					return "editEmpProfile";
				}
				if (UserInfo.getEmaiID() == null) {
					model.addAttribute("emailid", "Please enter a valid email id");
					return "editEmpProfile";
				}
				String phoneNumber = "" + UserInfo.getPhoneNumber();
				if (UserInfo.getPhoneNumber() == null || !phoneNumber.matches("\\d{10}")) {
					model.addAttribute("phoneNumber", "Please enter a valid 10 digit phone number");
					return "editEmpProfile";
				}
				if (UserInfo.getAddress1() != null) {
					if (!(UserInfo.getAddress1()).matches("^[\\d]+[A-Za-z0-9\\s,\\.\\#]+?[\\d\\-]+|^[A-Za-z0-9\\s,\\.\\#]+?$")) {
						model.addAttribute("addresserror",
								"Please enter a valid address having characters numbers and #");
						return "editEmpProfile";
					}
				}
				if (UserInfo.getAddress2() != null) {
					if (!(UserInfo.getAddress2()).matches("^[\\d]+[A-Za-z0-9\\s,\\.\\#]+?[\\d\\-]+|^[A-Za-z0-9\\s,\\.\\#]+?$")) {
						model.addAttribute("addresserror2",
								"Please enter a valid address having characters numbers and #");
						return "editEmpProfile";
					}
				}
				

				UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
			
				if (UserInfo.getAddress1() != ui.getAddress1()) {
					ui.setAddress1(UserInfo.getAddress1());
				}
				if (UserInfo.getAddress2() != ui.getAddress1()) {
					ui.setAddress2(UserInfo.getAddress2());
				}
				if (UserInfo.getEmaiID() != ui.getEmaiID()) {
					ui.setEmaiID(UserInfo.getEmaiID());
				}
				if (UserInfo.getPhoneNumber() != ui.getPhoneNumber()) {
					ui.setPhoneNumber(UserInfo.getPhoneNumber());
				}
				System.out.println("editEmpProfile: updating info with " + ui.toString());
				userService.updateUserInfo(ui);
				System.out.println("editEmpProfile : updated user info ");
				UserInfo = userService.getUserInfobyUserName(UserInfo.getUserName());
				UserInfo.setRole(userService.setRoleToDisplayUI(UserInfo.getRole()));
				model.addAttribute("accessInfo", UserInfo);
				model.addAttribute("success", "Updated details successfully");
				return "editEmpProfile";
			}
			if (!(UserInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$")) {
				model.addAttribute("usernameerror", "Please enter a valid username");
				return "editEmpProfile";
			} else {
				// validate if reasonable request and username exists
				if (userService.getUserInfobyUserName(UserInfo.getUserName()) == null) {
					model.addAttribute("usernameerror", "Specified username does not exist");
					return "editEmpProfile";
				} else {
					UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
					// check if the user is an external user
					if (ui.getRole().equals("ROLE_U") || ui.getRole().equals("ROLE_M")) {
						ui.setRole(userService.setRoleToDisplayUI(ui.getRole()));
						model.addAttribute("accessInfo", ui);
						return "editEmpProfile";
					} else {
						model.addAttribute("usernameerror", "Not a valid customer");
						return "editEmpProfile";
					}
				}
			}
		} else {
			model.addAttribute("usernameerror", "Please enter the username");
			return "viewEmpProfile";
		}
	}

	@RequestMapping(value = "/viewMyIntProfile", method = RequestMethod.GET)
	public String viewMyself(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfo user = userService.getUserInfobyUserName(username);
		model.addAttribute("accessInfo", user);
		return "viewIntInfo";
	}

}