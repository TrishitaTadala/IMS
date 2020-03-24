//All the variables should be declared
//======================================================
// Class      : BillToReference
// Description: The Bill to reference field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 01/14/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import inmarsatXML.inmarsatXML;

public class BillToReference {
public static String[][] BillToReference_PDF;
public static String[] billToReference_PDF = new String[25];
public static String billtoreference_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void billToReference() throws Exception{
		BillToReference_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.BillToReference_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				billtoreference_PDF =BillToReference_PDF[i][k];
					if(billtoreference_PDF.equalsIgnoreCase("reference"))
					{
						m =i;
						billToReference_PDF[p]=BillToReference_PDF[m+1][k];		
						billToReference_PDF[p]=billToReference_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
billToReference_PDF[p] =null;
}

p = p +1;
}


}
}
