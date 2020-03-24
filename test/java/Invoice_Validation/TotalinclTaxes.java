//package inmarsatPDF;

//All the variables should be declared
//======================================================
//Class      : TotalinclTaxes
//Description: The total inclusive taxes displayed in the Front Page 
//             under the Summary Section
//======================================================
//Changes----------------------------------------------
//Date         Test Analyst        Change
//01/08/20     Trishita Tadala     Written
//======================================================
package Invoice_Validation;

//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class TotalinclTaxes {
public static String[][] TotalAmountIncl_PDF;
public static String[] totalAmountIncl_PDF = new String[5000];
public static String totalamountincl_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void totalinclTaxes() throws Exception{
		TotalAmountIncl_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.TotalAmountincl_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				totalamountincl_PDF =TotalAmountIncl_PDF[i][k];
					if(totalamountincl_PDF.equalsIgnoreCase("incl"))
					{
						m =i;
						//System.out.println(TotalAmountIncl_PDF[m+2]);
						totalAmountIncl_PDF[p]=TotalAmountIncl_PDF[m+2][k];		
						totalAmountIncl_PDF[p]=totalAmountIncl_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
	totalAmountIncl_PDF[p] =null;
}

p = p +1;
}


}
}
