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
	public static String  xmlrows;
	public static boolean q;
	public static String WholesaleRemarks;
	
//public static boolean FeesummaryPDF(String filename,String xmlpath) throws Exception{
	public static void main(String[] args) throws Exception {		

		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents

		String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\INVOICE_5448299_117921_202004.pdf";
				
				//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\INVOICE_5450013_118047_202004.pdf";
			
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\Wholesale\\QA_129171_117921_5448299_20200430.xml";
		//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\Wholesale\\QA_129171_118047_5450013_20200430.xml";
		
		PdfReader reader = new PdfReader(filename); 
		
		PDFHashMap.put("Front&Last Pages Excluded"+"\n", getPDFtext(reader));
				
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			//System.out.println(key + PDFHashMap.get(key));
			PDFtext = PDFHashMap.get(key);
			System.out.println(PDFtext);
		}
			
		Compare(readFeeSummaryLinesXML(xmlpath)); //Fees Summary 
		//Compare(readFSSubtotalXML( xmlpath));
		
		
		
		
		//return q;
	}
	
	/****************************************************************************/
	public static LinkedHashMap<String,String> readFeeSummaryLinesXML(String xmlfilepath) throws Exception{
        
        LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();
              
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

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
            
	}

    /****************************************************************************/   
    public static LinkedHashMap<String,String> readFSSubtotalXML(String xmlfilepath) throws Exception {
      
      LinkedHashMap<String, String> FeeSummaryTableRowsMap= new LinkedHashMap<>();
      
      
      File xmlinputFile = new File(xmlfilepath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      
      Document doc1 = dBuilder.parse(xmlinputFile);
      doc1.getDocumentElement().normalize();
      XPath xpath = XPathFactory.newInstance().newXPath();

      
       XPathExpression exprfsPD = xpath.compile("//Fees/FeeSummarySubtotal");
      
      NodeList PerProdRows = (NodeList)exprfsPD.evaluate(doc1, XPathConstants.NODESET);
      for (int i = 0; i < PerProdRows.getLength(); i++) {
                     
          Element PerProductDetails = (Element)PerProdRows.item(i);
          
          try {
              
              String SumTotal = PerProductDetails.getElementsByTagName("SumTotal").item(0).getFirstChild().getTextContent();
              
              String  FeeSummaryTableRow = ("Total Subscription "+SumTotal);
              FeeSummaryTableRowsMap.put("Set"+i,FeeSummaryTableRow);
              
          }  catch(Exception e){}  
          
      }       
           return FeeSummaryTableRowsMap;               

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
			WholesaleRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
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
                //System.out.println(PDFtext);
        return PDFtext;
	}
	
	
}
