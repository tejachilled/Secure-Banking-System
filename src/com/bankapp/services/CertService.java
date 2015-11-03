package com.bankapp.services;

import java.io.IOException;

public interface CertService {
	void loadRoot() throws Exception;
	void initCertPfx(String username, String password) throws Exception;
	boolean sendCert(String toEmailAddress, String userName) throws IOException;
	boolean sendPfx(String toEmailAddress, String userName);
	boolean verifyCert(String cert_path, String username) throws Exception;
	String getEncryptedString(String userName, String plainText) throws Exception;
}

