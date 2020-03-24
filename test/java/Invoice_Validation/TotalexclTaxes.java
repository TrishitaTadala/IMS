//All the variables should be declared
//======================================================
// Class      : Execute
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
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class TotalexclTaxes {
public static String[][] TotalAmountExcl_PDF;
public static String[] totalAmountExcl_PDF = new String[5000];
public static String totalamountexcl_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void totalexclTaxes() throws Exception{
		TotalAmountExcl_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.TotalAmountexcl_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				totalamountexcl_PDF =TotalAmountExcl_PDF[i][k];
					if(totalamountexcl_PDF.equalsIgnoreCase("excl"))
					{
						m =i;
						//System.out.println(TotalAmountExcl_PDF[m+2]);
						totalAmountExcl_PDF[p]=TotalAmountExcl_PDF[m+2][k];		
						totalAmountExcl_PDF[p]=totalAmountExcl_PDF[p].replaceAll(",", "");
						break;
					}
			}catch(Exception e){
				
			}
}
}
else
{
	totalAmountExcl_PDF[p] =null;
}

p = p +1;
}


}
}
