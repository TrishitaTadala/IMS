//All the variables should be declared
//======================================================
// Class      : InvoiceDate
// Description: The Date field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 02/11/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import inmarsatXML.inmarsatXML;

public class InvoiceDate {
public static String[][] InvoiceDate_PDF;
public static String[] invoiceDate_PDF = new String[25];
public static String invoicedate_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void invoiceDate() throws Exception{
		InvoiceDate_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.InvoiceDate_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				invoicedate_PDF =InvoiceDate_PDF[i][k];
				if(invoicedate_PDF.equalsIgnoreCase("Date"))
					{
						m =i;
						invoiceDate_PDF[p]=InvoiceDate_PDF[m+1][k];		
						invoiceDate_PDF[p]=invoiceDate_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
invoiceDate_PDF[p] =null;
}

p = p +1;
}


}
}
