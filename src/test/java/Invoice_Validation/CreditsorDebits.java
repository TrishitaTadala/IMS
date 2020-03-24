//All the variables should be declared
//======================================================
// Class      : CreditsorDebits
// Description: The CreditsorDebits field under the Invoice Details Section
//              from the CreditNote PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 02/06/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;

//import inmarsatXML.inmarsatXML;

public class CreditsorDebits {
public static String[][] CreditsOrDebits_PDF;
public static String[] creditsOrDebits_PDF = new String[25];
public static String creditsordebits_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void creditsOrDebits() throws Exception{
		CreditsOrDebits_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.CreditsorDebits_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				creditsordebits_PDF = CreditsOrDebits_PDF[i][k];
					if(creditsordebits_PDF.equalsIgnoreCase("Debits"))
					{
						m =i;
						creditsOrDebits_PDF[p]=CreditsOrDebits_PDF[m+1][k];		
						creditsOrDebits_PDF[p]=creditsOrDebits_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
	creditsOrDebits_PDF[p] =null;
}

p = p +1;
}


}
}
