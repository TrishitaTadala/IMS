//All the variables should be declared
//======================================================
// Class      : SummaryComparision
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
// 19/03/20     Trishita Tadala     Modification
//======================================================
//package inmarsatPDF;
package Invoice_Validation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//import ReadExcelFile.ReadExcelFile;


public class SummaryComparision {
	public static String[][] servicetotal=new String[1000][1000];
	public static String[][] xmltotal=new String[1000][1000];
	public static boolean a;
	public static int i=0;
	public static String summaryline;
	public static void summaryComparision() throws Exception{
		/*ReadExcelFile.ReadExcelFile();
		PDFFileExtract.pdfextract();
		inmarsatXML.inmarsatxml();
		Servicesummary.Servicesummary();
		Servicesdetails.Servicesdetails();*/
		servicetotal = Servicesdetails.servicesDetails;
		xmltotal=inmarsatXML.Total;
		FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
		  
		  org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
		  
		  
//	    XSSFWorkbook workbook = new XSSFWorkbook(file);
		  
		  org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
//	    XSSFSheet sheet = workbook.getSheetAt(0);
	    Cell cell = null;
	    
		for (int k=0;k<PDFFileExtract.k;k++) //comment
		{ i=0;summaryline=null;
		//System.out.println("**************************************");
		//System.out.println("PDF AND XML FILE NO :"+k);
		//System.out.println("**************************************");
		while(servicetotal[i][k]!=null){
			try{
			if(xmltotal[i][k].equalsIgnoreCase(servicetotal[i][k])){
				//System.out.println(xmltotal[i][k]+"XML");
				//System.out.println(servicetotal[i][k]+"PDF");
				a =true;
			}
			else{
				//System.out.println("---------------------");
				//System.out.println(xmltotal[i][k]+"XML");
				//System.out.println(servicetotal[i][k]+"PDF");
				summaryline=summaryline+xmltotal[i][k]+"XML"+servicetotal[i][k]+"PDF";
				System.out.println(summaryline);
				a =false;
				System.out.println("SUMMARY PAGE IS FAILED at"+a);
			}}catch(Exception e){}
		i = i +1;}cell = sheet.getRow(k+1).getCell(4);
		   cell.setCellValue(summaryline);

			FileOutputStream outFile =new FileOutputStream(new File(System.getProperty("user.dir")+"\\InmarsatPDFExcel.xlsx"));
		      workbook.write(outFile);
		      outFile.close();
		}
		}
}