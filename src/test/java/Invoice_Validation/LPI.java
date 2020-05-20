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

	public class LPI {

		public static String PDFtext;
		public static String textPDF;
		public static String  xmlrows;
		public static boolean q;
		public static String lpiRemarks ;

		
		public static void main(String[] args) throws Exception {
	    //public static String carrierAirtime(String pdfpath,String xmlpath) throws Exception {
			lpiRemarks ="";
			 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Content
	 

			String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_6400000055_972830_202005_lpi.pdf";					
				
			String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001555_972830_1590972_20200514_lpi.xml";
			
			
			File xmlinputFile = new File(xmlpath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(xmlinputFile);
	        doc1.getDocumentElement().normalize();
	        
	            Compare(getLPItextPDF(pdfpath,"FrontPage"),readLPISingleNodeXML(doc1,"SLEName"));
	            System.out.println("*****************FrontPage Late Payment Interest Validation_XML ******************");
	            Compare(getLPItextPDF(pdfpath,"Address"),readLPISingleNodeXML(doc1,"CustName"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Line1"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Line2"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Suburb"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"City"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"State"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Country"));
	            Compare(getLPItextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"PostCode"));
	            
	            System.out.println("*****************FrontPage Legacy IDs Validation_XML ******************");
	            Compare(getLPItextPDF(pdfpath,"FrontPage"),readLPISingleNodeXML(doc1,"AccountNo"));
	            Compare(getLPItextPDF(pdfpath,"FrontPage"),readLPISingleNodeXML(doc1,"BillingProfile"));

			    System.out.println("*****************Page2 Late Payment Interest Validation_XML ******************");
				 Compare(getLPItextPDF(pdfpath,"Default"),readLPISingleNodeXML(doc1,"SLEName"));
								      	
				System.out.println("*****************Invoice Details_XML - Page2******************"); 
				       // Compare(getCAtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number")); 
				 Compare(getLPItextPDF(pdfpath,"Default"),readLPISingleNodeXML(doc1,"LPIInvoiceNo"));
       			 Compare(getLPItextPDF(pdfpath,"Default"),readLPISingleNodeXML(doc1,"BillToRef"));
				 Compare(getLPItextPDF(pdfpath,"Default"),readLPISingleNodeXML(doc1,"SoldToRef"));
				 Compare(getLPItextPDF(pdfpath,"Default"),readLPISingleNodeXML(doc1,"InvoiceDate"));
				 Compare(getLPItextPDF(pdfpath,"Default"),readLPISingleNodeXML(doc1,"Currency"));
				System.out.println("*****************LatePaymentInterest Section_XML******************");

				 Compare(getLPItextPDF(pdfpath,"Default"),readLPILinesXML(doc1,"LinesItems"));
				 Compare(getLPItextPDF(pdfpath,"Default"),readLPILinesXML(doc1,"LPITotal"));
			}
			
	    public static String getLPItextPDF(String pdfpath, String Page) throws Exception{
		
		  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		  
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
					 lpiRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
			     }
			
			 } 
				
		}


		public static LinkedHashMap<String,String> readBillAddressXML(Document doc1,String VarType) throws Exception {
		        
		        LinkedHashMap<String, String> CAFrontPageMap= new LinkedHashMap<>();
		       
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

	   public static LinkedHashMap<String,String> readLPISingleNodeXML(Document doc1, String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> FrontPageMap= new LinkedHashMap<>();
		    
	            
	         switch (VarType){
	    	
	    	case "SLEName":  
	    		FrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
		         break;
		         
	    	case "LPIInvoiceNo":  
	    		FrontPageMap.put("LPIInvoiceNo","Number "+doc1.getElementsByTagName("LPIInvoiceNo").item(0).getTextContent());
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
                    int btcn =0;
                    do {
                    	FrontPageMap.put(btcn+"Line",CustName[btcn]);
               	
                    	btcn++;
                      }while (CustName[btcn]!= "" ||CustName[btcn]!= null);
                    
               	 }catch(Exception e){}
               	 break;
	      		 

	        case "AccountNo":
	     		try {
	     			String AccNo = (doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent());
	     			if (AccNo!= null ||AccNo!= ""||AccNo!= " " ) {
	     				FrontPageMap.put("AccountNo","Account Number: "+AccNo+".");
	     		}
	     		}catch(Exception e){}
	            break; 
	            
	        case "BillingProfile":
	     	   			
	     			String BPCode = doc1.getElementsByTagName("BillingProfileCode").item(0).getTextContent();
	     			if (BPCode!= null ||BPCode!= ""||BPCode!= " " ) {
	     			FrontPageMap.put("BillingProfile","Billing Profile: "+BPCode+".");
	     			}
	     		
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

		/******************************************************************************************************************/
	
	    public static LinkedHashMap<String,String> readLPILinesXML(Document doc, String VarType) throws Exception{
	    	LinkedHashMap<String, String> LPITableMap= new LinkedHashMap<>();      
	         
	            switch (VarType) {       
	         
	         case "LinesItems":
	        	 
	        	 XPath xpath = XPathFactory.newInstance().newXPath();

	 	        XPathExpression exprLPI = xpath.compile("//LatePaymentInterest/PerLineItem");
	 	        
	 	        NodeList PerLPIRows = (NodeList)exprLPI.evaluate(doc, XPathConstants.NODESET);
	 	        
	 	        for (int j = 0; j < PerLPIRows.getLength(); j++) {
	 	                       
	 	            Element PerLPI = (Element)PerLPIRows.item(j);
	           
	            String DocNum = PerLPI.getElementsByTagName("DocumentNumber").item(0).getFirstChild().getTextContent();
	            String PeriodStartDt = PerLPI.getElementsByTagName("PeriodStartDate").item(0).getFirstChild().getTextContent();
	            String PeriodEndDt = PerLPI.getElementsByTagName("PeriodEndDate").item(0).getFirstChild().getTextContent();           
	            String Days = PerLPI.getElementsByTagName("Days").item(0).getFirstChild().getTextContent();
	            String Rate = PerLPI.getElementsByTagName("InterestRate").item(0).getFirstChild().getTextContent();
	            String DocCurrency = PerLPI.getElementsByTagName("DocumentCurrency").item(0).getFirstChild().getTextContent();
	            String DocAmt = PerLPI.getElementsByTagName("OrigDocumentAmount").item(0).getFirstChild().getTextContent();
	            String InterestAmt = PerLPI.getElementsByTagName("InterestAmount").item(0).getFirstChild().getTextContent();

	            		
	            LPITableMap.put("Line"+j+" ",DocNum+" "+PeriodStartDt+" "+PeriodEndDt+
	            		" "+Days+" "+Rate+"%"+" "+DocCurrency+" "+DocAmt+" "+InterestAmt);
	 	        }
	            break;
	 	      
	        
	         case "LPITotal":  
	        	 
	        	 String Total = doc.getElementsByTagName("TotalLPIAmount").item(0).getFirstChild().getTextContent();
	        	 LPITableMap.put("GrandTotal", "Total Late Payment Interest "+Total);
	         }
				
	        return LPITableMap;

	    }

	}
	  

