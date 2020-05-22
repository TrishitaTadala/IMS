//All the variables should be declared
//======================================================
//Class      : TaxInfo
//Description: The Tax Information in the Front Page of the invoice
//======================================================
//Changes----------------------------------------------
//Date         Test Analyst        Change
//22/05/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;

import java.util.LinkedHashMap;
import java.util.Iterator;

import org.junit.Test;

import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import javax.xml.xpath.XPathConstants;

import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPath;

import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Element;

public class TaxInfo {
	
	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String Remarks;
	
	//public static void FrontPage throws Exception {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Remarks ="";
		LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 
		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1386249_986021_201904.pdf";
				
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\117976_986021_1386249_20190430.xml";
				
		File inputFile = new File(xmlpath);
       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       
       Document doc1 = dBuilder.parse(inputFile);
       doc1.getDocumentElement().normalize();
       
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"SLEName"));
       System.out.println("*****************FrontPage Validation_XML ******************");
       Compare(gettextPDF(pdfpath,"Address"),readSingleNodeXML(doc1,"CustName"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Line1"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Line2"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Suburb"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"City"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"State"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Country"));
       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"PostCode"));
      //Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"VAT"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"PricingID"));
       
       System.out.println("*****************Invoice Details_XML - FRONT PAGE******************"); 
       // Compare(getCAtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number")); 
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"InvoiceNo"));
	   Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"BillToRef"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"SoldToRef"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"InvoiceDate"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"YourRef"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"YourRef2"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"BPED"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"DueDate"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"Currency"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"PONumber"));
       System.out.println("*****************FrontPage Legacy IDs Validation_XML ******************");
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"AccountNo"));
       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"BillingProfile"));
       

	    System.out.println("*****************Page2 Validation_XML ******************");
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"SLEName"));
						      	
		System.out.println("*****************Invoice Details_XML - Page2******************"); 
		       // Compare(getCAtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number")); 
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"InvoiceNo"));
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"BillToRef"));
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"SoldToRef"));
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"YourRef"));
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"InvoiceDate"));
		 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"Currency"));
		System.out.println("*****************Tax Information Section_XML******************");

	}
	
public static String gettextPDF(String pdfpath, String Page) throws Exception{

 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
 String textPDF = "";
 
  PdfReader reader = new PdfReader(pdfpath); 
  
  switch (Page){

  case "FrontPage":
	   
	   PDFHashMap.put("frontpage", getPageText(reader,1));
	   
	   break;
    
  case "Address":
	  
	   PDFHashMap.put("Address", getBillingAddress(reader));
	   break;
	   

  default:
		 PDFHashMap.put("middlepages", getPageText(reader,2));
		 break;
  }

Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();

while(PDFkeySetIterator.hasNext()) {
	
	String key = PDFkeySetIterator.next();
	
	textPDF += PDFHashMap.get(key);
	//System.out.println(PDFtext);
	
	}
return textPDF;


}
	
