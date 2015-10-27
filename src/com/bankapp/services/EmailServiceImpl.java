package com.bankapp.services;

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

import com.sun.mail.smtp.SMTPTransport;


@Service("emailService")
public class EmailServiceImpl implements EmailService {
	public String  generatePassword()
	{
		String chars = "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

		final int PW_LENGTH = 6;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++)
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		
		return pass.toString();		
	}
	
	 public void Send(String userName,final String tempPassword, String recipientEmail,Long accNo) throws AddressException, MessagingException {
		 final String FromEmail = "SunDevilBankASU";
		 final String password = "SunDevilBank";
		 final String title = "Confidential Information enclosed from SunDevilBank";
		 String message = "Your Username : "+ userName+ "\n "+
				 "Your temporary password : "+tempPassword+ "\n ";
		 EmailServiceImpl.Send(FromEmail, password, recipientEmail, "", title, message);
	    }
	 public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
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
	        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
	        to true (the default), causes the transport to wait for the response to the QUIT command.

	        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
	                http://forum.java.sun.com/thread.jspa?threadID=5205249
	                smtpsend.java - demo program from javamail
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

	        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

	        t.connect("smtp.gmail.com", username, password);
	        t.sendMessage(msg, msg.getAllRecipients());      
	        t.close();
	    }
	 // Not working for attachent as file was not presenr. Please some one fix it
	 public boolean sendEmailWithAttachment(String emailId,String username,String decodedPwd)
		{
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"SunDevilBankASU@gmail.com", "SunDevilBank");
						}
					});
			
			try {
				  MimeMessage message = new MimeMessage(session);
			         message.setFrom(new InternetAddress("SunDevilBankASU@gmail.com"));
			         message.addRecipient(Message.RecipientType.TO,
			                                  new InternetAddress(emailId));
			         message.setSubject("RichiRich Bank ");
			         BodyPart messageBodyPart = new MimeBodyPart();
			         messageBodyPart.setText("This is your token have fun!!   "+"\n Your Password is " + decodedPwd);
			         Multipart multipart = new MimeMultipart();

			         multipart.addBodyPart(messageBodyPart);
			         // Part two is attachment
			         messageBodyPart = new MimeBodyPart();
			         String filename = "file.txt";
			         DataSource source = new FileDataSource(filename);
			         messageBodyPart.setDataHandler(new DataHandler(source));
			         messageBodyPart.setFileName(filename);
			         multipart.addBodyPart(messageBodyPart);

			         // Send the complete message parts
			         message.setContent(multipart );
//
//			         messageBodyPart = new MimeBodyPart();
//			         String filename = username+".pfx";
//			         DataSource source = new FileDataSource(filename);
//			         messageBodyPart.setDataHandler(new DataHandler(source));
//			         messageBodyPart.setFileName(filename);
//			         multipart.addBodyPart(messageBodyPart);
//			         message.setContent(multipart );
//			         
//			         messageBodyPart=new MimeBodyPart();
//			         String filename1= "Encryptor.jar";
//			         DataSource source1=new FileDataSource(filename);
//			         messageBodyPart.setDataHandler(new DataHandler(source1));
//			         messageBodyPart.setFileName(filename1);
//			         multipart.addBodyPart(messageBodyPart);
//			         message.setContent(multipart);
//			         
			         Transport.send(message);
			         System.out.println("Sent message successfully....");
			        

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

			return false;

		}
}
	
	
