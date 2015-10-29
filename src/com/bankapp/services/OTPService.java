package com.bankapp.services;

import com.bankapp.userexceptions.CustomException;

public interface OTPService {

	boolean sendOTP(String toEmailAddress, String userName);
	boolean checkOTP(String userName,Long userOTP) throws CustomException;

}