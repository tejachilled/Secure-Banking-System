package com.bankapp.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;


public class LogRecords {
	//@Autowired
    //ServletContext context=this.getServletContext();;
	FileWriter fw;
	//return true if log.txt can be opened successfully
	private  boolean openLogFile(){
		
	
		//String realPath = context.getRealPath("/");		
		//File file = new File(realPath+"/TestFolder", "testFIle.txt");
		//System.out.println("realPath:"+realPath);
		
		String realPath = System.getProperty("user.dir");
		try {
			File file=new File(realPath+File.separatorChar+"log.txt");
			String s=file.getAbsolutePath();
			System.out.println(s);
			if(!file.exists()) {
				System.out.println("no log file");
				file.createNewFile();
				System.out.println("created log file");
			}
			fw=new FileWriter(file,true);
			System.out.println("log file opened");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//all following methods return true when the recording is successful and fasle oterwise
	
	//param1: usertype of the user; param2: name of the user
	public  boolean recordUserLogin(String usertype, String username){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> logged in", timeStamp,usertype, username));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordUserLogout(String usertype, String username){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> logged out", timeStamp,usertype, username));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordUserCreation(String usertype, String username, String createdusertype, String createdUsername){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> created <%s %s>", timeStamp,usertype, username, createdusertype, createdUsername));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordUserModification(String usertype, String username, String modifiedUsertype, String modifiedUsername){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> modified <%s %s>", timeStamp,usertype, username, modifiedUsertype, modifiedUsername));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordUserDeletion(String usertype, String username, String deletedUsertype, String deletedUsername){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> deleted <%s %s>", timeStamp,usertype, username, deletedUsertype, deletedUsername));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//Description could be ""
	public  boolean recordTransactionCreation(String usertype, String username, String transactionNo, String describtion){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> created transaction <%s> (%s)", timeStamp,usertype, username, transactionNo, describtion));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//Description could be ""
	public  boolean recordTransactionModification(String usertype, String username, String transactionNo, String describtion){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> modified transaction # <%s> (%s)", timeStamp,usertype, username, transactionNo, describtion));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//Description could be ""
	public  boolean recordTransactionDeletion(String usertype, String username, String transactionNo, String describtion){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> deleted transaction # <%s> (%s)", timeStamp,usertype, username, transactionNo, describtion));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//Description could be ""
	public  boolean recordTransactionApproval(String usertype, String username, String transactionNo, String describtion){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> approved transaction # <%s> (%s)", timeStamp,usertype, username, transactionNo, describtion));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordAuthRequestSent(String usertype, String username, String autoNo, String describtion){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> sent authorization request # <%s> (%s)", timeStamp,usertype, username, autoNo, describtion));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordAuthRequestProcessed(String usertype, String username, String autoNo, String describtion){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> pocessed authorization request # <%s> (%s)", timeStamp,usertype, username, autoNo, describtion));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean recordPasswordReset(String usertype, String username){
		if(!openLogFile()){
			System.out.println("cannot find log.txt");
			return false;
		}
		try {
			java.util.Date date= new java.util.Date();
			String timeStamp = new Timestamp(date.getTime()).toString();
			//fw.write(System.lineSeparator());
			fw.write(String.format("[%s] <%s %s> resetted his/her password ", timeStamp,usertype,username));
			fw.close();
			System.out.println("log file closed");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
