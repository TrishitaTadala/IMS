package Invoice_Validation;


import java.util.HashMap;
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


import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ExtractInvoiceDetails {
	public static void main(String[] args) throws Exception {
		
		 //Create a hash map
		 HashMap<String,String> PDFHashMap  = new HashMap();
		 PDFHashMap.clear();
		 
		 // Put elements to the map
		String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1386249_986021_201904.pdf";
		PdfReader reader = new PdfReader(filename);  
		
		PDFHashMap.put("BillTo", getBillingAddress(reader));
		PDFHashMap.put("Legacy IDs", getLegacyId(reader));
		PDFHashMap.put("ServiceSummary", getServiceSummary(reader));
		/*
		getBillingAddress(reader);
		getInvoiceDetails(reader);
		getPricingAgreementId(reader);
		
		getSummary(reader);
		getTaxInfo(reader);
		getServiceSummary(reader);*/
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			System.out.println(key + PDFHashMap.get(key));
		}
		
		readXML();
		reader.close();
		
		
		
		
	}
	
	/****************************************************************************/
	public static void readXML() throws Exception{
        
      // ArrayList duplicateRows = new ArrayList<String>();
        int rateRowCount = 0;
        //int noOfXML = 0;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        HashMap<String, HashMap> rateTable = new HashMap<>();
        
        LinkedHashMap<String, String> ProductTableRows;
        ProductTableRows = new LinkedHashMap<>();
        //ProductTableRows.clear();
        String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\117976_986021_1386249_20190430.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        XPathExpression exprRR = xpath.compile("//ServiceSummary/ProductDetails");
        
        
        //ArrayList<String> Actualvalue= new ArrayList<String>();
        //ArrayList<String> Trimmedvalue= new ArrayList<String>();
        //int count = 0;
        NodeList ProductRows = (NodeList)exprRR.evaluate(doc1, XPathConstants.NODESET);
        for (int i = 0; i < ProductRows.getLength(); i++) {
            rateRowCount++;
            
            Element ProductDetails = (Element)ProductRows.item(i);

            String ProductGroupValue = ProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
            String ProductValue = ProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();
            String ProductTypeValue = ProductDetails.getElementsByTagName("ProductType").item(0).getFirstChild().getTextContent();
            String ProductUsageValue = ProductDetails.getElementsByTagName("Usage").item(0).getFirstChild().getTextContent();
            String ProductTotalValue = ProductDetails.getElementsByTagName("Total").item(0).getFirstChild().getTextContent();
           
            ProductTableRows.put("ProductGroup"+i+" ", ProductGroupValue);
            ProductTableRows.put("Product"+i+" ", ProductValue);
            ProductTableRows.put("ProductType"+i+" ", ProductTypeValue);
            ProductTableRows.put("Usage"+i+" ", ProductUsageValue);
            ProductTableRows.put("Total"+i+" ", ProductTotalValue);
            
           // ProductTableRows= ProductTableRows(0)+ProductTableRows;
    		
            
            }	
        
Iterator<String> XMLkeySetIterator = ProductTableRows.keySet().iterator();
				
				while(XMLkeySetIterator.hasNext()) {
   	
   	String key1 = XMLkeySetIterator.next();
   	System.out.println(key1 + ProductTableRows.get(key1)); 
   		}
        //return ProductTableRows;
       	}
	
	/*
        
        try {
           // DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //DocumentBuilder db = dbf.newDocumentBuilder();
            //Document doc = db.parse("C://logs//XML.xml");
        	NodeList nodeList = (NodeList)exprRR.evaluate(doc1, XPathConstants.NODESET);
            //NodeList nodeList = exprRR.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node textChild = nodeList.item(i);
                NodeList childNodes = textChild.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node grantChild = childNodes.item(j);
                   // NodeList grantChildNodes = grantChild.getChildNodes();
                  /*  for (int k = 0; k < grantChildNodes.getLength(); k++) {
                       //if(!StrUtl.isEmptyTrimmed( grantChildNodes.item(k).getTextContent() ) ) {
                            
                                    map.put(grantChildNodes.item(k).getNodeName() , grantChildNodes.item(k).getTextContent());
                                    System.out.println(map);
                        //}
                    }*/
                   // map.put(childNodes.item(j).getNodeName() , childNodes.item(j).getTextContent());
                    //System.out.println(map);
               // }
            //}
       // }catch (Exception e){
       //         e.printStackTrace();
       // }
        
       // return map;
	//}
          
	
        


	
	public static String getBillingAddress(PdfReader reader) throws Exception {		    
        Rectangle billingAddressSection = new Rectangle(0, 620, 200, 750);
        RenderFilter filter = new RegionTextRenderFilter(billingAddressSection);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
       return PdfTextExtractor.getTextFromPage(reader, 1, strategy);
        // System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));       
	}
	
	public static void getInvoiceDetails(PdfReader reader) throws Exception {
		Rectangle invoiceDetailsSection = new Rectangle(300, 620, 500, 750);
        RenderFilter filter = new RegionTextRenderFilter(invoiceDetailsSection);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static void getPricingAgreementId(PdfReader reader) throws Exception {
		Rectangle pricingAgreementId = new Rectangle(0, 600, 200, 650);
        RenderFilter filter = new RegionTextRenderFilter(pricingAgreementId);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static String getLegacyId(PdfReader reader) throws Exception {
		Rectangle legacyIds = new Rectangle(0, 550, 200, 600);
        RenderFilter filter = new RegionTextRenderFilter(legacyIds);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        return PdfTextExtractor.getTextFromPage(reader, 1, strategy);
	}
	
	public static String getSummary(PdfReader reader) throws Exception {
		Rectangle summarySection = new Rectangle(0, 500, 600, 400);
        RenderFilter filter = new RegionTextRenderFilter(summarySection);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        return (PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static void getTaxInfo(PdfReader reader) throws Exception { 
		Rectangle taxInfo = new Rectangle(0, 378, 600, 375);
        RenderFilter filter = new RegionTextRenderFilter(taxInfo);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static String getServiceSummary(PdfReader reader) throws Exception {
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 400);
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        return(PdfTextExtractor.getTextFromPage(reader, 2, strategy));
	}
	
	
}
