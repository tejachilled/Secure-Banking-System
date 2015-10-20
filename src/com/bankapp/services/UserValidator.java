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
		
		if(firstname!= null && !firstname.matches("^[a-zA-Z]{1,12}$"))
		{
			arg1.rejectValue("firstName", "UserInfo.firstName");
		}
		
		if(lastname!=null && !lastname.matches("^[a-zA-Z]{1,12}$"))
		{
			arg1.rejectValue("lastName", "UserInfo.lastName");
		}
					
		if(password !=null && !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,10}$"))
		{
			System.out.println("cmg here");
			arg1.rejectValue("password", "UserInfo.password");
			 
		}
			
		if(username!=null && !username.matches("^[a-z0-9_-]{3,16}$"))
		{
			arg1.rejectValue("userName", "UserInfo.userName");
		}
		if(phoneNumber!=null && !phoneNumber.matches("\\d{10}")){
			arg1.rejectValue("phoneNumber", "UserInfo.phoneNumber");
		}
		
		
		System.out.println("in validator");
	}

	
}
