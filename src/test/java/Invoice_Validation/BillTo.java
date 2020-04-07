

//All the variables should be declared
//======================================================
// Class      : BillTo
// Description: The BillTo field under the Invoice Details Section
//              from the invoice PDF is captured
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 07/04/20     Trishita Tadala     Written
//======================================================

package Invoice_Validation;


public class BillTo {
public static String[][] BillTo_PDF;
public static String[] billTo_PDF = new String[500];
public static String billto_PDF;
public static int p;
public static int q;
public static int m=0;
	public static void billTo() throws Exception{
		BillTo_PDF = PDFFileExtract.cmparry;
		
		for (int k=0;k<PDFFileExtract.k;k++)
	{
			if(inmarsatXML.BillToFrontPage_XML[k]!=null)
			{
			for(int i =0;i<PDFFileExtract.i;i++)
		{
			try{
				billto_PDF =BillTo_PDF[i][k];
					if(billto_PDF.equalsIgnoreCase("Bill"))
					{
						m =i+1;
						String Address = null;
						while (m>0)
						{
							Address =BillTo_PDF[m+1][k];
							
							Address =Address.replaceAll(",", "");
							//Address =Address.replaceAll("Number", "");
							
							if(billTo_PDF[p] == null)
							{
								billTo_PDF[p] = Address;
								
							}
							else {
								billTo_PDF[p] = billTo_PDF[p]+ " " + Address;
								billTo_PDF[p] =billTo_PDF[p].replaceAll(" Number", "");
							}
							
							if (Address.equalsIgnoreCase("Number")) 
							{
								//Address =Address.replaceAll("Number", "");
								break;
							}
							else {
								m++;
							}
						//break;		
						}
						
						break;
									}
			}catch(Exception e){
				
			}
			
}
			
}
else
{
billTo_PDF[p] =null;
}

p = p +1;
}


}
}
