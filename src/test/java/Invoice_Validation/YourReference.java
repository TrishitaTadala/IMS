//All the variables should be declared
//======================================================
// Class      : YourReference
// Description: The Your reference field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 22/01/20     Trishita Tadala     Written
// 13/04/20     Trishita Tadala     Modified
//======================================================

package Invoice_Validation;


public class YourReference {
public static String[][] YourReference_PDF;
public static String[] yourReference_PDF = new String[100000];
public static String yourreference_PDF;
public static int p;
public static int q;
public static int m=0;// start line
public static int n=0;//end line position
public static  int j=0;
public static String yourRef=null;

	public static void yourToReference() throws Exception{
		YourReference_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.YourReference_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				yourreference_PDF =YourReference_PDF[i][k];
				if(yourreference_PDF.equalsIgnoreCase("Your"))
				{
					m =i;
					yourReference_PDF[p]=YourReference_PDF[m+2][k];		
					yourReference_PDF[p]=yourReference_PDF[p].replaceAll(",", "");
					break;
				}
			}
			catch(Exception e){
				
			}
			
			
} 
}
else
{
yourReference_PDF[p] =null;


p = p +1;
}
}
	}
}



