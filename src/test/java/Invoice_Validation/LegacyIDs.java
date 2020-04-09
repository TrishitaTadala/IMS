//All the variables should be declared
//======================================================
// Class      : Legacy IDs
// Description: The Legacy IDs in the front Page in the invoice  
//              PDF is captured using Coordinates Extraction
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 08/04/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;

//import java.io.File;
import java.io.IOException;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

//import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class LegacyIDs  {
	
	public static String[] LegacyIDsExtract_PDF = new String[5000];
    public static boolean[] isFoundLegacyIDs = new boolean[5000];
    public static String[] legacyIDRemarks = new String[5000];
	public static void legacyIDs() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		for(int k =0;k<ReadExcelFile.i;k++){	
			String[] filepaths = ReadExcelFile.PDFFILENAME; //File location of the PDF 
				
			try {
				LegacyIDsExtract_PDF[k]= getLegacyId(0f, 550f, 200f, 600f,filepaths[k]);
				LegacyIDsExtract_PDF[k] =LegacyIDsExtract_PDF[k].replaceAll(" ", "");
										
			 isFoundLegacyIDs[k] = (inmarsatXML.LegacyIDs_XML[k].indexOf(LegacyIDsExtract_PDF[k])) !=-1? true: false;
			 //true
			 legacyIDRemarks[k] = (k+1)+" XML value "+ inmarsatXML.LegacyIDs_XML[k]+ "\n"+"PDF value "+LegacyIDsExtract_PDF[k]; 
			 
			 			 
			}catch(Exception e){
				
			}
			
		}
		}
	
	
	//public class ExtractPageContentArea {
		public static String getLegacyId(float x,float y,float width,float height,String pdf) throws IOException {
			String LegacyIDsExtract;		
			PdfReader reader = new PdfReader(pdf);
	        Rectangle rect = new Rectangle(x, y, width, height);
	        RenderFilter filter = new RegionTextRenderFilter(rect);
	        TextExtractionStrategy strategy;
	       
	            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	            LegacyIDsExtract = PdfTextExtractor.getTextFromPage(reader, 1, strategy);
	            //System.out.println("PDF"+LegacyIDsExtract);
	           
	       
	        //reader.close();
	       return  LegacyIDsExtract;
	    }
}
