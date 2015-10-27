package com.bankapp.services;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
	String  generatePassword();
	public boolean sendEmailWithAttachment(String emailId,String username,String decodedPwd);
	public void Send(String username,final String tempPassword, String recipientEmail, Long accno) throws AddressException, MessagingException ;
}