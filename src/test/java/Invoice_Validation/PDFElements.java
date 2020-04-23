package Invoice_Validation;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class PDFElements {
    private static Hashtable<Integer,ArrayList<Integer>> D1 = new Hashtable<Integer, ArrayList<Integer>>();
    private static Hashtable<Integer,ArrayList<Integer>> D2 = new Hashtable<Integer,ArrayList<Integer>>();
     static Integer ScreenID;
     static ArrayList<Integer> StringID;
     static ArrayList<Integer> WidgetID;
     static Integer WidgetID2;
  
     
     public static void main(String argv[]) {

   // try {

    File fXmlFile = new File("C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\10001327_954226_1590428_20200430.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = null;
    try {
        dBuilder = dbFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
    Document doc = null;
    try {
        doc = dBuilder.parse(fXmlFile);
    } catch (SAXException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


    doc.getDocumentElement().normalize();
    
    javax.xml.xpath.XPath xPath =  XPathFactory.newInstance().newXPath();
     try {
    String path ="//Bill_To_Party";
    NodeList nodesetpath = (NodeList) xPath.compile(path).evaluate(doc, XPathConstants.NODESET);
   
    
    printNote(nodesetpath);
    
     }catch(Exception e){}
    
    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    //if(taxnode.hasChildNodes()){

        

    //}


    }
public static void printNote(NodeList nodeList) {


        for (int count = 0; count < nodeList.getLength(); count++) {

        Node tempNode = nodeList.item(count);


        // make sure it's element node.
        if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

            // get node name and value
            System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
            System.out.println("Node Value =" + tempNode.getTextContent());

            if (tempNode.hasAttributes()) {

                // get attributes names and values
                NamedNodeMap nodeMap = tempNode.getAttributes();

                for (int i = 0; i < nodeMap.getLength(); i++) {

                    Node node = nodeMap.item(i);
                    System.out.println("attr name : " + node.getNodeName());
                    System.out.println("attr value : " + node.getNodeValue());


            }

        }

        if (tempNode.hasChildNodes()) {

            // loop again if has child nodes
            printNote(tempNode.getChildNodes());

        }

        System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

    }

    }

  }


}
