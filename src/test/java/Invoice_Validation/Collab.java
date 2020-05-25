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

public class Collab {
	
	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String Remarks;
	

	public static void main(String[] args) throws Exception{
/***********************************************************************************************************/		
			ReadExcelFile.readExcelFile(); // Reading the Contents of the Excel file containing the path to access the Invoice PDF and the Single view XML
			for(int k =0;k<ReadExcelFile.i;k++){
				System.out.println("*****************ITERATION of Each Invoice "+k+" th***************"); 	
				String xmlpath = ReadExcelFile.XMLFILENAME[k];
				String pdfpath = ReadExcelFile.PDFFILENAME[k];
			
				
				File inputFile = new File(xmlpath);
		       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		       
		       Document doc1 = dBuilder.parse(inputFile);
		       doc1.getDocumentElement().normalize();

		       
/****************************************FRONT PAGE*************************************************************/		
		       
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"SLEName"));
		       System.out.println("*****************FrontPage Validation_XML "+k+" th******************\n");
		       Compare(gettextPDF(pdfpath,"Address"),readSingleNodeXML(doc1,"CustName"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Line1"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Line2"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Suburb"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"City"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"State"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"Country"));
		       Compare(gettextPDF(pdfpath,"Address"),readBillAddressXML(doc1,"PostCode"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"VAT"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"PricingID"));
		       
		       System.out.println("*****************Invoice Details_XML - FRONT PAGE "+k+" th***************\n"); 
		       
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
		      
		       System.out.println("*****************FrontPage Legacy IDs Validation_XML "+k+" th**************\n");
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"AccountNo"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"BillingProfile"));
		       
