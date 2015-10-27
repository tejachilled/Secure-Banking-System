package com.bankapp.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.bankapp.services.OTPServiceImpl;

public class OTPTest {
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
	public static void main(String[] args) {
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
		   System.out.println(date.getTime());
		   long t = date.getTime();
		   Date after5 = new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
		   System.out.println(after5.getTime());
    }
  
}


