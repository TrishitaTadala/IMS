package Invoice_Validation;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class CNT {

	

	public static String PDFtext;
	public static String textPDF;
	public static String  xmlrows;
	public static boolean q;
	public static String CntRemarks ;

	
	public static void main(String[] args) throws Exception {
		//public static void setup() {
    //public static String CreditNote(String pdfpath,String xmlpath) throws Exception {
			CntRemarks ="";
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504942_1038204_202005.pdf";
				//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504192_114251_202002.pdf";
			
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001503_1038204_1590891_20200507.xml";
				//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10000601_114251_1588607_20200220.xml";
		
		            Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"SLEName"));
		System.out.println("*****************CreditNote Billing Address_XML - FrontPage******************"); 
		            Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"CustName"));	            
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"Line1"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"Line2"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"Suburb"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"City"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"State"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"Country"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readBillAddressXML(xmlpath,"PostCode"));
		
		System.out.println("*****************CreditNote Invoice Details_XML - FrontPage******************"); 
		            
		            Compare(getCNTtextPDF(pdfpath,"Front"),readCDtotalXML(xmlpath,"Number")); 
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"BillToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"SoldToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"InvoiceDate"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"DueDate"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"Currency"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"CDSummary"));
			        
	       
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"PricingID"));
        System.out.println("*****************CreditNote Legacy IDs_XML - FrontPage******************");
                    Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"AccountNo"));
                    Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"BillingProfile"));
                    Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"MIPS"));
                    Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"ShipAccountID"));
                    Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSingleNodeXML(xmlpath,"DpID"));    
	
        System.out.println("*****************Page2 onwards CreditNote Validation_XML ******************");
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSingleNodeXML(xmlpath,"SLEName"));
			      	    	
			      	
			System.out.println("*****************CreditNote Invoice Details_XML - Page2******************"); 
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number"));  
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSingleNodeXML(xmlpath,"BillToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSingleNodeXML(xmlpath,"SoldToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSingleNodeXML(xmlpath,"InvoiceDate"));
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSingleNodeXML(xmlpath,"Currency"));
			System.out.println("*****************Credits/Debits Section_XML******************");
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"Credits/Debits"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"InvoiceID"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"Title"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"Description"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"ChargeType"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"CDtotal"));
		    //System.out.println("*****************LastPage_Static Text******************");
		            //Compare(getCNTtextPDF(pdfpath,"Last"),readLastPageStaticText());  
			      	
			      	//return CntRemarks;
		}
		
    public static String getCNTtextPDF(String pdfpath, String Page) throws Exception{
	
	  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
	  
	   PdfReader reader = new PdfReader(pdfpath); 
	   
	   switch (Page){
    
	   case "Front":
	      PDFHashMap.put("FrontPage", getCNTfrontpage(reader));
	     break;
	     
	   case "Last":
		   PDFHashMap.put("LastPage", getCNTfrontpage(reader));
		     break;
		     
	   default:
			 PDFHashMap.put("middlepages", getCNTtext(reader));
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
				 CntRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
		     }
		
		 } 
			
	}

	public static String getCNTfrontpage(PdfReader reader) throws Exception {
		Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
			
	    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
	    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);           
	    
	    strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	        
	    PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, 1, strategy);
	        
	    PDFtext = PDFtext.replace(",","");
	            //System.out.println(PDFtext);
	    return PDFtext;
	   
	}

	public static LinkedHashMap<String,String> readBillAddressXML(String xmlfilepath,String VarType) throws Exception {
	        
	        LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();
	        
	        
	        File xmlinputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(xmlinputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();
	         XPathExpression exprCNTAdd = xpath.compile("//Bill_To_Party/AddressDetails");
	        
	           NodeList PerCNTTransRows = (NodeList)exprCNTAdd.evaluate(doc1, XPathConstants.NODESET);
	           Element PerTransaction = (Element)PerCNTTransRows.item(0);  
	           
	           switch (VarType){
	       	
	         	case "Line1":
	         		try {
	         			CNTFrontPageMap.put("Line1",(PerTransaction.getElementsByTagName("Line1").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;  
	                
	         	case "Line2":
	         		try {
	         			CNTFrontPageMap.put("Line2",(PerTransaction.getElementsByTagName("Line2").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	         	
	         	case "Suburb":
	         		try {
	         			CNTFrontPageMap.put("Suburb",(PerTransaction.getElementsByTagName("Suburb").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "City":
	         		try {
	         			CNTFrontPageMap.put("City",(PerTransaction.getElementsByTagName("City").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "State":
	         		try {
	         			CNTFrontPageMap.put("State",(PerTransaction.getElementsByTagName("State").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "Country":
	         		try {
	         			CNTFrontPageMap.put("Country",(PerTransaction.getElementsByTagName("Country").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "PostCode":
	         		try {
	         			CNTFrontPageMap.put("PostCode",(PerTransaction.getElementsByTagName("Postcode").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	         	
	         		           }
	                return CNTFrontPageMap;               

	  	}
	    
	public static String getCNTlastpage(PdfReader reader) throws Exception {
		Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
			
	    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
	    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);           
	    
	    strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	        
	    PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader,reader.getNumberOfPages() , strategy);
	        
	    PDFtext = PDFtext.replace(",","");
	            //System.out.println(PDFtext);
	    return PDFtext;
	}
	
	public static String getCNTtext(PdfReader reader) throws Exception {
	Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
		
    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            
   for (int i = 2; i <= (reader.getNumberOfPages() -1); i++) {
    
        strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        
        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);
        
    }
    PDFtext = PDFtext.replace(",","");
            //System.out.println(PDFtext);
    return PDFtext;
}

	public static LinkedHashMap<String,String> readCNTSingleNodeXML(String xmlfilepath, String VarType) throws Exception{
	        
	        LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();
	          
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
     
            
         switch (VarType){
    	
    	case "SLEName":  
	         CNTFrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
	         break;
	    		
    	case "BillToRef":   
    		 CNTFrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillingProfileID").item(0).getTextContent());
    		 break;	
    		
    	case "SoldToRef":  
    		 CNTFrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent());
    		 break;
    		
        case "InvoiceDate":   
    		 CNTFrontPageMap.put("InvoiceDate","Date "+doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent());
    		 break;  
    		
        case "Currency":   
     		 CNTFrontPageMap.put("Currency","Currency "+doc1.getElementsByTagName("CustomerInvoiceCurrency").item(0).getTextContent());
     		 break;
     		
        case "CustName":
      		 CNTFrontPageMap.put("CustName",doc1.getElementsByTagName("BillToCustomerName").item(0).getTextContent());
      		 break; 
      		 
        case "CDSummary":
     		 CNTFrontPageMap.put("CDSummary","Credits / Debits "+doc1.getElementsByTagName("TotalAmount").item(1).getTextContent());
     		 break;
     		 
        case "AccountNo":
     		try {
     			String AccNo = (doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent());
     			if (AccNo!= null ||AccNo!= ""||AccNo!= " " ) {
     			CNTFrontPageMap.put("AccountNo","Account Number "+AccNo+".");
     		}
     		}catch(Exception e){}
            break; 
            
        case "BillingProfile":
     		try {
     			CNTFrontPageMap.put("BillingProfile","Billing Profile : "+doc1.getElementsByTagName("BillingProfileId").item(0).getTextContent()+".");
     		}catch(Exception e){}
            break; 
            
        case "DpID":
     		try {
     			CNTFrontPageMap.put("DpId","DP ID: "+(doc1.getElementsByTagName("DPId").item(0).getTextContent())+".");
     		}catch(Exception e){}
            break;
            
        case "ShipAccountID":
     		try {
     			String shipacc = doc1.getElementsByTagName("GWShipAccntId").item(0).getTextContent();
     			
     			if (shipacc!= null||shipacc!= ""||shipacc!= " ") {
     			CNTFrontPageMap.put("ShipAccId","Ship Acct ID: "+shipacc+".");
     			}
     			
     		}catch(Exception e){}
            break;
            
        case "PricingID":
     		try {
     			CNTFrontPageMap.put("Pricing Agreement ID: ","Pricing Agreement ID: "+(doc1.getElementsByTagName("PricingAgreementID").item(0).getTextContent()));
     		}catch(Exception e){}
            break;
            
        case "MIPS":
     		try {
     			String mips = doc1.getElementsByTagName("MIPSMasterAccountId").item(0).getTextContent();    		
     			if (mips!= null||mips!= ""||mips!= " ") {
     			CNTFrontPageMap.put("MIPS","MIPS Master Acc ID: "+mips+".");
     			}
     		}catch(Exception e){}
            break;
            
        case "DueDate":
     		try {
     			String DueDate = doc1.getElementsByTagName("PaymentDueDate").item(0).getTextContent();
     			
     			if (DueDate!= null) {
     			CNTFrontPageMap.put("PDD","Payment due date "+DueDate);
     			}
     		}catch(Exception e){}
            break;
      		 
      	    }       
	        return CNTFrontPageMap;
	            
		}

	public static LinkedHashMap<String,String> readCNTLinesXML(String xmlfilepath,String VarType) throws Exception{
        
        LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();
        

		
          
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprCNT = xpath.compile("//CreditNote/Adjustments/PerLineItem");
        
        NodeList PerCNTTransRows = (NodeList)exprCNT.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerCNTTransRows.getLength(); i++) {
                       
            Element PerCNTTransaction = (Element)PerCNTTransRows.item(i);   
            
            
            
         switch (VarType){
    	
    	case "Credits/Debits":
    		String Source = PerCNTTransaction.getElementsByTagName("Source").item(0).getFirstChild().getTextContent();
    		String ID = PerCNTTransaction.getElementsByTagName("ID").item(0).getFirstChild().getTextContent();
    		if (Source != "SAP") {
    		CNTFrontPageMap.put("Line"+i+" ","Credit/Debit ID: "+ ID);
    		}
    		else {
    			CNTFrontPageMap.put("Line"+i+" ","SAP Ref.No "+ ID);
    		}
    	    break;
    	
    	case "InvoiceID":
    		try {
    		String InvoiceID = PerCNTTransaction.getElementsByTagName("InvoiceId").item(0).getFirstChild().getTextContent();  
    		if (InvoiceID != null) {
    		CNTFrontPageMap.put("Line"+i+" ","Related Invoice ID: "+ InvoiceID);
    		}
    		break;
    		
         }catch(Exception e){}  
    	
    	case "Title":
    		String Title = PerCNTTransaction.getElementsByTagName("Title").item(0).getFirstChild().getTextContent();  
    		CNTFrontPageMap.put("Line"+i+" ","Title: "+ Title);
    		break;	
    	
    	case "Description":
    		String Description = PerCNTTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent(); 
    		String NetAmt = PerCNTTransaction.getElementsByTagName("NetAmount").item(0).getFirstChild().getTextContent();
    		CNTFrontPageMap.put("Line"+i+" ","Description: "+ Description+" "+NetAmt);
    		break;	
    		
    	case "ChargeType":
    		try {
    		String ChargeType = PerCNTTransaction.getElementsByTagName("ChargeType").item(0).getFirstChild().getTextContent();  
    		CNTFrontPageMap.put("Line"+i+" ","Charge Type: "+ ChargeType);
         }catch(Exception e){}
    		break;
    	}   
        
        }  
        
        return CNTFrontPageMap;

	}         
   	
    public static LinkedHashMap<String,String> readCDtotalXML(String xmlfilepath,String VarType) throws Exception {
        
        LinkedHashMap<String, String> CNTSecondPageMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
         XPathExpression exprCNT = xpath.compile("//CreditNote");
        
           NodeList PerCNTTransRows = (NodeList)exprCNT.evaluate(doc1, XPathConstants.NODESET);
           Element PerTransaction = (Element)PerCNTTransRows.item(0);  
           
           switch (VarType){
       	
         	case "CDtotal":
                
            String SummaryTotal = PerTransaction.getElementsByTagName("TotalAmount").item(0).getFirstChild().getTextContent();
                
               
                CNTSecondPageMap.put("CDTotal",("Total Credits / Debits "+SummaryTotal));
                break;  
         	case "Number":
                
                String CNTNumber = PerTransaction.getElementsByTagName("ADJNumber").item(0).getFirstChild().getTextContent();
                    
                CNTSecondPageMap.put("Number",("Number "+CNTNumber));
                    break;  
           }
                return CNTSecondPageMap;               

  	}
    
   
}
  