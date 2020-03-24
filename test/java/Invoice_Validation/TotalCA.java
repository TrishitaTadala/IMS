//All the variables should be declared
//======================================================
// Class      : TotalCA
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
// 19/03/20     Trishita Tadala     Modification
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import inmarsatXML.inmarsatXML;

public class TotalCA {
	public static String[][] TotalCA_PDF;
	public static String[] totalCA_PDF = new String[5000];
	public static String totalca_PDF;
	public static int p;
	public static int q;
	public static int m=0;
	public static void totalCA() throws Exception{
		TotalCA_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
		{
			if(inmarsatXML.TotalCarrierAirtime_XML[k]!=null)
			{		
				for(int i =0;i<PDFFileExtract.i;i++)
			{
				try{
					totalca_PDF =TotalCA_PDF[i][k];
						if(totalca_PDF.equalsIgnoreCase("Carrier"))
						{
							m =i;
							totalCA_PDF[p]=TotalCA_PDF[m+2][k];		
							totalCA_PDF[p]=totalCA_PDF[p].replaceAll(",", "");
							break;
						}
					
				}catch(Exception e){
					
				}
}
}
else
{
	totalCA_PDF[p] =null;
}

p = p +1;
}


}
}
