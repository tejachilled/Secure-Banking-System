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
public class OTPServiceImpl implements OTPService, SendGridEmailService {

	@Autowired
	OTPDAO otpDao;

	@Override
	public boolean sendOTP(String toEmailAddress, String userName) {
		final String OTP = new BigInteger(40, new SecureRandom()).toString(32);
		otpDao.storeOTP(userName, OTP);
		return sendEmail("User", "SunDevil Bank", toEmailAddress, "SunDevilBankASU@gmail.com",
				"OTP for SunDevil Bank Operation",
				"Dear User,\n\nUse " + OTP
						+ " as your OTP for logging into the Secure Banking System for Richie Rich Bank!\n\nHave a great day!",
				null);
	}

	@Override
	public boolean sendEmail(String toName, String fromName, String toAddress, String fromAddress, String subject,
			String body, File attachment) {
		SendGrid sendgrid = new SendGrid("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");
		Email email = new Email();
		email.addTo(toAddress);
		email.setFromName(fromName);
		email.addToName(toName);
		email.setFrom(fromAddress);
		email.setSubject(subject);
		email.setText(body);
		try {
			if (attachment != null) {
				// change the file name here
				email.addAttachment("filename.extension", attachment);
			}
			sendgrid.send(email);
		} catch (FileNotFoundException fnfe) {
			return false;
		} catch (IOException fnfe) {
			return false;
		} catch (SendGridException fnfe) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkOTP(String userName, String userOTP) throws CustomException {
		// TODO Auto-generated method stub
		return otpDao.checkOTP(userName, userOTP);
	}
}