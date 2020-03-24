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

public class Servicesdetails {
public static int[][] arrays = new int[50][50];
public static String[][] ServicesDetails = new String[1000][1000];
public static String[][] servicesDetails = new String[1000][1000];
public static String[] services = new String[1000];
public static String servicesdetails;
public static int m=0;
public static int n=0;
public static int p=0;
public static int q=0;
public static int row=0;
	public static void Servicesdetails() throws Exception{
		/*ReadExcelFile.ReadExcelFile();
		PDFFileExtract.pdfextract();
		inmarsatXML.inmarsatxml();
		Servicesummary.Servicesummary();*/
		arrays=Servicesummary.array;
		ServicesDetails=PDFFileExtract.cmparry;
		for (int k=0;k<PDFFileExtract.k;k++)
		{ p =0;q=0;
			while(arrays[p][k]!=0){
				row=0;
				//System.out.println(ServicesDetails[arrays[p][k]][k]+"PPPPPPPP");
				for(int i =arrays[p][k];i<PDFFileExtract.i;i++)
			{
				try{ 
				row=+i;
				servicesdetails=ServicesDetails[row][k];
				if(servicesdetails.equalsIgnoreCase("Total")){
					m=row;
				//System.out.println(ServicesDetails[m+1][k]);
					servicesDetails[q][k]=ServicesDetails[m+1][k].replaceAll(",", "");
					//System.out.println("PDF FILE NO" +k);
					//System.out.println(servicesDetails[q][k]);
					services[q]=servicesDetails[q][k];
					q = q +1;
				break;
				}
			}catch(Exception e){}
			}
					
			p = p +1;
		}
		}
		
			
	}
}