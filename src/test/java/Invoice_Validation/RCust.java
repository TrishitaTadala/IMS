//All the variables should be declared
//======================================================
//Class      : WCust
//Description: The WholesaleCustomer Invoice Validation 
//             with exception to Front Page and Last Pagefrom other packages
//======================================================
//Changes----------------------------------------------
//Date         Test Analyst        Change
//23/04/20     Trishita Tadala     Written
//05/05/20     Trishita Tadala     FeeDetail Section
//XX/05/20     Trishita Tadala     AirtimeDetail Section
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
public class RCust {

	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String WCustRemarks;
	
	
	public static void main(String[] args) throws Exception {
		

		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1590428_954226_202004.pdf";
				
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
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
		
		//*********FeeDetailValidation Compare(readFDChargeTotalsXML(xmlpath));*************
		/*
		Compare(readFDTotalsXML(xmlpath,"ServiceGroup"));//Fee Detail ServiceGroup
		Compare(readFDTotalsXML(xmlpath,"ProductGroup"));//Fee Detail ServiceGroup
		Compare(readFDTotalsXML(xmlpath,"RatePlan"));//Fee Detail RatePlan
		Compare(readFeeDetailRecurringLinesXML(xmlpath)); //Fee Detail Recurring Lines
		Compare(readFeeDetailOneoffLinesXML(xmlpath)); //Fee Detail OneOff Lines
		Compare(readFDTotalsXML(xmlpath,"TotalFD")); //Fee Detail Totals
		Compare(readFDChargeTotalsXML(xmlpath));//Fee Detail Charge Totals
		*/
		
		//*********AirtimeDetailValidation Compare(readADGeneralVariantLinesXML(xmlpath));*************
		Compare(readADGeneralVariantLinesXML(xmlpath));
		
	}
	
	public static void Compare(LinkedHashMap<String,String> XMLHashMap){
        
		PDFtext = PDFtext.replaceAll(",", "");
		
		System.out.println("XML Contents ");    
        
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
                
       for (int i = 3; i <= (reader.getNumberOfPages() -1); i++) {
        //for (int i = 3; i <= 5; i++) {
            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            //System.out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
            PDFtext += PdfTextExtractor.getTextFromPage(reader, i, strategy);
            
        }
        PDFtext = PDFtext.replace(",","");
                System.out.println(PDFtext);
        return PDFtext;
	}
/********************************FEE DETAIL RETAIL CUSTOMER*******************************************/
	
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
    
/********************************AIRTIME SUMMARY WHOLESALE CUSTOMER*******************************************/
    // Line Items having new Line values in the columns are identified as mismatch
    public static LinkedHashMap<String,String> readADGeneralVariantLinesXML(String xmlfilepath) throws Exception{
        
        LinkedHashMap<String, String> AirtimeDetailTableRowsMap= new LinkedHashMap<>();
       
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprAirtimeD = xpath.compile("//CustomerNodeList/CustomerNode/Product/ProductDetails/ServiceDetails/ServiceCharges/UsageCharges/UsageDetails");
        
        NodeList PerRows = (NodeList)exprAirtimeD.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < PerRows.getLength(); i++) {
                       
            Element PerTransAD = (Element)PerRows.item(i);
            
            try {
            String CallDateTimeAD = PerTransAD.getElementsByTagName("DateTime").item(0).getFirstChild().getTextContent();
           /*String DestinationAD = PerTransAD.getElementsByTagName("DestinationCountry").item(0).getFirstChild().getTextContent();
            String AirtimeTypeAD = PerTransAD.getElementsByTagName("AirtimeType").item(0).getFirstChild().getTextContent();
            String CalledNumberAD = PerTransAD.getElementsByTagName("DestinationNumber").item(0).getFirstChild().getTextContent();
            String AllowanceAD = PerTransAD.getElementsByTagName("Allowance").item(0).getFirstChild().getTextContent();
            String UnitsAD = PerTransAD.getElementsByTagName("Volume").item(0).getFirstChild().getTextContent();
            String UoMAD = PerTransAD.getElementsByTagName("UoM").item(0).getFirstChild().getTextContent();
            String RateAD = PerTransAD.getElementsByTagName("Rate").item(0).getFirstChild().getTextContent();
            String TotalChargeAD = PerTransAD.getElementsByTagName("TotalCharge").item(0).getFirstChild().getTextContent();*/
            
            //String  AirtimeDetailTableRow = CallDateTimeAD+" "+DestinationAD+" "+AirtimeTypeAD
            	//	+" "+CalledNumberAD+" "+AllowanceAD+" "+UnitsAD+" "+UoMAD+" "+" "+RateAD+" "+TotalChargeAD;
            AirtimeDetailTableRowsMap.put("Line"+i+" ",CallDateTimeAD );
            }catch(Exception e){
				
			}
        }
            
        return AirtimeDetailTableRowsMap;
            
	}

}
