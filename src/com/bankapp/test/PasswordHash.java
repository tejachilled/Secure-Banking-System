package com.bankapp.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHash {
	@Test
	public void testPasswordGenerator()
	{
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String password=encoder.encode("teja");
	//	System.out.println(password);
		
	}
}
