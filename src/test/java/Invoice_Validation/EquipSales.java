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

public class EquipSales {
	
	public static String PDFtext;
	public static String textPDF;
	public static String  xmlrows;
	public static boolean q;
	public static String SAPRemarks ;

	
	public static void main(String[] args) throws Exception {
    //public static String carrierAirtime(String pdfpath,String xmlpath) throws Exception {
		SAPRemarks ="";
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_90103369_1747216_201911.pdf";
				
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\So_50031181_117885_1747216_1386024_20191113.xml";
				
		File inputFile = new File(xmlpath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
		
        
	       Compare(getSAPtextPDF(pdfpath,"FrontPage"),readShipAddressXML(doc,"Name"));
	       Compare(getSAPtextPDF(pdfpath,"FrontPage"),readShipAddressXML(doc,"Street1"));
	       Compare(getSAPtextPDF(pdfpath,"FrontPage"),readShipAddressXML(doc,"Street2"));
	       Compare(getSAPtextPDF(pdfpath,"FrontPage"),readShipAddressXML(doc,"City"));
	       Compare(getSAPtextPDF(pdfpath,"FrontPage"),readShipAddressXML(doc,"Country"));
	       Compare(getSAPtextPDF(pdfpath,"FrontPage"),readShipAddressXML(doc,"PostalCode"));
            System.out.println("*****************Page2 onwards Validation_XML ******************");
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"SLEName"));
			
			      	
			System.out.println("*****************Invoice Details_XML - Page2******************"); 
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"InvoiceNumber")); 
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"BillToRef"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"SoldToRef"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"InvoiceDate"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"Currency"));
			System.out.println("*****************Equipment Sales Specifications_XML******************");
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"SalesOrderNo"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"Delivery note no/Waybill Id"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"ShipmentDate"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"CountryOfDelivery"));

			System.out.println("*****************Equipment Sales Section_XML******************");
			Compare(getSAPtextPDF(pdfpath,"Default"),readEquipSalesLinesXML(doc,"ProductID"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readEquipSalesLinesXML(doc,"ProdDesc1"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readEquipSalesLinesXML(doc,"LineItems"));
			Compare(getSAPtextPDF(pdfpath,"Default"),readSingleNodeXML(doc,"TotalEQS"));
		}
		
    public static String getSAPtextPDF(String pdfpath, String Page) throws Exception{
	
	  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
	  
	   PdfReader reader = new PdfReader(pdfpath); 
	   
	   switch (Page){
    
	   case "FrontPage":
		   PDFHashMap.put("Frontpage", getText(reader,1));
			 break; 
	   
	   default:
			 PDFHashMap.put("middlepages", getText(reader,2));
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
				 SAPRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
		     }
		
		 } 
			
	}

	public static String getText(PdfReader reader,int pg) throws Exception {
	Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
		
    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            
   for (int i = pg; i <= (reader.getNumberOfPages() -1); i++) {
    
        strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        
        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);
        
    }
    PDFtext = PDFtext.replace(",","");
            //System.out.println(PDFtext);
    return PDFtext;
}

	public static LinkedHashMap<String,String> readShipAddressXML(Document doc1,String VarType) throws Exception {
	       
	       LinkedHashMap<String, String> FrontPageMap= new LinkedHashMap<>();
	      
	       XPath xpath = XPathFactory.newInstance().newXPath();
	        XPathExpression exprCAAdd = xpath.compile("//ShipToAddress");
	       
	          NodeList PerCATransRows = (NodeList)exprCAAdd.evaluate(doc1, XPathConstants.NODESET);
	          Element PerTransaction = (Element)PerCATransRows.item(0);  
	          
	          switch (VarType){
	      	
	        			               
	        	case "Name":
	        		try {
	        			FrontPageMap.put("Name",(PerTransaction.getElementsByTagName("Name").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	        		}catch(Exception e){}
	               break;
	        	
	        	case "Street1":
	        		try {
	        			FrontPageMap.put("Street1",(PerTransaction.getElementsByTagName("Street1").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	        		}catch(Exception e){}
	               break;
	               
	        	case "City":
	        		try {
	        			FrontPageMap.put("City",(PerTransaction.getElementsByTagName("City").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	        		}catch(Exception e){}
	               break;
	               
	        	case "Street2":
	        		try {
	        			FrontPageMap.put("State",(PerTransaction.getElementsByTagName("Street2").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	        		}catch(Exception e){}
	               break;
	               
	        	case "Country":
	        		try {
	        			FrontPageMap.put("Country",(PerTransaction.getElementsByTagName("Country").item(0).getFirstChild().getTextContent()).replaceAll(",", ""));
	        		}catch(Exception e){}
	               break;
	               
	        	case "PostalCode":
	        		try {
	        			FrontPageMap.put("PostalCode",(PerTransaction.getElementsByTagName("PostalCode").item(0).getFirstChild().getTextContent()));
	        		}catch(Exception e){}
	               break;
	        	
	         }
	               return FrontPageMap;
	 	}	
public static LinkedHashMap<String,String> readSingleNodeXML(Document doc1, String VarType) throws Exception{
	        
	        LinkedHashMap<String, String> CAFrontPageMap= new LinkedHashMap<>();              
         
	    switch (VarType){
    	
    	case "SLEName":  
	         CAFrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
	         break;
	         
    	case "InvoiceNumber":   
   		 CAFrontPageMap.put("InvoiceDate","Number "+doc1.getElementsByTagName("InvoiceNumber").item(1).getTextContent());
   		 break; 
   		 
    	case "BillToRef":   
    		 CAFrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillTo").item(0).getTextContent());
    		 break;	
    		
    	case "SoldToRef":  
    		 CAFrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SoldTo").item(0).getTextContent());
    		 break;
    		
        case "InvoiceDate":   
    		 CAFrontPageMap.put("InvoiceDate","Date "+doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent());
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
        
        case "TotalEQS":  
   		 CAFrontPageMap.put("TotalEQS","Total Equipment Sales "+doc1.getElementsByTagName("EquipInstallTotalCharge").item(0).getTextContent());
   		 break;   
            
            
      	    }       
	        return CAFrontPageMap;
	            
		}
    public static LinkedHashMap<String,String> readEquipSalesLinesXML(Document doc1, String VarType) throws Exception{
        
        LinkedHashMap<String, String> EquipSalesTableRowsMap= new LinkedHashMap<>();
        String Discount;
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprEquipSales = xpath.compile("//EquipmentSale/SaleDetails/PerLineItem");
        
        NodeList PerEquipSalesRows = (NodeList)exprEquipSales.evaluate(doc1, XPathConstants.NODESET);
        
        for (int j = 0; j < PerEquipSalesRows.getLength(); j++) {
                       
            Element PerESTransaction = (Element)PerEquipSalesRows.item(j);
            try {  
          Discount = PerESTransaction.getElementsByTagName("DiscountPercentage").item(0).getFirstChild().getTextContent();
            }catch(Exception e){Discount = null;}
            switch (VarType) {
       
                        
         case "ProductID": 
        	 
        	 String ProductID = PerESTransaction.getElementsByTagName("ProductID").item(0).getFirstChild().getTextContent();	
             // String ProduDes = PerPTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent(); 
        	 EquipSalesTableRowsMap.put("Line"+j+" ",ProductID);
        	 break;
        
          case "AdditionalInfo1": 
        	 
        	 String TransID = PerESTransaction.getElementsByTagName("TransID").item(0).getFirstChild().getTextContent();	
             // String ProduDes = PerPTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent(); 
        	 EquipSalesTableRowsMap.put("Line"+j+" ","Trans ID: "+TransID+". Date:");
        	 break;	 
          
          case "AdditionalInfo2": 
         	 
         	 String Date = PerESTransaction.getElementsByTagName("Date").item(0).getFirstChild().getTextContent();	
             String UserRef = PerESTransaction.getElementsByTagName("UserReference").item(0).getFirstChild().getTextContent(); 
             EquipSalesTableRowsMap.put("Line"+j+" ",Date+". User Ref: "+UserRef);
         	 break;
        	 
          
          case "ProdDesc1":
        	  
         	 
              String[] ProduDes1 = PerESTransaction.getElementsByTagName("Description").item(0).getFirstChild().getTextContent().replace(",","").replace("|"," ").split(" ");
               
            	try {  
            	  int i =0;
                  do {
                	  EquipSalesTableRowsMap.put(j+"Line"+i,ProduDes1[i]);
             	 i++;
                    }while (ProduDes1[i]!= "" ||ProduDes1[i]!= null);
                  
             	 }catch(Exception e){}
         	 break;
         
          
        	 
        	 default:
        		 
                 String Units = PerESTransaction.getElementsByTagName("Units").item(0).getFirstChild().getTextContent();           
                 String UoM = PerESTransaction.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
                 String Rate = PerESTransaction.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
                 String TotalCharge = PerESTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
                
                 	
                EquipSalesTableRowsMap.put("Line"+j+" "," "+Units+
                         		" "+UoM+" "+Rate+" "+TotalCharge);
                if(Discount!= null || Discount!="")
             
        		break;
                 
         }
			}
        return EquipSalesTableRowsMap;

    }	
}


