package hu.domparse.dbo8mh;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DBO8MHDOMQuery {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/hu/domparse/dbo8mh/DBO8MH_XML.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("1. LEKÉRDEZÉS: Összes 'Opel' gyártmányú autó");
            // Most már GyartoRef-et is nézhetünk, vagy maradhat a Tipus contains 'Astra'
            // De mivel a feladat "Opel típusút" ír, és van GyartoRef, használjuk azt is!
            NodeList carList = doc.getElementsByTagName("Jarmu");
            for (int i = 0; i < carList.getLength(); i++) {
                Element car = (Element) carList.item(i);
                String gyarto = car.getElementsByTagName("GyartoRef").item(0).getTextContent();
                
                if (gyarto.equalsIgnoreCase("Opel")) {
                    String plate = car.getAttribute("rendszam");
                    String tipus = car.getElementsByTagName("Tipus").item(0).getTextContent();
                    System.out.println("Találat: " + gyarto + " " + tipus + "  Rendszám: " + plate);
                }
            }

            System.out.println("\n2. LEKÉRDEZÉS: 'Nagy Éva' autói");
            NodeList ownerList = doc.getElementsByTagName("Tulajdonos");
            for (int i = 0; i < ownerList.getLength(); i++) {
                Element owner = (Element) ownerList.item(i);
                
                // ÖSSZETETT NÉV KEZELÉSE A KERESÉSHEZ
                Element nevElem = (Element) owner.getElementsByTagName("Nev").item(0);
                String vnev = nevElem.getElementsByTagName("Vezeteknev").item(0).getTextContent();
                String knev = nevElem.getElementsByTagName("Keresztnev").item(0).getTextContent();
                String fullName = vnev + " " + knev;
                
                if (fullName.equals("Nagy Éva")) {
                    System.out.println("Tulajdonos megtalálva: " + fullName);
                    NodeList cars = owner.getElementsByTagName("Jarmu");
                    for(int j=0; j<cars.getLength(); j++) {
                        Element car = (Element) cars.item(j);
                        System.out.println("Autó: " + car.getAttribute("rendszam") + " (" + 
                                           car.getElementsByTagName("Tipus").item(0).getTextContent() + ")");
                    }
                }
            }

            System.out.println("\n3. LEKÉRDEZÉS: Miskolci szervizek");
            NodeList serviceList = doc.getElementsByTagName("Szerviz");
            for (int i = 0; i < serviceList.getLength(); i++) {
                Element srv = (Element) serviceList.item(i);
                String address = srv.getElementsByTagName("Cim").item(0).getTextContent();
                
                if (address.contains("Miskolc")) {
                    System.out.println("Szerviz: " + srv.getElementsByTagName("Nev").item(0).getTextContent());
                    System.out.println("Cím: " + address);
                }
            }

            System.out.println("\n4. LEKÉRDEZÉS: 'ABC-123' rendszámú autó szervizelései");
            NodeList logs = doc.getElementsByTagName("Szervizeles");
            int count = 0;
            for (int i = 0; i < logs.getLength(); i++) {
                Element log = (Element) logs.item(i);
                String ref = log.getElementsByTagName("JarmuRef").item(0).getTextContent();
                
                if (ref.equals("ABC-123")) {
                    System.out.println("Dátum: " + log.getElementsByTagName("Datum").item(0).getTextContent() + 
                                       " | " + log.getElementsByTagName("Leiras").item(0).getTextContent());
                    count++;
                }
            }
            if(count == 0) System.out.println("Nincs találat.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}