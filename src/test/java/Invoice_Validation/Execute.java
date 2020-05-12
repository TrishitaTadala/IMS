//All the variables should be declared
//======================================================
// Class      : Execute
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
// 20/03/20     Trishita Tadala     Modification
// 23/04/20     Trishita Tadala     Bill To Comparison Logic
// 24/04/20     Trishita Tadala     ServiceSummaryCall
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


public class Execute {

   
public static void main(String[] args) throws Exception{
/***********************************************************************************************************/		
	ReadExcelFile.readExcelFile(); // Reading the Contents of the Excel file containing the path to access the Invoice PDF and the Single view XML
	inmarsatXML.inmarsatxml(); //Single View XML
	PDFFileExtract.pdfextract(); // Extraction of PDF contents to notepad
/***********************************************************************************************************/	
	BillTo.billTo();
/***********************************************************************************************************/		
	InvoiceNumber.invoiceNumber();// Extraction of Invoice Number
	BillToReference.billToReference();//Bill To Reference Extraction
	SoldToReference.soldToReference();// Sold to reference Extraction
	YourReference.yourToReference();//Your Reference Extraction
	InvoiceCurrency.invoiceCurrency();//Currency
	InvoiceDate.invoiceDate();//Date
/***********************************************************************************************************/			
	PricingAgreeID.pricingAgreeID();
	
	LegacyIDs.legacyIDs();
/***********************************************************************************************************/		
	TotalFees.totalFees();
	TotalAirtime.Totalairtime();
	TotalCA.totalCA();
	PrepaidAirtime.prepaidAirtime(); //Prepaid Airtime - Vouchers
	TotalexclTaxes.totalexclTaxes();
	TotalTaxes.totalTaxes();
	USFFee.uSFFee();
	TotalinclTaxes.totalinclTaxes();
/***********************************************************************************************************/
	//Fees WholesaleCustomer.FeeSummary(); 
/***********************************************************************************************************/
	CreditsorDebits.creditsOrDebits();//Credits and Debits CNT
	//Servicesummary.Servicesummary();
	//Servicesdetails.Servicesdetails();
	//SummaryComparision.summaryComparision(); //Front Page
	//TotalAdjust.totaladjust();
	
/***********************************************************************************************************/
	Footer1.footer1();// Footer across all pages
	
/************************************************BOOLEAN VALIDATION VARIABLES***********************************************************/	
	Boolean a,b,c = true,d,e,f = true,g,i,h,j,z,y,x,l,t,r,u= true, s= true,p =true;// 

/***********************************************************************************************************/	
	FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
	  
	org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
	  
	org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

    Cell cell = null;
    /***********************************************************************************************************/   
    
	for(int k =0;k<ReadExcelFile.i;k++){
		String XMLpath = ReadExcelFile.XMLFILENAME[k];
		String PDFpath = ReadExcelFile.PDFFILENAME[k];
		
		if (inmarsatXML.BillRunType_XML[k].equals("Standard Bill Run")) {
		String remarks = ""; // Remarks set to Blank
		
		//System.out.println("This is " + inmarsatXML.DisplayText_XML[k] + " validation");
		
		System.out.println("**************************************");
		System.out.println("Front Page of the "+ inmarsatXML.DisplayText_XML[k] + " ID :" + inmarsatXML.InvoiceNumber_XML[k]);
		System.out.println("**************************************");
		
/***********************************BILL TO ADDRESS*****************************************************************/		
		
		String billstr1 = inmarsatXML.BillToFrontPage_XML[k].replaceAll("\n", " ");
        String billstr2 = BillTo.billTo_PDF[k];
        
        Set<String> set1 = new TreeSet<String>(Arrays.asList(billstr1.split(" ")));
        Set<String> set2 = new TreeSet<String>(Arrays.asList(billstr2.split(" ")));
        
		if(inmarsatXML.BillToFrontPage_XML[k]==null ||inmarsatXML.BillToFrontPage_XML[k]==""||set1.equals(set2)){
		      
		      
		//System.out.println("Bill To Address - XML value:"+inmarsatXML.BillToFrontPage_XML[k]);
		//System.out.println("Bill To Address - PDF value:"+BillTo.billTo_PDF[k]);
			System.out.println("No Discrepancies for Bill To Address Info");
		l = true;
				 
				     
		}
		else 
		{
			System.out.println("Bill To Address - XML value:"+inmarsatXML.BillToFrontPage_XML[k]);
			System.out.println("Bill To Address - PDF value:"+BillTo.billTo_PDF[k]);	
			l = false;
			remarks+= (" Bill To Address - XML value:"+inmarsatXML.BillToFrontPage_XML[k]+ "Bill To Address - PDF value:"+BillTo.billTo_PDF[k]);
		}
		
		/***********************************INVOICE DETAILS*****************************************************************/					
			if(inmarsatXML.InvoiceNumber_XML[k]==null ||inmarsatXML.InvoiceNumber_XML[k]==""||inmarsatXML.InvoiceNumber_XML[k].equalsIgnoreCase(InvoiceNumber.invoiceNumber_PDF[k])){
			      
			      //Front Page->Invoice Details->Invoice Number Jan-8-2020
			System.out.println("Invoice Number - XML value:"+inmarsatXML.InvoiceNumber_XML[k]);
			System.out.println("Invoice Number - PDF value:"+InvoiceNumber.invoiceNumber_PDF[k]);
					  a = true;
					     
			}
			else 
			{
				System.out.println("Invoice Number - XML value:"+inmarsatXML.InvoiceNumber_XML[k]);
				System.out.println("Invoice Number - PDF value:"+InvoiceNumber.invoiceNumber_PDF[k]);	
				a = false;
				remarks+= (" Invoice Number - XML value:"+inmarsatXML.InvoiceNumber_XML[k]+ "Invoice Number - PDF value:"+InvoiceNumber.invoiceNumber_PDF[k]);
			}
		
			
				
				if(inmarsatXML.BillToReference_XML[k]==null ||inmarsatXML.BillToReference_XML[k]==""||inmarsatXML.BillToReference_XML[k].equalsIgnoreCase(BillToReference.billToReference_PDF[k])){
				      
				      //Front Page->Invoice Details->Bill to Reference Jan-8-2020
				System.out.println("Bill To Reference - XML value:"+inmarsatXML.BillToReference_XML[k]);
				System.out.println("Bill To Reference - PDF value:"+BillToReference.billToReference_PDF[k]);
						  h = true;
						     
				}
				else 
				{
					System.out.println("Bill To Reference - XML value:"+inmarsatXML.BillToReference_XML[k]);
					System.out.println("Bill To Reference - PDF value:"+BillToReference.billToReference_PDF[k]);	
					h = false;
					remarks+= (" Bill To Reference - XML value:"+inmarsatXML.BillToReference_XML[k]+ "Bill To Reference - PDF value:"+BillToReference.billToReference_PDF[k]);
				}
				
				if(inmarsatXML.SoldToReference_XML[k]==null ||inmarsatXML.SoldToReference_XML[k]==""||inmarsatXML.SoldToReference_XML[k].equalsIgnoreCase(SoldToReference.soldToReference_PDF[k])){
				      
				      //Front Page->Invoice Details->Sold to Reference Jan-8-2020
				System.out.println("Sold To Reference - XML value:"+inmarsatXML.SoldToReference_XML[k]);
				System.out.println("Sold To Reference - PDF value:"+SoldToReference.soldToReference_PDF[k]);
						  j = true;
						     
				}
				else 
				{
					System.out.println("Sold To Reference - XML value:"+inmarsatXML.SoldToReference_XML[k]);
					System.out.println("Sold To Reference - PDF value:"+SoldToReference.soldToReference_PDF[k]);	
					j = false;
					remarks+= (" Sold To Reference - XML value:"+inmarsatXML.SoldToReference_XML[k]+ "Sold To Reference - PDF value:"+ SoldToReference.soldToReference_PDF[k]);
				}
		      if(inmarsatXML.YourReference_XML[k]==null ||inmarsatXML.YourReference_XML[k]=="-"||inmarsatXML.YourReference_XML[k]==""||inmarsatXML.YourReference_XML[k].equalsIgnoreCase(YourReference.yourReference_PDF[k])){
				      
				      //Front Page->Invoice Details->Your Reference Jan-22-2020
				System.out.println("Your Reference - XML value:"+inmarsatXML.YourReference_XML[k]);
				System.out.println("Your Reference - PDF value:"+YourReference.yourReference_PDF[k]);
						  r = true;
						     
				}
				else 
				{
					System.out.println("Your Reference - XML value:"+inmarsatXML.YourReference_XML[k]);
					System.out.println("Your Reference - PDF value:"+YourReference.yourReference_PDF[k]);	
					r = false;
					remarks+= (" Your Reference - XML value:"+inmarsatXML.YourReference_XML[k]+ "Your Reference - PDF value:"+ YourReference.yourReference_PDF[k]);
				}
				
				
				if(inmarsatXML.InvoiceDate_XML[k]==null ||inmarsatXML.InvoiceDate_XML[k]==""||inmarsatXML.InvoiceDate_XML[k].equalsIgnoreCase(InvoiceDate.invoiceDate_PDF[k])){
				      
				      //Front Page->Invoice Details->Date Feb-11-2020
				System.out.println("Date - XML value:"+inmarsatXML.InvoiceDate_XML[k]);
				System.out.println("Date - PDF value:"+InvoiceDate.invoiceDate_PDF[k]);
						  y = true;
						     
				}
				else 
				{
					System.out.println("Date - XML value:"+inmarsatXML.InvoiceDate_XML[k]);
					System.out.println("Date - PDF value:"+InvoiceDate.invoiceDate_PDF[k]);	
					y = false;
					remarks+= (" Date - XML value:"+inmarsatXML.InvoiceDate_XML[k]+ "Date - PDF value:"+InvoiceDate.invoiceDate_PDF[k]);
				}
				if(inmarsatXML.Currency_XML[k]==null ||inmarsatXML.Currency_XML[k]==""||inmarsatXML.Currency_XML[k].equalsIgnoreCase(InvoiceCurrency.invoiceCurrency_PDF[k])){
				      
				      //Front Page->Invoice Details->Currency Feb-11-2020
				System.out.println("Currency - XML value:"+inmarsatXML.Currency_XML[k]);
				System.out.println("Currency - PDF value:"+InvoiceCurrency.invoiceCurrency_PDF[k]);
						  z = true;
						     
				}
				else 
				{
					System.out.println("Currency - XML value:"+inmarsatXML.Currency_XML[k]);
					System.out.println("Currency - PDF value:"+InvoiceCurrency.invoiceCurrency_PDF[k]);	
					z= false;
					remarks+= (" Currency - XML value:"+inmarsatXML.Currency_XML[k]+ "Currency - PDF value:"+InvoiceCurrency.invoiceCurrency_PDF[k]);
				}
			
				if(inmarsatXML.PricingAgreeID_XML[k]==null ||inmarsatXML.PricingAgreeID_XML[k]==""||inmarsatXML.PricingAgreeID_XML[k].equalsIgnoreCase(PricingAgreeID.pricingAgree_PDF[k])){
				      
				      //Front Page->Invoice Details->Invoice Number Jan-8-2020
				System.out.println("Pricing Agreement ID - XML value:"+inmarsatXML.PricingAgreeID_XML[k]);
				System.out.println("Pricing Agreement ID - PDF value:"+PricingAgreeID.pricingAgree_PDF[k]);
						  a = true;
						     
				}
				else 
				{
					System.out.println("Pricing Agreement ID - XML value:"+inmarsatXML.PricingAgreeID_XML[k]);
					System.out.println("Pricing Agreement ID - PDF value:"+PricingAgreeID.pricingAgree_PDF[k]);	
					a = false;
					remarks+= (" Pricing Agreement ID - XML value:"+inmarsatXML.PricingAgreeID_XML[k]+ "Pricing Agreement ID - PDF value:"+PricingAgreeID.pricingAgree_PDF[k]);
				}
				
				if (LegacyIDs.isFoundLegacyIDs[k] == true) {
					 t=true;
					System.out.println("LegacyIDs Passed");}
				 else {
					 t= false;
					 System.out.println("Discrepancies"+ LegacyIDs.legacyIDRemarks[k]);
					 remarks+= LegacyIDs.legacyIDRemarks[k];
				 }
				
			
		
			System.out.println("****Summary Section****");
		if(inmarsatXML.TotalFees_XML[k]==null ||inmarsatXML.TotalFees_XML[k]==""||inmarsatXML.TotalFees_XML[k].equalsIgnoreCase(TotalFees.totalFees_PDF[k])){
		      
		      //Front Page->Summary->Total Fees Jan-8-2020
		System.out.println("Total Fees - XML value:"+inmarsatXML.TotalFees_XML[k]);
		System.out.println("Total Fees - PDF value:"+TotalFees.totalFees_PDF[k]);
				  a = true;
				     
		}
		else 
		{
			System.out.println("Total Fees - XML value:"+inmarsatXML.TotalFees_XML[k]);
			System.out.println("Total Fees - PDF value:"+TotalFees.totalFees_PDF[k]);	
			a = false;
			remarks+= (" Total Fees - XML value:"+inmarsatXML.TotalFees_XML[k]+" Total Fees - PDF value:"+TotalFees.totalFees_PDF[k]);
		}
	
		
		if(inmarsatXML.TotalAirtime_XML[k]==null ||inmarsatXML.TotalAirtime_XML[k]==""||inmarsatXML.TotalAirtime_XML[k].equalsIgnoreCase(TotalAirtime.totalAirtime_PDF[k])){
		      
		      //Update the value of cell
		System.out.println("TotalAirtime - XML value:"+inmarsatXML.TotalAirtime_XML[k]);
		System.out.println("TotalAirtime - PDF value:"+TotalAirtime.totalAirtime_PDF[k]);
		b= true;
		     
		}
		else 
		{
			System.out.println("TotalAirtime - XML value:"+inmarsatXML.TotalAirtime_XML[k]);
			System.out.println("TotalAirtime - PDF value:"+TotalAirtime.totalAirtime_PDF[k]);
			b= false;
			remarks+=(" TotalAirtime - XML value:"+inmarsatXML.TotalAirtime_XML[k]+"TotalAirtime - PDF value:"+TotalAirtime.totalAirtime_PDF[k]);
		}
	
		/*
		if(inmarsatXML.TotalAdjust_XML[k]==null ||inmarsatXML.TotalAdjust_XML[k]==""||inmarsatXML.TotalAdjust_XML[k].equalsIgnoreCase(TotalAdjust.totalAdjust_PDF[k]) ){
		      //Update the value of cell
		System.out.println("TotalAdjustments - XML value"+inmarsatXML.TotalAdjust_XML[k]);
		System.out.println("TotalAdjustments - PDF value"+TotalAdjust.totalAdjust_PDF[k]);  
		c=true;
		}
		else 
		{
			System.out.println("TotalAdjustments - XML value"+inmarsatXML.TotalAdjust_XML[k]);
			System.out.println("TotalAdjustments - PDF value"+TotalAdjust.totalAdjust_PDF[k]);     
		c=false;
		remarks+= (" TotalAdjustments - XML value"+inmarsatXML.TotalAdjust_XML[k]+"TotalAdjustments - PDF value"+TotalAdjust.totalAdjust_PDF[k]);
		} */
		
	
		//System.out.println(inmarsatXML.TotalCarrierAirtime_XML[k]);	

		if(inmarsatXML.TotalCarrierAirtime_XML[k]== null ||inmarsatXML.TotalCarrierAirtime_XML[k]=="" || inmarsatXML.TotalCarrierAirtime_XML[k].equalsIgnoreCase(TotalCA.totalCA_PDF[k])){
		      //Front Page->Summary->Prepaid Airtime Jan-8-2020
		System.out.println("CarrierAirtime - XML value:"+inmarsatXML.TotalCarrierAirtime_XML[k]);
		System.out.println("CarrierAirtime - PDF value:"+TotalCA.totalCA_PDF[k]);	     
		d=true;
		}
		else 
		{//System.out.println("-------------");
			System.out.println("Carrier Airtime - XML value:"+inmarsatXML.TotalCarrierAirtime_XML[k]);
			System.out.println("Carrier Airtime - PDF value:"+TotalCA.totalCA_PDF[k]);     
		d=false;
		remarks+= (" CarrierAirtime - XML value"+inmarsatXML.TotalCarrierAirtime_XML[k]+"CarrierAirtime - PDF value:"+TotalCA.totalCA_PDF[k]);
		}
		
		
		if(inmarsatXML.PrepaidAirtime_XML[k]==null ||inmarsatXML.PrepaidAirtime_XML[k]==""||inmarsatXML.PrepaidAirtime_XML[k].equalsIgnoreCase(PrepaidAirtime.prepaidAirtime_PDF[k])){
		      
		      //Front Page->Summary->Prepaid Airtime Jan-8-2020
		System.out.println("Prepaid Airtime - XML value:"+inmarsatXML.PrepaidAirtime_XML[k]);
		System.out.println("Prepaid Airtime - PDF value:"+PrepaidAirtime.prepaidAirtime_PDF[k]);
				  e = true;
				     
		}
		else 
		{
			System.out.println("Prepaid Airtime - XML value:"+inmarsatXML.PrepaidAirtime_XML[k]);
			System.out.println("Prepaid Airtime - PDF value:"+PrepaidAirtime.prepaidAirtime_PDF[k]);	
			e = false;
			remarks+= (" Prepaid Airtime - XML value:"+inmarsatXML.PrepaidAirtime_XML[k]+"Prepaid Airtime - PDF value:"+PrepaidAirtime.prepaidAirtime_PDF[k]);
		}
	
			
		
		if(inmarsatXML.TotalAmountexcl_XML[k]== null ||inmarsatXML.TotalAmountexcl_XML[k]=="" || inmarsatXML.TotalAmountexcl_XML[k].equalsIgnoreCase(TotalexclTaxes.totalAmountExcl_PDF[k])){
		      //Update the value of cell
		System.out.println("TotalAmountexcl - XML value:"+inmarsatXML.TotalAmountexcl_XML[k]);
		System.out.println("TotalAmountexcl - PDF value:"+TotalexclTaxes.totalAmountExcl_PDF[k]);	     
		f=true;
		}
		else 
		{
		System.out.println("TotalAmountexcl - XML value:"+inmarsatXML.TotalAmountexcl_XML[k]);
		System.out.println("TotalAmountexcl - PDF value:"+TotalexclTaxes.totalAmountExcl_PDF[k]);  
		f=false;
		remarks+=(" TotalAmountexcl - XML value:"+inmarsatXML.TotalAmountexcl_XML[k]+"TotalAmountexcl - PDF value:"+TotalexclTaxes.totalAmountExcl_PDF[k]);
		}
		

		if(inmarsatXML.TotalTaxes_XML[k]== null ||inmarsatXML.TotalTaxes_XML[k]=="" || inmarsatXML.TotalTaxes_XML[k].equalsIgnoreCase(TotalTaxes.totalTaxes_PDF[k])){
		      //Update the value of cell
		System.out.println("Total Taxes - XML value:"+inmarsatXML.TotalTaxes_XML[k]);
		System.out.println("Total Taxes - PDF value:"+TotalTaxes.totalTaxes_PDF[k]);	     
		i=true;
		}
		// Tax Information not applicable in the invoice Jan-8-2020
				
		else 
		{
		System.out.println("Total Taxes - XML value:"+inmarsatXML.TotalTaxes_XML[k]);
		System.out.println("Total Taxes - PDF value:"+TotalTaxes.totalTaxes_PDF[k]);     
		i=false;
		remarks+=" Total Taxes - XML value:"+inmarsatXML.TotalTaxes_XML[k]+"Total Taxes - PDF value:"+TotalTaxes.totalTaxes_PDF[k];
		}
		//System.out.println(inmarsatXML.TaxType_XML[k]);
		if(inmarsatXML.USFFee_XML[k]== null ||inmarsatXML.USFFee_XML[k]=="" || inmarsatXML.USFFee_XML[k].equalsIgnoreCase(USFFee.usfFee_PDF[k])){
		      //Update the value of cell
		System.out.println("USF Fee - XML value:"+inmarsatXML.USFFee_XML[k]);
		System.out.println("USF Fee - PDF value:"+USFFee.usfFee_PDF[k]);	     
		x=true;
		}
		// Tax Information not applicable in the invoice Jan-8-2020
				
		else 
		{
			System.out.println("USF Fee - XML value:"+inmarsatXML.USFFee_XML[k]);
			System.out.println("USF Fee - PDF value:"+USFFee.usfFee_PDF[k]);    
		x=false;
		remarks+=" USF Fee - XML value:"+inmarsatXML.USFFee_XML[k]+"USF Fee - PDF value:"+USFFee.usfFee_PDF[k];
		}
		
		
		
		if(inmarsatXML.TotalAmountincl_XML[k]== null ||inmarsatXML.TotalAmountincl_XML[k]=="" || inmarsatXML.TotalAmountincl_XML[k].equalsIgnoreCase(TotalinclTaxes.totalAmountIncl_PDF[k])){
		      //Front Page->Summary->Total Inclusive Taxes Jan-8-2020
		System.out.println("Total Incl Taxes - XML value:"+inmarsatXML.TotalAmountincl_XML[k]);
		System.out.println("Total Incl Taxes - PDF value:"+TotalinclTaxes.totalAmountIncl_PDF[k]);
		g=true;
		}
		else 
		{
			System.out.println("Total Incl Taxes - XML value:"+inmarsatXML.TotalAmountincl_XML[k]);
			System.out.println("Total Incl Taxes - PDF value:"+TotalinclTaxes.totalAmountIncl_PDF[k]);   
		g=false;
		remarks+=" Total Incl Taxes - XML value:"+inmarsatXML.TotalAmountincl_XML[k]+"  Total Incl Taxes - PDF value:"+TotalinclTaxes.totalAmountIncl_PDF[k];
		}
/****************************************************TAX INFORMATION*******************************************************/			
		/*try {
		
		if (TaxInfo.isFoundTaxInfo[k] == true) {
			 s=true;
			System.out.println("Tax Information -  PASSED ");}
		 else {
			 s= false;
			 System.out.println("Discrepancies"+ TaxInfo.TaxInfoRemarks[k]);
			 remarks+= TaxInfo.TaxInfoRemarks[k];
		 }		
		
		}catch(Exception e2) {}*/


		//(PDFpath, XMLpath);

/****************************************************FEE SUMMARY LINE ITEMS***************************************************************/

		if (inmarsatXML.CustomerInvoicePreference_XML[k].equals("SUMMARY")) {
		
			System.out.println("This is a Wholesale Customer");
		    
			
			//p = WholesaleCustomer.FeesummaryPDF(PDFpath, XMLpath);
			
			//remarks+= WholesaleCustomer.FeeSummaryLineItemsRemarks;
		
		}
		else {
			System.out.println("This is a Retail Customer");
		}
		
/****************************************************FOOTER***************************************************************/			
		try {
		
		if (Footer1.isFound[k] == true) {
			
			System.out.println("****************PASSED - No Footer Discrepancies*********************");
			
			u = true;
			
		}
		else {
			u = false;
			remarks+= " XML Footer Contents "+inmarsatXML.Footer_XML[k]+ "\n"+ " PDF Footer Contents " + Footer1.FooterExtract_PDF[k];
			System.out.println("*******FAILED - There are Footer Discrepancies********");
			System.out.println( "XML Footer Contents"+inmarsatXML.Footer_XML[k]+ "\n"+ " PDF Footer Contents" + Footer1.FooterExtract_PDF[k]);
		}
		
		}catch(Exception e2) {}
		
		if (a&&b&&c&&d&&e&&f&&g&&i&&h&&j&&x&&z&&y&&l&&u&&t&&s&&r&&p){
			 cell = sheet.getRow(k+1).getCell(2);
			   cell.setCellValue("PASS");
			   
		}
		else{
			cell = sheet.getRow(k+1).getCell(2);
			   cell.setCellValue("FAIL");
			   cell=sheet.getRow(k+1).getCell(3);
			   //System.out.println(remarks);
			   cell.setCellValue(remarks);
		}
		
		
		
		FileOutputStream outFile =new FileOutputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
	      workbook.write(outFile);
	      outFile.close();
	      
	   /****************************************************SERVICE SUMMARY ***************************************************************/		
		    //Servicesummary.servicesummary(PDFpath, XMLpath);
		//	remarks+= Servicesummary.SSRemarks;
	/****************************************************LAST PAGE ***************************************************************/		
			//LastPage.lastpage(PDFpath, XMLpath);	
			//remarks+= LastPage.LPRemarks;
	      
	      
		}
		
		/* Immediate Bill Run Invoices Front Page Validation*/
		else {
			
			System.out.println("**************************************");
			//System.out.println("This is " + inmarsatXML.DisplayText_XML[k] + " validation");
			String remarks = ""; // Remarks set to Blank
			System.out.println("**************************************");
			System.out.println("Front Page of the "+ inmarsatXML.DisplayText_XML[k] + " ID : "+ inmarsatXML.ADJNmber_XML[k]);
			System.out.println("**************************************");
			//CNT.CreditNote(PDFpath, XMLpath);
			//System.out.println(CNT.CntRemarks);
			
					if (CNT.CntRemarks ==null){
						 cell = sheet.getRow(k+1).getCell(2);
						   cell.setCellValue("PASS");
						   
					}
					else{
						cell = sheet.getRow(k+1).getCell(2);
						   cell.setCellValue("FAIL");
						   cell=sheet.getRow(k+1).getCell(3);
						   //System.out.println(remarks);
						   cell.setCellValue(CNT.CntRemarks);
					}
					
					
					
					FileOutputStream outFile =new FileOutputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
				      workbook.write(outFile);
				      outFile.close();
		}
	}
 }
}
	
