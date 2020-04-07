//All the variables should be declared
//======================================================
// Class      : Footer1
// Description: The Footer1 across all the Pages
//              in the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 07/04/20     Trishita Tadala     Written
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


public class Footer1  {
	public static String FooterExtract;
	public static String[] FooterExtract_PDF;
    public static boolean[] isFound;
	public static void footer1() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		for(int k =0;k<ReadExcelFile.i;k++){	
			String[] filepaths = ReadExcelFile.PDFFILENAME; //File location of the PDF 
			//File file = new File(filepaths[k]);
			//System.out.println(filepaths[k]);
			extractPageContentArea(0f, 0f, 595f, 40f,filepaths[k]);
			try {
			FooterExtract_PDF[k] = FooterExtract;
			}catch(Exception e1){ 
			//String input = "Android gave new life to Java";
			
			try {
			 isFound[k] = (FooterExtract_PDF[k].indexOf(inmarsatXML.Footer_XML[k])) !=-1? true: false; //true
             }catch(Exception e){
				
			}
			
		}
	
	}
	}
	
	//public class ExtractPageContentArea {
		public static void extractPageContentArea(float x,float y,float width,float height,String pdf) throws IOException {
					
			PdfReader reader = new PdfReader(pdf);
	        Rectangle rect = new Rectangle(x, y, width, height);
	        RenderFilter filter = new RegionTextRenderFilter(rect);
	        TextExtractionStrategy strategy;
	       for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	            //System.out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
	            FooterExtract = PdfTextExtractor.getTextFromPage(reader, i, strategy);
	        }
	        reader.close();
	    }
}
