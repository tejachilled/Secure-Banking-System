package com.bankapp.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
	String  generatePassword();
	boolean sendEmailWithAttachment(String toName, String fromName, String toAddress, String fromAddress, String subject, String body, File attachment);
	void Send(String username,final String tempPassword, String recipientEmail, Long accno) throws AddressException, MessagingException ;
	
}