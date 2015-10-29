package com.bankapp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.bankapp.model.UserInfo;
import com.bankapp.services.EmailService;
import com.bankapp.services.UserService;
import com.bankapp.services.UserValidator;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserNameExists;

@Controller
public class AdminController {
	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserValidator userValidator;
	
	@Autowired
	EmailService emailService;

	private static final Logger logger = Logger.getLogger(AdminController.class);

	// Admin functionalities
	@RequestMapping(value = "/ViewInternalEmpProfile", method = RequestMethod.GET)
	public String viewInternalEmpProfile(Model model) {
		model.addAttribute("accessInfo", new UserInfo());

		return "viewInternalEmpProfile";
	}

	@RequestMapping(value = "/ViewInternalEmpProfile", method = { RequestMethod.POST }, params = "Delete")
	public String deleteEmpProfile1(@ModelAttribute("accessInfo") @Validated UserInfo userInfo,
			HttpServletRequest request, BindingResult result, SessionStatus status, Model model) {
		String role = request.getParameter("role").toString();
		System.out.println("in dele method with user role : " + role);
		if (role != null) {
			logger.info("Requested delete");
			userInfo.setRole(role);
			userService.deleteUserInfo(userInfo);
			model.addAttribute("success", "Successfully deleted!");
			logger.info("Internal employee successfuly deleted!");
		}
		return "viewInternalEmpProfile";
	}

