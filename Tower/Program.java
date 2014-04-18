package Tower;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import Tower.Cell.CellType;

public class Program {
	static Map map;

	public static void main(String[] args) {
		try {
			File file = new File(args[0]);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			if(!doc.getDocumentElement().getNodeName().equals("map")) {
				// TODO: a root element nem 'map', ami nalunk nem lehet. kulturaltan kilepni
				System.exit(-1);
			}
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printNote(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = tempNode.getNodeName();
				if(nodeName.equals("map")) {
					mapMap();
				} else if (nodeName.equals("cell")) {
					mapCell((Element)tempNode);
				}
				// get node name and value
				System.out.println("\nNode Name =" + nodeName + " [OPEN]");
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

	private static void mapMap() {
		map = new Map(4);
	}

	private static void mapCell(Element tempNode) {
		if(tempNode.getAttribute("type").equals("startpoint")) {
			new Cell(map, CellType.StartPoint);
		} else if(tempNode.getAttribute("type").equals("endpoint")) {
			new Cell(map, CellType.EndPoint);
		} else if(tempNode.getAttribute("type").equals("road")) {
			new Cell(map, CellType.Road);
		} else if(tempNode.getAttribute("type").equals("terrain")) {
			new Cell(map, CellType.Terrain);
		}
		//TODO: cellat hozza is kell adni map-hoz
	}
}
