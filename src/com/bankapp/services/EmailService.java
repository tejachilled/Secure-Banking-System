package com.bankapp.services;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
	String generatePassword();

	void Send(String username, final String tempPassword, String recipientEmail)
			throws AddressException, MessagingException;

	boolean sendEmailSendGrid(String toName, String fromName, String toAddress, String fromAddress, String subject,
			String body, String fileNameAndExtension, String fileContents);

}