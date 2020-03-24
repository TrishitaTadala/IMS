//All the variables should be declared
//======================================================
// Class      : inmarsatXML
// Description: Read the Single View XML for invoice Validation
//              the Single View XML from the InmarsatPDFExcel.xlsx
// ======================================================
// Changes----------------------------------------------
// Date         Test Analyst        Change
// 26/12/19     Trishita Tadala     Written
//======================================================

//package inmarsatXML;
package Invoice_Validation;
//import org.apache.xmlbeans.impl.common.XPath;
//import org.w3c.dom.*;
import org.xml.sax.SAXException;

//import ReadExcelFile.ReadExcelFile;

import javax.xml.parsers.*;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class BillToPartyXML {
	
	public static String[][][][] Address = new String[50][50][50][50];
	public static String[][] Total = new String[50][50];
	public static String[] Totals = new String[50];
	public static int y=0;
	public static int m=0;
	public static int p=0;
	public static void billtopartyXML() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		
		
			for(int k =0;k<ReadExcelFile.i;k++){	
			System.out.println(ReadExcelFile.XMLFILENAME[k]);
			File fXmlFile = new File(ReadExcelFile.XMLFILENAME[k]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			// normalize text representation
			doc.getDocumentElement().normalize();
			//System.out.println("----------------------------");
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			//System.out.println("----------------------------");
			
			//Front Page Invoice Details tags from the Single View XML Jan-8-2020
			
			
			//Line Items child Nodes in the XML
			
			
	        
	       
	        }
			}
	}

