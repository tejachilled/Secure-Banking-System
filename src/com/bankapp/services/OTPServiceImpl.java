/**
 * @author Girish Raman
 */

package com.bankapp.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.dao.OTPDAO;
import com.bankapp.userexceptions.CustomException;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;

@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	OTPDAO otpDao;
	
	@Autowired
	EmailService emailService;

	@Override
	public boolean sendOTP(String toEmailAddress, String userName) {
		final String OTP = new BigInteger(40, new SecureRandom()).toString(32);
		otpDao.storeOTP(userName, OTP);
		return emailService.sendEmailWithAttachment(userName, "SunDevil Bank", toEmailAddress, "SunDevilBankASU@gmail.com",
				"OTP for SunDevil Bank Operation",
				"Dear User,\n\nUse " + OTP
						+ " as your OTP for logging into the Secure Banking System for Richie Rich Bank!\n\nHave a great day!",
				null);
	}

	

	@Override
	public boolean checkOTP(String userName, String userOTP) throws CustomException {
		// TODO Auto-generated method stub
		return otpDao.checkOTP(userName, userOTP);
	}
}