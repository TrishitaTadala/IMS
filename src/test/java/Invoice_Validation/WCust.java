//All the variables should be declared
//======================================================
//Class      : WCust
//Description: The WholesaleCustomer Invoice Validation 
//             with exception to Front Page and Last Pagefrom other packages
//======================================================
//Changes----------------------------------------------
//Date         Test Analyst        Change
//23/04/20     Trishita Tadala     Written
//24/04/20     Trishita Tadala     FeeSummary Section
//04/05/20     Trishita Tadala     AirtimeSummary Section
//======================================================

package Invoice_Validation;

import java.util.LinkedHashMap;
import java.util.Iterator;

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
public class WCust {

	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String WCustRemarks;
	
	
	public static void main(String[] args) throws Exception {
		

		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents

		//String pdfpath ="C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\INVOICE_1590512_118014_202009.pdf";
		String pdfpath ="C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\INVOICE_5448299_117921_202004.pdf";
			   //INVOICE_5450013_118047_202004.pdf"
	           //INVOICE_5450013_118047_202004.pdf";
			
		//String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\Wholesale\\116111_118014_1590512_20200930.xml";
		String xmlpath="C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\Wholesale\\QA_129171_117921_5448299_20200430.xml";
			    //QA_129171_118047_5450013_20200430.xml"
		        //QA_129171_118047_5450013_20200430.xml";
		
		PdfReader reader = new PdfReader(pdfpath); 
		
		PDFHashMap.put("Front&Last Pages Excluded"+"\n", getPDFtext(reader));
				
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			//System.out.println(key + PDFHashMap.get(key));
			PDFtext = PDFHashMap.get(key);
			
			//System.out.println(PDFtext);
		}
		
		
		Compare(getXMLText.readSingleNodeXML(xmlpath,"SLEName"));
	    
	    System.out.println("*****************Billing Address_XML - FrontPage******************");
	    Compare(getXMLText.readSingleNodeXML(xmlpath,"CustName"));		    
        Compare(getXMLText.readBillAddressXML(xmlpath,"Line1"));
        Compare(getXMLText.readBillAddressXML(xmlpath,"Line2"));
        Compare(getXMLText.readBillAddressXML(xmlpath,"Suburb"));
        Compare(getXMLText.readBillAddressXML(xmlpath,"City"));
        Compare(getXMLText.readBillAddressXML(xmlpath,"State"));
        Compare(getXMLText.readBillAddressXML(xmlpath,"Country"));
        Compare(getXMLText.readBillAddressXML(xmlpath,"PostCode"));
        
        System.out.println("*****************Invoice Details - FrontPage******************");
        
        Compare(getXMLText.readSingleNodeXML(xmlpath,"InvoiceNumber"));	
       
        Compare(getXMLText.readSingleNodeXML(xmlpath,"BillToRef"));
        Compare(getXMLText.readSingleNodeXML(xmlpath,"SoldToRef"));
        Compare(getXMLText.readSingleNodeXML(xmlpath,"InvoiceDate"));
        Compare(getXMLText.readSingleNodeXML(xmlpath,"DueDate"));
        Compare(getXMLText.readSingleNodeXML(xmlpath,"Currency"));
        Compare(getXMLText.readSingleNodeXML(xmlpath,"CDSummary"));
		
		/*********FeeSummaryValidation*************/
        System.out.println( "*************************FEE SUMMARY***********************************");
		  Compare(readFSGroupChargeXML(xmlpath));//Service Group - Fee Summary
		  Compare(readFeeSummaryLinesXML(xmlpath)); //Fees Summary Lines
		  Compare(readFSSubtotalXML(xmlpath));//Fees Summary Sub Total Lines
		  Compare(readFSProductGroupXML(xmlpath));//Fees Summary Product Group Lines
		  Compare(readFSChargeTotalXML(xmlpath));//Fees Summary Charge Totals
		  Compare(readFStotalXML(xmlpath));//Total Fees Summary

		//*********Compare(readASTrafficTypeXML(xmlpath));*************
		
