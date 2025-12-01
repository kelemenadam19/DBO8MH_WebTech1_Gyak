package hu.domparse.dbo8mh;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBO8MHDOMModify {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/hu/domparse/dbo8mh/DBO8MH_XML.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("--- Módosítások végrehajtása... ---");

            //Szerviz név csere
            NodeList services = doc.getElementsByTagName("Szerviz");
            for (int i = 0; i < services.getLength(); i++) {
                Element srv = (Element) services.item(i);
                if (srv.getAttribute("szervizID").equals("SZ01")) {
                    Node nameNode = srv.getElementsByTagName("Nev").item(0);
                    System.out.println("Régi név: " + nameNode.getTextContent());
                    nameNode.setTextContent("Mester Szerviz PRO Kft.");
                    System.out.println("--> Új név: Mester Szerviz PRO Kft.");
                }
            }

            //Jármű típus cserélése
            NodeList cars = doc.getElementsByTagName("Jarmu");
            for(int i=0; i<cars.getLength(); i++) {
                Element car = (Element) cars.item(i);
                if(car.getAttribute("rendszam").equals("DEF-456")) {
                    Node typeNode = car.getElementsByTagName("Tipus").item(0);
                    typeNode.setTextContent("Ford Focus Kombi");
                    System.out.println("--> Módosítás: DEF-456 típusa 'Ford Focus Kombi' lett.");
                }
            }

            //Új Szervizelés
            Element newLog = doc.createElement("Szervizeles");
            newLog.setAttribute("szelID", "SZL_NEW");

            Element datum = doc.createElement("Datum");
            datum.setTextContent("2024-05-01");
            Element leiras = doc.createElement("Leiras");
            leiras.setTextContent("Klíma tisztítás");
            Element jRef = doc.createElement("JarmuRef");
            jRef.setTextContent("ABC-123");
            Element sRef = doc.createElement("SzervizRef");
            sRef.setTextContent("SZ03");

            newLog.appendChild(datum);
            newLog.appendChild(leiras);
            newLog.appendChild(jRef);
            newLog.appendChild(sRef);

            doc.getDocumentElement().appendChild(newLog);
            System.out.println("Új szervizelés (SZL_NEW) hozzáadva.");

            //Elem törlése (SZL002)
            NodeList logs = doc.getElementsByTagName("Szervizeles");
            for(int i=0; i<logs.getLength(); i++) {
                Element log = (Element) logs.item(i);
                if(log.getAttribute("szelID").equals("SZL002")) {
                    log.getParentNode().removeChild(log);
                    System.out.println("SZL002 azonosítójú elem törölve.");
                    break;
                }
            }

            // Kiírás konzolra
            System.out.println("\nMódosított XML struktúra:");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}