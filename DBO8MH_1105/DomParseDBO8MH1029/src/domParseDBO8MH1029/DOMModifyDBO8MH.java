package domParseDBO8MH1029;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMModifyDBO8MH {
	public static void main(String argv[])
	{
		try{
			File inputFile = new File("hallgatoDBO8MH.xml");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.parse(inputFile);
			
			Node hallgatok = doc.getFirstChild();
			
			Node hallgat = doc.getElementsByTagName("hallgato").item(0);
			
			NamedNodeMap attr = hallgat.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("01");
			
			NodeList list = hallgat.getChildNodes();
			
			for(int temp = 0; temp < list.getLength(); temp++)
			{
				Node node = list.item(temp);
				
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement  = (Element) node;
					
					if("keresztnev".equals(eElement.getNodeName())) {
						if("Pál".equals(eElement.getTextContent())) {
							eElement.setTextContent("Olivia");
						}
					}
					if("vezeteknev".equals(eElement.getNodeName())) {
						if("Kiss".equals(eElement.getTextContent())) {
							eElement.setTextContent("Erős");
						}
					}	
				}			
			}
			TransformerFactory transformerFactry = TransformerFactory.newInstance();
			Transformer transformer = transformerFactry.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			
			System.out.println("---módosított fájl---");
			
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source,consoleResult);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}
