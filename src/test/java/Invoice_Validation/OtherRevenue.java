//All the variables should be declared
//======================================================
//Class      : Other Revenue
//Description: The Adjustment section in the invoice
//======================================================
//Changes----------------------------------------------
//Date         Test Analyst        Change
//20/05/20     Trishita Tadala     Written
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

public class OtherRevenue {
	
	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String OtherRevenueRemarks;
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		OtherRevenueRemarks ="";
		LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 
		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1173684_945125_201906_OtherRevenue.pdf";
				
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\114302_945125_1173684_20190618_OtherRevenue.xml";
				
		File inputFile = new File(xmlpath);
       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       
       Document doc = dBuilder.parse(inputFile);
       doc.getDocumentElement().normalize();
		
       System.out.println("*****************Page2 onwards Validation_XML ******************");
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"SLEName"));
		
		      	
		System.out.println("*****************Invoice Details_XML - Page2******************"); 
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"InvoiceNumber")); 
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"BillToRef"));
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"SoldToRef"));
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"YourRef"));
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"InvoiceDate"));
		Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"Currency"));

		System.out.println("*****************Other Revenue Section_XML******************");
		
		Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc,"LineItems"));
		Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc,"ServiceDetails"));
		Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc,"PONumber"));
		Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc,"GrandTotal"));
		
		
		
		}
		
   public static String gettextPDF(String pdfpath, String Page) throws Exception{
	
	  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
	  String textPDF = "";
	  
	   PdfReader reader = new PdfReader(pdfpath); 
	   
	   switch (Page){
  
	   default:
			 PDFHashMap.put("middlepages", getText(reader));
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
//				 try {
//				 ExtentManager.test.log(LogStatus.FAIL," Expected: "+xmlrows+" Actual: "+ PDFtext);
//				 }catch(Exception e){}
				 OtherRevenueRemarks  += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
		     }
		
		 } 
			
	}

	public static String getText(PdfReader reader) throws Exception {
	Rectangle EQI = new Rectangle(0, 950, 900, 20);
		
   RenderFilter filter = new RegionTextRenderFilter(EQI);
   TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
           
  for (int i = 2; i <= (reader.getNumberOfPages() -1); i++) {
   
       strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
       
       PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);
       
   }
   PDFtext = PDFtext.replace(",","");
           //System.out.println(PDFtext);
   return PDFtext;
}

	public static LinkedHashMap<String,String> readSingleNodeXML(Document doc1, String VarType) throws Exception{
	        
	        LinkedHashMap<String, String> CAFrontPageMap= new LinkedHashMap<>();              
        
	    switch (VarType){
   	
   	case "SLEName":  
	         CAFrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
	         break;
	         
   	case "InvoiceNumber":   
  		 CAFrontPageMap.put("InvoiceNo","Number "+doc1.getElementsByTagName("InvoiceNumber").item(0).getTextContent());
  		 break; 
  		 
   	case "BillToRef":   
   		 CAFrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillingProfileId").item(0).getTextContent());
   		 break;	
   		
   	case "SoldToRef":  
   		 CAFrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent());
   		 break;
   		
       case "InvoiceDate":   
   		 CAFrontPageMap.put("InvoiceDate","Date "+doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent());
   		 break;  
   		 
       case "YourRef":   
  		 CAFrontPageMap.put("YourRef","Your reference "+(doc1.getElementsByTagName("YourReference").item(0).getTextContent()).replace(",", ""));
  		 break;
   		
       case "Currency":   
    		 CAFrontPageMap.put("Currency","Currency "+doc1.getElementsByTagName("InvoiceCurrency").item(0).getTextContent());
    		 break;
    		
       case "CustName":
     		 CAFrontPageMap.put("CustName",doc1.getElementsByTagName("BillToCustomerName").item(0).getTextContent());
     		 break; 
     		 
      /* case "CASummary":
    		 CAFrontPageMap.put("CASummary","Carrier Airtime "+doc1.getElementsByTagName("TotalAmount").item(1).getTextContent());
    		 break;*/
    		 
       case "AccountNo":
    		try {
    			String AccNo = (doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent());
    			if (AccNo!= null ||AccNo!= ""||AccNo!= " " ) {
    			CAFrontPageMap.put("AccountNo","Account Number "+AccNo+".");
    		}
    		}catch(Exception e){}
           break; 
           
       case "BillingProfile":
    		try {
    			CAFrontPageMap.put("BillingProfile","Billing Profile : "+doc1.getElementsByTagName("BillingProfileId").item(0).getTextContent()+".");
    		}catch(Exception e){}
           break; 
           
       case "DpID":
    		try {
    			CAFrontPageMap.put("DpId","DP ID: "+(doc1.getElementsByTagName("DPId").item(0).getTextContent())+".");
    		}catch(Exception e){}
           break;
           
       case "ShipAccountID":
    		try {
    			String shipacc = doc1.getElementsByTagName("GWShipAcCAId").item(0).getTextContent();
    			
    			if (shipacc!= null||shipacc!= ""||shipacc!= " ") {
    			CAFrontPageMap.put("ShipAccId","Ship Acct ID: "+shipacc+".");
    			}
    			
    		}catch(Exception e){}
           break;
           
       case "PricingID":
    		try {
    			CAFrontPageMap.put("Pricing Agreement ID: ","Pricing Agreement ID: "+(doc1.getElementsByTagName("PricingAgreementID").item(0).getTextContent()));
    		}catch(Exception e){}
           break;
           
       case "MIPS":
    		try {
    			String mips = doc1.getElementsByTagName("MIPSMasterAccountId").item(0).getTextContent();    		
    			if (mips!= null||mips!= ""||mips!= " ") {
    			CAFrontPageMap.put("MIPS","MIPS Master Acc ID: "+mips+".");
    			}
    		}catch(Exception e){}
           break;
           
       case "DueDate":
    		try {
    			String DueDate = doc1.getElementsByTagName("PaymentDueDate").item(0).getTextContent();
    			
    			if (DueDate!= null) {
    			CAFrontPageMap.put("PDD","Payment due date "+DueDate);
    			}
    		}catch(Exception e){}
           break;
           
       case "SalesOrderNo":
    		try {
    			String SalesOrderNo = doc1.getElementsByTagName("SalesOrderNo").item(0).getTextContent();
    			
    			if (SalesOrderNo!= null) {
    			CAFrontPageMap.put("SO","Sales order no "+SalesOrderNo);
    			}
    		}catch(Exception e){}
           break;
           
           
       case "Delivery note no/Waybill Id":
    		try {
    			String Delivery = doc1.getElementsByTagName("DeliveryNoteNoWaybillId").item(0).getTextContent();
    			
    			if (Delivery!= null) {
    			CAFrontPageMap.put("DN","Delivery note no/Waybill Id "+Delivery);
    			}
    		}catch(Exception e){}
           break;
           
       case "ShipmentDate":
    		try {
    			String ShipmentDate = doc1.getElementsByTagName("ShipmentDate").item(0).getTextContent();
    			
    			if (ShipmentDate!= null) {
    			CAFrontPageMap.put("SD","Shipment date "+ShipmentDate);
    			}
    		}catch(Exception e){}
           break;
                  
       case "CountryOfDelivery":
    		try {
    			String CountryOfDelivery = doc1.getElementsByTagName("CountryOfDelivery").item(0).getTextContent();
    			
    			if (CountryOfDelivery!= null) {
    			CAFrontPageMap.put("SD","Country of delivery "+CountryOfDelivery);
    			}
    		}catch(Exception e){}
           break;
       
       case "TotalEQI":  
  		 CAFrontPageMap.put("TotalEQS","Total Equipment Installation and Service "+doc1.getElementsByTagName("EquipInstallTotalCharge").item(0).getTextContent());
  		 break;   
           
           
     	    }       
	        return CAFrontPageMap;
	            
		}
  
	public static LinkedHashMap<String,String> readOtherRevenueLinesXML(Document doc1, String VarType) throws Exception{
       
       LinkedHashMap<String, String> ORTableRowsMap= new LinkedHashMap<>();
 
       XPath xpath = XPathFactory.newInstance().newXPath();

       
           
           switch (VarType) {    
           
       case "ServiceDetails":
        	   
        	   XPathExpression exprOR1 = xpath.compile("//OtherRevenue/PerCharge");
               
               NodeList PerOR1Rows = (NodeList)exprOR1.evaluate(doc1, XPathConstants.NODESET);
               
               for (int i = 0; i < PerOR1Rows.getLength(); i++) {
                              
                   Element PerOR1Transaction = (Element)PerOR1Rows.item(i);
        	   String ServiceStartDate = PerOR1Transaction.getElementsByTagName("PeriodStartDate").item(0).getFirstChild().getTextContent(); 
        	   String ServiceEndDate = PerOR1Transaction.getElementsByTagName("PeriodEndDate").item(0).getFirstChild().getTextContent();   
        	   
        	   
        	   ORTableRowsMap.put("Line"+i+" ","Service Start:"+ServiceStartDate+" "+"Service End:"+ServiceEndDate);
               }
               break;
               
       case "PONumber":
    	   
    	   XPathExpression exprOR2 = xpath.compile("//OtherRevenue/PerCharge");
           
           NodeList PerOR2Rows = (NodeList)exprOR2.evaluate(doc1, XPathConstants.NODESET);
           
           for (int i = 0; i < PerOR2Rows.getLength(); i++) {
                          
               Element PerOR2Transaction = (Element)PerOR2Rows.item(i);
    	   String PONumber = PerOR2Transaction.getElementsByTagName("PONumber").item(0).getFirstChild().getTextContent();    	   
    	   
    	   ORTableRowsMap.put("Line"+i+" ","PO Number: "+PONumber);
           }
           break;
         
         case "GrandTotal":

      	   String GrandTotalAdj = doc1.getElementsByTagName("TotalOtherRevenue").item(0).getFirstChild().getTextContent();   
      	 ORTableRowsMap.put("GrandTotalAdj","Total Other Revenue "+GrandTotalAdj);
           
             break;
               
               
       	 default:      		 
       	         
       		 XPathExpression exprOR = xpath.compile("//OtherRevenue/PerCharge");
             
             NodeList PerOrRows = (NodeList)exprOR.evaluate(doc1, XPathConstants.NODESET);
             
             for (int j = 0; j < PerOrRows.getLength(); j++) {
                            
                 Element PerORTransaction = (Element)PerOrRows.item(j);
                 
                String ProductID = PerORTransaction.getElementsByTagName("ProductID").item(0).getFirstChild().getTextContent();
                String Desc = PerORTransaction.getElementsByTagName("InvoiceTxt").item(0).getFirstChild().getTextContent();
                String Units = PerORTransaction.getElementsByTagName("Units").item(0).getFirstChild().getTextContent();
                String UoM = PerORTransaction.getElementsByTagName("UoM").item(0).getFirstChild().getTextContent();
                String Rate = PerORTransaction.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
                String TotalCharge = PerORTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
                //ProductID+" "+Desc+
                ORTableRowsMap.put("Line"+j+" ",ProductID+" "+Desc+" "+Units+" "+UoM+" "+Rate+" "+TotalCharge);
             }
             
       		break;
                
        
			}
       

   
       return ORTableRowsMap;
	}
	
}



