//All the variables should be declared
//======================================================
// Class      : ReadExcelFile
// Description: Reads the locations of the Invoice PDF and
//              the Single View XML from the InmarsatPDFExcel.xlsx
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
//24/03/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;

import java.io.File;
import java.io.FileInputStream;
//import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;  
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {
  public static String [] XMLFILENAME = new String[5000];
  public static int i=0;
  public static String [] PDFFILENAME = new String[5000];
       public static void readExcelFile() throws Exception{ 
    	   
    	   //Input Stream reads the data from the file
    			FileInputStream inputStream = new FileInputStream("C:\\Users\\Trishita.Tadala\\git\\IMS_GIT\\InmarsatPDFExcel.xlsx");
    			//FileInputStream inputStream = new FileInputStream(new File(System.getProperty("C:\\Users\\Praveen Kumar Voora\\eclipse-workspace\\INMARSAT BTP Regression\\BTP_Regression\\src\\test\\java\\Invocie_Validation\\InmarsatPDFExcel.xlsx")));  
    			int m=0;
    		      Workbook workbook = new XSSFWorkbook(inputStream);
    		      Sheet firstSheet = workbook.getSheetAt(0);
    		      Iterator<Row> iterator = firstSheet.iterator();
    		       
    		      while (iterator.hasNext()) {
    		          Row nextRow = iterator.next();
    		          Iterator<Cell> cellIterator = nextRow.cellIterator();
    		           
    		          while (cellIterator.hasNext()) {
    		        	  try{
    		              Cell cell = cellIterator.next();
    		              
    		              if(nextRow.getRowNum() > 0){ //To filter column headings
    	                        if(cell.getColumnIndex() == 0){// To match column index
    	                            switch (cell.getCellType()) {
    	                            case STRING:
    	                            	//System.out.println(cell.getStringCellValue());//commented 
    	                            	 XMLFILENAME[i] = cell.getStringCellValue();
    	                            	// System.out.println(i);
    	                            	i = i +1;
    	                            }
    	                        }
    	                        else if(cell.getColumnIndex()==1){
    	                        	switch (cell.getCellType()) {
    	                            case STRING:
    	                            	//System.out.println(cell.getStringCellValue()); //commented
    	                            	 PDFFILENAME[m] = cell.getStringCellValue();
    	                            	//System.out.println(i); //comment
    	                            	m = m +1;
    	                            }
    	                        }
    	                    }  
    		        	  }catch(Exception E){}
    		          }
    		      }
    		     // for (int k=0;k<i;k++){
    		    	  //System.out.println(XMLFILENAME[k]); 
    		    	 // System.out.println(PDFFILENAME[k]);
    		    //  }


    		      
    		      workbook.close();
    		      inputStream.close();
    		}
}

