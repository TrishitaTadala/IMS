package Invoice_Validation;


import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class ExtractInvoiceDetails {
	public static void main(String[] args) throws Exception {
		String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1386249_986021_201904.pdf";
		PdfReader reader = new PdfReader(filename);  
		getBillingAddress(reader);
		getInvoiceDetails(reader);
		getPricingAgreementId(reader);
		getLegacyId(reader);
		getSummary(reader);
		getTaxInfo(reader);
		getServiceSummary(reader);
		reader.close();
	}

	public static void getBillingAddress(PdfReader reader) throws Exception {		    
        Rectangle billingAddressSection = new Rectangle(0, 620, 200, 750);
        RenderFilter filter = new RegionTextRenderFilter(billingAddressSection);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));       
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
	
	public static void getLegacyId(PdfReader reader) throws Exception {
		Rectangle legacyIds = new Rectangle(0, 550, 200, 600);
        RenderFilter filter = new RegionTextRenderFilter(legacyIds);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static void getSummary(PdfReader reader) throws Exception {
		Rectangle summarySection = new Rectangle(0, 500, 600, 400);
        RenderFilter filter = new RegionTextRenderFilter(summarySection);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static void getTaxInfo(PdfReader reader) throws Exception {
		Rectangle taxInfo = new Rectangle(0, 378, 600, 375);
        RenderFilter filter = new RegionTextRenderFilter(taxInfo);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
	}
	
	public static void getServiceSummary(PdfReader reader) throws Exception {
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 400);
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 2, strategy));
	}
}
