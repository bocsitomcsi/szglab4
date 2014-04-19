package Tower;
import java.io.File;
import java.util.HashMap;

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
	static Saruman saruman;
	static Round round;

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
					xmlMap((Element)tempNode);
				} else if (nodeName.equals("cell")) {
					xmlCell((Element)tempNode);
				} else if (nodeName.equals("saruman")) {
					xmlSaruman((Element)tempNode);
				} else if (nodeName.equals("enemy")) {
					xmlEnemy((Element)tempNode);
				} else if (nodeName.equals("obstacle")) {
					xmlObstacle((Element)tempNode);
				} else if (nodeName.equals("tower")) {
					xmlTower((Element)tempNode);
				} else if (nodeName.equals("round")) {
					xmlRound((Element)tempNode);
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
					// loop again if it has child nodes
					printNote(tempNode.getChildNodes());
				}
				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
			}
		}
	}

	private static void xmlMap(Element tempNode) {
		int nn = 4;
		
		if(tempNode.getAttribute("neightbourNumber") != "") {
			nn = Integer.parseInt(tempNode.getAttribute("neightbourNumber"));
		}
		map = new Map(nn);
	}

	private static void xmlCell(Element tempNode) {
		Cell cell;
		if(tempNode.getAttribute("type").equals("startpoint")) {
			cell = new Cell(map, CellType.StartPoint);
		} else if(tempNode.getAttribute("type").equals("endpoint")) {
			cell = new Cell(map, CellType.EndPoint);
		} else if(tempNode.getAttribute("type").equals("road")) {
			cell = new Cell(map, CellType.Road);
		} else if(tempNode.getAttribute("type").equals("terrain")) {
			cell = new Cell(map, CellType.Terrain);
		}
		//TODO: cellat hozza is kell adni map-hoz
		//TODO: a cellanak vannak szomszedjai ezeket hozzakene adni a cellahoz
	}
	
	private static void xmlSaruman(Element tempNode) {
		int mp = 200;
		int tc = 50;
		int oc = 80;
		int mc = 100;
		String ms;
		
		if(tempNode.getAttribute("magicPower") != "") {
			mp = Integer.parseInt(tempNode.getAttribute("magicPower"));
		}
		if(tempNode.getAttribute("towerCost") != "") {
			tc = Integer.parseInt(tempNode.getAttribute("towerCost"));
		}
		if(tempNode.getAttribute("obstacleCost") != "") {
			oc = Integer.parseInt(tempNode.getAttribute("obstacleCost"));
		}
		if(tempNode.getAttribute("magicStoneCost") != "") {
			mc = Integer.parseInt(tempNode.getAttribute("magicStoneCost"));
		}
		saruman = new Saruman(mp,tc,oc,mc,map);
		
		if(tempNode.getAttribute("magicStone") != "") {
			ms = tempNode.getAttribute("magicStone");
			saruman.createStone(ms);
		}
	}
	
	private static void xmlEnemy(Element tempNode) {
		Enemy enemy;
		
		if(tempNode.getAttribute("type").equals("human")) {
			enemy = new Human();
		} else if(tempNode.getAttribute("type").equals("elf")) {
			enemy = new Elf();
		} else if(tempNode.getAttribute("type").equals("dwarf")) {
			enemy = new Dwarf();
		} else {
			enemy = new Hobbit();
		}
			
		if(tempNode.getAttribute("health") != "") {
			enemy.setHealthPoint(Integer.parseInt(tempNode.getAttribute("health")));
		}
		if(tempNode.getAttribute("actualSpeed") != "") {
			enemy.setActualSpeed(Integer.parseInt(tempNode.getAttribute("actualSpeed")));
		}
		if(tempNode.getAttribute("magic") != "") {
			enemy.setMagic(Integer.parseInt(tempNode.getAttribute("magic")));
		}
		//TODO: add enemy to cell
	}
	
	private static void xmlObstacle(Element tempNode) {
		Obstacle obs;
		HashMap<String, Double> bonus;
		
		//TODO: a parent Cellat beadni isBusy-t beallitani
		obs = new Obstacle(new Cell(map, CellType.Terrain));
		bonus = new HashMap<String, Double>();
		
		if(tempNode.getAttribute("slowRate") != "") {
			obs.setSlowRate(Integer.parseInt(tempNode.getAttribute("slowRate")));
		}
		if(tempNode.getAttribute("humanBonus") != "") {
			bonus.put("humanBonus", Double.parseDouble(tempNode.getAttribute("humanBonus")));
		}
		if(tempNode.getAttribute("dwarfBonus") != "") {
			bonus.put("dwarfBonus", Double.parseDouble(tempNode.getAttribute("dwarfBonus")));
		}
		if(tempNode.getAttribute("elfBonus") != "") {
			bonus.put("elfBonus", Double.parseDouble(tempNode.getAttribute("elfBonus")));
		}
		if(tempNode.getAttribute("hobbitBonus") != "") {
			bonus.put("hobbitBonus", Double.parseDouble(tempNode.getAttribute("hobbitBonus")));
		}
		
		obs.setBonusSlowRates(bonus);
	}
	
	private static void xmlTower(Element tempNode) {
		Tower tower;
		HashMap<String, Integer> bonus;
		
		//TODO: a parent Cellat beadni isBusy-t beallitani
		tower = new Tower(new Cell(map, CellType.Terrain), map);
		bonus = new HashMap<String, Integer>();
		
		if(tempNode.getAttribute("power") != "") {
			tower.setFirePower(Integer.parseInt(tempNode.getAttribute("power")));
		}
		if(tempNode.getAttribute("attackSpeed") != "") {
			tower.setAttackSpeed(Integer.parseInt(tempNode.getAttribute("attackSpeed")));
		}
		if(tempNode.getAttribute("range") != "") {
			tower.setRange(Integer.parseInt(tempNode.getAttribute("range")));
		}
		if(tempNode.getAttribute("rangeDecreaseByFog") != "") {
			//ilyen setterunk nincs
			//tower.set(Integer.parseInt(tempNode.getAttribute("rangeDecreaseByFog")));
		}
		if(tempNode.getAttribute("fogActive") != "") {
			Boolean fogActive = false;
			if(tempNode.getAttribute("fogActive") == "true"){
				fogActive = true;
			}
			tower.setFogActive(fogActive);
		}
		if(tempNode.getAttribute("humanBonus") != "") {
			bonus.put("humanBonus", Integer.parseInt(tempNode.getAttribute("humanBonus")));
		}
		if(tempNode.getAttribute("dwarfBonus") != "") {
			bonus.put("dwarfBonus", Integer.parseInt(tempNode.getAttribute("dwarfBonus")));
		}
		if(tempNode.getAttribute("elfBonus") != "") {
			bonus.put("elfBonus", Integer.parseInt(tempNode.getAttribute("elfBonus")));
		}
		if(tempNode.getAttribute("hobbitBonus") != "") {
			bonus.put("hobbitBonus", Integer.parseInt(tempNode.getAttribute("hobbitBonus")));
		}
		tower.setBonusPowers(bonus);
	}
	
	private static void xmlRound(Element tempNode) {
		round = new Round();
		
		if(tempNode.getAttribute("enemyNumber") != "") {
			round.setEnemyNumber(Integer.parseInt(tempNode.getAttribute("enemyNumber")));
		}
		if(tempNode.getAttribute("enemyAddingTime") != "") {
			round.setEnemyAddingTime(Integer.parseInt(tempNode.getAttribute("enemyAddingTime")));
		}
		if(tempNode.getAttribute("enemyNumberMultiplier") != "") {
			round.setEnemyNumberMultiplier(Integer.parseInt(tempNode.getAttribute("enemyNumberMultiplier")));
		}
		if(tempNode.getAttribute("enemyAddingTimeMultiplier") != "") {
			round.setEnemyAddingTimeMultiplier(Integer.parseInt(tempNode.getAttribute("enemyAddingTimeMultiplier")));
		}
		if(tempNode.getAttribute("roundTime") != "") {
			round.setRoundTime(Integer.parseInt(tempNode.getAttribute("roundTime")));
		}
		if(tempNode.getAttribute("roundNumber") != "") {
			//TODO: nincs setRoundNumberunk
			//round.setRoundNumber(Integer.parseInt(tempNode.getAttribute("roundNumber")));
		}
		if(tempNode.getAttribute("maxRounds") != "") {
			round.setMaxRounds(Integer.parseInt(tempNode.getAttribute("maxRounds")));
		}
	}
}
