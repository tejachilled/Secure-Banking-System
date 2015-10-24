/**
 * @author Girish Raman
 */

package com.bankapp.services;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;

@Service
public class OTPServiceImpl implements OTPService {

	@Override
	public String sendOTP(String toEmailAddress) {

		final String OTP = new BigInteger(40, new SecureRandom()).toString(32);

		SendGrid sendgrid = new SendGrid("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");
		Email email = new Email();
		email.addTo(toEmailAddress);
		email.setFromName("Richie Rich Bank");
		email.addToName("User");
		email.setFrom("admin@richierichbank.com");
		email.setSubject("OTP for Secure Banking System");
		email.setText("Dear User,\n\nUse " + OTP
				+ " as your OTP for logging into the Secure Banking System for Richie Rich Bank!\n\nHave a great day!");
		try {
			sendgrid.send(email);
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
		}

		return OTP;
	}

}