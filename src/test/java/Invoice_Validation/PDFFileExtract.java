//All the variables should be declared
//======================================================
// Class      : PDFFileExtract
// Description: Reads the contents of the Invoice PDF and
//              writes the same to a Notepad
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 12/26/19     Trishita Tadala     Written
//======================================================


//package inmarsatPDF;
package Invoice_Validation;

import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

//import ReadExcelFile.ReadExcelFile;

public class PDFFileExtract {
	
	public static int i =0;
	public static int k =0;
	public static String[][] cmparry = new String[5000][5000];
		public static void pdfextract() throws Exception{
			for(k =0;k<ReadExcelFile.i;k++){
				String[] filepaths = ReadExcelFile.PDFFILENAME; //File location of the PDF 
				File file = new File(filepaths[k]);
				System.out.println(filepaths[k]);
				FileWriter writer = new FileWriter("C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\PDFtoText\\write.txt", true);
		       //Formatter f = new Formatter("D:\\IMS\\PDFtoText\\write.txt");  
		      //Scanner s = new Scanner("D:\\IMS\\PDFtoText\\write.txt");
		      PDDocument document = PDDocument.load(file);
		      PDFTextStripper pdfStripper = new PDFTextStripper();
		      String text = pdfStripper.getText(document);
		      StringTokenizer t = new StringTokenizer(text);
		      String word ="";
		      i = 0;
		      // Address code for the pdf statement
		      while(t.hasMoreTokens())
		      { 
		    	  try{
		    	  if (i<5000){
		    	  i = i +1;
		          word = t.nextToken();
		          writer.write(word);
		          writer.write("\r\n"); 
		          cmparry[i][k]=word;
		      }  
		      else break;
		      }catch(Exception E){}
		      }
		      document.close();
		      writer.close();
			   
		  	}
	}
}
