//All the variables should be declared
//======================================================
// Class      : LastPage
// Description: The Support & Inquiries class that that validates
//               the static Text - Support & Inquiries and Glossary
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 28/04/20     Trishita Tadala     Written
// 28/04/20     Trishita Tadala     Glossary Section
// XX/XX/20     Trishita Tadala     
//======================================================


package Invoice_Validation;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class LastPage {
	
	public static String PDFtext;
	public static boolean q;
	public static String LPRemarks;
	public static String xmlSSrows;
	public static void main(String[] args) throws Exception {
	//public static boolean lastpage(String filename, String xmlpath) throws Exception {
		LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap(); //PDF Contents
		 LinkedHashMap<String,String>XMLHashMap  = new LinkedHashMap(); //XML Contents
		 PDFHashMap.clear();
		 String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1590428_954226_202004.pdf";
		 PdfReader reader = new PdfReader(filename);
		 PDFHashMap.put("Support & Enquiries"+"\n",getLastpage(reader));
		 Iterator<String> PDFkeySetIterator = PDFHashMap.keySet().iterator();
			
			while(PDFkeySetIterator.hasNext()) {
				
				String key = PDFkeySetIterator.next();
				//System.out.println(key + PDFHashMap.get(key));
				//PDFtext = (key+PDFHashMap.get(key)).replaceAll(",", "");
				PDFtext = (key+PDFHashMap.get(key));
				System.out.println(PDFtext);
			}
			
			System.out.println("************************Contents of Last Page from the Mockup*******************************");
			XMLHashMap = readLastPageStaticText();
			Iterator<String> XMLkeySetIterator = XMLHashMap.keySet().iterator();
	         while(XMLkeySetIterator.hasNext()) {
				
				String keyXML = XMLkeySetIterator.next();
				
				xmlSSrows = XMLHashMap.get(keyXML);
				//System.out.println(xmlSSrows);
			
			if (  PDFtext.indexOf(xmlSSrows)!=-1? true: false){
				
				System.out.println(xmlSSrows +" It's a Match!!!");
				 q = true;
				 //LPRemarks = "\n"+ xmlSSrows +" It's a Match!!!";
				
			}
			else{
				System.out.println( xmlSSrows +" ~~~Mismatch It is~~~");
					 q = false;	
					 LPRemarks += "\n"+xmlSSrows +" Mismatch ";
			}
			
			
	         }
			
		 
		 //return q;
	}
	
	public static LinkedHashMap<String,String> readLastPageStaticText() throws Exception {
		
		LinkedHashMap<String, String> LastPageRowsMap= new LinkedHashMap<>();
		
		LastPageRowsMap.put("Line1", "Billing Inquiry (except LPI)");
		LastPageRowsMap.put("Line2", "Please contact Global Customer Operations with any query or dispute");
		//LastPageRowsMap.put("Line3","regarding this invoice within seven days of the invoice date. Please");
		//LastPageRowsMap.put("Line4","regarding this invoice within seven days of the invoice date. Please");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		LastPageRowsMap.put("SI - TSI",("Enterprise, Aviation and Government customers:"+"\n"+"Technical support information"+"\n"+"   globalcustomersupport@inmarsat.com"
		+"\n"+""+"Inmarsat is dedicated to delivering to you the best customer support possible    +44 (0) 207 728 1020"+"\n"
		+"by going beyond your expectations, no matter where you are or what time of"+"\n"
		+"day it is.  Global Customer Operations operates 24-hours a day, 365-days a Maritime customers:"+"\n"
		+"year.    support.maritime@inmarsat.com"+"\n"+"   +47 (0) 70 17 24 00"));
		
		LastPageRowsMap.put("SI - SSI",("Enterprise, Aviation and Government customers:"+"\n"+"Sales support information"+"\n"+"   globalcustomersupport@inmarsat.com"+"\n"+
		"   +44 (0) 207 728 1020"+"\n"+""+"\n"+"Maritime customers:"+"\n"+"   support.maritime@inmarsat.com"+"\n"+"   +47 (0) 70 17 24 00"));
				
		LastPageRowsMap.put("Glossary -","Glossary");
		LastPageRowsMap.put("Glossary -","");
		
		LastPageRowsMap.put("Glossary - CF1","Call Forwarded. For example where a SIM has been configured so that an incoming call is");
		LastPageRowsMap.put("Glossary - CF2","forwarded to another number, eg a corporate office.");
				
		LastPageRowsMap.put("Glossary - ICCID1","Integrated Circuit Card Identifier. Unique identifier, stored within and printed on a SIM");
		LastPageRowsMap.put("Glossary - ICCID2","(Subscriber Identity Module) card.");
		
		LastPageRowsMap.put("Glossary - IMN1","Inmarsat Mobile Number.  The diallable number for a subscriber using an Inmarsat I-3");
		LastPageRowsMap.put("Glossary - IMN2","terminal.");
		
		LastPageRowsMap.put("Glossary - IMSI1","International Mobile Subscriber Identity. A unique number, usually 15 digits, identifying a");
		LastPageRowsMap.put("Glossary - IMSI2","subscriber in a mobile telecommunication network.");
		
		LastPageRowsMap.put("Glossary - ISN1","Inmarsat Serial Number.  The hardware ID on an Inmarsat I-3 terminal.  A single terminal");
		LastPageRowsMap.put("Glossary - ISN2","may have several related IMNs.");
		
		LastPageRowsMap.put("Glossary - MB1","Megabyte.   Measurement unit for airtime.   Refer to the relevant commercial documentation");
		LastPageRowsMap.put("Glossary - MB2"," for further details");
		
		LastPageRowsMap.put("Glossary - MO","Mobile Originated. Call is initiated on a user terminal. Also referred to as 'From Mobile' traffic.");
					
		LastPageRowsMap.put("Glossary - MSISDN1","Mobile Station International Subscriber Directory Number. A number used to route calls to");
		LastPageRowsMap.put("Glossary - MSISDN2","and from a terminal in a mobile telecommunications network.");
				
		LastPageRowsMap.put("Glossary - MT","Mobile Terminated. Call is received by a user terminal. Also referred to as 'To Mobile' traffic.");
				
		LastPageRowsMap.put("Glossary -SCAP1","Shared Corporate Allowance Plan. A type of rate plan which allows many users to utilise a");
		LastPageRowsMap.put("Glossary -SCAP2","single allowance.");
		
		LastPageRowsMap.put("Glossary - sqt1","Super Quiet Time. A commercial offer which provides advantageous rates for qualifying");
		LastPageRowsMap.put("Glossary - sqt2","traffic. May have time-based restrictions.");
		
		LastPageRowsMap.put("Glossary -UoM1","Unit of Measure. Used to quantify the volume or amount of services or goods provided.");
		LastPageRowsMap.put("Glossary -UoM2"," Eg Minute, Megabyte. Usually abbreviated.");
		
		
		LastPageRowsMap.put("Glossary -utc1","Universal Coordinated Time. Equivalent to Greenwich Mean Time for most purposes. All");
		LastPageRowsMap.put("Glossary -utc2","dates and times in this invoice/credit note are in UTC, unless otherwise stated.");
		

		
		LastPageRowsMap.put("Glossary -last","Please be aware that for some airtime, a call setup charge, in additional to a volume based charge, may be applicable."
				+"\n"+"Please refer to the relevant pricing documentation for further information.");
		
		
		
		return LastPageRowsMap;
		
		
		
	}
	
	
	
	public static String getLastpage(PdfReader reader) throws Exception {
		
		String PDFtext;

		Rectangle LastPage = new Rectangle(0, 800, 600, 10);
			
        RenderFilter filter = new RegionTextRenderFilter(LastPage);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        PDFtext = (PdfTextExtractor.getTextFromPage(reader,reader.getNumberOfPages(), strategy));
        return PDFtext;
	}

}
