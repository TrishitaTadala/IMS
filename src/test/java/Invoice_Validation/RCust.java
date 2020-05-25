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
	public static String RCustRemarks;
	
	
	public static void main(String[] args) throws Exception {
		

		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1590428_954226_202004.pdf";
				
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
			    
		
        File inputFile = new File(xmlpath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
		/****************************************************************************/
		PdfReader reader = new PdfReader(pdfpath); 
		
		PDFHashMap.put("Front&Last Pages Excluded"+"\n", getPDFtext(reader));
				
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			//System.out.println(key + PDFHashMap.get(key));
			PDFtext = PDFHashMap.get(key);
			System.out.println(PDFtext);
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
		Compare(readADGeneralVariantLinesXML(doc1,xmlpath,"CallDateTime"));
		Compare(readADGeneralVariantLinesXML(doc1,xmlpath,"DestinationCountry"));
		Compare(readADGeneralVariantLinesXML(doc1,xmlpath,"AirtimeType"));
		Compare(readADGeneralVariantLinesXML(doc1,xmlpath,"LineItems"));
		
		Compare(readADGeneralVariantTotalsXML(doc1,xmlpath,"AirtimeTotal"));
		Compare(readADGeneralVariantTotalsXML(doc1,xmlpath,"SIMTotal"));
		Compare(readADGeneralVariantTotalsXML(doc1,xmlpath,"GrandTotal"));
		
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
			RCustRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
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
               // System.out.println(PDFtext);
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
    
/********************************AIRTIME DETAIL RETAIL CUSTOMER*******************************************/
    // Line Items having new Line values in the columns are identified as mismatch
    public static LinkedHashMap<String,String> readADGeneralVariantLinesXML(Document doc,String xmlfilepath,String VarType) throws Exception{
        
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

    public static LinkedHashMap<String,String> readADGeneralVariantTotalsXML(Document doc,String xmlfilepath,String VarTotal) throws Exception{
        
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
            	
            	AirtimeDetailTableRowsMap.put("GrandTotalAD","Total for Airtime Detail "+doc.getElementsByTagName("TotalUsagesConsolidated").item(0).getFirstChild().getTextContent());
            
        }
            
        return AirtimeDetailTableRowsMap;
            
	}

}
