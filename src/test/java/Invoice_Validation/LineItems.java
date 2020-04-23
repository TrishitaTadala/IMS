package Invoice_Validation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
* This Class reads the rate rows from the Pricing Xml
* @author smruti.ingawale
*
*/
public class LineItems {

                static HashMap<String, HashMap> priceRows = new HashMap();
                static HashMap<String, String> priceCols = new HashMap();
                static int rowsPerChargeFile = 50;
                static int rateRowCount = 0;
                static List<String> duplicateRows;
                
                public static int getRateRowCount() {
                                return rateRowCount;
                }
                
                public static List<String> getDuplicateRows() {
                                return duplicateRows;
                }
                public static void main(String[] args)throws Exception {
                                readXML(".xml");
                }

                /**
                * Reading the XMl through This method
                * @param filename
                * @throws Exception
                */
                public static void readXML(String filename) throws Exception{
                                
                                duplicateRows = new ArrayList<String>();
                                int rowCount = 0;
                                int noOfXML = 0;
                               // String xmlfilepath = System.getProperty("user.dir")+"/Pricing/"+filename;
                                String xmlfilepath = "C:\\Users\\Trishita.Tadala\\Desktop\\IMS\\XMLs\\119618_946713_1588207_20200229.xml";
                                File xmlinputFile = new File(xmlfilepath);
                                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                Document doc = dBuilder.parse(xmlinputFile);
                                doc.getDocumentElement().normalize();

                                //Element gxProduct = null;

                                XPath xpath = XPathFactory.newInstance().newXPath(); 
//                            XPathExpression exprP2P = xpath.compile("//Root/Package/Product_To_Product/Product");
//                            NodeList nListP2P = (NodeList)exprP2P.evaluate(doc, XPathConstants.NODESET);
//                            Node nNode = nListP2P.item(0);
//                            Element productName = (Element) nNode;
//                            XPathExpression exprP2PInner = xpath.compile("Product_To_Product/Product");
//                            NodeList nListP2PInner = (NodeList)exprP2PInner.evaluate(productName, XPathConstants.NODESET);
//                            for (int i = 0; i < nListP2PInner.getLength(); i++) {
//                                            Node gxProductNode = nListP2PInner.item(i);
//                                            Element psElement = (Element) gxProductNode;
//                                            String psName = psElement.getElementsByTagName("Name").item(0).getTextContent();
//                                            if (psName.equalsIgnoreCase("GX")) {
//                                                            gxProduct = psElement;
//                                            }
//                            }
                                Element chargeTable = null;

                                XPathExpression exprP2CInner = xpath.compile("//BillingCharges");
                                NodeList chargeTypes = (NodeList)exprP2CInner.evaluate(doc, XPathConstants.NODESET);
                                for (int i = 0; i < chargeTypes.getLength(); i++) {
                                                Node chargeNode = chargeTypes.item(i);
                                                Element psElement = (Element) chargeNode;
                                                String psName = psElement.getElementsByTagName("ProductType").item(0).getTextContent();
                                                List<String> psNames = new ArrayList<>();
                                                psNames.add("Out-Term Recurring");
                                                /*psNames.add("GX Subscription MRC");
                                                psNames.add("Additional SVN MRC");
                                                psNames.add("GX MMP Transit MRC");
                                                psNames.add("National Numbers MRC");
                                                psNames.add("Additional SVN MRC");*/
                                                if (psNames.contains(psName)) {
                                                                chargeTable = psElement;
                                                }
                                }
                                NodeList chargeRows = chargeTable.getElementsByTagName("Rate_Recurring");
                                rowCount = chargeRows.getLength();

                                if (rowCount < rowsPerChargeFile) {
                                                noOfXML = 1;
                                }
                                else {
                                                if (rowCount%rowsPerChargeFile == 0) {
                                                                noOfXML = (rowCount/rowsPerChargeFile);
                                                }
                                                else {
                                                                noOfXML = (rowCount/rowsPerChargeFile)+1;
                                                }
                                }
                                generateXMLChargeRows(noOfXML,chargeRows);
                                priceRows = getChargeDetails(noOfXML);
                                chargeRows = null;

                }
/**
* Getting the Charge details
* @param fileCount
* @return
* @throws Exception
*/
                public static HashMap<String, HashMap> getChargeDetails(int fileCount) throws Exception{

                                HashMap<String, HashMap> rateTable = new HashMap<>();
                                HashMap<String, String> rateTableRows;
                                XPath xpath = XPathFactory.newInstance().newXPath();
                                
                                for (int k = 1; k <= fileCount; k++) {

                                                String filename = "Charges~.xml".replace("~", Integer.toString(k));
                                                String chargesFolderPath = System.getProperty("user.dir")+"/UPC XMLs/Charges XMLs";
                                                if (!Files.isDirectory(Paths.get(chargesFolderPath))) {   
                                                                File file = new File(chargesFolderPath);
                                                                file.mkdir();
                                                }
                                                String xmlfilepath = chargesFolderPath+"/"+filename;
                                                File xmlinputFile = new File(xmlfilepath);
                                                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                                Document doc = dBuilder.parse(xmlinputFile);
                                                doc.getDocumentElement().normalize();

                                                XPathExpression exprRR = xpath.compile("//Root/Rate_Recurring");
                                                ArrayList<String> Actualvalue= new ArrayList<String>();
                                                ArrayList<String> Trimmedvalue= new ArrayList<String>();
                                                int count = 0;
                                                NodeList chargeRows = (NodeList)exprRR.evaluate(doc, XPathConstants.NODESET);
                                                for (int i = 0; i < chargeRows.getLength(); i++) {
                                                                rateRowCount++;
                                                                rateTableRows = new HashMap<>();
                                                                Element chargeAttributes = (Element)chargeRows.item(i);

                                                                String subPlanIdValue = chargeAttributes.getElementsByTagName("Subscription_Plan_ID").item(0).getFirstChild().getTextContent();

                                                                XPathExpression exprAttributeName = xpath.compile("Distribution_Channel/Description");
                                                                Node distChannel  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                String distributionChannelValue = distChannel.getTextContent();

                                                                XPathExpression exprChargeType = xpath.compile("Charge_Type/Description");
                                                                Node chargeType  = ((NodeList)exprChargeType.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                String chargeTypeValue = chargeType.getTextContent();


                                                                String rowKey = subPlanIdValue+"-"+distributionChannelValue+"-"+chargeTypeValue;
                                                                if (rateTable.containsKey(rowKey)) {
                                                                                if (!duplicateRows.contains(rowKey)) {
                                                                                                duplicateRows.add(rowKey);
                                                                                }                                                              
                                                                }
                                                                rateTable.put(rowKey, rateTableRows);

                                                                String rateValue = chargeAttributes.getElementsByTagName("Rate").item(0).getTextContent();
                                                                rateTableRows.put("MRC*", rateValue);

                                                                String startDateValue = chargeAttributes.getElementsByTagName("Start_Date").item(0).getTextContent();
                                                                rateTableRows.put("Start Date*", startDateValue);

                                                                Element packageGroup = (Element) chargeAttributes.getElementsByTagName("Package_Group").item(0);
                                                                if (packageGroup != null) {
                                                                                String packageGroupValue = packageGroup.getTextContent();
                                                                                rateTableRows.put("Package Group", packageGroupValue);
                                                                }
                                                                
                                                                Element endDate = (Element) chargeAttributes.getElementsByTagName("End_Date").item(0);
                                                                if (endDate != null) {
                                                                                String endDateValue = endDate.getTextContent();
                                                                                System.out.println("End Date: "+endDateValue);
                                                                                rateTableRows.put("End Date", endDateValue);
                                                                }
                                                                
                                                                Element packagePriorityTable = (Element) chargeAttributes.getElementsByTagName("Package_Priority_Table").item(0);
                                                                if (packagePriorityTable != null) {
                                                                                String packagePriorityTableValue = packagePriorityTable.getTextContent();
                                                                                rateTableRows.put("Package Priority Table", packagePriorityTableValue);
                                                                }
                                                                
                                                                Element availableEndDate = (Element) chargeAttributes.getElementsByTagName("Available_End_Date").item(0);
                                                                if (availableEndDate != null) {
                                                                                String availableEndDateValue = availableEndDate.getTextContent();
                                                                                rateTableRows.put("Available End Date", availableEndDateValue);
                                                                }
if(chargeAttributes.getElementsByTagName("Subscription_Plan_Name").item(0)!=null){
                                                                String subPlanNameValue = chargeAttributes.getElementsByTagName("Subscription_Plan_Name").item(0).getTextContent();
                                                                rateTableRows.put("Subscription Plan Name*", subPlanNameValue);
                                                                Actualvalue.add(subPlanNameValue);
                                                                Trimmedvalue.add(subPlanNameValue.trim());
                                                                count = Actualvalue.size();                           
}
                                                                exprAttributeName = xpath.compile("Subscription_Plan_Type/Name");
                                                                Node subPlanType  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(subPlanType!=null){
                                                                String subPlanTypeValue = subPlanType.getTextContent();
                                                                rateTableRows.put("Subscription Plan Type*", subPlanTypeValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Level/Name");
                                                                Node level  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(level!=null){
                                                                String levelValue = level.getTextContent();
                                                                rateTableRows.put("Level*", levelValue);
                                                                }
                                                                exprAttributeName = xpath.compile("MIR_FWD/Name");
                                                                Node mirFwd  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(mirFwd!=null){
                                                                String mirFwdValue = mirFwd.getTextContent();
                                                                rateTableRows.put("MIR FWD (download) kbps*", mirFwdValue);
                                                                }
                                                                exprAttributeName = xpath.compile("MIR_RTN/Name");
                                                                Node mirRtn  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(mirRtn!=null){
                                                                String mirRtnValue = mirRtn.getTextContent();
                                                                rateTableRows.put("MIR RTN (upload) kbps*", mirRtnValue);
                                                                }
                                                                exprAttributeName = xpath.compile("CIR_FWD/Name");
                                                                Node cirFwd  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(cirFwd!=null){
                                                                String cirFwdValue = cirFwd.getTextContent();
                                                                rateTableRows.put("CIR FWD (download) kbps*", cirFwdValue);
                                                                }
                                                                exprAttributeName = xpath.compile("CIR_RTN/Name");
                                                                Node cirRtn  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(cirRtn!=null){
                                                                String cirRtnValue = cirRtn.getTextContent();
                                                                rateTableRows.put("CIR RTN (upload) kbps*", cirRtnValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Regionality/Name");
                                                                Node regionality  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(regionality!=null){
                                                                String regionalityValue = regionality.getTextContent();
                                                                rateTableRows.put("Regionality*", regionalityValue);
                                                                }
                                                                exprAttributeName = xpath.compile("SLA/Name");
                                                                Node sla  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(sla!=null){
                                                                String slaValue = sla.getTextContent();
                                                                rateTableRows.put("SLA%*", slaValue);
                                                                }
                                                                exprAttributeName = xpath.compile("MIR_is_to_CIR_Ratio/Name");
                                                                Node mirCirRatio  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if (mirCirRatio != null) {
                                                                                String mirCirRatioValue = mirCirRatio.getTextContent();
                                                                                rateTableRows.put("MIR:CIR Ratio", mirCirRatioValue);
                                                                }

                                                                exprAttributeName = xpath.compile("Terminal_Group_or_Class/Name");
                                                                Node terminalGrpClass  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                if(terminalGrpClass!=null){
                                                                String terminalGroupValue = terminalGrpClass.getTextContent();
                                                                rateTableRows.put("Terminal Group/Class*", terminalGroupValue);
                                                }
                                                                exprAttributeName = xpath.compile("Minimum_Subscription_Period/Name");
                                                                Node minSubPeriod  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                if(minSubPeriod!=null){
                                                                String minSubPeriodValue = minSubPeriod.getTextContent();
                                                                rateTableRows.put("Minimum Subscription Period*", minSubPeriodValue);

                                                }
                                                                exprAttributeName = xpath.compile("Subscription_Period_Units/Name");
                                                                Node subPeriodUnit  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                if(subPeriodUnit!=null){
                                                                String subPeriodUnitsValue = subPeriodUnit.getTextContent();
                                                                rateTableRows.put("Subscription Period Units*", subPeriodUnitsValue);
                                                }
                                                                Element ratePlanPriority = (Element) chargeAttributes.getElementsByTagName("Rate_Plan_Priority").item(0);
                                                                if (ratePlanPriority != null) {
                                                                                String ratePlanPriorityValue = ratePlanPriority.getTextContent();
                                                                                rateTableRows.put("Priority", ratePlanPriorityValue);
                                                                }

                                                                Element volumeAllowance = (Element) chargeAttributes.getElementsByTagName("Volume_Allowance").item(0);
                                                                if (volumeAllowance != null) {
                                                                                exprAttributeName = xpath.compile("Volume_Allowance/Name");
                                                                                Node volumeAllowanceNode  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                                String volumeAllowanceValue = volumeAllowanceNode.getTextContent();
                                                                                rateTableRows.put("Volume Allowance (GB)*", volumeAllowanceValue);
                                                                }
                                                                Element volumeAllowanceCarryOver = (Element) chargeAttributes.getElementsByTagName("Volume_Allowance_Carry_Over").item(0);
                                                                if (volumeAllowanceCarryOver != null) {
                                                                                exprAttributeName = xpath.compile("Volume_Allowance_Carry_Over/Name");
                                                                                Node vollumeAllowanceCONode  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                                String volumeAllowanceCOValue = vollumeAllowanceCONode.getTextContent();
                                                                                rateTableRows.put("Volume Allowance Carry Over (GB)*", volumeAllowanceCOValue);
                                                                }


                                                                exprAttributeName = xpath.compile("Market_BU/Name");
                                                                Node marketBU  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(marketBU!=null) {
                                                                                String marketBUValue = marketBU.getTextContent();
                                                                                rateTableRows.put("Market/BU*", marketBUValue);
                                                                }

                                                                exprAttributeName = xpath.compile("Bandwidth/Name");
                                                                Node bandwidth  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(bandwidth!=null) {
                                                                                String bandwidthValue = bandwidth.getTextContent();
                                                                                rateTableRows.put("Bandwidth (Mbps)", bandwidthValue);
                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("MMP_Transit_Route/Name");
                                                                Node transitRoute  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(transitRoute!=null) {
                                                                                String transitRouteValue = transitRoute.getTextContent();
                                                                                rateTableRows.put("MMP Transit Route", transitRouteValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Activation_Profile_ID/Name");
                                                                Node actProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                if(actProfileId!=null){
                                                                String activationProfileIdValue = actProfileId.getTextContent();
                                                                rateTableRows.put("Activation Profile ID*", activationProfileIdValue);
                                                }
                                                                exprAttributeName = xpath.compile("Temporary_Deactivation_Profile_ID/Name");
                                                                Node tempDeactProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                if(tempDeactProfileId!=null){
                                                                String tempDeactivationProfileIdValue = tempDeactProfileId.getTextContent();
                                                                rateTableRows.put("Temporary Deactivation Profile ID*", tempDeactivationProfileIdValue);
                                                }

                                                                exprAttributeName = xpath.compile("Penalty_Profile_ID/Name");
                                                                Node penProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(penProfileId!=null) {
                                                                                String penaltyProfileIdValue = penProfileId.getTextContent();
                                                                                rateTableRows.put("Penalty Profile ID*", penaltyProfileIdValue);                                               

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Early_Termination_Profile_ID/Name");
                                                                Node earlyTermProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(earlyTermProfileId!=null) {
                                                                                String earlyTermProfileIdValue = earlyTermProfileId.getTextContent();
                                                                                rateTableRows.put("Early Termination Profile ID*", earlyTermProfileIdValue);                                                

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Test_Profile_ID/Name");
                                                                Node testProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(testProfileId!=null) {
                                                                                String testProfileIdValue = testProfileId.getTextContent();
                                                                                rateTableRows.put("Test Profile ID*", testProfileIdValue);                                             

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Trial_Profile_ID/Name");
                                                                Node trialProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(trialProfileId!=null) {
                                                                                String trialProfileIdValue = trialProfileId.getTextContent();
                                                                                rateTableRows.put("Trial Profile ID*", trialProfileIdValue);                                            

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("UD_Profile_ID/Name");
                                                                Node udProfileId  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(udProfileId!=null) {
                                                                                String earlyTermProfileIdValue = udProfileId.getTextContent();
                                                                                rateTableRows.put("UD Profile ID*", earlyTermProfileIdValue);                                  

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("SSPP_Auto_Creation/Name");
                                                                Node ssppAutoCreation  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(ssppAutoCreation!=null) {
                                                                                String ssppAutoCreationValue = ssppAutoCreation.getTextContent();
                                                                                rateTableRows.put("SSPP Auto Creation*", ssppAutoCreationValue);                                       

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("SSPP_Technical_Variant/Name");
                                                                Node ssppTechVariant  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(ssppTechVariant!=null) {
                                                                                String ssppTechVariantValue = ssppTechVariant.getTextContent();
                                                                                rateTableRows.put("SSPP Technical Variant*", ssppTechVariantValue);                                   

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("High_Capacity_Plan/Name");
                                                                Node highCapacityPlan  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(highCapacityPlan!=null) {
                                                                                String highCapacityPlanValue = highCapacityPlan.getTextContent();
                                                                                rateTableRows.put("High Capacity Plan*", highCapacityPlanValue);                                          

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Precedence/Name");
                                                                Node precedence  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(precedence!=null) {
                                                                                String precedenceValue = precedence.getTextContent();
                                                                                rateTableRows.put("Precedence*", precedenceValue);                                  

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Grandfathering_Type/Name");
                                                                Node grandfatheringType  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(grandfatheringType!=null) {
                                                                                String grandfatheringTypeValue = grandfatheringType.getTextContent();
                                                                                rateTableRows.put("Grandfathering Type*", grandfatheringTypeValue);                                 

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Grace_Period/Name");
                                                                Node gracePeriod  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(gracePeriod!=null) {
                                                                                String gracePeriodValue = gracePeriod.getTextContent();
                                                                                rateTableRows.put("Grace Period (days)*", gracePeriodValue);                                  

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Freeze_Period/Name");
                                                                Node freezePeriod  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(freezePeriod!=null) {
                                                                                String freezePeriodValue = freezePeriod.getTextContent();
                                                                                rateTableRows.put("Freeze Period (days)*", freezePeriodValue);                                

                                                                }
                                                                
                                                                exprAttributeName = xpath.compile("Overage_Volume/Name");
                                                                Node overageVol  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(overageVol!=null){
                                                                String overageVolValue = overageVol.getTextContent();
                                                                rateTableRows.put("Overage Volume*", overageVolValue);
                                                                }
                                                                exprAttributeName = xpath.compile("FAP_Variant/Name");
                                                                Node fapVariant  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(fapVariant!=null){
                                                                String fapVariantValue = fapVariant.getTextContent();
                                                                rateTableRows.put("FAP Variant*", fapVariantValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Maritime/Name");
                                                                Node maritime  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(maritime!=null){
                                                                String maritimeValue = maritime.getTextContent();
                                                                rateTableRows.put("Maritime*", maritimeValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Enterprise/Name");
                                                                Node enterprise  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(enterprise!=null){
                                                                String enterpriseValue = enterprise.getTextContent();
                                                                rateTableRows.put("Enterprise*", enterpriseValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Aviation/Name");
                                                                Node aviation  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(aviation!=null){
                                                                String aviationValue = aviation.getTextContent();
                                                                rateTableRows.put("Aviation*", aviationValue);
                                                                }
                                                                exprAttributeName = xpath.compile("Global_Government/Name");
                                                                Node globalGovt  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(globalGovt!=null){
                                                                String globalGovtValue = globalGovt.getTextContent();
                                                                rateTableRows.put("Global Government*", globalGovtValue);
                                                                }
                                                                exprAttributeName = xpath.compile("US_Government/Name");
                                                                Node usGovt  = ((NodeList)exprAttributeName.evaluate(chargeAttributes, XPathConstants.NODESET)).item(0);
                                                                if(usGovt!=null){
                                                                String usGovtValue = usGovt.getTextContent();
                                                                rateTableRows.put("US Government*", usGovtValue);
                                                                }
                                                                }
                                                for(int j=0;j<count;j++)
                                                {
                                                                if(Actualvalue.get(j)!= Trimmedvalue.get(j))
                                                                {
                                                                System.out.println("XML Plan with extra space : "+ Actualvalue.get(j));
                                                                }
                                                }
                                }

                                return rateTable;
                }

                public static void generateXMLChargeRows(int fileCount, NodeList chargeRows) throws Exception{

                                int startRow=0;
                                int endRow=0;

                                for (int k = 1; k <= fileCount; k++) {

                                                if (k == 1) {
                                                                startRow = 0;
                                                                endRow = rowsPerChargeFile;
                                                }
                                                else if (k == fileCount) {
                                                                startRow = ((k-1)*rowsPerChargeFile)+1;
                                                                if (chargeRows.getLength() % rowsPerChargeFile == 0) {endRow = startRow + (rowsPerChargeFile-2);}
                                                                else {endRow = (startRow + chargeRows.getLength()% rowsPerChargeFile)-1;}
                                                }
                                                else {
                                                                startRow = ((k-1)*rowsPerChargeFile)+1;
                                                                endRow = startRow + (rowsPerChargeFile - 1);
                                                }                                              
                                                if (chargeRows.getLength() < rowsPerChargeFile) {
                                                                endRow = chargeRows.getLength();
                                                }

                                                Document newXmlDocument = DocumentBuilderFactory.newInstance()
                                                                                .newDocumentBuilder().newDocument();
                                                Element root = newXmlDocument.createElement("Root");
                                                root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                                                newXmlDocument.appendChild(root);

                                                for (int i = startRow; i <= endRow; i++) {
                                                                if (i == chargeRows.getLength()) {
                                                                                break;
                                                                }
                                                                Node node = chargeRows.item(i);
                                                                Node copyNode = newXmlDocument.importNode(node, true);
                                                                root.appendChild(copyNode);
                                                }
                                                String chargesFolderPath = System.getProperty("user.dir")+"/UPC XMLs/Charges XMLs";
                                                if (!Files.isDirectory(Paths.get(chargesFolderPath))) {   
                                                                File file = new File(chargesFolderPath);
                                                                file.mkdir();
                                                }
                                                String chargesXML = chargesFolderPath+"/"+"Charges~.xml".replace("~", Integer.toString(k));
                                                Source source = new DOMSource(newXmlDocument);
                                                File file = new File(chargesXML);
                                                Result result = new StreamResult(file);
                                                Transformer xformer = TransformerFactory.newInstance().newTransformer();
                                                xformer.transform(source, result);
                                                System.gc();
                                }
                }
                
                public static HashMap<String, HashMap> getXMLData(String filename) throws Exception{
                                
                                readXML(filename);
                                return priceRows;
                }
}
