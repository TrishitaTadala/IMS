//All the variables should be declared
//======================================================
// Class      : PrepaidAirtime
// Description: PrepaidAirtime field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/01/19     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import inmarsatXML.inmarsatXML;

public class PrepaidAirtime {
public static String[][] PrepaidAirtime_PDF;
public static String[] prepaidAirtime_PDF = new String[5000];
public static String prepaidairtime_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void prepaidAirtime() throws Exception{
		PrepaidAirtime_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.PrepaidAirtime_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				prepaidairtime_PDF =PrepaidAirtime_PDF[i][k];
					if(prepaidairtime_PDF.equalsIgnoreCase("Prepaid")) //Airtime
					{
						m =i;
						prepaidAirtime_PDF[p]=PrepaidAirtime_PDF[m+2][k];		
						prepaidAirtime_PDF[p]=prepaidAirtime_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
prepaidAirtime_PDF[p] =null;
}

p = p +1;
}


}
}
