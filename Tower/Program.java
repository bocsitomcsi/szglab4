package Tower;
import java.io.File;
import java.util.ArrayList;
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
	static String testcase;
	static String inputfile;
	static String outputfile;
	
	static Map map;
	static Saruman saruman;
	static Round round;

	public static void main(String[] args) {
		try {
			File tc = new File(args[0]);
			DocumentBuilder casedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document tcdoc = casedBuilder.parse(tc);
			if(!tcdoc.getDocumentElement().getNodeName().equals("testCase")){
				//TODO: EXIT
				System.exit(-1);
			}
			testcase = (tcdoc.getDocumentElement()).getAttribute("name");
			Node commands = tcdoc.getFirstChild();
			command(commands);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void command(Node commands){
		NodeList nodeList = commands.getChildNodes();
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = tempNode.getNodeName();
				if(nodeName.equals("load")) {
					inputfile = ((Element)tempNode).getAttribute("file");
					try{
						File file = new File("xml/" + inputfile);
						DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document doc = dBuilder.parse(file);
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
				} else if (nodeName.equals("save")) {
					outputfile = ((Element)tempNode).getAttribute("file");
					//TODO: save
				} else if (nodeName.equals("addTower")) {
					//TODO: Cell id-n keresztuli letrehozas
					if(saruman.getMagicPower() > saruman.getTowerCost()){
						saruman.changeMagicPowerBy((-1)*saruman.getTowerCost());
						Tower tower = new Tower(new Cell(map, null),map);
					
						ArrayList<Tower> towerList;
						towerList = map.getTowers();
						towerList.add(tower);
						map.setTowers(towerList);
					}
					else {
						//TODO: Rendes hibauzenet
						System.out.println("Sarumannak nincs magicje, why!");
					}
				} else if (nodeName.equals("addObstacle")) {
					//TODO: Cell id-n keresztuli letrehozas
					if(saruman.getMagicPower() > saruman.getObstacleCost()){
						saruman.changeMagicPowerBy((-1)*saruman.getObstacleCost());
						Obstacle obs = new Obstacle(new Cell(map, null));
					
						ArrayList<Obstacle> obsList;
						obsList = map.getObstacles();
						obsList.add(obs);
						map.setObstacles(obsList);
					}
					else {
						//TODO: Rendes hibauzenet
						System.out.println("Sarumannak nincs magicje, why!");
					}
				} else if (nodeName.equals("createStone")) {
					if(saruman.getMagicPower() > saruman.getMagicStoneCost()){
						saruman.changeMagicPowerBy((-1)*saruman.getMagicStoneCost());
						saruman.createStone(((Element)tempNode).getAttribute("type"));
					}
					else {
						//TODO: Rendes hibauzenet
						System.out.println("Sarumannak nincs magicje, why!");
					}
				} else if (nodeName.equals("upgradeTower")) {
					if(saruman.getSelectedMagicStone() != null){
						//TODO: Megfelelo CellID-n levo torony upgradeje
						Tower tower = new Tower(new Cell(map, null),map);
						saruman.upgradeItem(tower);
					}
					else {
						//TODO: Rendes hibauzenet
						System.out.println("Sarumannak nincs magicstoneja, why!");
					}
				} else if (nodeName.equals("upgradeObstacle")) {
					if(saruman.getSelectedMagicStone() != null){
						//TODO: Megfelelo CellID-n levo akadaly upgradeje
						Obstacle obs = new Obstacle(new Cell(map, null));
						saruman.upgradeItem(obs);
					}
					else {
						//TODO: Rendes hibauzenet
						System.out.println("Sarumannak nincs magicstoneja, why!");
					}
				}
			}
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
					xmlCell((Element)tempNode, tempNode.getChildNodes());
				} else if (nodeName.equals("saruman")) {
					xmlSaruman((Element)tempNode);
				} else if (nodeName.equals("round")) {
					xmlRound((Element)tempNode);
				}
				if (tempNode.hasChildNodes()) {
					// loop again if it has child nodes
					printNote(tempNode.getChildNodes());
				}
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

	private static void xmlCell(Element Node, NodeList nodeList) {
		Cell cell;
		if(Node.getAttribute("type").equals("startpoint")) {
			cell = new Cell(map, CellType.StartPoint);
		} else if(Node.getAttribute("type").equals("endpoint")) {
			cell = new Cell(map, CellType.EndPoint);
		} else if(Node.getAttribute("type").equals("road")) {
			cell = new Cell(map, CellType.Road);
		} else {
			cell = new Cell(map, CellType.Terrain);
		}
		ArrayList<Cell> cellList;
		cellList = map.getCells();
		cellList.add(cell);
		map.setCells(cellList);
		
		//TODO: a cellanak vannak szomszedjai ezeket hozzakene adni a cellahoz
		
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = tempNode.getNodeName();
				if (nodeName.equals("enemy")) {
					xmlEnemy((Element)tempNode, cell);
				} else if (nodeName.equals("obstacle")) {
					xmlObstacle((Element)tempNode, cell);
				} else if (nodeName.equals("tower")) {
					xmlTower((Element)tempNode, cell);
				}
			}
		}
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
	
	private static void xmlEnemy(Element tempNode, Cell cell) {
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
		enemy.setPosition(cell);
		
		ArrayList<Enemy> enemyList;
		enemyList = map.getEnemies();
		enemyList.add(enemy);
		map.setEnemies(enemyList);
	}
	
	private static void xmlObstacle(Element tempNode, Cell cell) {
		Obstacle obs;
		HashMap<String, Double> bonus;
		
		obs = new Obstacle(cell);
		cell.setBusy(true);
		bonus = new HashMap<String, Double>();
		
		if(tempNode.getAttribute("slowRate") != "") {
			obs.setSlowRate(Integer.parseInt(tempNode.getAttribute("slowRate")));
		}
		if(tempNode.getAttribute("humanBonus") != "") {
			bonus.put("humanBonus", Double.valueOf(tempNode.getAttribute("humanBonus")));
		}
		if(tempNode.getAttribute("dwarfBonus") != "") {
			bonus.put("dwarfBonus", Double.valueOf(tempNode.getAttribute("dwarfBonus")));
		}
		if(tempNode.getAttribute("elfBonus") != "") {
			bonus.put("elfBonus", Double.valueOf(tempNode.getAttribute("elfBonus")));
		}
		if(tempNode.getAttribute("hobbitBonus") != "") {
			bonus.put("hobbitBonus", Double.valueOf(tempNode.getAttribute("hobbitBonus")));
		}
		
		obs.setBonusSlowRates(bonus);
		
		ArrayList<Obstacle> obsList;
		obsList = map.getObstacles();
		obsList.add(obs);
		map.setObstacles(obsList);
	}
	
	private static void xmlTower(Element tempNode, Cell cell) {
		Tower tower;
		HashMap<String, Integer> bonus;
		
		tower = new Tower(cell, map);
		cell.setBusy(true);
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
		
		ArrayList<Tower> towerList;
		towerList = map.getTowers();
		towerList.add(tower);
		map.setTowers(towerList);
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
			round.setEnemyNumberMultiplier(Double.valueOf(tempNode.getAttribute("enemyNumberMultiplier")));
		}
		if(tempNode.getAttribute("enemyAddingTimeMultiplier") != "") {
			round.setEnemyAddingTimeMultiplier(Double.valueOf(tempNode.getAttribute("enemyAddingTimeMultiplier")));
		}
		if(tempNode.getAttribute("roundTime") != "") {
			round.setRoundTime(Integer.parseInt(tempNode.getAttribute("roundTime")));
		}
		if(tempNode.getAttribute("maxRounds") != "") {
			round.setMaxRounds(Integer.parseInt(tempNode.getAttribute("maxRounds")));
		}
		//TODO map.setRound(round);
	}
}
