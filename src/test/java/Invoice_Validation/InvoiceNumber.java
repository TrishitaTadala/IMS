//All the variables should be declared
//======================================================
// Class      : InvoiceNumber
// Description: The Number field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 01/08/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;



public class InvoiceNumber {
public static String[][] InvoiceNumber_PDF;
public static String[] invoiceNumber_PDF = new String[7];
public static String invoicenumber_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void invoiceNumber() throws Exception{
		InvoiceNumber_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.InvoiceNumber_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				invoicenumber_PDF =InvoiceNumber_PDF[i][k];
					if(invoicenumber_PDF.equalsIgnoreCase("Number"))
					{
						m =i;
						invoiceNumber_PDF[p]=InvoiceNumber_PDF[m+1][k];		
						invoiceNumber_PDF[p]=invoiceNumber_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
invoiceNumber_PDF[p] =null;
}

p = p +1;
}


}
}
