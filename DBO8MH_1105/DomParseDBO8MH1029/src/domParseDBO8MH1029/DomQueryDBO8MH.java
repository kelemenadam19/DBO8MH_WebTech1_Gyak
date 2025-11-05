package domParseDBO8MH1029;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQueryDBO8MH {
	public static void main(String argv[]) {
        try {
            File inputFile = new File("hallgatoDBO8MH.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.print("Gyökér elem: ");
            System.out.println(doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("hallgato");
            System.out.print("---");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nAktuális elem: ");
                System.out.print(nNode.getNodeName());
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    //System.out.print("\nHallgato: ");
                    System.out.println(eElement.getAttribute("Hallgato"));
                    NodeList hallgatoNevLista = eElement.getElementsByTagName("vezeteknev");

                    for (int count = 0; count < hallgatoNevLista.getLength(); count++) {
                        Node node1 = hallgatoNevLista.item(count);

                        if (node1.getNodeType() == node1.ELEMENT_NODE) {
                            Element hallgat = (Element) node1;
                            System.out.print("vezeteknev: ");
                            System.out.println(hallgat.getTextContent());
                            System.out.print("hallgat Id : ");
                            System.out.println(hallgat.getAttribute("id"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
