package com.bankapp.util;

import java.util.List;

import com.bankapp.model.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author sunny
 * Reference:- code taken from- http://hmkcode.com/java-itext-pdf-table-structure-style/ 
 */
public class TableBuilder {
	private static final int COLUMN_NUM= 6;
	Style style;
	
	public TableBuilder(){
		style= new Style();
	}
	  // create table
    public PdfPTable createTable(List<Transaction> transactionList) throws DocumentException {
 
        // create 6 column table
        PdfPTable table = new PdfPTable(COLUMN_NUM);
 
        // set the width of the table to 100% of page
        table.setWidthPercentage(100);
 
        // set relative columns width
       // table.setWidths(new float[]{0.6f, 1.4f, 0.8f,0.8f,1.8f,2.6f});
 
        // ----------------Table Header "Title"----------------
        // font
        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
        // create header cell
        PdfPCell cell = new PdfPCell(new Phrase("Account Summary",font));
        // set Column span "1 cell = 6 cells width"
        cell.setColspan(6);
        // set style
        style.headerCellStyle(cell);
        // add to table
        table.addCell(cell);
 
        // insert label
        insertLabelInCell(table);
        
        for(Transaction trans: transactionList){
        	insertValuesInCellPerRow(table, trans);
        }
 
        return table;
    }
    
    private void insertValuesInCellPerRow(PdfPTable table, Transaction trans){
        String type= "C".equals(trans.getType()) ? "CREDIT" : "DEBIT";
        String dateApproved= trans.getDataApproved()== null ? "" :String.valueOf(trans.getDataApproved());
        String remarks= trans.getRemark()==null ? "" :trans.getRemark();
    	
        table.addCell(createValueCell(trans.getTransactionID())); // trans id
        table.addCell(createValueCell(String.valueOf(trans.getAccountId()))); // accountid
        table.addCell(createValueCell(type));                      //type
        table.addCell(createValueCell(String.valueOf(trans.getAmount()))); //amount
        table.addCell(createValueCell(dateApproved)); //date approved
        table.addCell(createValueCell(remarks)); //remark
    }
    
 private void insertLabelInCell(PdfPTable table){
    	
        table.addCell(createLabelCell("TRANSACTION ID")); // create static final fields
        table.addCell(createLabelCell("ACCOUNT ID"));
        table.addCell(createLabelCell("CREDIT/DEBIT"));
        table.addCell(createLabelCell("AMOUNT"));
        table.addCell(createLabelCell("DATE APPROVED"));
        table.addCell(createLabelCell("REMARKS"));
    }
 
    // create cells
    private PdfPCell createLabelCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);
 
        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
        // set style
        style.labelCellStyle(cell);
        return cell;
    }
 
    // create cells
    private PdfPCell createValueCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
 
        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));
 
        // set style
        style.valueCellStyle(cell);
        return cell;
    }

	
}