		//Compare(readASGroupChargeXML(xmlpath));//Service Group - Airtime Summary
		  System.out.println( "******************************AIRTIME SUMMARY**************************");
		Compare(readAirtimeSummaryLinesXML(xmlpath,"TrafficType"));// Airtime Summary LineItems
		Compare(readAirtimeSummaryLinesXML(xmlpath,"any"));// Airtime Summary LineItems
		Compare(readAStotalXML(xmlpath));
	}
	
	public static void Compare(LinkedHashMap<String,String> XMLHashMap){
        
		PDFtext = PDFtext.replaceAll(",", "");
		   
		Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
         while(XMLkeySetIterator.hasNext()) {
			
			String keyXML = XMLkeySetIterator.next();
			
			xmlrows = XMLHashMap.get(keyXML);
			//System.out.println(xmlrows);
		
		if (PDFtext.indexOf(xmlrows)!=-1? true: false){
			
			System.out.println(xmlrows +" It's a Match!!!");
			 q = true;
					
		    }
		else{
			System.out.println( xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~");
				 q = false;	
			WCustRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
		     }
		
		
         } 
			
	}

	public static String getPDFtext(PdfReader reader) throws Exception {
		//public static String getPDFtext(PdfReader reader) throws Exception {	
		

		Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
			
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                
       for (int i = 1; i <= (reader.getNumberOfPages() -1); i++) {
        //for (int i = 3; i <= 5; i++) {
            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            //System.out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
            PDFtext += PdfTextExtractor.getTextFromPage(reader, i, strategy);
            
        }
        PDFtext = PDFtext.replace(",","");
                //System.out.println(PDFtext);
        return PDFtext;
	}
	
	/********************************FRONT PAGE*******************************************/	
	public static LinkedHashMap<String,String> readSingleNodeXML(String xmlfilepath, String VarType) throws Exception{
        
        LinkedHashMap<String, String> FrontPageMap= new LinkedHashMap<>();
          
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
 
        
     switch (VarType){
	
	case "SLEName":  
		FrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
         break;
         
	case "InvoiceNumber":   
    	FrontPageMap.put("InvoiceNumber","Number  "+doc1.getElementsByTagName("InvoiceNumber").item(0).getTextContent());
		 break; 
	
	case "ADJNumber": 
	try{	
    	FrontPageMap.put("CNTNumber","Number "+doc1.getElementsByTagName("ADJNumber").item(0).getTextContent());
     }catch(Exception e){}
		 break; 
    		
	case "BillToRef":   
		FrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillingProfileID").item(0).getTextContent());
		 break;	
		
	case "SoldToRef":  
		FrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent());
		 break;
		
    case "InvoiceDate":   
    	FrontPageMap.put("InvoiceDate","Date "+doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent());
		 break;  
		
    case "Currency":   
    	FrontPageMap.put("Currency","Currency "+doc1.getElementsByTagName("CustomerInvoiceCurrency").item(0).getTextContent());
 		 break;
 		
    case "CustName":
   	 
  	 try {
       String[] CustName = doc1.getElementsByTagName("BillToCustomerName").item(0).getTextContent().replace (","," "
      		 ).split(" "); 
       int cn =0;
       do {
    	   FrontPageMap.put(cn+"Line",CustName[cn]);
  	
  	 cn++;
         }while (CustName[cn]!= "" ||CustName[cn]!= null);
       
  	 }catch(Exception e){}
  	 break;

    case "AccountNo":
 		try {
 			String AccNo = (doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent());
 			if (AccNo!= null ||AccNo!= ""||AccNo!= " " ) {
 				FrontPageMap.put("AccountNo","Account Number "+AccNo+".");
 		}
 		}catch(Exception e){}
        break; 
        
    case "BillingProfile":
 		try {
 			FrontPageMap.put("BillingProfile","Billing Profile : "+doc1.getElementsByTagName("BillingProfileId").item(0).getTextContent()+".");
 		}catch(Exception e){}
        break; 
        
    case "DpID":
 		try {
 			FrontPageMap.put("DpId","DP ID: "+(doc1.getElementsByTagName("DPId").item(0).getTextContent())+".");
 		}catch(Exception e){}
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
 		try {
 			FrontPageMap.put("Pricing Agreement ID: ","Pricing Agreement ID: "+(doc1.getElementsByTagName("PricingAgreementID").item(0).getTextContent()));
 		}catch(Exception e){}
        break;
        
    case "MIPS":
 		try {
 			String mips = doc1.getElementsByTagName("MIPSMasterAccountId").item(0).getTextContent();    		
 			if (mips!= null||mips!= ""||mips!= " ") {
 				FrontPageMap.put("MIPS","MIPS Master Acc ID: "+mips+".");
 			}
 		}catch(Exception e){}
        break;
        
    case "FeeSummary": 
        try {   
        	FrontPageMap.put("TFSTotal",("Total Fee Summary "+doc1.getElementsByTagName("FeeSumTotal").item(0).getTextContent()));
         }  catch(Exception e){} 
        break;
        
    case "DueDate":
 		try {
 			String DueDate = doc1.getElementsByTagName("PaymentDueDate").item(0).getTextContent();
 			
 			if (DueDate!= null) {
 				FrontPageMap.put("PDD","Payment due date "+DueDate);
 			}
 		}catch(Exception e){}
        break;
  		 
  	    }       
        return FrontPageMap;
	 
	
	}
	public static LinkedHashMap<String,String> readBillAddressXML(String xmlfilepath,String VarType) throws Exception {
        
        LinkedHashMap<String, String> FrontPageMap= new LinkedHashMap<>();
        
        
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
                    String[] Line1 = PerTransaction.getElementsByTagName("Line1").item(0).getFirstChild().getTextContent().replace (","," "
                   		 ).split(" "); 
                    int ln =0;
                    do {
                 	   FrontPageMap.put(ln+"Line",Line1[ln]);
               	
                 	  ln++;
                      }while (Line1[ln]!= "" ||Line1[ln]!= null);
                    
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
/********************************FEE SUMMARY WHOLESALE CUSTOMER*******************************************/
	public static LinkedHashMap<String,String> readFSGroupChargeXML(String xmlfilepath) throws Exception {
		      
		      LinkedHashMap<String, String> FeeSummaryTableGCRowsMap= new LinkedHashMap<>();
		      
		      
		      File xmlinputFile = new File(xmlfilepath);
		      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		      
		      Document doc1 = dBuilder.parse(xmlinputFile);
		      doc1.getDocumentElement().normalize();
		      XPath xpath = XPathFactory.newInstance().newXPath();

		      
		       XPathExpression exprsGC = xpath.compile("//Fees/TotalforAllRatePlans/Subtotal");
		      
		      NodeList GroupChargeRows = (NodeList)exprsGC.evaluate(doc1, XPathConstants.NODESET);
		      for (int i = 0; i < GroupChargeRows.getLength(); i++) {
		                     
		          Element GroupProductDetails = (Element)GroupChargeRows.item(i);
		          
		          try {
		              
		              //String ChargeType = GroupProductDetails.getElementsByTagName("TypeOfCharge").item(0).getFirstChild().getTextContent();
		              String Prodgroup = GroupProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
		              
		              String  FeeSummaryTableRow = ("SERVICE GROUP: "+Prodgroup);
		              FeeSummaryTableGCRowsMap.put("Set"+i,FeeSummaryTableRow);
		              
		          }  catch(Exception e){}  
		          
		      }       
		           return FeeSummaryTableGCRowsMap;               

			}
    public static LinkedHashMap<String,String> readFeeSummaryLinesXML(String xmlfilepath) throws Exception{
        
        LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();
              
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprFees = xpath.compile("//Fees/PerTransaction/ChargePerTransaction");
        
        NodeList PerTransRows = (NodeList)exprFees.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerTransRows.getLength(); i++) {
                       
            Element PerTransaction = (Element)PerTransRows.item(i);
            
            
            String RatePlanID = PerTransaction.getElementsByTagName("RatePlan").item(0).getFirstChild().getTextContent();
            String Events = PerTransaction.getElementsByTagName("Events").item(0).getFirstChild().getTextContent();
            String Units = PerTransaction.getElementsByTagName("Units").item(0).getFirstChild().getTextContent();
            String UoM = PerTransaction.getElementsByTagName("UoM").item(0).getFirstChild().getTextContent();
            String Rate = PerTransaction.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
            String TotalCharge = PerTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
            
            String  FeeSummaryTableRow = RatePlanID+" " +Events+" "+Units+ " " +UoM+" " +Rate+" "+TotalCharge;
            FeeSummaryTableRowsMap.put("Line"+i+" ",FeeSummaryTableRow );
        }
            
        return FeeSummaryTableRowsMap;
            
	}
    public static LinkedHashMap<String,String> readFSSubtotalXML(String xmlfilepath) throws Exception {
      
      LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();
      
      
      File xmlinputFile = new File(xmlfilepath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      
      Document doc1 = dBuilder.parse(xmlinputFile);
      doc1.getDocumentElement().normalize();
      XPath xpath = XPathFactory.newInstance().newXPath();

      
       XPathExpression exprfsPD = xpath.compile("//Fees/TotalforAllRatePlans/Subtotal");
      
      NodeList PerProdRows = (NodeList)exprfsPD.evaluate(doc1, XPathConstants.NODESET);
      for (int i = 0; i < PerProdRows.getLength(); i++) {
                     
          Element PerProductDetails = (Element)PerProdRows.item(i);
          
          try {
              
              String SumTotal = PerProductDetails.getElementsByTagName("SumTotal").item(0).getFirstChild().getTextContent();
              
              String  FeeSummaryTableRow = ("Total "+SumTotal);
              FeeSummaryTableRowsMap.put("Set"+i,FeeSummaryTableRow);
              
          }  catch(Exception e){}  
          
      }       
           return FeeSummaryTableRowsMap;               

	}	
    public static LinkedHashMap<String,String> readFSChargeTotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> FeeSummaryTableTotalsMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        
         XPathExpression exprfsTT = xpath.compile("//Fees/FeeSummarySubtotal/ChargeTypeSum");
        
        NodeList PerProdRows = (NodeList)exprfsTT.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductGroup = (Element)PerProdRows.item(i);
            
            try {
                
                String ProductGroupSumTotal = PerProductGroup.getElementsByTagName("SumTotal").item(0).getFirstChild().getTextContent();
                String ChargeType = PerProductGroup.getElementsByTagName("TypeOfCharge").item(0).getFirstChild().getTextContent();
                String  FeeSummaryTableTotalRow = ("Total "+ChargeType+" "+ProductGroupSumTotal);
                FeeSummaryTableTotalsMap.put("Set"+i,FeeSummaryTableTotalRow);
                
            }  catch(Exception e){}  
            
        }       
             return FeeSummaryTableTotalsMap;               

  	}	
    public static LinkedHashMap<String,String> readFSProductGroupXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> FeeSummaryTableProductsMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        
         XPathExpression exprfsTT = xpath.compile("//Fees/TotalforAllRatePlans/ProductGroupTotal");
        
        NodeList PerProdRows = (NodeList)exprfsTT.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductGroup = (Element)PerProdRows.item(i);
            
            try {
                
                
                String ProductType = PerProductGroup.getElementsByTagName("ProductType").item(0).getFirstChild().getTextContent();
                
                FeeSummaryTableProductsMap.put("Set"+i,ProductType);
                
            }  catch(Exception e){}  
            
        }       
             return FeeSummaryTableProductsMap;               

  	}	
    public static LinkedHashMap<String,String> readFStotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> FeeSummaryTableTotal= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
       
                
                String SummaryTotal = doc1.getElementsByTagName("FeeSumTotal").item(0).getTextContent();
                
                String  FeeSummaryTableRow = ("Total Fee Summary "+SummaryTotal);
                FeeSummaryTableTotal.put("TFSTotal",FeeSummaryTableRow);
      
                return FeeSummaryTableTotal;               

  	}	   

