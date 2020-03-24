package Utilities;
/**
 * @author Praveen
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static int getSheetIndex; 
	private static XSSFCell Cell;
	private static XSSFRow Row;
	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	public static void setExcelFile(String Path,String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			getSheetIndex = ExcelWBook.getSheetIndex(SheetName);
			Log.info("Excel sheet opened");
		} catch (Exception e){
			throw (e);
		}
	}
	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum) throws Exception{
		try{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		}catch (Exception e){
			return"";
		}
	}

	public static void setCellData(String Result,  int RowNum, int colResult) throws Exception    {
		try{
			Row  = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(colResult, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(colResult);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Praveen Kumar Voora\\eclipse-workspace\\INMARSAT BTP Regression\\BTP_Regression\\TestData\\BGAN Regression.xlsx");
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			throw (e);
		}
	}

	public static void setCellDataorder(String BGANorderID,  int RowNum, int Col_OrderID) throws Exception    {
		try{
			Row  = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(Col_OrderID, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(Col_OrderID);
				Cell.setCellValue(Col_OrderID);
			} else {
				Cell.setCellValue(Col_OrderID);
			}
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Praveen Kumar Voora\\eclipse-workspace\\INMARSAT BTP Regression\\BTP_Regression\\TestData\\BGAN Regression.xlsx");
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			throw (e);
		}
	}
	public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
		int i;
		try {
			int rowCount = ExcelUtils.getRowUsed();
			for ( i=0 ; i<rowCount; i++){
				if  (ExcelUtils.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
					break;
				}
			}
			return i;
		}catch (Exception e){
			Log.error("Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
			throw(e);
		}
	}

	public static int getRowUsed() throws Exception {
		try{
			int RowCount = ExcelWSheet.getLastRowNum();
			Log.info("Total number of Row used return as < " + RowCount + " >.");		
			return RowCount;
		}catch (Exception e){
			Log.error("Class ExcelUtil | Method getRowUsed | Exception desc : "+e.getMessage());
			System.out.println(e.getMessage());
			throw (e);
		}

	}


	public static int getColumnNumber(String ColumnName) throws Exception{
		int TotalColumnNumber = 29;
		int ColumnNumber=0;
		for (int i= 0; i<TotalColumnNumber; i++) {
			if(getCellData(0, i).equalsIgnoreCase(ColumnName)){
				break;
			}
			ColumnNumber=ColumnNumber+1;
		}
		return ColumnNumber;
	}
	
	
	// 
	private static int getRowNumber(int columnNumber, String cellValue) {

		//ArrayList<String> rowNumber = new ArrayList<String>();
		int rowNumber=0;
		try{		
			int lastrow = ExcelWSheet.getLastRowNum();

			for(int rowIndex=1; rowIndex<=lastrow; rowIndex++)
			{
				XSSFCell rowDataCell = ExcelWSheet.getRow(rowIndex).getCell(columnNumber);				

				if(!(rowDataCell.getCellType()==org.apache.poi.ss.usermodel.CellType.BLANK))
				{		

					if(rowDataCell.getCellType()==org.apache.poi.ss.usermodel.CellType.NUMERIC || rowDataCell.getCellType()==org.apache.poi.ss.usermodel.CellType.FORMULA)
					{							
						if(String.valueOf(rowDataCell.getNumericCellValue()).replace(".0", "").trim().equalsIgnoreCase(cellValue))
						{						
							//rowNumber.add(i, String.valueOf(rowIndex));
							//i=i+1;					
						}
					}			

					if(rowDataCell.getCellType()==org.apache.poi.ss.usermodel.CellType.STRING)
					{
						if(rowDataCell.getStringCellValue().equalsIgnoreCase(cellValue)){					
							//rowNumber.add(i, String.valueOf(rowIndex));
							//i=i+1;
							rowNumber = rowIndex;
						}
					}
				}			
			}

		}catch(Exception e){
			//e.printStackTrace();			
		}			

		return rowNumber;
	}
	
	
	public static Map retrieveRowData(String columnName, String cellValue){		

		Map<String, String> DetailsMap  = new HashMap<String, String>();		


		try{					
			//excel_Reader(path);

			int index = getSheetIndex;

			if(!(index==-1))
			{
				//sheet = workbook.getSheetAt(index);
				// column number for the particular column (Test Case Column)
				int colCount = getColumnNumber(columnName);				

				// This give the header Row number = 0
				int rowHeaderCount = ExcelWSheet.getFirstRowNum();				

				// This gives the all the Row0 data (header data)
				XSSFRow headerRowData = ExcelWSheet.getRow(rowHeaderCount);

				// CellValue = Test Case Name from Test Script 
				//rowDATA = gives all the rowdata ie rowvalue for the perticular test case
				int rowNumber = getRowNumber(colCount, cellValue);					

				if(rowNumber!=0)
				{	
					for(int i=0; i<rowNumber; i++)
					{	
						HashMap<String, String> myDataMap = new HashMap<String,String>();

						XSSFRow rowData = ExcelWSheet.getRow(rowNumber);

						Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = rowData.cellIterator();

						Iterator<org.apache.poi.ss.usermodel.Cell> headerCellIterator = headerRowData.cellIterator();						

						while(cellIterator.hasNext())	
						{	
							XSSFCell excelCell = (XSSFCell) cellIterator.next();	

							XSSFCell headerCell = (XSSFCell) headerCellIterator.next();					

							if(excelCell.getCellType()==CellType.BLANK)
							{
								String cellText = " ";
								if(headerCell.getCellType()==CellType.NUMERIC)
								{
									
									myDataMap.put(String.valueOf(headerCell.getNumericCellValue()).replace(".0", "").trim(), cellText);
								}	else if(headerCell.getCellType()==CellType.FORMULA)
								{
									
									myDataMap.put(String.valueOf(headerCell.getCellFormula()), cellText);
								}else if(headerCell.getCellType()==CellType.STRING)
								{
									
									myDataMap.put(String.valueOf(headerCell.getStringCellValue()), cellText);
								}
							}

							if(excelCell.getCellType()==CellType.NUMERIC)
							{								
								String cellText;							
								if (HSSFDateUtil.isCellDateFormatted(excelCell)) {
									/**** format in form of M/D/YY *****/
									double d = excelCell.getNumericCellValue();

									Calendar cal =Calendar.getInstance();

									cal.setTime(HSSFDateUtil.getJavaDate(d));



									cellText = (String.valueOf(cal.get(Calendar.YEAR)));

									int value = Integer.parseInt(String.valueOf(cal.get(Calendar.MONTH))) +1;



									cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
											String.valueOf(Integer.parseInt(String.valueOf(cal.get(Calendar.MONTH))) +1)+ "/" + 
											cellText;								
								}else{
									cellText =String.valueOf(excelCell.getNumericCellValue()).replace(".0", "").trim();
								}



								if(headerCell.getCellType()==CellType.NUMERIC)
								{
									
									myDataMap.put(String.valueOf(headerCell.getNumericCellValue()).replace(".0", "").trim(), cellText);
								}	else if(headerCell.getCellType()==CellType.FORMULA)
								{
									
									myDataMap.put(String.valueOf(headerCell.getCellFormula()), cellText);
								}else if(headerCell.getCellType()==CellType.STRING)
								{
									
									myDataMap.put(headerCell.getStringCellValue(), cellText);
								}			    
							}	



							if(excelCell.getCellType()==CellType.STRING)
							{
								if(headerCell.getCellType()==CellType.NUMERIC)
								{
									
									myDataMap.put(String.valueOf(headerCell.getNumericCellValue()).replace(".0", "").trim(), excelCell.getStringCellValue());
								}	else if(headerCell.getCellType()==CellType.FORMULA)
								{
									
									myDataMap.put(String.valueOf(headerCell.getCellFormula()), excelCell.getStringCellValue());
								}else if(headerCell.getCellType()==CellType.STRING)
								{
									
									myDataMap.put(String.valueOf(headerCell.getStringCellValue()), excelCell.getStringCellValue());
								}
							}

							if( excelCell.getCellType()==CellType.FORMULA){

								if(headerCell.getCellType()==CellType.NUMERIC)
								{
									
									myDataMap.put(String.valueOf(headerCell.getNumericCellValue()).replace(".0", "").trim(), String.valueOf(excelCell.getCellFormula()));
								}	else if(headerCell.getCellType()==CellType.FORMULA)
								{
									
									myDataMap.put(String.valueOf(headerCell.getCellFormula()), String.valueOf(excelCell.getCellFormula()));
								}else if(headerCell.getCellType()==CellType.STRING)
								{
									
									myDataMap.put(String.valueOf(headerCell.getStringCellValue()), String.valueOf(excelCell.getCellFormula()));
								}
							}						
						}

						//DetailsMap.put(String.valueOf(i+1),myDataMap);
						DetailsMap = myDataMap;
					}
				}	
			}			
		}catch(Exception e)
		{
			e.printStackTrace();
		}			
		return DetailsMap;		
	}
	

}