package com.bankapp.services;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.bankapp.model.UserInfo;



public class UserValidator implements Validator {

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return UserInfo.class.isAssignableFrom(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

		UserInfo user=(UserInfo)arg0;
		String username=user.getUserName();
		String firstname=user.getFirstName();
		String lastname=user.getLastName();
		String password=user.getPassword();
		String phoneNumber = ""+user.getPhoneNumber();
		String emaiID = user.getEmaiID();
		String address1 = user.getAddress1();
		String address2 = user.getAddress2();

		if(firstname!= null && !firstname.matches("^[a-zA-Z]{1,12}$"))
		{
			arg1.rejectValue("firstName", "UserInfo.firstName");
		}

		if(lastname!=null && !lastname.matches("^[a-zA-Z]{1,12}$"))
		{
			arg1.rejectValue("lastName", "UserInfo.lastName");
		}

//		if(password !=null && !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,10}$"))
//		{
//			System.out.println("cmg here");
//			arg1.rejectValue("password", "UserInfo.password");
//		}

		if(username!=null && !username.matches("^[a-z0-9_-]{3,16}$"))
		{
			arg1.rejectValue("userName", "UserInfo.userName");
		}
		if(phoneNumber!=null && !phoneNumber.matches("\\d{10}")){
			arg1.rejectValue("phoneNumber", "UserInfo.phoneNumber");
		}

		if (address1 != null && !address1.matches("^[\\d]+[A-Za-z0-9\\s,\\.\\#]+?[\\d\\-]+|^[A-Za-z0-9\\s,\\.\\#]+?$")) {
			arg1.rejectValue("address1", "UserInfo.address1");
		}
		if (address2 != null && !address2.matches("^[\\d]+[A-Za-z0-9\\s,\\.\\#]+?[\\d\\-]+|^[A-Za-z0-9\\s,\\.\\#]+?$")) {
			arg1.rejectValue("address2", "UserInfo.address2");
		}

		if (emaiID != null && !emaiID.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			arg1.rejectValue("emaiID", "UserInfo.emaiID");
		}

		System.out.println("in validator");
	}


}