/********************************AIRTIME SUMMARY WHOLESALE CUSTOMER*******************************************/
    public static LinkedHashMap<String,String> readASGroupChargeXML(String xmlfilepath) throws Exception {
	      
	      LinkedHashMap<String, String> AirtimeSummaryTableGCRowsMap= new LinkedHashMap<>();
	      
	      
	      File xmlinputFile = new File(xmlfilepath);
	      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	      
	      Document doc1 = dBuilder.parse(xmlinputFile);
	      doc1.getDocumentElement().normalize();
	      XPath xpath = XPathFactory.newInstance().newXPath();

	      
	       XPathExpression exprasGC = xpath.compile("//Airtime/ChargeDetails");
	      
	      NodeList GroupChargeRows = (NodeList)exprasGC.evaluate(doc1, XPathConstants.NODESET);
	      for (int i = 0; i < GroupChargeRows.getLength(); i++) {
	                     
	          Element GroupProductDetails = (Element)GroupChargeRows.item(i);
	          
	          try {
	              
	              //String ChargeType = GroupProductDetails.getElementsByTagName("TypeOfCharge").item(0).getFirstChild().getTextContent();
	              String Prodgroup = GroupProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
	              
	              String  AirtimeSummaryTableRow = ("SERVICE GROUP: "+Prodgroup);
	              AirtimeSummaryTableGCRowsMap.put("Set"+i,AirtimeSummaryTableRow);
	              
	          }  catch(Exception e){}  
	          
	      }       
	           return AirtimeSummaryTableGCRowsMap;               

		}
    public static LinkedHashMap<String,String> readAirtimeSummaryLinesXML(String xmlfilepath,String VarType) throws Exception{
        
        LinkedHashMap<String, String> AirtimeSummaryTableRowsMap= new LinkedHashMap<>();
       
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprAirtime = xpath.compile("//Airtime/ChargeDetails/ChargePerTransaction");
        
        NodeList PerChargeRows = (NodeList)exprAirtime.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerChargeRows.getLength(); i++) {
                       
            Element PerTransaction = (Element)PerChargeRows.item(i);
            
            switch (VarType){
           	
         	case "TrafficType":
         		
         		try {
                    String[] TrafficType = PerTransaction.getElementsByTagName("TrafficType").item(0).getFirstChild().getTextContent().replace (","," "
                   		 ).split(" "); 
                    int ln =0;
                    do {
                    	AirtimeSummaryTableRowsMap.put(ln+"Line"+i,TrafficType[ln]);
               	
                 	  ln++;
                      }while (TrafficType[ln]!= "" ||TrafficType[ln]!= null);
                    
               	 }catch(Exception e){}
               	 break;
               	 
               	 default:
            
            try {
            //String TrafficType = PerTransaction.getElementsByTagName("TrafficType").item(0).getFirstChild().getTextContent();
            String IPGroup = PerTransaction.getElementsByTagName("IPGroup").item(0).getFirstChild().getTextContent();
            String[] BitRate = PerTransaction.getElementsByTagName("BitRate").item(0).getFirstChild().getTextContent().split(" ");
            String CallDestination = PerTransaction.getElementsByTagName("CallDestination").item(0).getFirstChild().getTextContent();
            String CallDir = PerTransaction.getElementsByTagName("CallDir").item(0).getFirstChild().getTextContent();
            String Allow = PerTransaction.getElementsByTagName("Allow").item(0).getFirstChild().getTextContent();
            String Events = PerTransaction.getElementsByTagName("Events").item(0).getFirstChild().getTextContent();
            String Units = PerTransaction.getElementsByTagName("Units").item(0).getFirstChild().getTextContent();
            String UoM = PerTransaction.getElementsByTagName("UoM").item(0).getFirstChild().getTextContent();
            String TotalCharge = PerTransaction.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
            //" "+TrafficType+
            String  AirtimeSummaryTableRow = " "+IPGroup+" "+BitRate[0]+" "+CallDestination
            		+" "+CallDir+" "+Allow+" "+Events+" "+Units+" "+UoM+" "+TotalCharge;
            AirtimeSummaryTableRowsMap.put("Line"+i+" ",AirtimeSummaryTableRow );
            }catch(Exception e){
				
			}
        }
        }
            
        return AirtimeSummaryTableRowsMap;
            
	}
    public static LinkedHashMap<String,String> readAStotalXML(String xmlfilepath) throws Exception {
      
      LinkedHashMap<String, String> AirtimeSummaryTableTotal= new LinkedHashMap<>();
      
      
      File xmlinputFile = new File(xmlfilepath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      
      Document doc1 = dBuilder.parse(xmlinputFile);
      doc1.getDocumentElement().normalize();
     
              try {
              String SummaryTotal = doc1.getElementsByTagName("AirtimeOverAllTotal").item(0).getTextContent();
              
              String  FeeSummaryTableRow = ("Total Airtime Summary "+SummaryTotal);
              AirtimeSummaryTableTotal.put("TASTotal",FeeSummaryTableRow);
              }catch(Exception e){}
              return AirtimeSummaryTableTotal;               

	}
}