	@RequestMapping(value = "/ViewInternalEmpProfile", method = RequestMethod.POST)
	public String viewEmpProfile(@ModelAttribute("accessInfo") @Validated UserInfo userInfo, BindingResult result,
			SessionStatus status, Model model) {

		// add objects to model
		model.addAttribute("accessInfo", userInfo);
		model.addAttribute("usernameerror", null);
		// validate input format
		if (userInfo.getUserName() != null) {
			logger.info("Viewing Internal Employee: " + userInfo.getUserName().toUpperCase() + " profile");

			if (!(userInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$")) {
				model.addAttribute("usernameerror", "Please enter a valid username");
				logger.info(userInfo.getUserName().toUpperCase() + " is not a valid Internal Employee");

				return "viewInternalEmpProfile";
			} else {

				UserInfo ui = userService.getUserInfobyUserName(userInfo.getUserName());
				// validate if reasonable request and username exists

				if (ui == null) {
					model.addAttribute("usernameerror", "Specified username does not exist");
					logger.info(userInfo.getUserName().toUpperCase() + " internal employee doesn't exist");

					return "viewInternalEmpProfile";
				} else {
					// System.out.println("Admin controller user role :
					// "+ui.getRole());
					if (ui.getRole().equalsIgnoreCase("ROLE_RE") || ui.getRole().equalsIgnoreCase("ROLE_SM")) {
						ui.setRole(userService.setRoleToDisplayUI(ui.getRole()));
						model.addAttribute("accessInfo", ui);

						return "viewInternalEmpProfile";
					} else {
						logger.info(userInfo.getUserName().toUpperCase() + " is not a valid internal employee");

						model.addAttribute("usernameerror", "Not a valid User");
						return "viewInternalEmpProfile";
					}
				}
			}
		} else {
			model.addAttribute("usernameerror", "Please enter the username");
			return "viewInternalEmpProfile";
		}
	}

	@Transactional
	@RequestMapping(value = "/addInternalUser")
	public String registerAInternalUser(ModelMap model) {
		model.addAttribute("intUser", new UserInfo());
		return "addInternalUser";
	}

	@Transactional
	@RequestMapping(value = "/addInternalUser", method = RequestMethod.POST)
	public String addInternalUser(ModelMap model, @ModelAttribute("intUser") @Validated UserInfo UserInfo,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		String role = request.getParameter("role").toString();
		userValidator.validate(UserInfo, result);

		if (result.hasErrors()) {
			System.out.println("error");
			return "addInternalUser";
		}
		logger.info("Adding an Internal Employee: with username" + UserInfo.getUserName().toUpperCase() + " with role: "
				+ role);

		try {
			final String tempPwd = emailService.generatePassword();
			UserInfo.setPassword(encoder.encode(tempPwd));
			userService.addNewInternaluser(UserInfo, role);
			emailService.Send(UserInfo.getUserName(), tempPwd, UserInfo.getEmaiID());
			model.addAttribute("success", "Added new user successfully!");
			logger.info("Employee created successfully!");
		} catch (CustomException exception) {
			model.addAttribute("exception", exception.getMessage());
		} catch (UserNameExists exception) {
			model.addAttribute("exception", exception.getMessage());
		}

		return "addInternalUser";

	}

	// Edit Employees
	@RequestMapping(value = "/EditInternalEmpProfile", method = RequestMethod.GET)
	public String editEmpProfile(Model model) {
		model.addAttribute("accessInfo", new UserInfo());
		return "editInternalEmpProfile";
	}

	@RequestMapping(value = "/EditInternalEmpProfile", method = RequestMethod.POST)
	public String editEmpProfile(@ModelAttribute("accessInfo") @Validated UserInfo UserInfo, BindingResult result,
			SessionStatus status, Model model) {
		// add objects to model
		model.addAttribute("accessInfo", UserInfo);
		System.out.println("editInternalEmpProfile: " + UserInfo.toString());
		model.addAttribute("usernameerror", null);
		model.addAttribute("addresserror", null);
		model.addAttribute("phoneNumber", null);
		model.addAttribute("emailid", null);

		// validate input format
		if (UserInfo.getUserName() != null) {
			logger.info("Editing Internal user profile with username : " + UserInfo.getUserName());
			if (UserInfo.getFirstName() != null) {
				if (UserInfo.getAddress1() == null || UserInfo.getAddress1().length() == 0) {
					model.addAttribute("addresserror", "Please enter a valid address having characters numbers and #");
					return "editInternalEmpProfile";
				}
				if (UserInfo.getEmaiID() == null) {
					model.addAttribute("emailid", "Please enter a valid email id");
					logger.info("Entered an Invalid email id");
					return "editInternalEmpProfile";
				}
				String phoneNumber = "" + UserInfo.getPhoneNumber();
				if (UserInfo.getPhoneNumber() == null || !phoneNumber.matches("\\d{10}")) {
					model.addAttribute("phoneNumber", "Please enter a valid 10 digit phone number");
					logger.info("Entered an invalid phone number");
					return "editInternalEmpProfile";
				}

				UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
				if (ui.getAddress1() != null) {
					if (!(ui.getAddress1()).matches("^[a-zA-Z0-9_#]*$")) {
						model.addAttribute("addresserror",
								"Please enter a valid address having characters numbers and #");
						return "editInternalEmpProfile";
					}
				}
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
				userService.updateInternalUserInfo(ui);
				logger.info("Successfully updated internal user profile");
				model.addAttribute("success", "Updated details successfully");
				UserInfo = userService.getUserInfobyUserName(UserInfo.getUserName());
				UserInfo.setRole(userService.setRoleToDisplayUI(UserInfo.getRole()));
				model.addAttribute("accessInfo", UserInfo);
				model.addAttribute("success", "updated Successfully!");
				return "editInternalEmpProfile";
			}
			if (!(UserInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$")) {
				model.addAttribute("usernameerror", "Please enter a valid username");
				return "editInternalEmpProfile";
			} else {
				// validate if reasonable request and username exists
				if (userService.getUserInfobyUserName(UserInfo.getUserName()) == null) {
					model.addAttribute("usernameerror", "Specified username does not exist");
					logger.info("Username - " + UserInfo.getUserName() + " does not exist");
					return "editInternalEmpProfile";
				} else {
					UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
					// check if the user is an Internal user
					if (ui.getRole().equals("ROLE_RE") || ui.getRole().equals("ROLE_SM")) {
						ui.setRole(userService.setRoleToDisplayUI(ui.getRole()));
						model.addAttribute("accessInfo", ui);
						logger.info("Successfully retried employee details");
						return "editInternalEmpProfile";
					} else {
						logger.info("Username - " + UserInfo.getUserName() + " is not a valid internal employee");
						model.addAttribute("usernameerror", "Not a valid user");
						return "editInternalEmpProfile";
					}
				}
			}
		} else {
			model.addAttribute("usernameerror", "Please enter the username");
			return "viewInternalEmpProfile";
		}
	}

	@RequestMapping(value = "/viewSystemLogs", method = RequestMethod.GET)
	public String viewSystemLogs(Model model) {
		List<String> fileNames = new ArrayList<String>();

		File folder = new File(System.getProperty("BankLogs.Home"));
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileNames.add(listOfFiles[i].getName());
			}
		}
		model.addAttribute("FileList", fileNames);
		return "viewSystemLogs";
	}

	@RequestMapping(value = "/downloadLogFile/{fileName}", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @PathVariable("fileName") String fileName) {

		File file = null;
		file = new File(System.getProperty("BankLogs.Home") + "/" + fileName);

		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			OutputStream outputStream = null;
			try {
				outputStream = response.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				return;
			}
			try {
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				return;
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				return;
			}
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);

		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

		/*
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition",
		// String.format("attachment; filename=\"%s\"", file.getName()));

		response.setContentLength((int) file.length());

		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			return;
		}

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			return;
		}
	}

}