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

public class CarrierAirtime {

	

	public static String PDFtext;
	public static String textPDF;
	public static String  xmlrows;
	public static boolean q;
	public static String CARemarks ;

	
	public static void main(String[] args) throws Exception {
		
   // public static String carrierAirtime(String pdfpath,String xmlpath) throws Exception {
			CARemarks ="";
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1328753_118010_201911_CA.pdf";
				
			
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\116260_118010_1328753_20190831_CA.xml";
				

        System.out.println("*****************Page2 onwards CreditNote Validation_XML ******************");
			      	Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"SLEName"));
			      	    	
			      	
			System.out.println("*****************CreditNote Invoice Details_XML - Page2******************"); 
			       // Compare(getCAtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number"));  
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"BillToRef"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"SoldToRef"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"InvoiceDate"));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCASingleNodeXML(xmlpath,"Currency"));
			System.out.println("*****************Carrier Airtime Section_XML******************");
			        Compare(getCAtextPDF(pdfpath,"Default"),readCarrierAirtimeLinesXML(xmlpath));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCAtotalsXML(xmlpath));
			        Compare(getCAtextPDF(pdfpath,"Default"),readCAGrandTotalXML(xmlpath));
		    //System.out.println("*****************LastPage_Static Text******************");
		            //Compare(getCAtextPDF(pdfpath,"Last"),readLastPageStaticText());  
			      	
			      	//return CARemarks;
		}
		
    public static String getCAtextPDF(String pdfpath, String Page) throws Exception{
	
	  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
	  
	   PdfReader reader = new PdfReader(pdfpath); 
	   
	   switch (Page){
    
	   case "Front":
	      PDFHashMap.put("FrontPage", getCAfrontpage(reader));
	     break;
	     
	   case "Last":
		   PDFHashMap.put("LastPage", getCAfrontpage(reader));
		     break;
		     
	   default:
			 PDFHashMap.put("middlepages", getCAtext(reader));
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

	public static String getCAfrontpage(PdfReader reader) throws Exception {
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
	    
	public static String getCAlastpage(PdfReader reader) throws Exception {
		Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
			
	    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
	    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);           
	    
	    strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	        
	    PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader,reader.getNumberOfPages() , strategy);
	        
	    PDFtext = PDFtext.replace(",","");
	            //System.out.println(PDFtext);
	    return PDFtext;
	}
	
	public static String getCAtext(PdfReader reader) throws Exception {
	Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
		
    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            
   for (int i = 3; i <= (reader.getNumberOfPages() -1); i++) {
    
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

	 public static LinkedHashMap<String,String> readCarrierAirtimeLinesXML(String xmlfilepath) throws Exception{
	        
	        LinkedHashMap<String, String> AirtimeSummaryTableRowsMap= new LinkedHashMap<>();
	       
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();

	        XPathExpression exprAirtime = xpath.compile("//CarrierAirtime/ChargeDetails/PerTransaction");
	        
	        NodeList PerChargeRows = (NodeList)exprAirtime.evaluate(doc1, XPathConstants.NODESET);
	        for (int i = 0; i < PerChargeRows.getLength(); i++) {
	                       
	            Element PerTransaction = (Element)PerChargeRows.item(i);
	            
	            try {
	            String TrafficType = PerTransaction.getElementsByTagName("TrafficType").item(0).getFirstChild().getTextContent();	            
	            String CallDestination = PerTransaction.getElementsByTagName("Destination").item(0).getFirstChild().getTextContent();
	            String Events = PerTransaction.getElementsByTagName("Events").item(0).getFirstChild().getTextContent();
	            String Units = PerTransaction.getElementsByTagName("Units").item(0).getFirstChild().getTextContent();
	            String UoM = PerTransaction.getElementsByTagName("UoM").item(0).getFirstChild().getTextContent();
	            String Rate = PerTransaction.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
	            String TotalCharge = PerTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
	            //" "+TrafficType+
	            String  AirtimeSummaryTableRow = TrafficType+" "+CallDestination
	            		+" "+Events+" "+Units+" "+UoM+" "+Rate+" "+TotalCharge;
	            AirtimeSummaryTableRowsMap.put("Line"+i+" ",AirtimeSummaryTableRow );
	            }catch(Exception e){
					
				}
	        }
	            
	        return AirtimeSummaryTableRowsMap;
	            
		}         
   	
    public static LinkedHashMap<String,String> readCAtotalsXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> CAPageMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
         XPathExpression exprCA = xpath.compile("//CarrierAirtime/TotalDetails/Total");
        
           NodeList PerCATransRows = (NodeList)exprCA.evaluate(doc1, XPathConstants.NODESET);
           for (int i = 0; i < PerCATransRows.getLength(); i++) {
           Element PerTransaction = (Element)PerCATransRows.item(i); 
           try {
           String Total = PerTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
           
           CAPageMap.put("Total"+i, "Total "+Total);
           
           }catch(Exception e){
           
           }
                              
           }
           return CAPageMap;
    }
   
public static LinkedHashMap<String,String> readCAGrandTotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> CAPageMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
         XPathExpression exprCA = xpath.compile("//CarrierAirtime/TotalDetails/TotalChargeByCurrency");
        
           NodeList PerCATransRows = (NodeList)exprCA.evaluate(doc1, XPathConstants.NODESET);
          
           Element PerTransaction = (Element)PerCATransRows.item(0); 
           try {
           String Total = PerTransaction.getElementsByTagName("TotalInUSD").item(0).getFirstChild().getTextContent();
           
           CAPageMap.put("GrandTotal", "Total Carrier Airtime "+Total);
           
           }catch(Exception e){
           
           }
                              
           
           return CAPageMap;
    }
   
}
  