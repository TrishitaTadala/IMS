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
				System.out.println("*****************ITERATION of Each Invoice "+k+" th***************\n"); 	
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
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"ORSum"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"CarrierAirtime"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"CDSummary"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"TotalAmtExcl"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"Tax"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"USFfee"));
		       Compare(gettextPDF(pdfpath,"FrontPage"),readSingleNodeXML(doc1,"TotalAmtIncl"));
		       
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
				   //Compare(gettextPDF(pdfpath,"Default"),readSSOtherXML(doc1,xmlpath)); //Further Modification Required
				   Compare(gettextPDF(pdfpath,"Default"),readSStotalXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSSubtotalXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSAllTotalXML(doc1,xmlpath));
				   Compare(gettextPDF(pdfpath,"Default"),readSSGrandTotalXML(doc1,xmlpath));
				System.out.println("***********************FEE DETAIL VALIDATION**- "+k+" th***********************\n");	   
				   Compare(gettextPDF(pdfpath,"Default"),readFDTotalsXML(doc1,"ServiceGroup"));//Fee Detail ServiceGroup
				   Compare(gettextPDF(pdfpath,"Default"),readFDTotalsXML(doc1,"ProductGroup"));//Fee Detail ServiceGroup
				   Compare(gettextPDF(pdfpath,"Default"),readFDTotalsXML(doc1,"RatePlan"));//Fee Detail RatePlan
				   Compare(gettextPDF(pdfpath,"Default"),readFeeDetailRecurringLinesXML(doc1,"SiteInfo")); //Fee Detail Recurring Lines
				   Compare(gettextPDF(pdfpath,"Default"),readFeeDetailRecurringLinesXML(doc1,"any"));
				   Compare(gettextPDF(pdfpath,"Default"),readFeeDetailOneoffLinesXML(doc1)); //Fee Detail OneOff Lines
				   Compare(gettextPDF(pdfpath,"Default"),readFDTotalsXML(doc1,"TotalFD")); //Fee Detail Totals
				   Compare(gettextPDF(pdfpath,"Default"),readFDChargeTotalsXML(doc1));//Fee Detail Charge Totals
				System.out.println("***********************AIRTIME DETAIL VALIDATION**- "+k+" th***********************\n");	   
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantLinesXML(doc1,"CallDateTime"));
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantLinesXML(doc1,"DestinationCountry"));
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantLinesXML(doc1,"AirtimeType"));
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantLinesXML(doc1,"LineItems"));
				   //Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantCLinesXML(doc1,"MsgRefNo"));
				   //Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantCLinesXML(doc1,"AddInfo"));
				  // Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantCLinesXML(doc1,"LineItems"));
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantTotalsXML(doc1,"AirtimeTotal"));
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantTotalsXML(doc1,"SIMTotal"));
				   Compare(gettextPDF(pdfpath,"Default"),readADGeneralVariantTotalsXML(doc1,"GrandTotal"));
			    System.out.println( "*************************FEE SUMMARY VALIDATION**- "+k+" th*****************************\n");
			       Compare(gettextPDF(pdfpath,"Default"),readFSGroupChargeXML(doc1));//Service Group - Fee Summary
			       Compare(gettextPDF(pdfpath,"Default"),readFeeSummaryLinesXML(doc1)); //Fees Summary Lines
				   Compare(gettextPDF(pdfpath,"Default"),readFSSubtotalXML(doc1));//Fees Summary Sub Total Lines
				   Compare(gettextPDF(pdfpath,"Default"),readFSProductGroupXML(doc1));//Fees Summary Product Group Lines
				   Compare(gettextPDF(pdfpath,"Default"),readFSChargeTotalXML(doc1));//Fees Summary Charge Totals
				   Compare(gettextPDF(pdfpath,"Default"),readFStotalXML(doc1));//Total Fees Summary
				 System.out.println( "******************************AIRTIME SUMMARY*- "+k+" th********************************\n");
				   Compare(gettextPDF(pdfpath,"Default"),readAirtimeSummaryLinesXML(doc1,"TrafficType"));// Airtime Summary LineItems
				   Compare(gettextPDF(pdfpath,"Default"),readAirtimeSummaryLinesXML(doc1,"any"));// Airtime Summary LineItems
				   Compare(gettextPDF(pdfpath,"Default"),readAStotalXML(doc1));
				   
				   
				   
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
			        
			     System.out.println("*****************Carrier Airtime Section_XML - "+k+" th*************\n");
			        
			        Compare(gettextPDF(pdfpath,"Default"),readCarrierAirtimeLinesXML(doc1));
			        Compare(gettextPDF(pdfpath,"Default"),readCAtotalsXML(doc1));
			        Compare(gettextPDF(pdfpath,"Default"),readCAGrandTotalXML(doc1));
			        
			     System.out.println("****************Other Revenue Section_XML - "+k+" th*************\n");
			        Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc1,"LineItems"));
					Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc1,"ServiceDetails"));
					Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc1,"PONumber"));
					Compare(gettextPDF(pdfpath,"Default"),readOtherRevenueLinesXML(doc1,"GrandTotal"));
		        
				System.out.println("*****************Credits/Debits Section_XML******************");
			      	Compare(gettextPDF(pdfpath,"Default"),readCNTLinesXML(doc1,"Credits/Debits"));
			      	Compare(gettextPDF(pdfpath,"Default"),readCNTLinesXML(doc1,"InvoiceID"));
			      	Compare(gettextPDF(pdfpath,"Default"),readCNTLinesXML(doc1,"Title"));
			      	Compare(gettextPDF(pdfpath,"Default"),readCNTLinesXML(doc1,"Description"));
			      	Compare(gettextPDF(pdfpath,"Default"),readCNTLinesXML(doc1,"ChargeType"));
			      	Compare(gettextPDF(pdfpath,"Default"),readCDtotalXML(doc1,"CDtotal"));	 
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
		/******************************************************************************************************************/			
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
		/**********************************BILL TO ADDRESS******************************************/	
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
		/**********************************SINGLENODE ELEMENTS******************************************/
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
		   case "Voucher": 
		       try {
		           String Vou = doc1.getElementsByTagName("VoucherTotal").item(0).getFirstChild().getTextContent();
		           
		           FrontPageMap.put("GrandTotal","Prepaid Airtime "+Vou);
		           
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
						FrontPageMap.put("Adj","Adjustments "+Adj);
					}
				}catch(Exception e){}
		      break;
		   case "ORSum":
				try {
					String ORsum = doc1.getElementsByTagName("TotalOtherRevenue").item(0).getTextContent();
					
					if (!(ORsum.isBlank())) {
						FrontPageMap.put("ORsum","Other Revenue "+ORsum);
					}
				}catch(Exception e){}
		      break;
		      
		   case "CarrierAirtime":
				try {
					String CarrierAirtime = doc1.getElementsByTagName("TotalInUSD").item(0).getTextContent();
					
					if (!(CarrierAirtime.isBlank())) {
						FrontPageMap.put("CA","Carrier Airtime "+CarrierAirtime);
					}
				}catch(Exception e){}
		      break;
		      
		   case "TotalAmtExcl":
				try {
					String TotalAmtExcl = doc1.getElementsByTagName("NetAmount").item(0).getTextContent();
					
					if (!(TotalAmtExcl.isBlank())) {
						FrontPageMap.put("TotalAmtExcl","Total excl Taxes "+TotalAmtExcl);
					}
				}catch(Exception e){}
		      break;
		   case "CDSummary":
			   try {
			   FrontPageMap.put("CDSummary","Credits / Debits "+doc1.getElementsByTagName("TotalAmount").item(1).getTextContent());
			   }catch(Exception e){} 
			   break;   
		   case "Tax":
			   String TotalTaxes = "0.00";
			   XPath xPath = XPathFactory.newInstance().newXPath();
				String pertaxtype ="//PerTaxType/TaxType";
				NodeList taxtype = (NodeList) xPath.compile(pertaxtype).evaluate(doc1, XPathConstants.NODESET);
		        NodeList taxnode = (NodeList) taxtype;
		        //int m = taxnode.getLength();
		        for (int i = 0; i < taxnode.getLength(); i++) {
		        	try{
		        		String TaxType = (taxnode.item(i).getFirstChild().getNodeValue());
		        		//System.out.println(doc.getElementsByTagName("TotalTaxAmount").item(i).getTextContent());
		        		if(TaxType.equals("USF Fee")) {
		        			
		        			 TotalTaxes = doc1.getElementsByTagName("TaxesWithOutUSF").item(0).getTextContent();
		        		}
		        		
	                    else 
	                    
	                    {
	                    	TotalTaxes = doc1.getElementsByTagName("GrandTotalTaxAmtAC").item(0).getTextContent(); 
	                    	
		                }
		        		
		        		
		        	}
		        		catch(Exception e){}
		        	
				}
		        FrontPageMap.put("Taxonly","Taxes "+TotalTaxes);
		        break;
		        
		        
		   case "USFfee":
			   XPath xpath = XPathFactory.newInstance().newXPath();
			   String pertaxtype1 ="//PerTaxType/TaxType";
				NodeList taxtype1 = (NodeList) xpath.compile(pertaxtype1).evaluate(doc1, XPathConstants.NODESET);
		        NodeList taxnode1 = (NodeList) taxtype1;
		        
		        for (int i = 0; i < taxnode1.getLength(); i++) {
		        	try{
		        	String TaxType1= (taxnode1.item(i).getFirstChild().getNodeValue());
		        		//System.out.println(doc.getElementsByTagName("TotalTaxAmount").item(i).getTextContent());
		        		if(TaxType1.equals("USF Fee")) {
		        		String	USFFee = doc1.getElementsByTagName("TotalTaxAmount").item(i+1).getTextContent();
		        		FrontPageMap.put("usfonly","USF Fee "+USFFee);	
		        		}   		        		
		        		
		        	}
		        		catch(Exception e){}
		        }
		      break;
		      
		   case "TotalAmtIncl":
				try {
					String TotalAmtIncl = doc1.getElementsByTagName("TotalAmountDue").item(0).getTextContent();
					
					if (!(TotalAmtIncl.isBlank())) {
						FrontPageMap.put("TotalAmtIncl","Total incl Taxes "+TotalAmtIncl);
					}
				}catch(Exception e){}
		      break;
		 	    
		    }
		       return FrontPageMap;
		           
			}
		/**********************************SERVICE SUMMARY******************************************/
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
	      public static LinkedHashMap<String,String> readSSOtherXML(Document doc,String xmlfilepath) throws Exception {
	        
	        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

	        XPath xpath = XPathFactory.newInstance().newXPath();
	        
	         XPathExpression exprSSPD = xpath.compile("//OtherRevenue/PerCharge");
	        
	        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
	        for (int i = 0; i < PerProdRows.getLength(); i++) {
	                       
	            Element PerProductDetails = (Element)PerProdRows.item(i);
	            
	            try {
	                
	                String Other = PerProductDetails.getElementsByTagName("TotalOtherRevenue").item(0).getFirstChild().getTextContent();
	                
	                String  ServiceSummaryTableRow = ("Other Revenue "+Other);
	                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
	                
	            }  catch(Exception e){}  
	            
	        }       
	             return ServiceSummaryTableRowsMap;
		}
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
		  /********************************FEE DETAIL RETAIL CUSTOMER*******************************************/			
		    public static LinkedHashMap<String,String> readFeeDetailRecurringLinesXML(Document doc, String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> FeeDetailTableRowsMap= new LinkedHashMap<>();
		        String CustomerInvoicePreference = doc.getElementsByTagName("CustomerInvoicePreference").item(0).getTextContent();
		        
		        if (CustomerInvoicePreference.equals("DETAIL")) { 
		        	
		        	XPath xpath = XPathFactory.newInstance().newXPath();

			        XPathExpression exprRecurring = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/BillingCharges/RecurringDetails");
			        
			        NodeList PerTransRows = (NodeList)exprRecurring.evaluate(doc, XPathConstants.NODESET);
			        for (int i = 0; i < PerTransRows.getLength(); i++) {
			                       
			            Element PerTransaction = (Element)PerTransRows.item(i);
		        	switch (VarType) {
		        	
		        	case "SiteInfo":
		        		try{
		        		String SiteInfo = PerTransaction.getElementsByTagName("SiteInformation").item(0).getFirstChild().getTextContent();
		        		FeeDetailTableRowsMap.put("Line"+i+" ",SiteInfo );
		        		 }  catch(Exception e){} 
		        	default:
		        
		            
		            try {
		            
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
		            
		            String  FeeDetailTableRow = " " +SiteRef+" "+MSISDN+ " " +PeriodStartDate+" " +PeriodEndDate
		            		                    +" "+Units+" "+UOM+" "+Rate+" "+TotalCharge;
		            FeeDetailTableRowsMap.put("Line"+i+" ",FeeDetailTableRow );
		            }  catch(Exception e){} 
		            break;  
		        }
			        }
		        
		        	
		        }      
		        return FeeDetailTableRowsMap;
		            
			}
		    public static LinkedHashMap<String,String> readFDTotalsXML(Document doc,String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> FeeDetailTableTotalMap= new LinkedHashMap<>();
		        String CustomerInvoicePreference = doc.getElementsByTagName("CustomerInvoicePreference").item(0).getTextContent();
		        
		        if (CustomerInvoicePreference.equals("DETAIL")) {       
		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprRecurring = xpath.compile("//CustomerNodeList/CustomerNode/FeeDetailsSubtotal/PerChargeGroup");
		        
		        NodeList PerTransRows = (NodeList)exprRecurring.evaluate(doc, XPathConstants.NODESET);
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
		        }
		        return FeeDetailTableTotalMap;
		            
			}
		    public static LinkedHashMap<String,String> readFeeDetailOneoffLinesXML(Document doc) throws Exception{
		        
		        LinkedHashMap<String, String> OFeeDetailTableRowsMap= new LinkedHashMap<>();
		        String CustomerInvoicePreference = doc.getElementsByTagName("CustomerInvoicePreference").item(0).getTextContent();
		        
		        if (CustomerInvoicePreference.equals("DETAIL")) {
		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprOneOff = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/BillingCharges/OneOffDetails");
		        
		        NodeList PerTranRows = (NodeList)exprOneOff.evaluate(doc, XPathConstants.NODESET);
		        for (int j = 0; j < PerTranRows.getLength(); j++) {
		                       
		            Element PerTrans = (Element)PerTranRows.item(j);
		            
		            
		            String[] OSiteInfo = PerTrans.getElementsByTagName("SiteInformation").item(0).getFirstChild().getTextContent().split(" ");
		            
		            try {
		            String OSiteRef = PerTrans.getElementsByTagName("SiteReference").item(0).getFirstChild().getTextContent();
		            if (OSiteRef != null) {}
		            else {OSiteRef ="";}
		            		//.replaceAll(null,"");
		            }  catch(Exception e){} 
		            String OMSISDN = PerTrans.getElementsByTagName("MSISDN").item(0).getFirstChild().getTextContent();

		            String OPeriodStartDate = PerTrans.getElementsByTagName("PeriodStartDate").item(0).getFirstChild().getTextContent();
		            String OPeriodEndDate = PerTrans.getElementsByTagName("PeriodEndDate").item(0).getFirstChild().getTextContent();
		            String OUnits = PerTrans.getElementsByTagName("Quantity").item(0).getFirstChild().getTextContent();
		            String OUOM = PerTrans.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
		            String ORate = PerTrans.getElementsByTagName("UnitPrice").item(0).getFirstChild().getTextContent();
		            String OTotalCharge = PerTrans.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
		            
		            String  OFeeDetailTableRow = (OSiteInfo[0]+" "+OMSISDN+ " " +OPeriodStartDate+" " +OPeriodEndDate
		            		                    +" "+OUnits+" "+OUOM+" "+ORate+" "+OTotalCharge);
		            //OSiteRef+" "+   ****************** OFeeDetailTableRow =OFeeDetailTableRow.replace(null, "");
		            OFeeDetailTableRowsMap.put("Line"+j+" ",OFeeDetailTableRow );
		            
		        }
		            
		        
		        }
		        return OFeeDetailTableRowsMap;  
			}
		    public static LinkedHashMap<String,String> readFDChargeTotalsXML(Document doc) throws Exception{
		        
		        LinkedHashMap<String, String> FeeDetailTableTotalMap= new LinkedHashMap<>();
		        String CustomerInvoicePreference = doc.getElementsByTagName("CustomerInvoicePreference").item(0).getTextContent();
		        
		        if (CustomerInvoicePreference.equals("DETAIL")) {      
		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprRecurring = xpath.compile("//CustomerNodeList/CustomerNode/ChargeTypeTotal");
		        
		        NodeList PerTransRows = (NodeList)exprRecurring.evaluate(doc, XPathConstants.NODESET);
		        for (int i = 0; i < PerTransRows.getLength(); i++) {
		                       
		            Element PerTransaction = (Element)PerTransRows.item(i);
		         
		            
		            String ChargeTotalsFD = PerTransaction.getElementsByTagName("ChargeTypeTolAmt").item(0).getFirstChild().getTextContent();
		            String ChargeType = PerTransaction.getElementsByTagName("ChargeType").item(0).getFirstChild().getTextContent();
		            
		            FeeDetailTableTotalMap.put("Line"+i+" ","Total "+ChargeType+" "+ChargeTotalsFD );
		                      
		        }
		        }    
		        return FeeDetailTableTotalMap;
		            
			}		    
		/********************************AIRTIME DETAIL RETAIL CUSTOMER*******************************************/
		    public static LinkedHashMap<String,String> readADGeneralVariantLinesXML(Document doc,String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> AirtimeDetailTableRowsMap= new LinkedHashMap<>();
		       

		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprAirtimeD = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/UsageCharges/UsageDetails");
		        
		        NodeList PerRows = (NodeList)exprAirtimeD.evaluate(doc, XPathConstants.NODESET);
		        for (int i = 0; i < PerRows.getLength(); i++) {
		                       
		            Element PerTransAD = (Element)PerRows.item(i);
		            
		            switch (VarType) {
		            
		            case "CallDateTime":   	
		            try {
		            String CallDateTimeAD = PerTransAD.getElementsByTagName("DateTime").item(0).getFirstChild().getTextContent();
		          
		            AirtimeDetailTableRowsMap.put("Line"+i+" ",CallDateTimeAD+" ");
		            }catch(Exception e){}
		            break;            
		            case "DestinationCountry":         
		            try {
				           String[] DestinationAD = PerTransAD.getElementsByTagName("DestinationCountry").item(0).getFirstChild().getTextContent().replace (",",""
				          		 ).split(" "); 
				           int desC =0;
				           do {
				        	   AirtimeDetailTableRowsMap.put(desC+"Line"+i,DestinationAD[desC]);
				      	
				        	   desC++;
				             }while (DestinationAD[desC]!= "" ||DestinationAD[desC]!= null);
				           
				      	 }catch(Exception e){}
				      	 break;
				      	 
		            case "AirtimeType":          
		                try {
		    		           String[] AirtimeType = PerTransAD.getElementsByTagName("AirtimeType").item(0).getFirstChild().getTextContent().replace (",",""
		    		          		 ).split(" "); 
		    		           int atC =0;
		    		           do {
		    		        	   AirtimeDetailTableRowsMap.put(atC+"Line"+i,AirtimeType[atC]);
		    		        	   atC++;
		    		             }while (AirtimeType[atC]!= "" ||AirtimeType[atC]!= null);
		    		           
		    		      	 }catch(Exception e){}
		    		      	 break;
		    		      	 
		           default:
		        	   try {
		        	String UnitsAD ;
		            String CalledNumberAD = PerTransAD.getElementsByTagName("DestinationNumber").item(0).getFirstChild().getTextContent();
		            String AllowanceAD = PerTransAD.getElementsByTagName("Allowance").item(0).getFirstChild().getTextContent();
		            String Volume =PerTransAD.getElementsByTagName("Volume").item(0).getFirstChild().getTextContent();
		            String Duration =PerTransAD.getElementsByTagName("Duration").item(0).getFirstChild().getTextContent();
		            if (Volume.equals("-")) {
		            UnitsAD = Duration;
		            }
		            else {UnitsAD = Volume;}
		            String UoMAD = PerTransAD.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
		            String RateAD = PerTransAD.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
		            String TotalChargeAD = PerTransAD.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
		            
		            String  AirtimeDetailTableRow = " "+CalledNumberAD+" "+AllowanceAD+" "+UnitsAD+" "+UoMAD+" "+RateAD+" "+TotalChargeAD;
		            AirtimeDetailTableRowsMap.put("Line"+i,AirtimeDetailTableRow);
		        	   }catch(Exception e){} 
		           break; 
		            }
		        }
		            
		        return AirtimeDetailTableRowsMap;
		            
			}
	        public static LinkedHashMap<String,String> readADGeneralVariantCLinesXML(Document doc,String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> AirtimeDetailTableRowsMap= new LinkedHashMap<>();
		       

		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprAirtimeD = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/UsageCharges/UsageDetails");
		        
		        NodeList PerRows = (NodeList)exprAirtimeD.evaluate(doc, XPathConstants.NODESET);
		        for (int i = 0; i < PerRows.getLength(); i++) {
		                       
		            Element PerTransAD = (Element)PerRows.item(i);
		            
		            switch (VarType) {
		            
		            case "CallDateTime":   	
		            try {
		            String CallDateTimeAD = PerTransAD.getElementsByTagName("DateTime").item(0).getFirstChild().getTextContent();
		          
		            AirtimeDetailTableRowsMap.put("Line"+i+" ",CallDateTimeAD+" ");
		            }catch(Exception e){}
		            break;            
		            case "MsgRefNo":         
		            try {
				           String[] MsgRefNo = PerTransAD.getElementsByTagName("MessageRefNo").item(0).getFirstChild().getTextContent().replace (",",""
				          		 ).split(" "); 
				           int desC =0;
				           do {
				        	   AirtimeDetailTableRowsMap.put(desC+"Line"+i,MsgRefNo[desC]);
				      	
				        	   desC++;
				             }while (MsgRefNo[desC]!= "" ||MsgRefNo[desC]!= null);
				           
				      	 }catch(Exception e){}
				      	 break;
		            case "AddInfo":         
			            try {
					           String[] AddInfo = PerTransAD.getElementsByTagName("DestinationNumber").item(0).getFirstChild().getTextContent().replace (",",""
					          		 ).split(""); 
					           int desC =0;
					           do {
					        	   AirtimeDetailTableRowsMap.put(desC+"Line"+i,AddInfo[desC]);
					      	
					        	   desC++;
					             }while (AddInfo[desC]!= "" ||AddInfo[desC]!= null);
					           
					      	 }catch(Exception e){}
					      	 break;      	 
		           default:
		        	   try {
		        	String UnitsAD ;
		        	//String MessageType = PerTransAD.getElementsByTagName("MessageType").item(0).getFirstChild().getTextContent();
		            //String CalledNumberAD = PerTransAD.getElementsByTagName("DestinationNumber").item(0).getFirstChild().getTextContent();
		            String CallDirection = PerTransAD.getElementsByTagName("DIR").item(0).getFirstChild().getTextContent();
		           // String AllowanceAD = PerTransAD.getElementsByTagName("Allowance").item(0).getFirstChild().getTextContent();
		            String Volume =PerTransAD.getElementsByTagName("Volume").item(0).getFirstChild().getTextContent();
		            String Duration =PerTransAD.getElementsByTagName("Duration").item(0).getFirstChild().getTextContent();
		            if (Volume.equals("-")) {
		            UnitsAD = Duration;
		            }
		            else {UnitsAD = Volume;}
		            String UoMAD = PerTransAD.getElementsByTagName("UOM").item(0).getFirstChild().getTextContent();
		            String RateAD = PerTransAD.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
		            String TotalChargeAD = PerTransAD.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();
		            
		            String  AirtimeDetailTableRow = " "+CallDirection+" "+UnitsAD+" "+UoMAD+" "+RateAD+" "+TotalChargeAD;
		            AirtimeDetailTableRowsMap.put("Line"+i,AirtimeDetailTableRow);
		        	   }catch(Exception e){} 
		           break; 
		            }
		        }
		            
		        return AirtimeDetailTableRowsMap;
		            
			}
		    public static LinkedHashMap<String,String> readADGeneralVariantTotalsXML(Document doc,String VarTotal) throws Exception{
		        
		        LinkedHashMap<String, String> AirtimeDetailTableRowsMap= new LinkedHashMap<>();
		        

		        switch (VarTotal) {
		        case "AirtimeTotal":
		        	
		        XPath xpath = XPathFactory.newInstance().newXPath();
		        XPathExpression exprAirtimeD = xpath.compile("//CustomerNodeList/CustomerNode/UsageChargesSubtotal/UsageChargesPerCaller/PerGroupType");
		        
		        NodeList PerRows = (NodeList)exprAirtimeD.evaluate(doc, XPathConstants.NODESET);
		        for (int a = 0; a < PerRows.getLength(); a++) {
		                       
		            Element PerTransAD = (Element)PerRows.item(a);
		      
		            String AirtimeTotal = PerTransAD.getElementsByTagName("TotalPerType").item(0).getFirstChild().getTextContent();
		          
		            AirtimeDetailTableRowsMap.put("AirtimeTotal"+a+" ","Total for Airtime Type "+AirtimeTotal);
		            
		            }   
		        case "SIMTotal":
		        	
		            XPath xpathSim = XPathFactory.newInstance().newXPath();
		            XPathExpression exprADSim = xpathSim.compile("//CustomerNodeList/CustomerNode/UsageChargesSubtotal/UsageChargesPerCaller");
		            
		            NodeList PerSimRows = (NodeList)exprADSim.evaluate(doc, XPathConstants.NODESET);
		            for (int b = 0; b < PerSimRows.getLength(); b++) {
		                           
		                Element PerTransAD = (Element)PerSimRows.item(b);
		          
		                String AirtimeTotal = PerTransAD.getElementsByTagName("TotalChargePerSIM").item(0).getFirstChild().getTextContent();
		              
		                AirtimeDetailTableRowsMap.put("SIMTotal"+b+" ","Total for SIM/Terminal "+AirtimeTotal);
		                
		                } 
		            default:
		            	try {
		            	
		            	AirtimeDetailTableRowsMap.put("GrandTotalAD","Total for Airtime Detail "+doc.getElementsByTagName("TotalUsagesConsolidated").item(0).getFirstChild().getTextContent());
		            	}catch (Exception e) {}
		        }
		            
		        return AirtimeDetailTableRowsMap;
		            
			}
		 /********************************FEE SUMMARY WHOLESALE CUSTOMER*******************************************/
	      public static LinkedHashMap<String,String> readFSGroupChargeXML(Document doc) throws Exception {
				      
				      LinkedHashMap<String, String> FeeSummaryTableGCRowsMap= new LinkedHashMap<>();

				      XPath xpath = XPathFactory.newInstance().newXPath();
				      XPathExpression exprsGC = xpath.compile("//Fees/TotalforAllRatePlans/Subtotal");
				      
				      NodeList GroupChargeRows = (NodeList)exprsGC.evaluate(doc, XPathConstants.NODESET);
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
		  public static LinkedHashMap<String,String> readFeeSummaryLinesXML(Document doc) throws Exception{
		        
		        LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();

		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprFees = xpath.compile("//Fees/PerTransaction/ChargePerTransaction");
		        
		        NodeList PerTransRows = (NodeList)exprFees.evaluate(doc, XPathConstants.NODESET);
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
		  public static LinkedHashMap<String,String> readFSSubtotalXML(Document doc) throws Exception {
		      
		      LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();

		      XPath xpath = XPathFactory.newInstance().newXPath();
		       XPathExpression exprfsPD = xpath.compile("//Fees/TotalforAllRatePlans/Subtotal");
		      
		      NodeList PerProdRows = (NodeList)exprfsPD.evaluate(doc, XPathConstants.NODESET);
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
		  public static LinkedHashMap<String,String> readFSChargeTotalXML(Document doc) throws Exception {
		        
		        LinkedHashMap<String, String> FeeSummaryTableTotalsMap= new LinkedHashMap<>();

		        XPath xpath = XPathFactory.newInstance().newXPath();
		        XPathExpression exprfsTT = xpath.compile("//Fees/FeeSummarySubtotal/ChargeTypeSum");
		        
		        NodeList PerProdRows = (NodeList)exprfsTT.evaluate(doc, XPathConstants.NODESET);
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
		  public static LinkedHashMap<String,String> readFSProductGroupXML(Document doc) throws Exception {
		        
		        LinkedHashMap<String, String> FeeSummaryTableProductsMap= new LinkedHashMap<>();

		        XPath xpath = XPathFactory.newInstance().newXPath();      
		         XPathExpression exprfsTT = xpath.compile("//Fees/TotalforAllRatePlans/ProductGroupTotal");
		        
		        NodeList PerProdRows = (NodeList)exprfsTT.evaluate(doc, XPathConstants.NODESET);
		        for (int i = 0; i < PerProdRows.getLength(); i++) {
		                       
		            Element PerProductGroup = (Element)PerProdRows.item(i);
		            
		            try {
		                
		                
		                String ProductType = PerProductGroup.getElementsByTagName("ProductType").item(0).getFirstChild().getTextContent();
		                
		                FeeSummaryTableProductsMap.put("Set"+i,ProductType);
		                
		            }  catch(Exception e){}  
		            
		        }       
		             return FeeSummaryTableProductsMap;               

		  	}	
		  public static LinkedHashMap<String,String> readFStotalXML(Document doc) throws Exception {
		        
		        LinkedHashMap<String, String> FeeSummaryTableTotal= new LinkedHashMap<>();
		        try {
 
		                String SummaryTotal = doc.getElementsByTagName("FeeSumTotal").item(0).getTextContent();
		                
		                String  FeeSummaryTableRow = ("Total Fee Summary "+SummaryTotal);
		                FeeSummaryTableTotal.put("TFSTotal",FeeSummaryTableRow);
		        }catch (Exception e) {}
		                return FeeSummaryTableTotal;
		    }
		/********************************AIRTIME SUMMARY WHOLESALE CUSTOMER*******************************************/  
		public static LinkedHashMap<String,String> readASGroupChargeXML(Document doc) throws Exception {
			      
			      LinkedHashMap<String, String> AirtimeSummaryTableGCRowsMap= new LinkedHashMap<>();
			      
			      XPath xpath = XPathFactory.newInstance().newXPath();

			      
			       XPathExpression exprasGC = xpath.compile("//Airtime/ChargeDetails");
			      
			      NodeList GroupChargeRows = (NodeList)exprasGC.evaluate(doc, XPathConstants.NODESET);
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
		  public static LinkedHashMap<String,String> readAirtimeSummaryLinesXML(Document doc,String VarType) throws Exception{
		        
		        LinkedHashMap<String, String> AirtimeSummaryTableRowsMap= new LinkedHashMap<>();
		       
		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprAirtime = xpath.compile("//Airtime/ChargeDetails/ChargePerTransaction");
		        
		        NodeList PerChargeRows = (NodeList)exprAirtime.evaluate(doc, XPathConstants.NODESET);
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
		  public static LinkedHashMap<String,String> readAStotalXML(Document doc) throws Exception {
		      
		      LinkedHashMap<String, String> AirtimeSummaryTableTotal= new LinkedHashMap<>();	      
		     
		              try {
		              String SummaryTotal = doc.getElementsByTagName("AirtimeOverAllTotal").item(0).getTextContent();
		              
		              String  FeeSummaryTableRow = ("Total Airtime Summary "+SummaryTotal);
		              AirtimeSummaryTableTotal.put("TASTotal",FeeSummaryTableRow);
		              }catch(Exception e){}
		              return AirtimeSummaryTableTotal;
		  }		    
    /************************************ADJUSTMENTS SECTION********************************************/
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
	/************************************CARRIER AIRTIME SECTION****************************************/		
		 public static LinkedHashMap<String,String> readCarrierAirtimeLinesXML(Document doc) throws Exception{
		        
		        LinkedHashMap<String, String> AirtimeSummaryTableRowsMap= new LinkedHashMap<>();
		       
		       
		        XPath xpath = XPathFactory.newInstance().newXPath();

		        XPathExpression exprAirtime = xpath.compile("//CarrierAirtime/ChargeDetails/PerTransaction");
		        
		        NodeList PerChargeRows = (NodeList)exprAirtime.evaluate(doc, XPathConstants.NODESET);
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
	     public static LinkedHashMap<String,String> readCAtotalsXML(Document doc) throws Exception {
	        
	        LinkedHashMap<String, String> CAPageMap= new LinkedHashMap<>();
	        
	        

	        XPath xpath = XPathFactory.newInstance().newXPath();
	         XPathExpression exprCA = xpath.compile("//CarrierAirtime/TotalDetails/Total");
	        
	           NodeList PerCATransRows = (NodeList)exprCA.evaluate(doc, XPathConstants.NODESET);
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
         public static LinkedHashMap<String,String> readCAGrandTotalXML(Document doc) throws Exception {
	        
	        LinkedHashMap<String, String> CAPageMap= new LinkedHashMap<>();
	        
	        XPath xpath = XPathFactory.newInstance().newXPath();
	         XPathExpression exprCA = xpath.compile("//CarrierAirtime/TotalDetails/TotalChargeByCurrency");
	        
	           NodeList PerCATransRows = (NodeList)exprCA.evaluate(doc, XPathConstants.NODESET);
	          
	           Element PerTransaction = (Element)PerCATransRows.item(0); 
	           try {
	           String Total = PerTransaction.getElementsByTagName("TotalInUSD").item(0).getFirstChild().getTextContent();
	           
	           CAPageMap.put("GrandTotal", "Total Carrier Airtime "+Total);
	           
	           }catch(Exception e){
	           
	           }
	                              
	           
	           return CAPageMap;
	    }
	/***********************************OTHER REVENUE SECTION*******************************************/
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
	        	 
	        	 try {

	      	   String GrandTotalAdj = doc1.getElementsByTagName("TotalOtherRevenue").item(0).getFirstChild().getTextContent();   
	      	 ORTableRowsMap.put("GrandTotalAdj","Total Other Revenue "+GrandTotalAdj);
	        	 }catch(Exception e){}
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
	     /***********************************CREDITS?DEBITS SECTION*******************************************/	
	     public static LinkedHashMap<String,String> readCNTLinesXML(Document doc,String VarType) throws Exception{
	         
	         LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();

	         XPath xpath = XPathFactory.newInstance().newXPath();

	         XPathExpression exprCNT = xpath.compile("//CreditNote/Adjustments/PerLineItem");
	         
	         NodeList PerCNTTransRows = (NodeList)exprCNT.evaluate(doc, XPathConstants.NODESET);
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
	     public static LinkedHashMap<String,String> readCDtotalXML(Document doc,String VarType) throws Exception {
	         
	         LinkedHashMap<String, String> CNTSecondPageMap= new LinkedHashMap<>();
	        
	         XPath xpath = XPathFactory.newInstance().newXPath();
	          XPathExpression exprCNT = xpath.compile("//CreditNote");
	         
	            NodeList PerCNTTransRows = (NodeList)exprCNT.evaluate(doc, XPathConstants.NODESET);
	            Element PerTransaction = (Element)PerCNTTransRows.item(0);  
	            
	            switch (VarType){
	        	
	          	case "CDtotal":
	                 try {
	             String SummaryTotal = PerTransaction.getElementsByTagName("TotalAmount").item(0).getFirstChild().getTextContent();
	                 
	                
	                 CNTSecondPageMap.put("CDTotal",("Total Credits / Debits "+SummaryTotal));
	                 } catch (Exception e) {}
	                 break;  
	          	  
	            }
	                 return CNTSecondPageMap;               

	   	}
}		


		/******************************************************************************************************************/



		
