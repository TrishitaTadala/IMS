package Invoice_Validation;

import java.io.IOException;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.pdfbox.text.TextPosition;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;


/* This is an example on how to get the x/y coordinates and size of each character in PDF
*/
public class CoordinatesPDF extends PDFTextStripper {
    public CoordinatesPDF() throws IOException {
    }
    
    
    /**
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException {
        PDDocument document = null;
        String fileName = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1386249_986021_201904.pdf";
        
        
        
        try {
            document = PDDocument.load( new File(fileName) );
            PDFTextStripper stripper = new CoordinatesPDF();
            
          /*  PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
            Rectangle rect = new Rectangle(35, 375, 340, 204); // Coordinates X,Y, Height & Width
            stripperArea.setSortByPosition(true);
            stripperArea.addRegion("class1", rect); 
            stripperArea.extractRegions(document.getPage(1));  
            System.out.println(stripperArea.getTextForRegion("class1"));*/
            stripper.setSortByPosition( true );
            stripper.setStartPage( 0 );
            stripper.setEndPage( 1 );
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, dummy);
            /*
             *100f, 375f, 10f, 1000f (Page Beginning)
             *0f, 0f, 595f, 842f
             *-20f,-35f, 225, 842f
             *-20f,-35f, 125f, 742f
             *0f, 10f, 600f, 20f
             **/
            ExtractPageContentArea.extractPageContentArea(0, 450, 600,450,"C:\\\\Users\\\\Trishita.Tadala\\\\Desktop\\\\IMS\\\\Invoice\\\\INVOICE_1386249_986021_201904.pdf"); 
          
        }
        finally {
            if( document != null ) {
                document.close();
            }
        }
    }
    
    
    /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        for (TextPosition text : textPositions) {
            /*System.out.println(text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
                    text.getYDirAdj() + ") height=" + text.getHeightDir() + " width=" +
                    text.getWidthDirAdj() + "]");*/
           /* System.out.println(text.getUnicode()+ " [(X=" + text.getX() + ",Y=" +
                    text.getY() + ") height=" + text.getHeight() + " width=" +
                    text.getWidth() + "]");*/
        }
        
        }
    }

//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

/*	javax.xml.xpath.XPath xPath =  XPathFactory.newInstance().newXPath();
	String equipsales ="//EquipmentSale";
	NodeList taxtype = (NodeList) xPath.compile(equipsales).evaluate(doc, XPathConstants.NODESET);
    NodeList taxnode = (NodeList) taxtype;
    m = taxnode.getLength();
    for (int i = 0; i < taxnode.getLength(); i++) {
    	try{
    		TaxType[k][i]= (taxnode.item(i).getFirstChild().getNodeValue());
}catch(Exception e){}
    }*/

/* 
String Addresspath ="//Bill_To_Party/AddressDetails";
NodeList Address = (NodeList) xPath.compile(Addresspath).evaluate(doc, XPathConstants.NODESET);
NodeList Addr = (NodeList) Address;
m = Addr.getLength();
for (int i = 0; i < Addr.getLength(); i++) {
	try{
	
		BillAddress[k][i]= (Addr.item(i).getNodeName());
		System
		
	switch (BillAddress[k][i]){
	
	case "Line1":
	Line1_XML[k] = Addr.item(i).getFirstChild().getNodeValue();
	
	
	case "Line2":
    Line2_XML[k] = Addr.item(i).getFirstChild().getNodeValue();
   	
    	
	case "City":
	City_XML[k] = Addr.item(i).getFirstChild().getNodeValue();


	case "State":
	State_XML[k] = Addr.item(i).getFirstChild().getNodeValue();
	

	case "Country":
	Country_XML[k] = Addr.item(i).getFirstChild().getNodeValue();
	
	
	case "Postcode":
	Postcode_XML[k] = Addr.item(i).getFirstChild().getNodeValue();
	
	
		        	
	}   	
		
}catch(Exception e){}
}*/
