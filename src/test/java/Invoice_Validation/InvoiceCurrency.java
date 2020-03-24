//All the variables should be declared
//======================================================
// Class      : InvoiceCurrency
// Description: The Currency field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 02/11/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import inmarsatXML.inmarsatXML;

public class InvoiceCurrency {
public static String[][] InvoiceCurrency_PDF;
public static String[] invoiceCurrency_PDF = new String[50];
public static String invoicecurrency_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void invoiceCurrency() throws Exception{
		InvoiceCurrency_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.Currency_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				invoicecurrency_PDF =InvoiceCurrency_PDF[i][k];
					if(invoicecurrency_PDF.equalsIgnoreCase("Currency"))
					{
						m =i;
						invoiceCurrency_PDF[p]=InvoiceCurrency_PDF[m+1][k];		
						invoiceCurrency_PDF[p]=invoiceCurrency_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
invoiceCurrency_PDF[p] =null;
}

p = p +1;
}


}
}
