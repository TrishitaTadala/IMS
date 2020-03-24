//All the variables should be declared
//======================================================
// Class      : YourReference
// Description: The Your reference field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 01/22/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

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
					if(yourreference_PDF.equalsIgnoreCase("reference")){
						
						m = (i+8);
						System.out.println(m);}
						
						//yourReference_PDF[p]=YourReference_PDF[m+8][k];		
						//yourReference_PDF[p]=yourReference_PDF[p].replaceAll(",", "");
						//break;
					
					if(yourreference_PDF.equalsIgnoreCase("additional")){n =(i-2);}
					
			}catch(Exception e){
				
			}
			
			do {
				yourRef = yourRef.concat(YourReference_PDF[m+j][k]);
				
				yourReference_PDF[p]= yourRef;		
			
				yourReference_PDF[p]=yourReference_PDF[p].replaceAll(",", "");
				j++;
				//break;
			} while (j>n);
			
} 
}
else
{
yourReference_PDF[p] =null;
}

p = p +1;
}


}
}
