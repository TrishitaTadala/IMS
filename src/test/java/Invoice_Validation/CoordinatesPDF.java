package Invoice_Validation;

import java.io.IOException;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.pdfbox.text.TextPosition;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;


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

/*************Line Items child Nodes in the XML**************************

//javax.xml.xpath.XPath xPath =  XPathFactory.newInstance().newXPath();
String expression ="//ServiceSummary/ProductDetails/Product";
NodeList list = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
NodeList nodes = (NodeList) list;
m = nodes.getLength();
for (int i = 0; i < nodes.getLength(); i++) {
	try{
	Product[k][i]= (nodes.item(i).getFirstChild().getNodeValue());
}catch(Exception e){}
}

String total ="//ServiceSummary/ProductDetails/Total";
NodeList listtotal = (NodeList) xPath.compile(total).evaluate(doc, XPathConstants.NODESET);
NodeList nodestotal = (NodeList) listtotal;
m = nodestotal.getLength();
for (y = 0; y < nodestotal.getLength(); y++) {
	try{
    	Total[y][k]= (nodestotal.item(y).getFirstChild().getNodeValue());
    	Totals[p]=Total[y][k];
    	
    			p= p +1;
    			//System.out.println(Total[y][k]);
    }catch(Exception e){}
}*/

/*
 XPathExpression exprFees = xpath.compile("//Fees/ProductDetails");
		        
		        
		        //ArrayList<String> Actualvalue= new ArrayList<String>();
		        //ArrayList<String> Trimmedvalue= new ArrayList<String>();
		        //int count = 0;
		        NodeList ProductRows = (NodeList)exprRR.evaluate(doc1, XPathConstants.NODESET);
		        for (int i = 0; i < ProductRows.getLength(); i++) {
		            rateRowCount++;
		            
		            Element ProductDetails = (Element)ProductRows.item(i);

		            String ProductGroupValue = ProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
		            String ProductValue = ProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();
		            //String ProductTypeValue = ProductDetails.getElementsByTagName("ProductType").item(0).getFirstChild().getTextContent();
		            String ProductUsageValue = ProductDetails.getElementsByTagName("Usage").item(0).getFirstChild().getTextContent();
		            String ProductTotalValue = ProductDetails.getElementsByTagName("Total").item(0).getFirstChild().getTextContent();
		           
		            ProductTableRows.put("ServiceGroup"+i+" ", ProductGroupValue);
		            ProductTableRows.put(""+i+" ", ProductValue);
		            //ProductTableRows.put("ProductType"+i+" ", ProductTypeValue);
		            ProductTableRows.put("Airtime"+i+" ", ProductUsageValue);
		            ProductTableRows.put("Total"+i+" ", ProductTotalValue);
		            
		           // ProductTableRows= ProductTableRows(0)+ProductTableRows;
		    		
		            
		            }	
		        System.out.println("ServiceSummary/ProductDetails");    
		Iterator<String> XMLkeySetIterator = ProductTableRows.keySet().iterator();
						
						while(XMLkeySetIterator.hasNext()) {
		   	
		   	String key1 = XMLkeySetIterator.next();
		   	System.out.println(key1 + ProductTableRows.get(key1)); 
		   		}
  *
  *
  *
  */

/*

try {
   // DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //DocumentBuilder db = dbf.newDocumentBuilder();
    //Document doc = db.parse("C://logs//XML.xml");
	NodeList nodeList = (NodeList)exprRR.evaluate(doc1, XPathConstants.NODESET);
    //NodeList nodeList = exprRR.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
        Node textChild = nodeList.item(i);
        NodeList childNodes = textChild.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node grantChild = childNodes.item(j);
           // NodeList grantChildNodes = grantChild.getChildNodes();
          /*  for (int k = 0; k < grantChildNodes.getLength(); k++) {
               //if(!StrUtl.isEmptyTrimmed( grantChildNodes.item(k).getTextContent() ) ) {
                    
                            map.put(grantChildNodes.item(k).getNodeName() , grantChildNodes.item(k).getTextContent());
                            System.out.println(map);
                //}
            }*/
           // map.put(childNodes.item(j).getNodeName() , childNodes.item(j).getTextContent());
            //System.out.println(map);
       // }
    //}
// }catch (Exception e){
//         e.printStackTrace();
// }

// return map;
//}
  

/*	Collection<String> values = FeeSummaryTableRowsMap.values();
String[] arrayValues = values.toArray( new String[values.size()] );

for(String value : arrayValues){
	
	XMLtext2 += value;   	
		}
System.out.println(XMLtext2);  */


