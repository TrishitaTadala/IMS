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

		
		 LinkedHashMap<String,String> XMLHashMap  = new LinkedHashMap(); //XML Contents

		String pdfpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504942_1038204_202005.pdf";
				//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\CNT_9000001504192_114251_202002.pdf";
			
		String xmlpath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001503_1038204_1590891_20200507.xml";
				//"C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10000601_114251_1588607_20200220.xml";
		
		System.out.println("*****************CreditNote Invoice Details_XML - FrontPage******************"); 
		            Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSLENameXML(xmlpath,"SLEName"));
		            Compare(getCNTtextPDF(pdfpath,"Front"),readCDtotalXML(xmlpath,"Number")); 
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSLENameXML(xmlpath,"BillToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSLENameXML(xmlpath,"SoldToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSLENameXML(xmlpath,"InvoiceDate"));
			        Compare(getCNTtextPDF(pdfpath,"Front"),readCNTSLENameXML(xmlpath,"Currency"));
	
			/*********SecondPageValidation Compare(readCDtotalXML(xmlpath));*************/
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSLENameXML(xmlpath,"SLEName"));
			      	    	/*****Rework on Bill To**********/
			      	
			System.out.println("*****************CreditNote Invoice Details_XML - Page2******************"); 
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"Number"));  
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSLENameXML(xmlpath,"BillToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSLENameXML(xmlpath,"SoldToRef"));
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSLENameXML(xmlpath,"InvoiceDate"));
			        Compare(getCNTtextPDF(pdfpath,"Default"),readCNTSLENameXML(xmlpath,"Currency"));
			System.out.println("*****************Credits/Debits Section_XML******************");
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"Credits/Debits"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"InvoiceID"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"Title"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"Description"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCNTLinesXML(xmlpath,"ChargeType"));
			      	Compare(getCNTtextPDF(pdfpath,"Default"),readCDtotalXML(xmlpath,"CDtotal"));
		}
		
  public static String getCNTtextPDF(String pdfpath, String Page) throws Exception{
	
	  LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
	  
	   PdfReader reader = new PdfReader(pdfpath); 
	   
	   switch (Page){
    
	   case "Front":
	      PDFHashMap.put("FrontPage", getCNTfrontpage(reader));
	     break;
	     
	   case "Last":
		   //PDFHashMap.put("middlepages", getCNTtext(reader));
		     break;
		     
		 default:
			 PDFHashMap.put("middlepages", getCNTtext(reader));
			 break;
	   }
	
	Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
	
	while(PDFkeySetIterator.hasNext()) {
		
		String key = PDFkeySetIterator.next();
		
		PDFtext += PDFHashMap.get(key);
		//System.out.println(PDFtext);
		
		}
	return PDFtext;
	
	
  }
	
	
	public static void Compare(String PDFtext,LinkedHashMap<String,String> XMLHashMap){
              
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

	public static String getCNTfrontpage(PdfReader reader) throws Exception {
		Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
			
	    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
	    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);           
	    
	    strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	        
	    PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, 1, strategy);
	        
	    PDFtext = PDFtext.replace(",","");
	            //System.out.println(PDFtext);
	    return PDFtext;
	}

	
	public static String getCNTtext(PdfReader reader) throws Exception {
	Rectangle serviceSummary = new Rectangle(0, 950, 900, 20);
		
    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            
   for (int i = 2; i <= (reader.getNumberOfPages() -1); i++) {
    
        strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        
        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);
        
    }
    PDFtext = PDFtext.replace(",","");
            //System.out.println(PDFtext);
    return PDFtext;
}

	
	public static LinkedHashMap<String,String> readCNTSLENameXML(String xmlfilepath, String VarType) throws Exception{
	        
	        LinkedHashMap<String, String> CNTFrontPageMap= new LinkedHashMap<>();
	          
	        File inputFile = new File(xmlfilepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        Document doc1 = dBuilder.parse(inputFile);
	        doc1.getDocumentElement().normalize();
     
            
         switch (VarType){
    	
    	case "SLEName":
	    		String SLEName = doc1.getElementsByTagName("SLEName").item(0).getTextContent();    
	    		CNTFrontPageMap.put("sleLine",SLEName);
	    		break;
	    		
    	case "BillToRef":
    		
    		String BillToRef = doc1.getElementsByTagName("BillingProfileID").item(0).getTextContent();    
    		CNTFrontPageMap.put("BillToRef","Bill to reference "+BillToRef);
    		break;	
    		
    	case "SoldToRef":
    		
    		String SoldToRef = doc1.getElementsByTagName("SAPAccountNumber").item(0).getTextContent();    
    		CNTFrontPageMap.put("SoldToRef","Sold to reference "+SoldToRef);
    		break;
    		
         case "InvoiceDate":
    		
    		String InvoiceDate = doc1.getElementsByTagName("InvoiceDate").item(0).getTextContent();    
    		CNTFrontPageMap.put("InvoiceDate","Date "+InvoiceDate);
    		break;  
    		
         case "Currency":
     		
     		String Currency = doc1.getElementsByTagName("CustomerInvoiceCurrency").item(0).getTextContent();    
     		CNTFrontPageMap.put("Currency","Currency "+Currency);
     		break;
    		
         }       
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
    		try {
    		String InvoiceID = PerCNTTransaction.getElementsByTagName("InvoiceId").item(0).getFirstChild().getTextContent();  
    		if (InvoiceID != null) {
    		CNTFrontPageMap.put("Line"+i+" ","Related Invoice ID: "+ InvoiceID);
    		}
    		break;
    		
         }catch(Exception e){}  
    	
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

    	
    public static LinkedHashMap<String,String> readCDtotalXML(String xmlfilepath,String VarType) throws Exception {
        
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
           
           switch (VarType){
       	
         	case "CDtotal":
                
            String SummaryTotal = PerTransaction.getElementsByTagName("TotalAmount").item(0).getFirstChild().getTextContent();
                
               
                CNTSecondPageMap.put("CDTotal",("Total Credits / Debits "+SummaryTotal));
                break;  
         	case "Number":
                
                String CNTNumber = PerTransaction.getElementsByTagName("ADJNumber").item(0).getFirstChild().getTextContent();
                    
                CNTSecondPageMap.put("Number",("Number "+CNTNumber));
                    break;  
           }
                return CNTSecondPageMap;               

  	}
}
  