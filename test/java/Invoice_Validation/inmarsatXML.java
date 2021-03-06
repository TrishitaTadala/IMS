//All the variables should be declared
//======================================================
// Class      : inmarsatXML
// Description: Read the Single View XML for invoice Validation
//              the Single View XML from the InmarsatPDFExcel.xlsx
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
// 20/03/20     Trishita Tadala     Modification
//======================================================

package Invoice_Validation;

//import org.apache.xmlbeans.impl.common.XPath;
//import org.w3c.dom.*;
import org.xml.sax.SAXException;

//import ReadExcelFile.ReadExcelFile;

import javax.xml.parsers.*;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class inmarsatXML { 
	public static String[] DisplayText_XML = new String[50];
    public static String[] InvoiceNumber_XML= new String[7]; //Invoice Number
	public static String[] ADJNmber_XML= new String[50]; //ADJ Number
	//public static String[] BillTo_XML = new String[5000];
	public static String[] BillToReference_XML = new String[5000];//Bill to reference
	public static String[] SoldToReference_XML = new String[5000];//Sold to reference
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
	public static String[][] TaxType = new String[50][50];
	public static String[] USFFee_XML =  new String[50];
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
			
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			Salesorg_XML[k] = doc.getElementsByTagName("SalesOrg").item(0).getTextContent();
			if  (Salesorg_XML[k]== "6720") {
		           TotalTaxes_XML[k] = doc.getElementsByTagName("VATInNOK").item(0).getTextContent();
 		}
			else {
			
			/*********** USF Fee, dated March-20-2020 *************/
			
			javax.xml.xpath.XPath xPath =  XPathFactory.newInstance().newXPath();
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
                    
                    {TotalTaxes_XML[k] = doc.getElementsByTagName("GrandTotalTaxAmtAC").item(0).getTextContent(); 
	        }
	        	}
	        		catch(Exception e){}
			}
			}
	        
	        			
			/*********** Invoice Details tags logic, dated Jan-14-2020 *************/
			//Invoice Number
			try {
				InvoiceNumber_XML[k] = doc.getElementsByTagName("InvoiceNumber").item(0).getTextContent();
			} catch (Exception e) {
				//e.printStackTrace();
							
			}
			
			//ADJNumber
			
			try {
				ADJNmber_XML[k] = doc.getElementsByTagName("ADJNumber").item(0).getTextContent();
			} catch (Exception e) {
				//e.printStackTrace();
							
			}
			
			
			//Bill To Reference
			
			try {
				BillToReference_XML[k] = doc.getElementsByTagName("BillingProfileId").item(0).getTextContent();
			} catch (Exception e) {
				//e.printStackTrace();
				
				
			}
			
			//Sold To Reference
			
			try {
				SoldToReference_XML[k] = doc.getElementsByTagName("SAPAccountNumber").item(0).getTextContent();
			} catch (Exception e) {
				//e.printStackTrace();
				
				
			}
			
           //Your Reference
			
			try {
				YourReference_XML[k] = doc.getElementsByTagName("YourReference").item(0).getTextContent();
			} catch (Exception e) {
				//e.printStackTrace();
				
				
			}
			
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
			
			BillRunType_XML[k] = doc.getElementsByTagName("BillRunType").item(0).getTextContent();
			
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
			catch (Exception e) {
				//e.printStackTrace();
										}
			
			/****************Line Items child Nodes in the XML***************************/
			
			javax.xml.xpath.XPath xPath =  XPathFactory.newInstance().newXPath();
			String expression ="//ServiceSummary/ProductDetails/Product";
			NodeList list = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) list;
	        m = nodes.getLength();
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	try{
	        	Product[k][i]= (nodes.item(i).getFirstChild().getNodeValue());
	        }catch(Exception e){}
	        }
	        
	        String total ="//ServiceSummary/ProductDetails/Total";
			NodeList listtotal = (NodeList) xPath.compile(total).evaluate(doc, XPathConstants.NODESET);
	        NodeList nodestotal = (NodeList) listtotal;
	        m = nodestotal.getLength();
	        for (y = 0; y < nodestotal.getLength(); y++) {
	        	try{
		        	Total[y][k]= (nodestotal.item(y).getFirstChild().getNodeValue());
		        	Totals[p]=Total[y][k];
		        	
		        			p= p +1;
		        			//System.out.println(Total[y][k]);
		        }catch(Exception e){}
	        }
	        
		} 
			else {
				DisplayText_XML[k] = doc.getElementsByTagName("DisplayText").item(1).getTextContent();
				CreditsorDebits_XML[k] = doc.getElementsByTagName("TotalAmount").item(1).getTextContent();
				TotalTaxes_XML[k] = doc.getElementsByTagName("TotalTaxConsolidationLC").item(0).getTextContent();
				
			}

}
}
}