		       System.out.println("*****************SUMMARY Information Section_XML"+k+" th**************\n");
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"Fees"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"Airtime"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"Voucher"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"LPI"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"AdjustSum"));
		       
		       /****************************************SECOND PAGE ONWARDS*************************************************************/		
			    System.out.println("*****************Page2 Validation_XML "+k+" th***************");
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"SLEName"));
								      	
				System.out.println("*****************PAGE2 onwards -Invoice Details_XML -  "+k+" th*************\n"); 
				      
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"InvoiceNo"));
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"BillToRef"));
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"SoldToRef"));
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"YourRef"));
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"InvoiceDate"));
				 Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"Currency"));
				
				System.out.println("********************SERVICE SUMMARY VALIDATION**- "+k+" th********************\n");
				   Compare(gettextPDF(pdfpath,"Default"),readSSGroupXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSProductXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSActivationXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSSubscriptionsXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSCancellationXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSEarlyTerminationXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSUsageXML(doc1,xmlpath));
				   //Compare(gettextPDF(pdfpath,"Default"),readSSOtherXML(doc1,doc1,xmlpath)); //Further Modification Required
				   Compare(gettextPDF(pdfpath,"Default"),readSStotalXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSSubtotalXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSAllTotalXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSGrandTotalXML(doc1,xmlpath));
				   
				   /****************************************Adjustments Section*************************************************************/			
				   System.out.println("********************ADJUSTMENT SECTION - "+k+" th********************\n");
					Compare(gettextPDF(pdfpath,"Default"),readAdjustmentLinesXML(doc1,"ServiceDetails"));
					Compare(gettextPDF(pdfpath,"Default"),readAdjustmentLinesXML(doc1,"LineItems"));
					Compare(gettextPDF(pdfpath,"Default"),readAdjustmentLinesXML(doc1,"TotalsAdj"));
					Compare(gettextPDF(pdfpath,"Default"),readAdjustmentLinesXML(doc1,"GrandTotal"));
					
					System.out.println("*****************Prepaid Airtime Section_XML - "+k+" th*************\n");
			        Compare(gettextPDF(pdfpath,"Default"),readPALinesXML(doc1,"ProductID")); 
			        Compare(gettextPDF(pdfpath,"Default"),readPALinesXML(doc1,"ProdDesc"));
			        Compare(gettextPDF(pdfpath,"Default"),readPALinesXML(doc1,"LinesItems"));
			        Compare(gettextPDF(pdfpath,"Default"),readPALinesXML(doc1,"AdditionalInfo1"));
			        Compare(gettextPDF(pdfpath,"Default"),readPALinesXML(doc1,"AdditionalInfo2"));
			        Compare(gettextPDF(pdfpath,"Default"),readSingleNodeXML(doc1,"VoucherTotal"));
					 
			        System.out.println("*****************END of ITERATION of Each Invoice "+k+" th***************\n");
			}
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
			
			}
		return textPDF;


		}
			
		public static void Compare(String PDFtext,LinkedHashMap<String,String> XMLHashMap){
			
			Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
		    while(XMLkeySetIterator.hasNext()) {
				
				String keyXML = XMLkeySetIterator.next();
				
				xmlrows = XMLHashMap.get(keyXML).replaceAll("\n", "");
							
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
		  
		       String BillRun =doc1.getElementsByTagName("BillRunType").item(0).getTextContent();			
		       
		    switch (VarType){
			
			case "SLEName":  
				FrontPageMap.put("sleLine",doc1.getElementsByTagName("SLEName").item(0).getTextContent());
		        break;
		        
			case "InvoiceNo":  
				
				if (BillRun.equals("Standard Bill Run")) {
				FrontPageMap.put("InvoiceNo","Number "+doc1.getElementsByTagName("InvoiceNumber").item(0).getTextContent());
				}
				
				else {
					try {
					FrontPageMap.put("InvoiceNo","Number "+doc1.getElementsByTagName("ADJNumber").item(0).getTextContent());
				}catch(Exception e){}
				}
		        break;
		   		
			case "BillToRef":   
				FrontPageMap.put("BillToRef","Bill to reference "+doc1.getElementsByTagName("BillingProfileID").item(0).getTextContent());
				 break;	
				
			case "SoldToRef":  
				FrontPageMap.put("SoldToRef","Sold to reference "+doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent());
				 break;
			
			case "YourRef":  
				String YourRef = doc1.getElementsByTagName("YourReference").item(0).getTextContent().replace(",", "");
				
			   	if (YourRef.isEmpty()  ) {}
			   	else {FrontPageMap.put("YourRef","Your reference "+YourRef);}
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
			   try {
			   
			   	String PONumber = (doc1.getElementsByTagName("OrderNumber").item(0).getTextContent());
					
			   	if (PONumber.isEmpty()  ) {}
			   	else {FrontPageMap.put("PONumber","PO Number: "+PONumber);}
		    }catch(Exception e){}
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
			   try {
				String AccountNo = doc1.getElementsByTagName("ARAccountNumber").item(0).getTextContent();
				
			   	if (AccountNo.isEmpty()  ) {}
			   	else {FrontPageMap.put("AccountNo","Account Number: "+AccountNo+".");}
			   }catch(Exception e){}
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
			   try {
			   String PricingID = doc1.getElementsByTagName("PricingAgreementID").item(0).getTextContent();
				if (PricingID.isEmpty()  ) {}
			   	else {FrontPageMap.put("Pricing Agreement ID: ","Pricing Agreement ID: "+PricingID);}
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
		   case "VoucherTotal": 
		       try {
		           String Total = doc1.getElementsByTagName("VoucherTotal").item(0).getFirstChild().getTextContent();
		           
		           FrontPageMap.put("GrandTotal", "Total Prepaid Airtime "+Total);
		           
		           }catch(Exception e){}
		       break;
		       
		   case "VAT":
				try {
					String vat = doc1.getElementsByTagName("VATRegistrationNumber").item(0).getTextContent();
					
					if (!(vat.isBlank())) {
						FrontPageMap.put("vat","VAT Reg. Number: "+vat);
					}
				}catch(Exception e){}
		      break;
		   case "Fees":
				try {
					String Fees = doc1.getElementsByTagName("TotalFees").item(0).getTextContent();
					
					if (!(Fees.isBlank())) {
						FrontPageMap.put("Fees","Fees "+Fees);
					}
				}catch(Exception e){}
		      break;
		   case "Airtime":
				try {
					String Airtime = doc1.getElementsByTagName("TotalAirtimeCharges").item(0).getTextContent();
					
					if (!(Airtime.isBlank())) {
						FrontPageMap.put("Airtime","Airtime "+Airtime);
					}
				}catch(Exception e){}
		      break;
		   case "LPI":
				try {
					String lpi = doc1.getElementsByTagName("TotalLPIAmount").item(0).getTextContent();
					
					if (!(lpi.isBlank())) {
						FrontPageMap.put("lpi","Late Payment Interest "+lpi);
					}
				}catch(Exception e){}
		      break;
		   case "AdjustSum":
				try {
					String Adj = doc1.getElementsByTagName("TotalAdjustment").item(0).getTextContent();
					
					if (!(Adj.isBlank())) {
						FrontPageMap.put("Adj","Adjustment "+Adj);
					}
				}catch(Exception e){}
		      break;
		 	    }       
		       return FrontPageMap;
		           
			}

		/****************************************************************************/
		public static LinkedHashMap<String,String> readSSActivationXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
	      
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
		public static LinkedHashMap<String,String> readSSSubscriptionsXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
	      
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
		public static LinkedHashMap<String,String> readSSCancellationXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
	       
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
		public static LinkedHashMap<String,String> readSSEarlyTerminationXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
	        
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
	    public static LinkedHashMap<String,String> readSSUsageXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
	      public static LinkedHashMap<String,String> readSSOtherXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
	      public static LinkedHashMap<String,String> readSStotalXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
	       
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
	      public static LinkedHashMap<String,String> readSSSubtotalXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
	        
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/Subtotal");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
	      public static LinkedHashMap<String,String> readSSAllTotalXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

	       
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/TotalOfAllProductGroups");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
	      public static LinkedHashMap<String,String> readSSGrandTotalXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//TotalForServiceSummary");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
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
		public static LinkedHashMap<String,String> readSSProductXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryGroupRowsMap= new LinkedHashMap<>();
	       
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
	        for (int i = 0; i < PerProdRows.getLength(); i++) {
	                       
	            Element PerProductDetails = (Element)PerProdRows.item(i);
	            
	            //String ServiceGroup = PerProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
	            String Product = PerProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();         
	            ServiceSummaryGroupRowsMap.put("ProductOnly"+i,Product);
	            
	        }       
	             return ServiceSummaryGroupRowsMap;               
	  
		}
			
		/****************************************************************************/
		public static LinkedHashMap<String,String> readSSGroupXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryGroupRowsMap= new LinkedHashMap<>();

	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
	        for (int i = 0; i < PerProdRows.getLength(); i++) {
	                       
	            Element PerProductDetails = (Element)PerProdRows.item(i);
	            
	            String ServiceGroup = PerProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
	            //String Product = PerProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();         
	            ServiceSummaryGroupRowsMap.put("ServiceGroupOnly"+i,ServiceGroup);
	            
	        }       
	             return ServiceSummaryGroupRowsMap;               
	  
		}	
		/************************************ADJUSTMENTS SECTION****************************************/
		public static LinkedHashMap<String,String> readAdjustmentLinesXML(Document doc1, String VarType) throws Exception{
		       
		       LinkedHashMap<String, String> AdjustmentTableRowsMap= new LinkedHashMap<>();
		       String Discount;
		       XPath xpath = XPathFactory.newInstance().newXPath();

		       
		           
		           switch (VarType) {         
		           
		           case "ServiceDetails":
		        	   
		        	   XPathExpression exprAdj = xpath.compile("//Adjustment/AdjustmentsDetails");
		               
		               NodeList PerAdjRows = (NodeList)exprAdj.evaluate(doc1, XPathConstants.NODESET);
		               
		               for (int j = 0; j < PerAdjRows.getLength(); j++) {
		                              
		                   Element PerAdjTransaction = (Element)PerAdjRows.item(j);
		        	   String ServiceDetails = PerAdjTransaction.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();   
		        		AdjustmentTableRowsMap.put("Line"+j+" ",ServiceDetails);
		               }
		               break;
		               
		         case "TotalsAdj":
		        	   
		        	   XPathExpression exprAdj3 = xpath.compile("//Adjustment/AdjustmentSubtotal/PerProductGroup");
		               
		               NodeList PerAdj3Rows = (NodeList)exprAdj3.evaluate(doc1, XPathConstants.NODESET);
		               
		               for (int j = 0; j < PerAdj3Rows.getLength(); j++) {
		                              
		                   Element PerAdj3Transaction = (Element)PerAdj3Rows.item(j);
		        	   String TotalsAdj = PerAdj3Transaction.getElementsByTagName("TotalChargePerProdGrp").item(0).getFirstChild().getTextContent();   
		        		AdjustmentTableRowsMap.put("Line"+j+" ","Total "+TotalsAdj);
		               }
		               break;
		               
		         case "GrandTotal":
               try {
		      	   String GrandTotalAdj = doc1.getElementsByTagName("TotalChargePerProdGrp").item(0).getFirstChild().getTextContent();   
		      		AdjustmentTableRowsMap.put("GrandTotalAdj","Total Adjustments "+GrandTotalAdj);
		           }  catch(Exception e){} 
		             break;
		               
		               
		       	 default:      		 
		       	         
		       		 XPathExpression exprAdj2 = xpath.compile("//Adjustment/AdjustmentsDetails");
		             
		             NodeList PerAdj2Rows = (NodeList)exprAdj2.evaluate(doc1, XPathConstants.NODESET);
		             
		             for (int j = 0; j < PerAdj2Rows.getLength(); j++) {
		                            
		                 Element PerAdjTransaction = (Element)PerAdj2Rows.item(j);
		                
		                String AdditionalInfo = PerAdjTransaction.getElementsByTagName("AdditionalInfo").item(0).getFirstChild().getTextContent();
		                String AdjustmentAmount = PerAdjTransaction.getElementsByTagName("AdjustmentAmount").item(0).getFirstChild().getTextContent();             
		                AdjustmentTableRowsMap.put("Line"+j+" ",AdditionalInfo+" "+AdjustmentAmount);
		             }
		             
		       		break;
		                
		        
					}
		       

		   
		       return AdjustmentTableRowsMap;
			}
		/************************************PREPAID AIRTIME SECTION****************************************/		
		 public static LinkedHashMap<String,String> readPALinesXML(Document doc,String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> PAirtimeTableRowsMap= new LinkedHashMap<>();
		       
		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprPAirtime = xpath.compile("//Voucher/BatchDetails");
		        
		        NodeList PerPChargeRows = (NodeList)exprPAirtime.evaluate(doc, XPathConstants.NODESET);
		        
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

		
}

		/******************************************************************************************************************/



		
