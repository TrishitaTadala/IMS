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

public class AirtimePrepaid {

	public static String PDFtext;
	public static String textPDF;
	public static String  xmlrows;
	public static boolean q;
	public static String CARemarks ;

	
	public static void main(String[] args) throws Exception {
    //public static String carrierAirtime(String pdfpath,String xmlpath) throws Exception {
			CARemarks ="";
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Prepay_GW\\INVOICE_1330369_1040039_201910.pdf";		
			
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\Prepay_GW\\117256_1040039_1330369_20191031.xml";
				

		System.out.println("********************Service Summary Validation*************************\n");
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSGroupXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSProductXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSActivationXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSSubscriptionsXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSCancellationXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSEarlyTerminationXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSUsageXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSStotalXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSSubtotalXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSAllTotalXML(xmlpath));
		            Compare(getCAtextPDF(pdfpath,"Default"),readSSGrandTotalXML(xmlpath));
            System.out.println("*****************Page2 onwards Validation_XML ******************");
			      	Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"SLEName"));
			System.out.println("*****************Fee Detail Validation_XML ******************");    	
			      	Compare(getCAtextPDF(pdfpath,"Default"),readFDTotalsXML(xmlpath,"ServiceGroup"));//Fee Detail ServiceGroup
					Compare(getCAtextPDF(pdfpath,"Default"),readFDTotalsXML(xmlpath,"ProductGroup"));//Fee Detail ServiceGroup
					Compare(getCAtextPDF(pdfpath,"Default"),readFDTotalsXML(xmlpath,"RatePlan"));//Fee Detail RatePlan
					Compare(getCAtextPDF(pdfpath,"Default"),readFeeDetailRecurringLinesXML(xmlpath)); //Fee Detail Recurring Lines
					Compare(getCAtextPDF(pdfpath,"Default"),readFeeDetailOneoffLinesXML(xmlpath)); //Fee Detail OneOff Lines
					Compare(getCAtextPDF(pdfpath,"Default"),readFDTotalsXML(xmlpath,"TotalFD")); //Fee Detail Totals
					Compare(getCAtextPDF(pdfpath,"Default"),readFDChargeTotalsXML(xmlpath));//Fee Detail Charge Totals
			      	    	
			      	
			System.out.println("*****************Invoice Details_XML - Page2******************"); 
			       // Compare(getCAtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number"));  
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"BillToRef"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"SoldToRef"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"InvoiceDate"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"Currency"));
			System.out.println("*****************Prepaid Airtime Section_XML******************");
			        Compare(getCAtextPDF(pdfpath,"Default"),readPALinesXML(xmlpath,"ProductID")); 
			        Compare(getCAtextPDF(pdfpath,"Default"),readPALinesXML(xmlpath,"ProdDesc"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readPALinesXML(xmlpath,"LinesItems"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readPALinesXML(xmlpath,"AdditionalInfo1"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readPALinesXML(xmlpath,"AdditionalInfo2"));
			       
			        Compare(getCAtextPDF(pdfpath,"Default"),readPAGrandTotalXML(xmlpath));
		    //System.out.println("*****************LastPage_Static Text******************");
		            //Compare(getCAtextPDF(pdfpath,"Last"),readLastPageStaticText());  
			      	
			      	//return CARemarks;
		}
		
    public static String getCAtextPDF(String pdfpath, String Page) throws Exception{
	
	  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
	  
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
				 CARemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
		     }
		
		 } 
			
	}


	public static LinkedHashMap<String,String> readBillAddressXML(String xmlfilepath,String VarType) throws Exception {
	        
	        LinkedHashMap<String, String> CAFrontPageMap= new LinkedHashMap<>();
	        
	        
	        File xmlinputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(xmlinputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();
	         XPathExpression exprCAAdd = xpath.compile("//Bill_To_Party/AddressDetails");
	        
	           NodeList PerCATransRows = (NodeList)exprCAAdd.evaluate(doc1, XPathConstants.NODESET);
	           Element PerTransaction = (Element)PerCATransRows.item(0);  
	           
	           switch (VarType){
	       	
	         	case "Line1":
	         		try {
	         			CAFrontPageMap.put("Line1",(PerTransaction.getElementsByTagName("Line1").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;  
	                
	         	case "Line2":
	         		try {
	         			CAFrontPageMap.put("Line2",(PerTransaction.getElementsByTagName("Line2").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	         	
	         	case "Suburb":
	         		try {
	         			CAFrontPageMap.put("Suburb",(PerTransaction.getElementsByTagName("Suburb").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "City":
	         		try {
	         			CAFrontPageMap.put("City",(PerTransaction.getElementsByTagName("City").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "State":
	         		try {
	         			CAFrontPageMap.put("State",(PerTransaction.getElementsByTagName("State").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "Country":
	         		try {
	         			CAFrontPageMap.put("Country",(PerTransaction.getElementsByTagName("Country").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	                
	         	case "PostCode":
	         		try {
	         			CAFrontPageMap.put("PostCode",(PerTransaction.getElementsByTagName("Postcode").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	         		}catch(Exception e){}
	                break;
	         	
	         		           }
	                return CAFrontPageMap;               

	  	}
	    

	public static String getText(PdfReader reader) throws Exception {
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

	public static LinkedHashMap<String,String> readCASingleNodeXML(String xmlfilepath, String VarType) throws Exception{
	        
	        LinkedHashMap<String, String> CAFrontPageMap= new LinkedHashMap<>();
	          
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
     
            
         switch (VarType){
    	
    	case "SLEName":  
	         CAFrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
	         break;
	    		
    	case "BillToRef":   
    		 CAFrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillingProfileID").item(0).getTextContent());
    		 break;	
    		
    	case "SoldToRef":  
    		 CAFrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent());
    		 break;
    		
        case "InvoiceDate":   
    		 CAFrontPageMap.put("InvoiceDate","Date "+doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent());
    		 break;  
    		
        case "Currency":   
     		 CAFrontPageMap.put("Currency","Currency "+doc1.getElementsByTagName("CustomerInvoiceCurrency").item(0).getTextContent());
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
      		 
      	    }       
	        return CAFrontPageMap;
	            
		}

	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSActivationXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String Activation = PerProductDetails.getElementsByTagName("Activation").item(0).getFirstChild().getTextContent();
                              
                String  ServiceSummaryTableRow = ("Fee - Activations "+Activation);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}
	
	
	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSSubscriptionsXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
           
            String Subscription = PerProductDetails.getElementsByTagName("Subscription").item(0).getFirstChild().getTextContent(); //String Total = PerProductDetails.getElementsByTagName("Total").item(0).getFirstChild().getTextContent();
           
            String  ServiceSummaryTableRow = ("Fee - Subscriptions "+Subscription);
            ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
            
        }  catch(Exception e){}    
        }
             return ServiceSummaryTableRowsMap;               
  
	}

	
	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSCancellationXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String Cancellation = PerProductDetails.getElementsByTagName("Cancellation").item(0).getFirstChild().getTextContent();
               
                String  ServiceSummaryTableRow = ("Fee - Cancellations "+Cancellation);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}

	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSEarlyTerminationXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String EarlyTermination = PerProductDetails.getElementsByTagName("EarlyTermination").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Fee - Early Terminations "+EarlyTermination);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}

	/****************************************************************************/   
    public static LinkedHashMap<String,String> readSSUsageXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String Usage = PerProductDetails.getElementsByTagName("Usage").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Airtime "+Usage);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}
      
      /****************************************************************************/   
    public static LinkedHashMap<String,String> readSSOtherXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String Other = PerProductDetails.getElementsByTagName("Other").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Fee - Other "+Other);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}
      
      
  	/****************************************************************************/   
    public static LinkedHashMap<String,String> readSStotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String Total = PerProductDetails.getElementsByTagName("Total").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Total "+Total);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}
      
    /****************************************************************************/   
    public static LinkedHashMap<String,String> readSSSubtotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/Subtotal");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String SubTotal = PerProductDetails.getElementsByTagName("ProductGroupTotalSum").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Total for Service Group "+SubTotal);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}
      
  	/****************************************************************************/   
    public static LinkedHashMap<String,String> readSSAllTotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/TotalOfAllProductGroups");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String SubTotal = PerProductDetails.getElementsByTagName("Total").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Total All Service Groups "+SubTotal);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}     
   	/****************************************************************************/   
    public static LinkedHashMap<String,String> readSSGrandTotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//TotalForServiceSummary");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String GrandTotal = PerProductDetails.getElementsByTagName("GrandTotal").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Total Service Summary "+GrandTotal);
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}

	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSProductXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryGroupRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            //String ServiceGroup = PerProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
            String Product = PerProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();         
            ServiceSummaryGroupRowsMap.put("ProductOnly"+i,Product);
            
        }       
             return ServiceSummaryGroupRowsMap;               
  
	}
		
	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSGroupXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryGroupRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
       
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            String ServiceGroup = PerProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
            //String Product = PerProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();         
            ServiceSummaryGroupRowsMap.put("ServiceGroupOnly"+i,ServiceGroup);
            
        }       
             return ServiceSummaryGroupRowsMap;               
  
	}	
	public static LinkedHashMap<String,String> readFeeDetailRecurringLinesXML(String xmlfilepath) throws Exception{
	        
	        LinkedHashMap<String, String> FeeDetailTableRowsMap= new LinkedHashMap<>();
	              
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();

	        XPathExpression exprRecurring = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/BillingCharges/RecurringDetails");
	        
	        NodeList PerTransRows = (NodeList)exprRecurring.evaluate(doc1, XPathConstants.NODESET);
	        for (int i = 0; i < PerTransRows.getLength(); i++) {
	                       
	            Element PerTransaction = (Element)PerTransRows.item(i);
	            
	            try {
	            String SiteInfo = PerTransaction.getElementsByTagName("SiteInformation").item(0).getFirstChild().getTextContent();
	            String SiteRef = PerTransaction.getElementsByTagName("SiteReference").item(0).getFirstChild().getTextContent();
	            		//.replaceAll(null,"");
	            String MSISDN = PerTransaction.getElementsByTagName("MSISDN").item(0).getFirstChild().getTextContent();
	            //String ChargeGroup = PerTransaction.getElementsByTagName("ChargeGroup").item(0).getFirstChild().getTextContent();
	            //String ServiceGroup = PerTransaction.getElementsByTagName("ServiceGroup").item(0).getFirstChild().getTextContent();
	            String PeriodStartDate = PerTransaction.getElementsByTagName("PeriodStartDate").item(0).getFirstChild().getTextContent();
	            String PeriodEndDate = PerTransaction.getElementsByTagName("PeriodEndDate").item(0).getFirstChild().getTextContent();
	            String Units = PerTransaction.getElementsByTagName("Quantity").item(0).getFirstChild().getTextContent();
	            String UOM = PerTransaction.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
	            String Rate = PerTransaction.getElementsByTagName("UnitPrice").item(0).getFirstChild().getTextContent();
	            String TotalCharge = PerTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
	            
	            String  FeeDetailTableRow = SiteInfo+" " +SiteRef+" "+MSISDN+ " " +PeriodStartDate+" " +PeriodEndDate
	            		                    +" "+Units+" "+UOM+" "+Rate+" "+TotalCharge;
	            FeeDetailTableRowsMap.put("Line"+i+" ",FeeDetailTableRow );
	            }  catch(Exception e){} 
	            
	        }
	            
	        return FeeDetailTableRowsMap;
	            
		}
	   public static LinkedHashMap<String,String> readFDTotalsXML(String xmlfilepath,String VarType) throws Exception{
	        
	        LinkedHashMap<String, String> FeeDetailTableTotalMap= new LinkedHashMap<>();
	              
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();

	        XPathExpression exprRecurring = xpath.compile("//CustomerNodeList/CustomerNode/FeeDetailsSubtotal/PerChargeGroup");
	        
	        NodeList PerTransRows = (NodeList)exprRecurring.evaluate(doc1, XPathConstants.NODESET);
	        for (int i = 0; i < PerTransRows.getLength(); i++) {
	                       
	            Element PerTransaction = (Element)PerTransRows.item(i);       
	            
	         switch (VarType){
	    	
	    	case "TotalFD":
	    		String TotalFD = PerTransaction.getElementsByTagName("FeeDetailTotalAmount").item(0).getFirstChild().getTextContent();
	    		FeeDetailTableTotalMap.put("Line"+i+" ","Total "+TotalFD );
	    	    break;
	    	
	    	case "ServiceGroup":
	    		String ServiceGroup = PerTransaction.getElementsByTagName("ServiceGroup").item(0).getFirstChild().getTextContent();    
	    		FeeDetailTableTotalMap.put("Line"+i+" ","SERVICE GROUP: "+ServiceGroup );
	    		break;
	        	
	    	case "ProductGroup":
	    		String ProductGroup = PerTransaction.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent(); 
	    		FeeDetailTableTotalMap.put("Line"+i+" ",ProductGroup );
	    		break;

	    	case "RatePlan":
	    		String RatePlan = PerTransaction.getElementsByTagName("RatePlan").item(0).getFirstChild().getTextContent(); 
	    		FeeDetailTableTotalMap.put("Line"+i+" ",RatePlan );
	    		break;	        	
	    	}   
	        
	        }     
	        return FeeDetailTableTotalMap;
	            
		}
	   public static LinkedHashMap<String,String> readFeeDetailOneoffLinesXML(String xmlfilepath) throws Exception{
	        
	        LinkedHashMap<String, String> OFeeDetailTableRowsMap= new LinkedHashMap<>();
	              
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();

	        XPathExpression exprOneOff = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/BillingCharges/OneOffDetails");
	        
	        NodeList PerTranRows = (NodeList)exprOneOff.evaluate(doc1, XPathConstants.NODESET);
	        for (int j = 0; j < PerTranRows.getLength(); j++) {
	                       
	            Element PerTrans = (Element)PerTranRows.item(j);
	            
	            try {
	            String[] OSiteInfo = PerTrans.getElementsByTagName("SiteInformation").item(0).getFirstChild().getTextContent().split(" ");
	            
	          
	            String OSiteRef = PerTrans.getElementsByTagName("SiteReference").item(0).getFirstChild().getTextContent();           
	            String OMSISDN = PerTrans.getElementsByTagName("MSISDN").item(0).getFirstChild().getTextContent();

	            String OPeriodStartDate = PerTrans.getElementsByTagName("PeriodStartDate").item(0).getFirstChild().getTextContent();
	            String OPeriodEndDate = PerTrans.getElementsByTagName("PeriodEndDate").item(0).getFirstChild().getTextContent();
	            String OUnits = PerTrans.getElementsByTagName("Quantity").item(0).getFirstChild().getTextContent();
	            String OUOM = PerTrans.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
	            String ORate = PerTrans.getElementsByTagName("UnitPrice").item(0).getFirstChild().getTextContent();
	            String OTotalCharge = PerTrans.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
	            
	            String  OFeeDetailTableRow = (OSiteInfo[0]+" "+OSiteRef+" "+OMSISDN+ " " +OPeriodStartDate+" " +OPeriodEndDate
	            		                    +" "+OUnits+" "+OUOM+" "+ORate+" "+OTotalCharge);
	            //OSiteRef+" "+   ****************** OFeeDetailTableRow =OFeeDetailTableRow.replace(null, "");
	            OFeeDetailTableRowsMap.put("Line"+j+" ",OFeeDetailTableRow );
	            }  catch(Exception e){} 
	        }
	            
	        return OFeeDetailTableRowsMap;
	            
		}
	   public static LinkedHashMap<String,String> readFDChargeTotalsXML(String xmlfilepath) throws Exception{
	        
	        LinkedHashMap<String, String> FeeDetailTableTotalMap= new LinkedHashMap<>();
	              
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();

	        XPathExpression exprRecurring = xpath.compile("//CustomerNodeList/CustomerNode/ChargeTypeTotal");
	        
	        NodeList PerTransRows = (NodeList)exprRecurring.evaluate(doc1, XPathConstants.NODESET);
	        for (int i = 0; i < PerTransRows.getLength(); i++) {
	                       
	            Element PerTransaction = (Element)PerTransRows.item(i);
	         
	            
	            String ChargeTotalsFD = PerTransaction.getElementsByTagName("ChargeTypeTolAmt").item(0).getFirstChild().getTextContent();
	            String ChargeType = PerTransaction.getElementsByTagName("ChargeType").item(0).getFirstChild().getTextContent();
	            
	            FeeDetailTableTotalMap.put("Line"+i+" ","Total "+ChargeType+" "+ChargeTotalsFD );
	                      
	        }
	            
	        return FeeDetailTableTotalMap;
	            
		}
 
    public static LinkedHashMap<String,String> readPALinesXML(String xmlfilepath, String VarType) throws Exception{
        
        LinkedHashMap<String, String> PAirtimeTableRowsMap= new LinkedHashMap<>();
       
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprPAirtime = xpath.compile("//Voucher/BatchDetails");
        
        NodeList PerPChargeRows = (NodeList)exprPAirtime.evaluate(doc1, XPathConstants.NODESET);
        
        for (int j = 0; j < PerPChargeRows.getLength(); j++) {
                       
            Element PerPTransaction = (Element)PerPChargeRows.item(j);
         
            switch (VarType) {
         
         case "LinesItems":
            try {
           
            String SiteName = PerPTransaction.getElementsByTagName("SiteName").item(0).getFirstChild().getTextContent();
            String SiteRef = PerPTransaction.getElementsByTagName("SiteReference").item(0).getFirstChild().getTextContent();
            String Units = PerPTransaction.getElementsByTagName("Unit").item(0).getFirstChild().getTextContent();           
            String UoM = PerPTransaction.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
            String Rate = PerPTransaction.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
            String TotalCharge = PerPTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
            //ProductID+" "+ProduDes+
             

            //String  PrepaidAirtimeTableRow = " "+SiteName+" "+SiteRef+" "+Units+" "
            		//+UoM+" "+Rate+" "+TotalCharge;
            		
            PAirtimeTableRowsMap.put("Line"+j+" ",SiteName+" "+SiteRef+" "+Units+
            		" "+UoM+" "+Rate+" "+TotalCharge);
            break;
            }catch(Exception e){
            }
            
         case "ProductID": 
        	 
        	 String ProductID = PerPTransaction.getElementsByTagName("ProductId").item(0).getFirstChild().getTextContent();	
             // String ProduDes = PerPTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent(); 
        	 PAirtimeTableRowsMap.put("Line"+j+" ",ProductID);
        	 break;
        
          case "AdditionalInfo1": 
        	 
        	 String TransID = PerPTransaction.getElementsByTagName("TransID").item(0).getFirstChild().getTextContent();	
             // String ProduDes = PerPTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent(); 
        	 PAirtimeTableRowsMap.put("Line"+j+" ","Trans ID: "+TransID+". Date:");
        	 break;	 
          
          case "AdditionalInfo2": 
         	 
         	 String Date = PerPTransaction.getElementsByTagName("Date").item(0).getFirstChild().getTextContent();	
             String UserRef = PerPTransaction.getElementsByTagName("UserReference").item(0).getFirstChild().getTextContent(); 
         	 PAirtimeTableRowsMap.put("Line"+j+" ",Date+". User Ref: "+UserRef);
         	 break;
        	 
          
          case "ProdDesc":
        	 
        	 try {
             String[] ProduDes = PerPTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent().replace (","," "
            		 ).split(" "); 
             int i =0;
             do {
        	 PAirtimeTableRowsMap.put(j+"Line"+i,ProduDes[i]);
        	 //System.out.println("**********************Debug"+ProduDes[i]);
        	 i++;
               }while (ProduDes[i]!= "" ||ProduDes[i]!= null);
             
        	 }catch(Exception e){}
        	 break;
         }
			}
        return PAirtimeTableRowsMap;

    }
    public static LinkedHashMap<String,String> readPAGrandTotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> PAPageMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
         XPathExpression exprPA = xpath.compile("//Voucher");
        
           NodeList PerPATransRows = (NodeList)exprPA.evaluate(doc1, XPathConstants.NODESET);
          
           Element PerTransaction = (Element)PerPATransRows.item(0); 
           try {
           String Total = PerTransaction.getElementsByTagName("VoucherTotal").item(0).getFirstChild().getTextContent();
           
           PAPageMap.put("GrandTotal", "Total Prepaid Airtime "+Total);
           
           }catch(Exception e){
           
           }
                              
           
           return PAPageMap;
    }
   
}
  