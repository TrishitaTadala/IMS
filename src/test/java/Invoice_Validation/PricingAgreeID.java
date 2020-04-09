//All the variables should be declared
//======================================================
// Class      : InvoiceNumber
// Description: The Pricing Agreement ID field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 08/04/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;

public class PricingAgreeID {

	public static String[][] PricingAgree_PDF;
	public static String[] pricingAgree_PDF = new String[7];
	public static String pricingagree_PDF;
	public static int p;
	public static int q;
	public static int m=0;
	
	public static void pricingAgreeID() throws Exception{
		PricingAgree_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.PricingAgreeID_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				pricingagree_PDF =PricingAgree_PDF[i][k];
					if(pricingagree_PDF.equalsIgnoreCase("Pricing"))
					{
						m =i;
						pricingAgree_PDF[p]=PricingAgree_PDF[m+3][k];		
						pricingAgree_PDF[p]=pricingAgree_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
	pricingAgree_PDF[p] =null;
}

p = p +1;
}


}
}

