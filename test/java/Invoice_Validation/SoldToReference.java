//All the variables should be declared
//======================================================
// Class      : SoldToReference
// Description: The Sold to reference field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 01/14/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class SoldToReference {
public static String[][] SoldToReference_PDF;
public static String[] soldToReference_PDF = new String[25];
public static String soldtoreference_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void soldToReference() throws Exception{
		SoldToReference_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.SoldToReference_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				soldtoreference_PDF =SoldToReference_PDF[i][k];
					if(soldtoreference_PDF.equalsIgnoreCase("reference"))
					{
						m =i;
						soldToReference_PDF[p]=SoldToReference_PDF[m+5][k];		
						soldToReference_PDF[p]=soldToReference_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
soldToReference_PDF[p] =null;
}

p = p +1;
}


}
}
