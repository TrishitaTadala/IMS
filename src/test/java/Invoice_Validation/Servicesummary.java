//All the variables should be declared
//======================================================
// Class      : Execute
// Description: The Parent class that invokes all the child classes
//               from other packages
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 19/03/20     Trishita Tadala     Written
//======================================================

//package inmarsatPDF;
package Invoice_Validation;
//import ReadExcelFile.ReadExcelFile;
//import inmarsatXML.inmarsatXML;

public class Servicesummary {
public static String[][] ServiceSummary_PDF;
public static int[][] array = new int[50][50];
public static String[] serviceSummary_PDF = new String[5000];
public static String servicesummary_PDF;
public static int p;
public static int q;
public static int m=0;
public static int l=0;
public static int j=0;
public static String[] splitStr;
	public static void Servicesummary() throws Exception{
		/*ReadExcelFile.ReadExcelFile();
		PDFFileExtract.pdfextract();
		inmarsatXML.inmarsatxml();*/
		ServiceSummary_PDF = PDFFileExtract.cmparry;
		for (int k=0;k<PDFFileExtract.k;k++)
	{q=0;
	for (int i= 0;i<inmarsatXML.m;i++)
		{	
		try{
		splitStr = inmarsatXML.Product[k][i].split("\\s+");
		m = splitStr.length;
		//System.out.println( inmarsatXML.Product[k][i]);
		
		for(l=0;l<m;l++){	
		for( j =q;j<PDFFileExtract.i;j++){
//			System.out.println(splitStr[m-1]);
				try{
				servicesummary_PDF =ServiceSummary_PDF[j][k];
				//System.out.println(servicesummary_PDF);
				//System.out.println(splitStr[l]);
				if(servicesummary_PDF.equalsIgnoreCase(splitStr[l]))//&& ServiceSummary_PDF[j+1][k].equalsIgnoreCase(splitStr[l+1]) && ServiceSummary_PDF[j+2][k].equalsIgnoreCase(splitStr[l+2]))
				{//System.out.println(servicesummary_PDF+"111111111");
				//System.out.println(ServiceSummary_PDF[j+1][k]+"2222222222");
				//System.out.println(ServiceSummary_PDF[j+2][k]+"333333333333");
				//System.out.println(splitStr[m]);
						q=j;
						break;
				}
				}catch(Exception e){}
				}
						}
					array[i][k]=q;
					//System.out.println(ServiceSummary_PDF[array[i][k]][k]+"PPPPPPPPP");
		}catch(Exception e){}
			}		
	}
	}
}