//All the variables should be declared
//======================================================
// Class      : Retail Customer
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 24/04/20     Trishita Tadala     Written
// 2X/04/20     Trishita Tadala     ServiceSummary Section
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

public class RetailCustomer {

	
	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String ServiceSummaryLineItemsRemarks;
	
public static boolean ServiceSummaryPDF(String filename,String xmlpath) throws Exception{
		
		 //Create a hash map
		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 PDFHashMap.clear();
		 XMLHashMap.clear();
		PdfReader reader = new PdfReader(filename);  
		
		PDFHashMap.put("ServiceSummaryPDF"+"\n", getServiceSummary(reader));
		
		
		/*
		getServiceSummary(reader);*/
		
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			//System.out.println(key + PDFHashMap.get(key));
			PDFtext = (key+PDFHashMap.get(key)).replaceAll(",", "");
			System.out.println(PDFtext);
		}
			
		   Compare(readSSGroupsXML(xmlpath));
		   Compare(readSSSubscriptionsXML(xmlpath));
		
         //reader.close();
		
		return q;
	}
	
	/****************************************************************************/
public static LinkedHashMap<String,String> readSSGroupsXML(String xmlfilepath) throws Exception {
    
    LinkedHashMap<String, String> ServiceSummaryGroupRowsMap= new LinkedHashMap<>();
    
    //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
    File xmlinputFile = new File(xmlfilepath);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    
    Document doc1 = dBuilder.parse(xmlinputFile);
    doc1.getDocumentElement().normalize();
    XPath xpath = XPathFactory.newInstance().newXPath();
    
     XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
    
    NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
    for (int i = 0; i < PerProdRows.getLength(); i++) {
                   
        Element PerProductDetails = (Element)PerProdRows.item(i);
        
        String ServiceGroup = PerProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
        String Product = PerProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();         
        ServiceSummaryGroupRowsMap.put("ServiceGroup"+i,"SERVICE GROUP: "+ServiceGroup+"\n"+Product);
        
    }       
         return ServiceSummaryGroupRowsMap;               

}

public static LinkedHashMap<String,String> readSSSubscriptionsXML(String xmlfilepath) throws Exception {
    
    LinkedHashMap<String, String> ServiceSummaryTableRowsMap= new LinkedHashMap<>();
    
    //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml";
    File xmlinputFile = new File(xmlfilepath);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    
    Document doc1 = dBuilder.parse(xmlinputFile);
    doc1.getDocumentElement().normalize();
    XPath xpath = XPathFactory.newInstance().newXPath();
    
     XPathExpression exprSSPD = xpath.compile("//ServiceSummary/ProductDetails");
    
    NodeList PerProdRows = (NodeList)exprSSPD.evaluate(doc1, XPathConstants.NODESET);
    for (int i = 0; i < PerProdRows.getLength(); i++) {
                   
        Element PerProductDetails = (Element)PerProdRows.item(i);
        try {
        
        String Subscription = PerProductDetails.getElementsByTagName("Subscription").item(0).getFirstChild().getTextContent();
        
        
        String  ServiceSummaryTableRow = ("Fee - Subscriptions "+Subscription);
        ServiceSummaryTableRowsMap.put("Set"+i,ServiceSummaryTableRow);
        }catch(Exception e){}
    }       
         return ServiceSummaryTableRowsMap;               

}

	
	
	public static String getServiceSummary(PdfReader reader) throws Exception {
		
		String PDFtext;
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 100);
			
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        PDFtext = (PdfTextExtractor.getTextFromPage(reader, 2, strategy));
        return PDFtext;
	}
	
	public static void Compare(LinkedHashMap<String,String> XMLHashMap){
        System.out.println("XML Contents ");    
        
		Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
         while(XMLkeySetIterator.hasNext()) {
			
			String keyXML = XMLkeySetIterator.next();
			
			xmlrows = XMLHashMap.get(keyXML);
			//System.out.println(xmlSSrows);
		
		if (  PDFtext.indexOf(xmlrows)!=-1? true: false){
			
			System.out.println(xmlrows +" It's a Match!!!");
			 q = true;
			 //ServiceSummaryLineItemsRemarks = "\n"+ xmlSSrows +" It's a Match!!!";
			
		}
		else{
			System.out.println( xmlrows +" Mismatch due to Discrepancies in Service Summary Line items");
				 q = false;	
				 ServiceSummaryLineItemsRemarks = "\n"+xmlrows +" Mismatch due to Discrepancies in Service Summary Line items";
		}
		
		
         } 
			
	
	
	}
	
	
}
