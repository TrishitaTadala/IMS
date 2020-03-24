//All the variables should be declared
//======================================================
// Class      : TotalFees
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

public class TotalFees {
public static String[][] TotalFees_PDF;
public static String[] totalFees_PDF = new String[5000];
public static String totalfees_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void totalFees() throws Exception{
		TotalFees_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.TotalFees_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				totalfees_PDF =TotalFees_PDF[i][k];
					if(totalfees_PDF.equalsIgnoreCase("FEES"))
					{
						m =i;
						totalFees_PDF[p]=TotalFees_PDF[m+1][k];		
						totalFees_PDF[p]=totalFees_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
totalFees_PDF[p] =null;
}

p = p +1;
}


}
}
