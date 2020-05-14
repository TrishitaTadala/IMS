package Invoice_Validation;

import java.io.File;
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

public class getXMLText {

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
  

}
