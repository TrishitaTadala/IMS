
//All the variables should be declared
//======================================================
// Class      : USFFee
// Description: The USF Fee applicable is captured from the PDF
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 20/03/20     Trishita Tadala     Written
// 00/00/20     Trishita Tadala     Modification
//======================================================

package Invoice_Validation;
//package inmarsatPDF;
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

//package inmarsatPDF;

//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class USFFee {
public static String[][] USFFee_PDF;
public static String[] usfFee_PDF = new String[5000];
public static String usffee_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void uSFFee() throws Exception{
		USFFee_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{		if(inmarsatXML.USFFee_XML!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				usffee_PDF =USFFee_PDF[i][k];
					if(usffee_PDF.equalsIgnoreCase("USF")) //Mar-20-2020
//					if (TotalTaxes_PDF[i-1][k].equalsIgnoreCase(""))
						{
						m =i;
						usfFee_PDF[p]=USFFee_PDF[m+2][k];		
						usfFee_PDF[p]=usfFee_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
usfFee_PDF[p] =null;
}

p = p +1;
}


}
}










