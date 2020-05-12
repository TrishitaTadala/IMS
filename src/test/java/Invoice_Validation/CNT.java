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
	public static String CntRemarks ="";

	
	//public static void main(String[] args) throws Exception {
		public static String CreditNote(String pdfpath,String xmlpath) throws Exception {
		
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 

		//String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504942_1038204_202005.pdf";
				//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504192_114251_202002.pdf";
			
		//String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001503_1038204_1590891_20200507.xml";
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
			      	
			      	return CntRemarks;
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
     			CNTFrontPageMap.put("AccountNo",(doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent())+".");
     		}catch(Exception e){}
            break; 
            
        case "BillingProfile":
     		try {
     			CNTFrontPageMap.put("AccountNo","Billing Profile : "+doc1.getElementsByTagName("BillingProfileId").item(0).getTextContent()+".");
     		}catch(Exception e){}
            break; 
            
        case "DpID":
     		try {
     			CNTFrontPageMap.put("DP ID: ","DP ID: "+(doc1.getElementsByTagName("DPId").item(0).getTextContent())+".");
     		}catch(Exception e){}
            break;
            
        case "ShipAccountID":
     		try {
     			CNTFrontPageMap.put("Ship Account ID: ","Ship Acct ID: "+(doc1.getElementsByTagName("GWShipAccntId").item(0).getTextContent())+".");
     		}catch(Exception e){}
            break;
            
        case "PricingID":
     		try {
     			CNTFrontPageMap.put("Pricing Agreement ID: ","Pricing Agreement ID: "+(doc1.getElementsByTagName("PricingAgreementID").item(0).getTextContent()));
     		}catch(Exception e){}
            break;
            
        case "MIPS":
     		try {
     			CNTFrontPageMap.put("MIPS","MIPS Master Acc ID: "+doc1.getElementsByTagName("MIPSMasterAccountId").item(0).getTextContent()+".");
     		}catch(Exception e){}
            break;
            
        case "DueDate":
     		try {
     			CNTFrontPageMap.put("PDD","Payment due date "+doc1.getElementsByTagName("PaymentDueDate").item(0).getTextContent());
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
    		String ChargeType = PerCNTTransaction.getElementsByTagName("ChargeType").item(0).getFirstChild().getTextContent();  
    		CNTFrontPageMap.put("Line"+i+" ","Charge Type: "+ ChargeType);
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
    
    public static LinkedHashMap<String,String> readLastPageStaticText() throws Exception {
		
		LinkedHashMap<String, String> LastPageRowsMap= new LinkedHashMap<>();
		
		LastPageRowsMap.put("Support & Inquiries", "Enterprise, Aviation and Government customers:"+"\n" 
				+"Billing enquiry (except LPI)"+"\n" + 
				"   globalcustomersupport@inmarsat.com"+"\n" + 
				"\n" + 
				"Please contact Global Customer Operations with any query or dispute "+"\n" + 
				"   +44 (0) 207 728 1020" +"\n" + 
				"regarding this invoice within seven days of the invoice date.  Please  "+"\n" + 
				"reference your Bill To number and invoice number in any correspondence. Maritime customers:"+"\n" + 
				"   support.maritime@inmarsat.com"+"\n" + 
				"   +47 (0) 70 17 24 00"+"\n" + 
				"Customers located in North America:"+"\n" + 
				"Late Payment Interest (LPI) Enquiry"+"\n" + 
				"    collections-americas@inmarsat.com"+"\n" + 
				"\n" + 
				"Please contact the Credit & Collections team with any query or dispute  "+"\n" + 
				"regarding late payment interest within seven days of the invoice date.  Customers located in Europe, the Middle East, or Africa:"+"\n" + 
				"Please reference your Bill To number and invoice number in any     collections-EMEA@inmarsat.com"+"\n" + 
				"correspondence."+"\n" + 
				" \n" + 
				"Customer located in Asia or the Pacific:"+"\n" + 
				"    collections-APAC@inmarsat.com"+"\n" + 
				"AR.inquiries@inmarsat.com\n" + 
				"Payment information"+"\n" + 
				"\n" + 
				"Please contact the Accounts Receivable team for questions relating to how "+"\n" + 
				"your payments are received and applied."+"\n" + 
				"Enterprise, Aviation and Government customers:"+"\n" + 
				"Technical support information"+"\n" + 
				"    globalcustomersupport@inmarsat.com"+"\n" + 
				"\n" + 
				"Inmarsat is dedicated to delivering to you the best customer support possible    +44 (0) 207 728 1020"+"\n" + 
				"by going beyond your expectations, no matter where you are or what time of  "+"\n" + 
				"day it is.  Global Customer Operations operates 24-hours a day, 365-days a Maritime customers:"+"\n" + 
				"year.    support.maritime@inmarsat.com"+"\n" + 
				"   +47 (0) 70 17 24 00"+"\n" + 
				"Enterprise, Aviation and Government customers:"+"\n" + 
				"Sales support information"+"\n" + 
				"   globalcustomersupport@inmarsat.com"+"\n" + 
				"   +44 (0) 207 728 1020"+"\n" + 
				" "+"\n" + 
				"Maritime customers:"+"\n" + 
				"   support.maritime@inmarsat.com"+"\n" + 
				"   +47 (0) 70 17 24 00");

		
     	LastPageRowsMap.put("Glossary -","Glossary");
				
		LastPageRowsMap.put("Glossary - CF1","Call Forwarded. For example where a SIM has been configured so that an incoming call is");
		LastPageRowsMap.put("Glossary - CF2","forwarded to another number, eg a corporate office.");
				
		LastPageRowsMap.put("Glossary - ICCID1","Integrated Circuit Card Identifier. Unique identifier, stored within and printed on a SIM");
		LastPageRowsMap.put("Glossary - ICCID2","(Subscriber Identity Module) card.");
		
		LastPageRowsMap.put("Glossary - IMN1","Inmarsat Mobile Number.  The diallable number for a subscriber using an Inmarsat I-3");
		LastPageRowsMap.put("Glossary - IMN2","terminal.");
		
		LastPageRowsMap.put("Glossary - IMSI1","International Mobile Subscriber Identity. A unique number, usually 15 digits, identifying a");
		LastPageRowsMap.put("Glossary - IMSI2","subscriber in a mobile telecommunication network.");
		
		LastPageRowsMap.put("Glossary - ISN1","Inmarsat Serial Number.  The hardware ID on an Inmarsat I-3 terminal.  A single terminal");
		LastPageRowsMap.put("Glossary - ISN2","may have several related IMNs.");
		
		LastPageRowsMap.put("Glossary - MB1","Megabyte.   Measurement unit for airtime.   Refer to the relevant commercial documentation");
		LastPageRowsMap.put("Glossary - MB2"," for further details");
		
		LastPageRowsMap.put("Glossary - MO","Mobile Originated. Call is initiated on a user terminal. Also referred to as 'From Mobile' traffic.");
					
		LastPageRowsMap.put("Glossary - MSISDN1","Mobile Station International Subscriber Directory Number. A number used to route calls to");
		LastPageRowsMap.put("Glossary - MSISDN2","and from a terminal in a mobile telecommunications network.");
				
		LastPageRowsMap.put("Glossary - MT","Mobile Terminated. Call is received by a user terminal. Also referred to as 'To Mobile' traffic.");
				
		LastPageRowsMap.put("Glossary -SCAP1","Shared Corporate Allowance Plan. A type of rate plan which allows many users to utilise a");
		LastPageRowsMap.put("Glossary -SCAP2","single allowance.");
		
		LastPageRowsMap.put("Glossary - sqt1","Super Quiet Time. A commercial offer which provides advantageous rates for qualifying");
		LastPageRowsMap.put("Glossary - sqt2","traffic. May have time-based restrictions.");
		
		LastPageRowsMap.put("Glossary -UoM1","Unit of Measure. Used to quantify the volume or amount of services or goods provided.");
		LastPageRowsMap.put("Glossary -UoM2"," Eg Minute, Megabyte. Usually abbreviated.");
		
		
		LastPageRowsMap.put("Glossary -utc1","Universal Coordinated Time. Equivalent to Greenwich Mean Time for most purposes. All");
		LastPageRowsMap.put("Glossary -utc2","dates and times in this invoice/credit note are in UTC, unless otherwise stated.");
		
	
		LastPageRowsMap.put("Glossary -last","Please be aware that for some airtime, a call setup charge, in additional to a volume based charge, may be applicable."
				+"\n"+"Please refer to the relevant pricing documentation for further information.");
		
		
		
		return LastPageRowsMap;
	
	}
}
  