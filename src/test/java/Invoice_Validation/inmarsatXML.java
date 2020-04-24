//All the variables should be declared
//======================================================
// Class      : inmarsatXML
// Description: Read the Single View XML for invoice Validation
//              the Single View XML from the InmarsatPDFExcel.xlsx
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
// 09/04/20     Trishita Tadala     Modification
//======================================================

package Invoice_Validation;

import org.xml.sax.SAXException;
import java.util.LinkedHashMap;
import java.util.Iterator;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class inmarsatXML { 
	public static String[] DisplayText_XML = new String[50];
	public static String[] CustomerInvoicePreference_XML = new String[50];
    public static String[] InvoiceNumber_XML= new String[100]; //Invoice Number
    public static String[] InvoiceNumber_SAP_XML = new String[100];
	public static String[] ADJNmber_XML= new String[50]; //ADJ Number
	
	public static String[] BillToFrontPage_XML = new String[5000];//BillTo Address Front Page
	public static String[] Name_XML = new String[5000];
	public static String[] Line1_XML = new String[5000];
	public static String[] Line2_XML = new String[5000];
	public static String[] Suburb_XML = new String[5000];
	public static String[] State_XML = new String[5000];
	public static String[] City_XML = new String[5000];
	public static String[] Country_XML = new String[5000];
	public static String[] Postcode_XML = new String[5000];
	
	public static String[] PricingAgreeID_XML = new String[5000];// Front Page Total Taxes
	
	public static String[] LegacyIDs_XML = new String[5000];// LegacyIDs Cluster
	public static String[] AccountNo_XML = new String[5000];//AccountNo
	public static String[] BillingProfile_XML =  new String[5000];//BillingProfile
	public static String[] MIPsID_XML =  new String[5000];//MIPS Account ID
	public static String[] DPID_XML =  new String[5000];//DP ID
	//public static String[] ShipAccID_XML =  new String[5000];//Ship Account ID
	
		
	public static String[] BillToReference_XML = new String[5000];//Bill to reference
	public static String[] BillTo_XML = new String[5000];//Bill to reference
	public static String[] SoldToReference_XML = new String[5000];//Sold to reference
	public static String[] SoldTo_XML = new String[5000];//Sold to reference
	public static String[] YourReference_XML = new String[5000];//Your reference
	public static String[] Currency_XML = new String[50];// CustomerInvoice Currency
	public static String[] InvoiceDate_XML = new String[20];//Invoice Date
	public static String[] TotalFees_XML= new String[5000]; //Fee Section
	public static String[] TotalAirtime_XML = new String[5000]; //AirtimeDetail Section
	public static String[] TotalAdjust_XML = new String[5000]; //Adjustments Section
	public static String[] TotalCarrierAirtime_XML = new String[5000]; // Carrier Airtime Section
	public static String[] PrepaidAirtime_XML = new String[5000]; //Prepaid Airtime Section
	public static String[] TotalAmountexcl_XML= new String[5000]; // Front Page Summary Amount Exclusive Taxes
	public static String[] TotalAmountincl_XML= new String[5000]; // Front Page Summary Amount Inclusive Taxes
	public static String[] TotalTaxes_XML = new String[5000];// Front Page Total Taxes
	
	public static String[] Salesorg_XML = new String[5000]; //Sales Org tag for Taxes logic
	public static String[] BillRunType_XML = new String[5000];
	public static String billRunType;
	public static String[][] Product = new String[50][50];
	public static String[][] Total = new String[50][50];
	public static String[] Totals = new String[50];
	public static String[] CreditsorDebits_XML= new String[5000]; // Front Page Summary Amount Exclusive Taxes
	
	
	
	public static String[] USFFee_XML =  new String[5000];
	public static String[] TaxInfo_XML =  new String[5000];
	public static String[][] TaxType = new String[500][500];
	
	public static String[] FeeSummaryLines_XML = new String[100000];
	public static String[] Footer_XML = new String[5000];
	public static int y=0;
	public static int m=0;
	public static int p=0;
	public static void inmarsatxml() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	
		
			for(int k =0;k<ReadExcelFile.i;k++){	
			System.out.println(ReadExcelFile.XMLFILENAME[k]);
			File fXmlFile = new File(ReadExcelFile.XMLFILENAME[k]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			
 /******************************************XPATH DECLARATION****************************/		
			
			javax.xml.xpath.XPath xPath =  XPathFactory.newInstance().newXPath();
			
/******************Invoice Number for a Standard Invoice************************/		
			try {
			InvoiceNumber_SAP_XML[k]= doc.getElementsByTagName("InvoiceNumber").item(1).getTextContent();
			DisplayText_XML[k] = doc.getElementsByTagName("DisplayText").item(0).getTextContent();
			}catch(Exception e){}

/******************Billing Address for a Standard Invoice dated 7-Apr-2020************************/	
			
			
			String billtoparty ="//Bill_To_Party/AddressDetails";
			NodeList Addlist = (NodeList) xPath.compile(billtoparty).evaluate(doc, XPathConstants.NODESET);
	        
			try {
				BillToFrontPage_XML[k] = doc.getElementsByTagName("BillToCustomerName").item(0).getTextContent();
			}catch(Exception e){}
			
			int count = (Addlist.getLength());
			try {
			for (int i = 0;i<=count;i++)
			{
				BillToFrontPage_XML[k]+= Addlist.item(i).getTextContent();
			}
			
			}catch(Exception e){}
/******************************Tax Related Logic Start*******************************************/
			
			Salesorg_XML[k] = doc.getElementsByTagName("SalesOrg").item(0).getTextContent();
			if  (Salesorg_XML[k]== "6720") {
		           TotalTaxes_XML[k] = doc.getElementsByTagName("VATInNOK").item(0).getTextContent();
 		        }
			else {
			
/***************************** USF Fee, dated March-20-2020 ****************************/
			
			String pertaxtype ="//PerTaxType/TaxType";
			NodeList taxtype = (NodeList) xPath.compile(pertaxtype).evaluate(doc, XPathConstants.NODESET);
	        NodeList taxnode = (NodeList) taxtype;
	        m = taxnode.getLength();
	        for (int i = 0; i < taxnode.getLength(); i++) {
	        	try{
	        		TaxType[k][i]= (taxnode.item(i).getFirstChild().getNodeValue());
	        		//System.out.println(doc.getElementsByTagName("TotalTaxAmount").item(i).getTextContent());
	        		if(TaxType[k][i].equals("USF Fee")) {
	        			USFFee_XML[k] = doc.getElementsByTagName("TotalTaxAmount").item(i+1).getTextContent();
	        			TotalTaxes_XML[k] = doc.getElementsByTagName("TaxesWithOutUSF").item(0).getTextContent();
	        		}
	        		
                    else if(TaxType[k][i]!= ("USF Fee")) 
                    
                    {
                    	TotalTaxes_XML[k] = doc.getElementsByTagName("GrandTotalTaxAmtAC").item(0).getTextContent(); 
                    	
	                }
	        		
	        		
	        	}
	        		catch(Exception e){}
			}
			}
			try{
			TaxInfo_XML[k] = doc.getElementsByTagName("TaxType").item(0).getTextContent()
					   + doc.getElementsByTagName("TaxPercentage").item(0).getTextContent()
				       + doc.getElementsByTagName("TotalTaxAmount").item(0).getTextContent()
				       + doc.getElementsByTagName("TaxDisclosure").item(0).getTextContent()
				       ;
		    TaxInfo_XML[k] = TaxInfo_XML[k].replaceAll("null ", "");
			}
    		catch(Exception e){}
//**********************************Tax Related Logic End*******************************************  
	        			
/*************************Invoice Details tags logic, dated Jan-14-2020 **********************************************************/
			
			try {
				//Invoice Number
				InvoiceNumber_XML[k] = doc.getElementsByTagName("InvoiceNumber").item(0).getTextContent();
				//ADJNumber
				ADJNmber_XML[k] = doc.getElementsByTagName("ADJNumber").item(0).getTextContent();
				//Bill To Reference
				BillToReference_XML[k] = doc.getElementsByTagName("BillingProfileId").item(0).getTextContent();
				//Sold To Reference
				SoldToReference_XML[k] = doc.getElementsByTagName("SAPAccountNumber").item(0).getTextContent();
				//Your Reference
				YourReference_XML[k] = doc.getElementsByTagName("YourReference").item(0).getTextContent();
			    } catch (Exception e) {}
			
			
			//Front Page Total Fees tags from the Single View XML Jan-8-2020
			try {
			TotalFees_XML[k]=doc.getElementsByTagName("TotalFees").item(0).getTextContent();
				}catch (Exception e) {
					//e.printStackTrace();
											}
			try {
			TotalAirtime_XML[k] = doc.getElementsByTagName("TotalAirtimeCharges").item(0).getTextContent();
			}catch (Exception e) {
				//e.printStackTrace();
										}
			try {
			TotalAdjust_XML[k]= doc.getElementsByTagName("TotalAdjustment").item(0).getTextContent();
			}catch (Exception e) {
				//e.printStackTrace();
										}
			try {
			TotalCarrierAirtime_XML[k]=doc.getElementsByTagName("TotalInUSD").item(0).getTextContent();
			}catch (Exception e) {
				//e.printStackTrace();
										}
			try {
				PrepaidAirtime_XML[k]=doc.getElementsByTagName("VoucherTotal").item(0).getTextContent();
				}catch (Exception e) {
					//Prepaid Airtime Total Jan-8-2020
											}
			
			try {
			TotalAmountexcl_XML[k]=doc.getElementsByTagName("NetAmount").item(0).getTextContent();
			}catch (Exception e) {}
				//e.printStackTrace();
			
/********************************************LEGACY IDS*******************************************/	
			try {
			AccountNo_XML[k] = doc.getElementsByTagName("SAPAccountNumber").item(0).getTextContent();
			BillingProfile_XML[k] = doc.getElementsByTagName("BillingProfileCode").item(0).getTextContent();
			MIPsID_XML[k] =  doc.getElementsByTagName("MIPSMasterAccountId").item(0).getTextContent();
			DPID_XML[k] =  doc.getElementsByTagName("DPId").item(0).getTextContent();
			//ShipAccID_XML[k] = doc.getElementsByTagName("GWShipAccntId").item(0).getTextContent();
			}catch (Exception e) {}
			
			LegacyIDs_XML[k] = "Legacy IDs: Account Number: "+ AccountNo_XML[k]
					          +".Billing Profile: "+BillingProfile_XML[k]
					          +".MIPS Master Acct ID: "+ MIPsID_XML[k]
					          //+". Ship Acct ID:"+ ShipAccID_XML
					          +".DP ID: "+DPID_XML[k]+".";
			
			LegacyIDs_XML[k] =LegacyIDs_XML[k].replaceAll("null", "");
			LegacyIDs_XML[k] =LegacyIDs_XML[k].replaceAll(" ", "");


/********************************************STANDARD BILL RUN*******************************************/

/******************Fee Summary Line Items for a Standard Invoice Wholesale Customer************************/
			
			BillRunType_XML[k] = doc.getElementsByTagName("BillRunType").item(0).getTextContent();
			
			CustomerInvoicePreference_XML[k] =doc.getElementsByTagName("CustomerInvoicePreference").item(0).getTextContent();
			          
		             
		            	
			// Invoice or Credit Note
			
			if (inmarsatXML.BillRunType_XML[k].equals("Standard Bill Run")) {
			
			DisplayText_XML[k] = doc.getElementsByTagName("DisplayText").item(0).getTextContent();
			
 /*********** Taxes is driven by conditional logic, dated Jan-14-2020 needs updated*************/	
			
								
        
			try {
				TotalAmountincl_XML[k] =doc.getElementsByTagName("TotalAmountDue").item(0).getTextContent();
			}
			catch (Exception e) {
				//e.printStackTrace();
										}
			try {
				Currency_XML[k] =doc.getElementsByTagName("CustomerInvoiceCurrency").item(0).getTextContent();
			}
			catch (Exception e) {
				//e.printStackTrace();
										}
			try {
				InvoiceDate_XML[k] =doc.getElementsByTagName("InvoiceDate").item(0).getTextContent();
			}
			catch (Exception e) {}
			
			try {
				PricingAgreeID_XML[k] =doc.getElementsByTagName("PricingAgreementID").item(0).getTextContent();
			}
			catch (Exception e) {}
			

			
			
/********************************************FOOTER***********************************************************/			
			try {
				Footer_XML[k] ="Soldby:"+doc.getElementsByTagName("SLEName").item(0).getTextContent()
						+" "+doc.getElementsByTagName("SLEAddress").item(0).getTextContent()
						+"|"+doc.getElementsByTagName("TaxRegNoDescription").item(0).getTextContent()
						+" "+doc.getElementsByTagName("TaxRegistrationNumber").item(0).getTextContent()
						+"|"+"www.inmarsat.com";
			}
			catch (Exception e) {}
			Footer_XML[k] = Footer_XML[k].replaceAll("null", "");
			Footer_XML[k] = Footer_XML[k].replaceAll(" ", "");
				
			
			
			
	        
		} 
			
//*************************IMMEDIATE BILL RUN*******************************************
			else if (InvoiceNumber_SAP_XML[k]!=null) {
			
				try {
				      BillTo_XML[k] = doc.getElementsByTagName("BillTo").item(0).getTextContent();
				      SoldTo_XML[k] = doc.getElementsByTagName("SoldTo").item(0).getTextContent();
				      
						} catch (Exception e) {}
							
					}
			else {
				
				try {
				DisplayText_XML[k] = doc.getElementsByTagName("DisplayText").item(1).getTextContent();
				CreditsorDebits_XML[k] = doc.getElementsByTagName("TotalAmount").item(1).getTextContent();
				TotalTaxes_XML[k] = doc.getElementsByTagName("TotalTaxConsolidationLC").item(0).getTextContent();
				}catch(Exception e){}
				
			}


}
	}
}




