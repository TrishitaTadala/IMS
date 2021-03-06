//All the variables should be declared
//======================================================
// Class      : Wholesale Customer
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 24/04/20     Trishita Tadala     Written
// 27/04/20     Trishita Tadala     ServiceSummary Section
// XX/XX/20     Trishita Tadala     
//======================================================

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

public class Servicesummary {

	
	public static String PDFtext;
	public static String  xmlSSrows;
	public static boolean q;
	public static String SSRemarks;
	public static String xmlpath;
	
	public static void main(String[] args) throws Exception {	 
	//public static void servicesummary(String filename, String xmlpath) throws Exception {
		LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String>XMLHashMap  = new LinkedHashMap(); //XML Contents
		 PDFHashMap.clear();
			
		//String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1590428_954226_202004.pdf";
		//String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
		 String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\INVOICE_5448299_117921_202004.pdf";
			
		String xmlpath ="C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\Wholesale\\QA_129171_117921_5448299_20200430.xml";
		
        File xmlinputFile = new File(xmlpath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        
		PdfReader reader = new PdfReader(filename);  
		
		PDFHashMap.put("ServiceSummaryPDF"+"\n", getServiceSummary(reader));
				
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			//System.out.println(key + PDFHashMap.get(key));
			PDFtext = (key+PDFHashMap.get(key)).replaceAll(",", "");
			//System.out.println(PDFtext);
		}
		
		System.out.println("********************XML SERVICE SUMMARY Values*************************\n");
		   Compare(readSSGroupXML(doc1));
		   Compare(readSSProductXML(doc1));
		   Compare(readSSActivationXML(doc1));
		   Compare(readSSSubscriptionsXML(doc1));
		   Compare(readSSCancellationXML(doc1));
		   Compare(readSSEarlyTerminationXML(doc1));
		   Compare(readSSUsageXML(doc1));
		   Compare(readSSOtherXML(doc1)); //Further Modification Required
		   Compare(readSStotalXML(doc1));
		   Compare(readSSSubtotalXML(doc1));
		   Compare(readSSAllTotalXML(doc1));
		   Compare(readSSGrandTotalXML(doc1));
		   //return q;
       	}
	

	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSActivationXML(Document doc) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";

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
	public static LinkedHashMap<String,String> readSSSubscriptionsXML(Document doc) throws Exception {
        
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
	public static LinkedHashMap<String,String> readSSCancellationXML(Document doc) throws Exception {
        
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
	public static LinkedHashMap<String,String> readSSEarlyTerminationXML(Document doc) throws Exception {
        
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
    public static LinkedHashMap<String,String> readSSUsageXML(Document doc) throws Exception {
        
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
      public static LinkedHashMap<String,String> readSSOtherXML(Document doc) throws Exception {
        
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
      public static LinkedHashMap<String,String> readSStotalXML(Document doc) throws Exception {
        
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
      public static LinkedHashMap<String,String> readSSSubtotalXML(Document doc) throws Exception {
        
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
      public static LinkedHashMap<String,String> readSSAllTotalXML(Document doc) throws Exception {
        
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
      public static LinkedHashMap<String,String> readSSGrandTotalXML(Document doc) throws Exception {
        
        LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();

        XPath xpath = XPathFactory.newInstance().newXPath();
        
         XPathExpression exprSSPD = xpath.compile("//TotalForServiceSummary");
        
        NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < PerProdRows.getLength(); i++) {
                       
            Element PerProductDetails = (Element)PerProdRows.item(i);
            
            try {
                
                String GrandTotal = PerProductDetails.getElementsByTagName("GrandTotal").item(0).getFirstChild().getTextContent();
                
                String  ServiceSummaryTableRow = ("Total Service Summary "+GrandTotal);
                if (ServiceSummaryTableRow!= "0.00") {
                ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);}
                
            }  catch(Exception e){}  
            
        }       
             return ServiceSummaryTableRowsMap;               
  
	}

	/****************************************************************************/
	public static LinkedHashMap<String,String> readSSProductXML(Document doc) throws Exception {
        
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
	public static LinkedHashMap<String,String> readSSGroupXML(Document doc) throws Exception {
        
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
	
	public static String getServiceSummary(PdfReader reader) throws Exception {
		
		String PDFtext = "";
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 100);
			
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        
       
       for (int i = 2; i <= reader.getNumberOfPages()-1; i++) {
        	
        	 PDFtext += (PdfTextExtractor.getTextFromPage(reader, i, strategy));
        
        }
        return PDFtext;
	}
		
	public static void Compare(LinkedHashMap<String,String> XMLHashMap){

        //System.out.println("XML Contents ");    
        
		Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
         while(XMLkeySetIterator.hasNext()) {
			
			String keyXML = XMLkeySetIterator.next();
			
			xmlSSrows = XMLHashMap.get(keyXML);
			//System.out.println(xmlSSrows);
		
		if (  PDFtext.indexOf(xmlSSrows)!=-1? true: false){
			
			System.out.println(xmlSSrows +" It's a Match!!!");
			 q = true;
			 //SSRemarks = "\n"+ xmlSSrows +" It's a Match!!!";
			
		}
		else{
			System.out.println( xmlSSrows +" Mismatch in the Line Indicated");
				 q = false;	
				 SSRemarks += "\n"+xmlSSrows +" Mismatch in the Line Indicated";
		}
		
		
         } 
			
		
	}
	
}
	
	
	
	
	

