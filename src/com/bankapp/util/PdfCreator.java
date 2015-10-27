package com.bankapp.util;

import com.bankapp.model.*;

import org.apache.log4j.Logger;
import java.util.*;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * @author sunny
 *
 */
public class PdfCreator {

	private final String pathName= "AccountStmt";
	private final Logger logger = Logger.getLogger(PdfCreator.class);
	private TableBuilder tableBuilder;
	public PdfCreator(){
		tableBuilder = new TableBuilder();
	}
	
	public String createPdf(List<Transaction> transactionList){
		try{
			Document document = new Document();
			document.setPageSize(PageSize.A4);
 			Date d= new Date();
			String fullPathName= pathName+d.getTime()+".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(fullPathName));
 			document.open();
	        document.add(tableBuilder.createTable(transactionList));
	        document.close();
	        // TODO:- delete that file after sometime- may make some async calls
	        System.out.println("PDF created");
	        logger.info(fullPathName+" pdf created");
	        return fullPathName;
		} catch (Exception e){
			logger.error("Cannot create PDF due to this exception:\n" + e);
			System.out.println("PDF not created");
		}
		return "";
	}
	
}
