//All the variables should be declared
//======================================================
// Class      : Execute
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
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class TotalAdjust {
public static String[][] TotalAdjust_PDF;
public static String[] totalAdjust_PDF = new String[5000];
public static String totaladjust_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void totalAdjust() throws Exception{
		TotalAdjust_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
		if(inmarsatXML.TotalAdjust_XML[k]!=null)
		{	
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				totaladjust_PDF =TotalAdjust_PDF[i][k];
					if(totaladjust_PDF.equalsIgnoreCase("Adjustments"))
					{
						m =i;
						totalAdjust_PDF[p]=TotalAdjust_PDF[m+1][k];		
						totalAdjust_PDF[p]=totalAdjust_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
								}
		}
		}
			else
			{
				totalAdjust_PDF[p] =null;
			}
				
		p = p +1;
	}
		
		
	}
}
