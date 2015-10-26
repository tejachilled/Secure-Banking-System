package com.bankapp.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoggerListener implements ServletContextListener {

	public LoggerListener() {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String currentDir = System.getProperty("user.dir");
		System.setProperty("BankLogs.Home", currentDir+"/logs");
		System.out.println("Custom Logs Home Directory"+ System.getProperty("BankLogs.Home"));
	}

}
