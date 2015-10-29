package com.bankapp.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import com.sendgrid.SendGrid.Email;
import com.sun.mail.smtp.SMTPTransport;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	public String generatePassword() {
		String chars = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

		final int PW_LENGTH = 6;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++)
			pass.append(chars.charAt(rnd.nextInt(chars.length())));

		return pass.toString();
	}

	public void Send(String userName, final String tempPassword, String recipientEmail)
			throws AddressException, MessagingException {
		final String FromEmail = "SunDevilBankASU";
		final String password = "SunDevilBank";
		final String title = "Confidential Information enclosed from SunDevilBank";
		String message = "Your Username : " + userName + "\n " + "Your temporary password : " + tempPassword + "\n ";
		EmailServiceImpl.Send(FromEmail, password, recipientEmail, "", title, message);
	}

	public static void Send(final String username, final String password, String recipientEmail, String ccEmail,
			String title, String message) throws AddressException, MessagingException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtps.auth", "true");

		/*
		 * If set to false, the QUIT command is sent and the connection is
		 * immediately closed. If set to true (the default), causes the
		 * transport to wait for the response to the QUIT command.
		 * 
		 * ref :
		 * http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/
		 * package-summary.html
		 * http://forum.java.sun.com/thread.jspa?threadID=5205249 smtpsend.java
		 * - demo program from javamail
		 */
		props.put("mail.smtps.quitwait", "false");

		Session session = Session.getInstance(props, null);

		// -- Create a new message --
		final MimeMessage msg = new MimeMessage(session);

		// -- Set the FROM and TO fields --
		msg.setFrom(new InternetAddress(username + "@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

		if (ccEmail.length() > 0) {
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
		}

		msg.setSubject(title);
		msg.setText(message, "utf-8");
		msg.setSentDate(new Date());

		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

		t.connect("smtp.gmail.com", username, password);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
	}
	
	/*
	 * Sample Usage: 		
	 
	 	with attachment
	 	email.sendEmailSendGrid("Girish", "Sundevil", "rgirish1994@gmail.com", "rgirish1994@gmail.com", "subj",
				"body", "file.txt", "yo mama");

		without attachment
		email.sendEmailSendGrid("Girish", "Sundevil", "rgirish1994@gmail.com", "rgirish1994@gmail.com", "subj",
				"body", null, null);
	 */

	@Override
	public boolean sendEmailSendGrid(String toName, String fromName, String toAddress, String fromAddress,
			String subject, String body, String fileNameAndExtension, String fileContents) {
		SendGrid sendgrid = new SendGrid("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");
		Email email = new Email();
		email.addTo(toAddress);
		email.setFromName(fromName);
		email.addToName(toName);
		email.setFrom(fromAddress);
		email.setSubject(subject);
		email.setText(body);
		try {
			if (fileNameAndExtension != null) {
				email.addAttachment(fileNameAndExtension, fileContents);
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
}
