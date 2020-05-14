//All the variables should be declared
//======================================================
// Class      : Versus
// Description: The Mail class that invokes all the validation classes
//              
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 05/14/20     Trishita Tadala     Written
// 
// 
// 
//======================================================


package Invoice_Validation;

//Main Executable Java class to validate the Totals in the Invoice against the Single View XML



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.itextpdf.text.pdf.PdfReader;


public class Versus {


	public static String  xmlrows;
	public static boolean q,a,b,c,d,e,f,g,h,i,j,l,m,n,o,p,rS,rI,fsa,fsb,fsc,fsd,fse,fsf;
	
	public static String Remarks;
   
public static void main(String[] args) throws Exception{
/***********************************************************************************************************/		
	ReadExcelFile.readExcelFile(); // Reading the Contents of the Excel file containing the path to access the Invoice PDF and the Single view XML
	inmarsatXML.inmarsatxml();
/***********************************************************************************************************/	
    FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
	  
 	org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
 	  
 	org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

     Cell cell = null;
 /***********************************************************************************************************/   
    Remarks ="";
	for(int k =0;k<ReadExcelFile.i;k++){
		String XMLpath = ReadExcelFile.XMLFILENAME[k];
		String PDFpath = ReadExcelFile.PDFFILENAME[k];
			
		
		System.out.println("**************************************");
		System.out.println("Front Page of the "+ k+" th Document");
		System.out.println("**************************************");
		   
		    a=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"SLEName"));
		    
		    System.out.println("*****************Billing Address_XML - FrontPage"+k+" th Document******************");
		    h =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"CustName"));		    
            i =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"Line1"));
            j= Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"Line2"));
            l= Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"Suburb"));
            m=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"City"));
            n=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"State"));
            o=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"Country"));
            p=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readBillAddressXML(XMLpath,"PostCode"));
            
            System.out.println("*****************Invoice Details - FrontPage"+k+" th Document******************");
            if (inmarsatXML.BillRunType_XML[k].equals("Standard Bill Run")) {
            rS =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"InvoiceNumber"));	
            }
            else {
            rI =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"ADJNumber"));
            }
	        b =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"BillToRef"));
	        c= Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"SoldToRef"));
	        d =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"InvoiceDate"));
	        e =Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"DueDate"));
	        f= Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"Currency"));
	        g = Compare(PDFTextOnly.getPageTextPDF(PDFpath,"Front"),getXMLText.readSingleNodeXML(XMLpath,"CDSummary"));
			
	       System.out.println("*****************FEE SUMMARY"+k+" th Document******************");
	       fsa= Compare(PDFTextOnly.getPageTextPDF(PDFpath,"any"),getXMLText.readFSGroupChargeXML(XMLpath));//Service Group - Fee Summary
	       fsb=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"any"),getXMLText.readFeeSummaryLinesXML(XMLpath)); //Fees Summary Lines
	       fsc=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"any"),getXMLText.readFSSubtotalXML(XMLpath));//Fees Summary Sub Total Lines
	       fsd=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"any"),getXMLText.readFSProductGroupXML(XMLpath));//Fees Summary Product Group Lines
	       fse=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"any"),getXMLText.readFSChargeTotalXML(XMLpath));//Fees Summary Charge Totals
	       fsf=Compare(PDFTextOnly.getPageTextPDF(PDFpath,"any"),getXMLText.readSingleNodeXML(XMLpath,"FeeSummary"));//Total Fees Summary
	        
	
			if (a&&b&&c&&d&&e&&f&&g&&h&&i&&j&&l&&m&&n&&o&&p&&rI&&rS&&fsa&&fsb&&fsc&&fsd&&fse&&fsf){
				 cell = sheet.getRow(k+1).getCell(2);
				   cell.setCellValue("PASS");
				   
			}
			else{
				cell = sheet.getRow(k+1).getCell(2);
				   cell.setCellValue("FAIL");
				   cell=sheet.getRow(k+1).getCell(3);
				   //System.out.println(remarks);
				   cell.setCellValue(Remarks);
			}
			
				
			
			FileOutputStream outFile =new FileOutputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
		      workbook.write(outFile);
		      outFile.close();	

		}
	}

public static boolean Compare(String PDFtext,LinkedHashMap<String,String> XMLHashMap){
	
	 String remarks = " ";
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
			remarks += xmlrows+"\n";
			//System.out.println(remarks);
	     }
	
	 } 
  Remarks =remarks; 
  return  q;   
     
     
}

 }

	
