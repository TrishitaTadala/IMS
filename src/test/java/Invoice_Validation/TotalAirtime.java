//All the variables should be declared
//======================================================
// Class      : TotalAirtime
// Description: The TotalAirtime field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 01/14/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import inmarsatXML.inmarsatXML;

public class TotalAirtime {
	public static String[][] TotalAirtime_PDF;
	public static String[] totalAirtime_PDF = new String[5000];
	public static String totalairtime_PDF;
	public static int p;
	public static int q;
	public static int m=0;
	public static void Totalairtime() throws Exception{
		TotalAirtime_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
		{
			if(inmarsatXML.TotalAirtime_XML[k]!=null)
			{		
				for(int i =0;i<PDFFileExtract.i;i++)
			{
				try{
						totalairtime_PDF =TotalAirtime_PDF[i][k];
						if(totalairtime_PDF.equalsIgnoreCase("Airtime"))
						{
							m =i;
							totalAirtime_PDF[p]=TotalAirtime_PDF[m+1][k];		
							totalAirtime_PDF[p]=totalAirtime_PDF[p].replaceAll(",", "");
							break;
						}
					
				}catch(Exception e){
					
				}
}
}
else
{
totalAirtime_PDF[p] =null;
}

p = p +1;
}


}
}
