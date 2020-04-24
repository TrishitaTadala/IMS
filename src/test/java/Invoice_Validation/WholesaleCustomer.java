//All the variables should be declared
//======================================================
// Class      : Wholesale Customer
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 23/04/20     Trishita Tadala     Written
// 24/04/20     Trishita Tadala     FeeSummary Section
// XX/04/20     Trishita Tadala     AirtimeSummary Section
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


public class  WholesaleCustomer   {
	
	public static String PDFtext;
	public static String  xmlFSrows;
	public static boolean q;
	public static String FeeSummaryLineItemsRemarks;
	
public static boolean FeesummaryPDF(String filename,String xmlpath) throws Exception{
		
		 //Create a hash map
		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents
		 
		 //PDFHashMap.clear();
		 //XMLHashMap.clear();
		 // Put elements to the map
		//String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\INVOICE_1590512_118014_202009.pdf";
		PdfReader reader = new PdfReader(filename);  
		//PDFHashMap.put("ServiceSummary"+"\n", getServiceSummary(reader));
		PDFHashMap.put("FeeSummary"+"\n", getFeeSummary(reader));
		
		
		/*
		getServiceSummary(reader);*/
		
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			//System.out.println(key + PDFHashMap.get(key));
			PDFtext = (key+PDFHashMap.get(key)).replaceAll(",", "");
			System.out.println(PDFtext);
		}
			
		XMLHashMap = readFeeSummaryXML(xmlpath);
		
		
		
		System.out.println("//Fees/PerTransaction/ChargePerTransaction");    
        
		Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
         while(XMLkeySetIterator.hasNext()) {
			
			String keyXML = XMLkeySetIterator.next();
			
			//System.out.println(XMLHashMap.get(keyXML));
			xmlFSrows = XMLHashMap.get(keyXML);
			
		
		if (  PDFtext.indexOf(xmlFSrows)!=-1? true: false){
			
			System.out.println(xmlFSrows +" It's a Match!!!");
			 q = true;
			 FeeSummaryLineItemsRemarks = xmlFSrows +" It's a Match!!!";
			
		}
		else{
			System.out.println(xmlFSrows +" Mismatch due to Discrepancies in Fee Summary Line items");
				 q = false;	
				 FeeSummaryLineItemsRemarks = xmlFSrows +" Mismatch due to Discrepancies in Fee Summary Line items";
		}
		
		
         }
		
         //reader.close();
		
		return q;
	}
	
	/****************************************************************************/
	public static LinkedHashMap<String,String> readFeeSummaryXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();
        
        //String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\Wholesale\\116111_118014_1590512_20200930.xml";
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        //XPathExpression exprRR = xpath.compile("//ServiceSummary/ProductDetails");
        XPathExpression exprFees = xpath.compile("//Fees/PerTransaction/ChargePerTransaction");
        
        NodeList PerTransRows = (NodeList)exprFees.evaluate(doc1, XPathConstants.NODESET);
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
            
            /*
            String ChargeTypeValue = PerTransaction.getElementsByTagName("ChargeType").item(0).getFirstChild().getTextContent();
            String ProductGroupValue = PerTransaction.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
            String ProductTypeValue = PerTransaction.getElementsByTagName("ProductType").item(0).getFirstChild().getTextContent();
            
            FeeSummaryTableRows.put(""+i+" ", ChargeTypeValue);
            FeeSummaryTableRows.put("ServiceGroup"+i+" ", ProductGroupValue);
            FeeSummaryTableRows.put("ProductType"+i+" ", ProductTypeValue);*/
                       
         
		//return 	FeeSummaryTableRowsMap;
		    
        
       	}
	
	
	
	
	public static String getServiceSummary(PdfReader reader) throws Exception {
		//LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap();
		String PDFtext;
		//0, 600, 600, 400 (Entire)
		//0, 450, 600, 400 (totals only)
		//(0, 600, 600, 478)
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 478);
			
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        PDFtext = (PdfTextExtractor.getTextFromPage(reader, 2, strategy));
        return PDFtext;
	}
	public static String getFeeSummary(PdfReader reader) throws Exception {
		//LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap();
		String PDFtext;
		//0, 600, 600, 400 (Entire)
		//0, 450, 600, 400 (totals only)
		//(0, 600, 600, 478)
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 50);
			
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        PDFtext = (PdfTextExtractor.getTextFromPage(reader, 3, strategy));
        return PDFtext;
	}
	
	
}
