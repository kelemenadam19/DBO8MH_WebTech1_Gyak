package xpathdbo8mh;

import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class xPathQueryDBO8MH {
	 public static void main(String[] args) {
	        try {
	            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	            Document document = documentBuilder.parse("studentDBO8MH.xml");
	            document.getDocumentElement().normalize();
	            XPath xPath = XPathFactory.newInstance().newXPath();
	            /*
	            1.
	            String DBO8MH = "diakok";
	            2.
	            String DBO8MH = "diakok/diak[@id = 2]";
	            3.
	            String DBO8MH = "diakok/*";
	            4.
	           	String DBO8MH = "diakok/diak[2]";
	           	5.
	            String DBO8MH = "diakok/diak[last()]";
	            6.
	            String DBO8MH = "diakok/diak[last()-1]";
	            7.
	            String DBO8MH = "diakok/diak[@id >=1 and @id<=2]";
	            8.
	            String DBO8MH = "diakok/*";
	            9.
	            
	            */
	            String DBO8MH = "*";
	            NodeList dbo8mh = (NodeList) xPath.compile(DBO8MH).evaluate(document, XPathConstants.NODESET);

	            for (int i = 0; i < dbo8mh.getLength(); i++) {
	                Node node = dbo8mh.item(i);
	                System.out.println("\nAktuális elem: " + node.getNodeName());

	                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("diak")) {
	                    Element element = (Element) node;
	                    System.out.println("Hallgató ID: " + element.getAttribute("id"));
	                    System.out.println("keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
	                    System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
	                    System.out.println("Becenév: " + element.getElementsByTagName("becenev").item(0).getTextContent());
	                    System.out.println("Kor: " + element.getElementsByTagName("kor").item(0).getTextContent());
	                }
	            }
	        } catch (ParserConfigurationException e) {
	            e.printStackTrace();
	        } catch (SAXException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	    }
	
	
}