public static void Compare(String PDFtext,LinkedHashMap<String,String> XMLHashMap){
	
	Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
    while(XMLkeySetIterator.hasNext()) {
		
		String keyXML = XMLkeySetIterator.next();
		
		xmlrows = XMLHashMap.get(keyXML).replaceAll("\n", "");
		//System.out.println(xmlrows);
	
	if (PDFtext.indexOf(xmlrows)!=-1? true: false){
		
		System.out.println(xmlrows +" It's a Match!!!");
		 q = true;
				
	    }
	else{
		System.out.println( xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~");
			 q = false;	
			 Remarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
	     }
	
	 } 
		
}


public static LinkedHashMap<String,String> readBillAddressXML(Document doc1,String VarType) throws Exception {
       
       LinkedHashMap<String, String> FrontPageMap= new LinkedHashMap<>();
      
       XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression exprCAAdd = xpath.compile("//Bill_To_Party/AddressDetails");
       
          NodeList PerCATransRows = (NodeList)exprCAAdd.evaluate(doc1, XPathConstants.NODESET);
          Element PerTransaction = (Element)PerCATransRows.item(0);  
          
          switch (VarType){
      	
        	case "Line1":
         		 
         		try {
                   String[] Line1 = doc1.getElementsByTagName("Line1").item(0).getTextContent().replace (",",""
                  		 ).split(" "); 
                   int btcn =0;
                   do {
                   	FrontPageMap.put(btcn+"Line",Line1[btcn]);
              	
                   	btcn++;
                     }while (Line1[btcn]!= "" ||Line1[btcn]!= null);
                   
              	 }catch(Exception e){}
              	 break;  
               
        	case "Line2":
        		try {
        			FrontPageMap.put("Line2",(PerTransaction.getElementsByTagName("Line2").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
        		}catch(Exception e){}
               break;
        	
        	case "Suburb":
        		try {
        			FrontPageMap.put("Suburb",(PerTransaction.getElementsByTagName("Suburb").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
        		}catch(Exception e){}
               break;
               
        	case "City":
        		try {
        			FrontPageMap.put("City",(PerTransaction.getElementsByTagName("City").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
        		}catch(Exception e){}
               break;
               
        	case "State":
        		try {
        			FrontPageMap.put("State",(PerTransaction.getElementsByTagName("State").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
        		}catch(Exception e){}
               break;
               
        	case "Country":
        		try {
        			FrontPageMap.put("Country",(PerTransaction.getElementsByTagName("Country").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
        		}catch(Exception e){}
               break;
               
        	case "PostCode":
        		try {
        			FrontPageMap.put("PostCode",(PerTransaction.getElementsByTagName("Postcode").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
        		}catch(Exception e){}
               break;
        	
        		           }
               return FrontPageMap;               

 	}
   

public static String getPageText(PdfReader reader,int Pg) throws Exception {
Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
	
RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
       
for (int i = Pg; i <= (reader.getNumberOfPages() -1); i++) {

   strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
   
   PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);
   
}
PDFtext = PDFtext.replace(",","");
       //System.out.println(PDFtext);
return PDFtext;
}

public static String getBillingAddress(PdfReader reader) throws Exception {		    
   Rectangle billingAddressSection = new Rectangle(0, 620, 200, 750);
   RenderFilter filter = new RegionTextRenderFilter(billingAddressSection);
   TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
   //System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));     
   return PdfTextExtractor.getTextFromPage(reader, 1, strategy);
     
}

public static LinkedHashMap<String,String> readSingleNodeXML(Document doc1, String VarType) throws Exception{
       
       LinkedHashMap<String, String> FrontPageMap= new LinkedHashMap<>();
   
       
    switch (VarType){
	
	case "SLEName":  
		FrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
        break;
        
	case "InvoiceNo":  
		FrontPageMap.put("InvoiceNo","Number "+doc1.getElementsByTagName("InvoiceNumber").item(0).getTextContent());
        break;
   		
	case "BillToRef":   
		FrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillingProfileID").item(0).getTextContent());
		 break;	
		
	case "SoldToRef":  
		FrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent());
		 break;
	
	case "YourRef":  
		String YourRef = doc1.getElementsByTagName("YourReference").item(0).getTextContent();
		
	   	if (YourRef.isEmpty()  ) {}
	   	else {FrontPageMap.put("PONumber","Your reference "+YourRef);}
		 break;
	case "YourRef2":  
		FrontPageMap.put("YourRef2","Your additional info "+doc1.getElementsByTagName("YourReference2").item(0).getTextContent());
		 break;
		 
	case "BPED":  
		FrontPageMap.put("BPED","Billing period end date "+doc1.getElementsByTagName("ChargePeriodEndDate").item(0).getTextContent());
		 break;
		
   case "InvoiceDate":   
   	FrontPageMap.put("InvoiceDate","Date "+doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent());
		 break;  
		
   case "Currency":   
   	FrontPageMap.put("Currency","Currency "+doc1.getElementsByTagName("CustomerInvoiceCurrency").item(0).getTextContent());
		 break;
	
   case "PONumber":
	   	String PONumber = (doc1.getElementsByTagName("OrderNumber").item(0).getTextContent());
			
	   	if (PONumber.isEmpty()  ) {}
	   	else {FrontPageMap.put("PONumber","PO Number: "+PONumber);}
		
      break; 
   case "CustName":
   		      		 
 		try {
           String[] CustName = doc1.getElementsByTagName("BillToCustomerName").item(0).getTextContent().replace (",",""
          		 ).split(" "); 
           int btcn =0;
           do {
           	FrontPageMap.put(btcn+"Line",CustName[btcn]);
      	
           	btcn++;
             }while (CustName[btcn]!= "" ||CustName[btcn]!= null);
           
      	 }catch(Exception e){}
      	 break;
 		 

   case "AccountNo":
  
		String AccountNo = doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent();
		
	   	if (AccountNo.isEmpty()  ) {}
	   	else {FrontPageMap.put("AccountNo","Account Number: "+AccountNo+".");}
		 break;
       
   case "BillingProfile":
	   			
			String BPCode = doc1.getElementsByTagName("BillingProfileCode").item(0).getTextContent();
			if (BPCode.isEmpty()  ) {}
		   	else {FrontPageMap.put("BPCode","Billing Profile: "+BPCode+".");}
			 break;
			 
   case "DpID":
	   
	   String DpID = doc1.getElementsByTagName("DPId").item(0).getTextContent();
		if (DpID.isEmpty()  ) {}
	   	else {FrontPageMap.put("BPCode","DP ID: "+DpID+".");}
		 break;
		
       
   case "ShipAccountID":
		try {
			String shipacc = doc1.getElementsByTagName("GWShipAcCAId").item(0).getTextContent();
			
			if (shipacc!= null||shipacc!= ""||shipacc!= " ") {
				FrontPageMap.put("ShipAccId","Ship Acct ID: "+shipacc+".");
			}
			
		}catch(Exception e){}
       break;
       
   case "PricingID":
	   
	   String PricingID = doc1.getElementsByTagName("PricingAgreementID").item(0).getTextContent();
		if (PricingID.isEmpty()  ) {}
	   	else {FrontPageMap.put("Pricing Agreement ID: ","Pricing Agreement ID: "+PricingID);}

       break;
       
   case "MIPS":
		try {
			String mips = doc1.getElementsByTagName("MIPSMasterAccountId").item(0).getTextContent();    		
			if (mips!= null||mips!= ""||mips!= " ") {
				FrontPageMap.put("MIPS","MIPS Master Acc ID: "+mips+".");
			}
		}catch(Exception e){}
       break;
       
   case "DueDate":
		try {
			String DueDate = doc1.getElementsByTagName("PaymentDueDate").item(0).getTextContent();
			
			if (DueDate!= null) {
				FrontPageMap.put("PDD","Payment due date "+DueDate);
			}
		}catch(Exception e){}
       break;
       
   case "VAT":
		try {
			String vat = doc1.getElementsByTagName("VATRegistrationNumber").item(0).getTextContent();
			
			if (vat!= null) {
				FrontPageMap.put("vat","Payment due date "+vat);
			}
		}catch(Exception e){}
      break;
 		 
 	    }       
       return FrontPageMap;
           
	}

/******************************************************************************************************************/



}


