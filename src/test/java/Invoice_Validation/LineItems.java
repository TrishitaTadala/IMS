package Invoice_Validation;


import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.File;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class LineItems {
    private LinkedHashMap<String, String> differencesMap;
    private LinkedHashMap<String, String> extraMap;

    private void main(LinkedHashMap<String, String> map1, LinkedHashMap<String, String> map2) {
        this.differencesMap = new LinkedHashMap<>();
        this.extraMap = new LinkedHashMap<>();

        Set<Map.Entry<String, String>> set1 = ((LinkedHashMap<String, String>) map1.clone()).entrySet();
        Set<Map.Entry<String, String>> set2 = ((LinkedHashMap<String, String>) map2.clone()).entrySet();

        set1.removeAll(set2);
        compare(set1, map2);

        set1 = map1.entrySet();

        set2.removeAll(set1);
        compare(set2, map1);
    }

    private LinkedHashMap<String, String> getDifferencesMap() {
        return differencesMap;
    }

    private LinkedHashMap<String, String> getExtraMap() {
        return extraMap;
    }

    private void compare(Set<Map.Entry<String, String>> set, LinkedHashMap<String, String> map) {
        for (Map.Entry<String, String> entry : set) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (map.containsKey(key) || map.containsValue(value)) {
                differencesMap.put(key, value);
            } else {
                extraMap.put(key, value);
            }
        }
    }
    
    public static String ServiceSummaryInfo() throws Exception {
		//LinkedHashMap<String,String> PDFHashMap  = new LinkedHashMap();
    	String filename = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\Invoice\\INVOICE_1386249_986021_201904.pdf";
		PdfReader reader = new PdfReader(filename); 
		String PDFtext;
		Rectangle serviceSummary = new Rectangle(0, 600, 600, 478);
        RenderFilter filter = new RegionTextRenderFilter(serviceSummary);
        TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
        PDFtext = (PdfTextExtractor.getTextFromPage(reader, 2, strategy));
        return PDFtext;
	}
    public static LinkedHashMap<String, String> readXML() throws Exception{
        
        // ArrayList duplicateRows = new ArrayList<String>();
          //int rateRowCount = 0;
          //int noOfXML = 0;
          //LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
          //HashMap<String, HashMap> rateTable = new HashMap<>();
          
          LinkedHashMap<String, String> ProductTableRows;
          ProductTableRows = new LinkedHashMap<>();
          //ProductTableRows.clear();
          String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\117976_986021_1386249_20190430.xml";
          File xmlinputFile = new File(xmlfilepath);
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          
          Document doc1 = dBuilder.parse(xmlinputFile);
          doc1.getDocumentElement().normalize();
          XPath xpath = XPathFactory.newInstance().newXPath();
          
          XPathExpression exprRR = xpath.compile("//ServiceSummary/ProductDetails");
          
          
          //ArrayList<String> Actualvalue= new ArrayList<String>();
          //ArrayList<String> Trimmedvalue= new ArrayList<String>();
          //int count = 0;
          NodeList ProductRows = (NodeList)exprRR.evaluate(doc1, XPathConstants.NODESET);
          for (int i = 0; i < ProductRows.getLength(); i++) {
             // rateRowCount++;
              
              Element ProductDetails = (Element)ProductRows.item(i);

              String ProductGroupValue = ProductDetails.getElementsByTagName("ProductGroup").item(0).getFirstChild().getTextContent();
              String ProductValue = ProductDetails.getElementsByTagName("Product").item(0).getFirstChild().getTextContent();
              String ProductTotalValue = ProductDetails.getElementsByTagName("Total").item(0).getFirstChild().getTextContent();
             
              ProductTableRows.put("ProductGroup"+i+" ", ProductGroupValue);
              ProductTableRows.put("Product"+i+" ", ProductValue);
              ProductTableRows.put("Total"+i+" ", ProductTotalValue);
              
          }
              
             // ProductTableRows= ProductTableRows(0)+ProductTableRows;
      		
            return ProductTableRows;  
              }	
          
    public static void main(String[] args) {
       LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
      
       /*map1.put("A", "a");
        map1.put("B", "b");
        map1.put("C", "c");*/
        LinkedHashMap<String, String> map2 = new LinkedHashMap<>();
        /*map2.put("A", "a");
        map2.put("C", "c");
        map2.put("B", "r");
        map2.put("Z", "z");*/
        
        
        try {
    	map1.put ("Servicesummary",ServiceSummaryInfo());
    	map2 = readXML() ;
        }catch(Exception e){} 
        
        

        LineItems mapCompare = new LineItems();
       mapCompare.main(map1,map2);
        //mapCompare.main(map2,map1);

        System.out.println("diff: " + mapCompare.getDifferencesMap());
        System.out.println("extra: " + mapCompare.getExtraMap());
    }
}