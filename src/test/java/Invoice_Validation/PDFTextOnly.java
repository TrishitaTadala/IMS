package Invoice_Validation;




import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PDFTextOnly {
	
	public static String PDFtext;
	
    public static String getPageTextPDF(String pdfpath, String Page) throws Exception{
	  
	   PdfReader reader = new PdfReader(pdfpath); 
	   
		Rectangle serviceSummary = new Rectangle(0, 1300, 1000, 200);
		
	    RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
	    TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);                   
  
	   switch (Page){
    
	   case "Front":
		   	     
		        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader,1, strategy);
		        
	   break;
	     
	   case "Last":
		   
		        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader,reader.getNumberOfPages() , strategy);
	        		            
		break;
		     
	   default:
	          
		   for (int i = 2; i <= (reader.getNumberOfPages() -1); i++) {
		    
		        strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
		        
		        PDFtext += "\n"+PdfTextExtractor.getTextFromPage(reader, i, strategy);    
		    }
		    
		          		    
		break;
	   }
	   
	   PDFtext = PDFtext.replace(",","");
       //System.out.println(PDFtext);
       return PDFtext;
     }

	public static String getInvoiceDetails(String pdfpath) throws Exception {
		 PdfReader reader = new PdfReader(pdfpath); 
		Rectangle invoiceDetailsSection = new Rectangle(300, 620, 500, 750);
        RenderFilter filter = new RegionTextRenderFilter(invoiceDetailsSection);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
        return (PdfTextExtractor.getTextFromPage(reader, 1, strategy));
        
	}
	
 
}
