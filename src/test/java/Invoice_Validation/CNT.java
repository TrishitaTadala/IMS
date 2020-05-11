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

public class CNT {

	

	public static String PDFtext;
	public static String  xmlrows;
	public static boolean q;
	public static String CntRemarks;

	
	public static void main(String[] args) throws Exception {

		 LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504192_114251_202002.pdf";
			
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10000601_114251_1588607_20200220.xml";

		PdfReader reader = new PdfReader(pdfpath); 
        
		PDFHashMap.put("middlepages", getCNTtext(reader));
		
		
		Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
		
		while(PDFkeySetIterator.hasNext()) {
			
			String key = PDFkeySetIterator.next();
			
			PDFtext += PDFHashMap.get(key);
			System.out.println(PDFtext);
			
			}	
			/*********SecondPageValidation Compare(readCDtotalXML(xmlpath));*************/
			      	Compare(readCNTSLENameXML(xmlpath));
			      	/*****Rework on Bill To**********/
			 System.out.println("*****************CreditsDebits_XML******************");
			      	Compare(readCNTLinesXML(xmlpath,"Credits/Debits"));
			      	Compare(readCNTLinesXML(xmlpath,"InvoiceID"));
			      	Compare(readCNTLinesXML(xmlpath,"Title"));
			      	Compare(readCNTLinesXML(xmlpath,"Description"));
			      	Compare(readCNTLinesXML(xmlpath,"ChargeType"));
			      	Compare(readCDtotalXML(xmlpath));
		}
		

	

	public static void Compare(LinkedHashMap<String,String> XMLHashMap){
        
		
        
		Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
         while(XMLkeySetIterator.hasNext()) {
			
			String keyXML = XMLkeySetIterator.next();
			
			xmlrows = XMLHashMap.get(keyXML).replaceAll("\n", "");
			//System.out.println(xmlrows);
		
		if (PDFtext.indexOf(xmlrows)!=-1? true: false){
			
			System.out.println(xmlrows +" It's a Match!!!");
			 q = true;
					
		    }
		else{
			System.out.println( xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~");
				 q = false;	
				 CntRemarks += "\n"+xmlrows +" ~~~~~~ Mismatch It is ~~~~~~~";
		     }
		
		
         } 
			
	}
	
	public static String getCNTtext(PdfReader reader) throws Exception {
	Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
		
    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            
   for (int i = 1; i <= (reader.getNumberOfPages() -1); i++) {
    
        strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        
        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);
        
    }
    PDFtext = PDFtext.replace(",","");
            //System.out.println(PDFtext);
    return PDFtext;
}

	
	public static LinkedHashMap<String,String> readCNTSLENameXML(String xmlfilepath) throws Exception{
	        
	        LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();
	          
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
     
	    	
	    		System.out.println("*****************SLEName_XML******************");
	    		String SLEName = doc1.getElementsByTagName("SLEName").item(0).getTextContent();    
	    		CNTFrontPageMap.put("sleLine",SLEName);
	   	           
	        return CNTFrontPageMap;
	            
		}

	public static LinkedHashMap<String,String> readCNTLinesXML(String xmlfilepath,String VarType) throws Exception{
        
        LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();
        

		
          
        File inputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(inputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression exprCNT = xpath.compile("//CreditNote/Adjustments/PerLineItem");
        
        NodeList PerCNTTransRows = (NodeList)exprCNT.evaluate(doc1, XPathConstants.NODESET);
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
    		String InvoiceID = PerCNTTransaction.getElementsByTagName("InvoiceId").item(0).getFirstChild().getTextContent();  
    		CNTFrontPageMap.put("Line"+i+" ","Related Invoice ID: "+ InvoiceID);
    		break;
    	
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
    		String ChargeType = PerCNTTransaction.getElementsByTagName("ChargeType").item(0).getFirstChild().getTextContent();  
    		CNTFrontPageMap.put("Line"+i+" ","Charge Type: "+ ChargeType);
    		break;
    	}   
        
        }     
        return CNTFrontPageMap;
            
	}
    	
    public static LinkedHashMap<String,String> readCDtotalXML(String xmlfilepath) throws Exception {
        
        LinkedHashMap<String, String> CNTSecondPageMap= new LinkedHashMap<>();
        
        
        File xmlinputFile = new File(xmlfilepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc1 = dBuilder.parse(xmlinputFile);
        doc1.getDocumentElement().normalize();
        XPath xpath = XPathFactory.newInstance().newXPath();
         XPathExpression exprCNT = xpath.compile("//CreditNote");
        
           NodeList PerCNTTransRows = (NodeList)exprCNT.evaluate(doc1, XPathConstants.NODESET);
           Element PerTransaction = (Element)PerCNTTransRows.item(0);   
                
            String SummaryTotal = PerTransaction.getElementsByTagName("TotalAmount").item(0).getFirstChild().getTextContent();
                
                String  CDSummaryTableRow = ("Total Credits / Debits "+SummaryTotal);
                CNTSecondPageMap.put("CDTotal",CDSummaryTableRow);
      
                return CNTSecondPageMap;               

  	}
            
	
	
	

}

