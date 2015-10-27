package com.bankapp.services;

import java.io.File;

public interface SendGridEmailService {

	boolean sendEmail(String toName, String fromName, String toAddress, String fromAddress, String subject, String body, File attachment);

}