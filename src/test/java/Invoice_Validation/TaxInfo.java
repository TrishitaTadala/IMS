//All the variables should be declared
//======================================================
// Class      : TaxInfo
// Description: The Tax Information in the front Page in the invoice  
//              PDF is captured using Coordinates Extraction
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 09/04/20     Trishita Tadala     Written
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

public class TaxInfo {
	public static String[] TaxInfoExtract_PDF = new String[5000];
    public static boolean[] isFoundTaxInfo = new boolean[5000];
    public static String[] TaxInfoRemarks = new String[5000];
    
    public static void taxInfo() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		for(int k =0;k<ReadExcelFile.i;k++){	
			String[] filepaths = ReadExcelFile.PDFFILENAME; //File location of the PDF 
				
			try {
				TaxInfoExtract_PDF[k]= getTaxInfo(0, 378, 600, 375,filepaths[k]);
				TaxInfoExtract_PDF[k] =TaxInfoExtract_PDF[k].replaceAll(" ", "");
										
				isFoundTaxInfo[k] = (TaxInfoExtract_PDF[k].indexOf(inmarsatXML.TaxInfo_XML[k])) !=-1? true: false;
			 //true
				TaxInfoRemarks[k] = (k+1)+" XML value "+ inmarsatXML.TaxInfo_XML[k]+ "\n"+"PDF value "+TaxInfoExtract_PDF[k]; 
			 
			 			 
			}catch(Exception e){
				
			}
			
		}
		}
	
	
	//public class ExtractPageContentArea {
		public static String getTaxInfo(float x,float y,float width,float height,String pdf) throws IOException {
			String TaxinfoExtract;		
			PdfReader reader = new PdfReader(pdf);
	        Rectangle rect = new Rectangle(x, y, width, height);
	        RenderFilter filter = new RegionTextRenderFilter(rect);
	        TextExtractionStrategy strategy;
	       
	            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	            TaxinfoExtract = PdfTextExtractor.getTextFromPage(reader, 1, strategy);
	            //System.out.println("PDF"+LegacyIDsExtract);
	           
	       
	        //reader.close();
	       return  TaxinfoExtract;
	    }
}


