
//All the variables should be declared
//======================================================
// Class      : TotalTaxes
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
// 19/03/20     Trishita Tadala     Modification
//======================================================

package Invoice_Validation;
//package inmarsatPDF;
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

//package inmarsatPDF;

//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class TotalTaxes {
public static String[][] TotalTaxes_PDF;
public static String[] totalTaxes_PDF = new String[5000];
public static String totaltaxes_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void totalTaxes() throws Exception{
		TotalTaxes_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{		if(inmarsatXML.TotalTaxes_XML!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				totaltaxes_PDF =TotalTaxes_PDF[i][k];
					if(totaltaxes_PDF.equalsIgnoreCase("excl")) //Jan-14-2020
//					if (TotalTaxes_PDF[i-1][k].equalsIgnoreCase(""))
						{
						m =i;
						totalTaxes_PDF[p]=TotalTaxes_PDF[m+4][k];		
						totalTaxes_PDF[p]=totalTaxes_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
	totalTaxes_PDF[p] =null;
}

p = p +1;
}


}
}
