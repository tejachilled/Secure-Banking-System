package com.bankapp.services;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
	String  generatePassword();
	public boolean sendEmailWithAttachment(String emailId,String username,String decodedPwd);
	public void Send(final String tempPassword, String recipientEmail) throws AddressException, MessagingException ;
} 