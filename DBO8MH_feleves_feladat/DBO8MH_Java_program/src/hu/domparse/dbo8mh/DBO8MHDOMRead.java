package hu.domparse.dbo8mh;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBO8MHDOMRead {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/hu/domparse/dbo8mh/DBO8MH_XML.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            File outputFile = new File("src/hu/domparse/dbo8mh/read_log.txt");
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                
                String rootNodeName = "Gyökér elem: " + doc.getDocumentElement().getNodeName();
                printAndLog(rootNodeName, writer);

                //  1. GYÁRTÓK BEOLVASÁSA (ÚJ) 
                printAndLog("\n Gyártók (Származási ország) ", writer);
                NodeList manufacturerList = doc.getElementsByTagName("Gyarto");
                for (int i = 0; i < manufacturerList.getLength(); i++) {
                    Node nNode = manufacturerList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String nev = elem.getAttribute("gyartoNev");
                        String orszag = elem.getElementsByTagName("Orszag").item(0).getTextContent();
                        printAndLog("Gyártó: " + nev + " (" + orszag + ")", writer);
                    }
                }

                //  2. TULAJDONOSOK BEOLVASÁSA 
                printAndLog("\n Tulajdonosok ", writer);
                NodeList ownerList = doc.getElementsByTagName("Tulajdonos");
                for (int i = 0; i < ownerList.getLength(); i++) {
                    Node nNode = ownerList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String uid = elem.getAttribute("adoszam");
                        
                        // ÖSSZETETT NÉV KEZELÉSE
                        Element nevElem = (Element) elem.getElementsByTagName("Nev").item(0);
                        String vnev = nevElem.getElementsByTagName("Vezeteknev").item(0).getTextContent();
                        String knev = nevElem.getElementsByTagName("Keresztnev").item(0).getTextContent();
                        String fullName = vnev + " " + knev;

                        printAndLog("\nTulajdonos [" + uid + "]", writer);
                        printAndLog("  Név: " + fullName, writer);

                        // TÖBB TELEFONSZÁM KEZELÉSE
                        NodeList telList = elem.getElementsByTagName("Telefonszam");
                        for (int k = 0; k < telList.getLength(); k++) {
                            printAndLog("  Tel (" + (k + 1) + "): " + telList.item(k).getTextContent(), writer);
                        }

                        // JÁRMŰVEK ÉS GYÁRTÓ REF
                        NodeList carList = elem.getElementsByTagName("Jarmu");
                        for (int j = 0; j < carList.getLength(); j++) {
                            Element carElem = (Element) carList.item(j);
                            String rendszam = carElem.getAttribute("rendszam");
                            String tipus = carElem.getElementsByTagName("Tipus").item(0).getTextContent();
                            String gyartoRef = carElem.getElementsByTagName("GyartoRef").item(0).getTextContent(); // ÚJ
                            
                            printAndLog("Jármű: " + gyartoRef + " " + tipus + " (" + rendszam + ")", writer);
                            
                            Element forgElem = (Element) carElem.getElementsByTagName("Forgalmi").item(0);
                            String okmany = forgElem.getAttribute("okmanySz");
                            String kiallitva = forgElem.getElementsByTagName("Kiallitva").item(0).getTextContent();
                            printAndLog("[Forgalmi: " + okmany + ", Kiállítva: " + kiallitva + "]", writer);
                        }
                    }
                }

                //  3. SZERVIZEK 
                printAndLog("\n Szervizek ", writer);
                NodeList serviceList = doc.getElementsByTagName("Szerviz");
                for (int i = 0; i < serviceList.getLength(); i++) {
                    Node nNode = serviceList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String sid = elem.getAttribute("szervizID");
                        String snev = elem.getElementsByTagName("Nev").item(0).getTextContent();
                        String scim = elem.getElementsByTagName("Cim").item(0).getTextContent();
                        
                        printAndLog("Szerviz [" + sid + "]: " + snev + " (" + scim + ")", writer);
                    }
                }

                //  4. SZERVIZELÉSEK 
                printAndLog("\n Szervizelések ", writer);
                NodeList logList = doc.getElementsByTagName("Szervizeles");
                for (int i = 0; i < logList.getLength(); i++) {
                    Node nNode = logList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String logID = elem.getAttribute("szelID");
                        String datum = elem.getElementsByTagName("Datum").item(0).getTextContent();
                        String leiras = elem.getElementsByTagName("Leiras").item(0).getTextContent();
                        String jRef = elem.getElementsByTagName("JarmuRef").item(0).getTextContent();
                        String sRef = elem.getElementsByTagName("SzervizRef").item(0).getTextContent();
                        
                        printAndLog("Bejegyzés [" + logID + "]: " + datum + " - " + leiras, writer);
                        printAndLog("Jármű Ref: " + jRef + ", Szerviz Ref: " + sRef, writer);
                    }
                }
                
                System.out.println("\n[INFO] Kiírás kész: src/hu/domparse/dbo8mh/read_log.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAndLog(String text, PrintWriter writer) {
        System.out.println(text);
        writer.println(text);
    }
}