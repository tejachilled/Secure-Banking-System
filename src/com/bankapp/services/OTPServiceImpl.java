/**
 * @author Girish Raman
 */

package com.bankapp.services;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.dao.OTPDAO;
import com.bankapp.userexceptions.CustomException;

@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	OTPDAO otpDao;

	@Autowired
	EmailService emailService;

	@Override
	public boolean sendOTP(String toEmailAddress, String userName) {
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		final Long OTP = (long) n;
		System.out.println(OTP);
		otpDao.storeOTP(userName, OTP);
		return emailService.sendEmailSendGrid(userName, "SunDevil Bank", toEmailAddress, "SunDevilBankASU@gmail.com",
				"OTP for SunDevil Bank Operation",
				"Dear User,\n\nUse " + OTP
						+ " as your OTP for logging into the Secure Banking System for Richie Rich Bank!\n\nHave a great day!",
				null, "");
	}

	@Override
	public boolean checkOTP(String userName, Long userOTP) throws CustomException {
		return otpDao.checkOTP(userName, userOTP);
	}
}