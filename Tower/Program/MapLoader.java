package Program;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Model.Cell;
import Model.Cell.CellType;
import Model.Dwarf;
import Model.Elf;
import Model.Enemy;
import Model.Hobbit;
import Model.Human;
import Model.Map;
import Model.Map.Direction;
import Model.Obstacle;
import Model.Round;
import Model.Saruman;
import Model.Tower;

public class MapLoader {
	/**
	 * Segedosztaly a cellak beolvasasahoz az
	 *  osszerendelesek miatt
	 */
	private static class CellHelper {
		/**
		 * A cella akire vonatkozik
		 */
		Cell cell;	
		/**
		 * A cella melyik iranyban levo szomszedja
		 */
		Direction direction; 
		/**
		 * A cella direction iranyban levo szomszedjanak id-ja
		 */
		int neighbourCellId; 
		/**
		 * A cella direction iranyban levo neighbourCellId szomszedjanak elerhetosege
		 */
		boolean neighbourEnabled; 
		
		public CellHelper(Cell cell, Direction direction, int neighbourCellId, boolean neighbourEnabled) {
			this.cell = cell;
			this.direction = direction;
			this.neighbourCellId = neighbourCellId;
			this.neighbourEnabled = neighbourEnabled;
		}
	}
	
	/**
	 * A cellak osszerendeleseit tartalmazo CellHelper-eket
	 *  tartalmazo lista
	 */
	private ArrayList<CellHelper> cellHelpers = 
			new ArrayList<CellHelper>();
	/**
	 * A cellakhoz tartozo azonositok osszerendelese
	 */
	private HashMap<String, Cell> CellIDs = 
			new HashMap<String, Cell>();
	/**
	 * Saruman referencia
	 */
	private Saruman saruman;
	/**
	 * Round referencia
	 */
	private Round round;
	/**
	 * A map referenciaja amit modositani fogunk
	 */
	private Map map;
	
	public static double sliceShootProbability = -1;
	
	/**
	 * Konstruktor.
	 * @param map A map referenciaja, amelybe be kell majd tolteni az uj adatokat.
	 */
	public MapLoader(Map map) {
		this.map = map;
	}
	
	/**
	 * Betolti a megadott fajlban leirt mapot a map attributumba.
	 * Csak modositja a map attributumai, magat a referenciat nem allitja at.
	 * @param fileName A file eleresi utvonala, amely a mapot tartalmazza.
	 * @return A beolvasas sikeressege
	 */
	public boolean loadFromFile(String fileName) {
		try{
			// Megnyitjuk a mapot tartalmazo fajlt es felepitjuk belole a fat
			File mapFile = new File(fileName);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(mapFile);
			
			// Ha nem egy mapot tartalmaz a file hamissal terunk vissza
			if(!doc.getDocumentElement().getNodeName().equals("map")) {
				return false;
			}
			
			if (doc.hasChildNodes()) {
				// Beolvassuk a mapot
				printNote(doc.getChildNodes());
				
				// Osszerendeljuk a cellakat
				xmlCellConnections();
			}
		} catch (Exception e) {
			// Hiba eseten hamissal terunk vissza
			return false;
		}
		
		return true;
	}
	
	/**
	 * Egyesevel vegig megy az xml elemeken es meghivja a megfelelo letrehozo fuggvenyt
	 * @param nodeList
	 */
	private void printNote(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
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
					// Vegig iteral a gyerek elemein is
					printNote(tempNode.getChildNodes());
				}
			}
		}
	}

	/**
	 * Letrehozza a map peldanyt es beallitja a megfelelo attributumokat
	 * @param tempNode
	 */
	private void xmlMap(Element tempNode) {
		int nn;
		if(tempNode.getAttribute("neightbourNumber") != "") {
			nn = Integer.parseInt(tempNode.getAttribute("neightbourNumber"));
			map.setNeighbourNumber(nn);
		}		
		
		// Sor es oszlop szamok elmentese
		if (tempNode.hasAttribute("rowNumber")) {
			map.setRowNumber(Integer.parseInt(tempNode.getAttribute("rowNumber")));
		}
		if (tempNode.hasAttribute("columnNumber")) {
			map.setColumnNumber(Integer.parseInt(tempNode.getAttribute("columnNumber")));
		}
	}

	/**
	 * Letrehozza a Cell peldanyokat es hozzaadja a maphoz
	 * @param Node
	 * @param nodeList
	 */
	private void xmlCell(Element Node, NodeList nodeList) {
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
		
		// Sor es oszlop azonositok elmentese
		if (Node.hasAttribute("rowid")) {
			cell.setRowId(Integer.parseInt(Node.getAttribute("rowid")));
		}
		if (Node.hasAttribute("columnid")) {
			cell.setColumnId(Integer.parseInt(Node.getAttribute("columnid")));
		}
		
		// Egy valtozoban eltaroljuk az ID-ket mivel a Cell-nek nincs ilyen attributuma
		CellIDs.put(Node.getAttribute("id"), cell);
		
		// Letrehozzuk a segedvaltozokat ami tarolja a szomszedokat
		Direction[] directions = {
				Direction.North, 
				Direction.West, 
				Direction.South, 
				Direction.East
		};
		String[] dirStrings = { 
				"northCell",
				"westCell",
				"southCell",
				"eastCell",
		};
		String[] enabledStrings = { 
				"northCellEnabled",
				"westCellEnabled",
				"southCellEnabled",
				"eastCellEnabled"
		};
		Direction direction;
		int neighbourCellId;
		boolean neighbourEnabled;
		
		for (int i = 0; i < 4; i++) {
			neighbourEnabled = false;
			if (Node.hasAttribute(dirStrings[i])) {
				direction = directions[i];
				neighbourCellId = Integer.parseInt(Node.getAttributeNode(dirStrings[i]).getValue());
				if (Node.hasAttribute(enabledStrings[i])) {
					String enabledString = Node.getAttributeNode(enabledStrings[i]).getValue();
					if (enabledString.equals("true")) {
						neighbourEnabled = true;
					}
					else if (enabledString.equals("false")) {
						neighbourEnabled = false;
					}
				}
				
				CellHelper cellHelper = new CellHelper(cell, direction, neighbourCellId, neighbourEnabled);
				cellHelpers.add(cellHelper);
			}
		}
		
		// A cellaban levo Item-ek es Enemy-k feldolgozasa
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
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
	
	/**
	 * A saruman-t letrehozo fuggveny a megfelelo ertekekkel
	 * @param tempNode
	 */
	private void xmlSaruman(Element tempNode) {
		// Magic Power, tower cost, obstacle cost, magicstone cost, magic stone type
		int mp = 200;
		int tc = 50;
		int oc = 80;
		int mc = 100;
		String ms;
		
		if(tempNode.hasAttribute("magicPower")) {
			mp = Integer.parseInt(tempNode.getAttribute("magicPower"));
		}
		if(tempNode.hasAttribute("towerCost")) {
			tc = Integer.parseInt(tempNode.getAttribute("towerCost"));
		}
		if(tempNode.hasAttribute("obstacleCost")) {
			oc = Integer.parseInt(tempNode.getAttribute("obstacleCost"));
		}
		if(tempNode.hasAttribute("magicStoneCost")) {
			mc = Integer.parseInt(tempNode.getAttribute("magicStoneCost"));
		}
		saruman = new Saruman(mp,tc,oc,mc,map);
		
		// Ha van varazs kove azt is letrehozza
		if(tempNode.hasAttribute("magicStone")) {
			if(!tempNode.getAttribute("magicStone").equals("")){
				ms = tempNode.getAttribute("magicStone");
				saruman.createStone(ms);
				saruman.changeMagicPowerBy(saruman.getMagicStoneCost());
			}
		}
		
		// Mapnak beallitjuk a saruman referenciat
		map.setSaruman(saruman);
	}
	
	/*
	 * 
	 */
	/**
	 * Enemy letrehozasa megfelelo ertekekkel
	 * A map.addEnemy()-t azert nem hasznaltuk, mert azon keresztul nem tudjuk
	 *  beallitani az enemy-nek az ertekeit
	 * @param tempNode
	 * @param cell
	 */
	private void xmlEnemy(Element tempNode, Cell cell) {
		Enemy enemy;
		
		// Tipustol fuggoen hozzuk letre
		if(tempNode.getAttribute("type").equals("human")) {
			enemy = new Human();
		} else if(tempNode.getAttribute("type").equals("elf")) {
			enemy = new Elf();
		} else if(tempNode.getAttribute("type").equals("dwarf")) {
			enemy = new Dwarf();
		} else {
			enemy = new Hobbit();
		}
			
		if(tempNode.hasAttribute("health")) {
			enemy.setHealthPoint(Integer.parseInt(tempNode.getAttribute("health")));
		}
		if(tempNode.hasAttribute("actualSpeed")) {
			enemy.setActualSpeed(Integer.parseInt(tempNode.getAttribute("actualSpeed")));
			if(!tempNode.hasAttribute("originalSpeed")) {
				enemy.setOriginalSpeed(Integer.parseInt(tempNode.getAttribute("actualSpeed")));
			}
		}
		if(tempNode.hasAttribute("magic")) {
			enemy.setMagic(Integer.parseInt(tempNode.getAttribute("magic")));
		}
		// Cellajat is beadjuk a konstruktorba
		enemy.setPosition(cell);
		
		// Maphoz is hozza adjuk
		ArrayList<Enemy> enemyList;
		enemyList = map.getEnemies();
		enemyList.add(enemy);
		map.setEnemies(enemyList);
		System.out.println(enemy.getActualSpeed());
	}
	
	/**
	 * Akadaly letrehozasa megfelelo ertekekkel
	 * Saruman.addObstacle()-t nem hasznaltuk mert az ertekeit nem lehet beallitani ugy
	 * @param tempNode
	 * @param cell
	 */
	private void xmlObstacle(Element tempNode, Cell cell) {
		Obstacle obs;
		
		// A bonus lassitas a megfelelo fajok ellen
		HashMap<String, Double> bonus;
		
		// Beallitjuk a cell-t foglaltra a rajta letrehozott obstacle miatt
		obs = new Obstacle(cell);
		cell.setBusy(true);
		bonus = new HashMap<String, Double>();
		
		if(tempNode.hasAttribute("slowRate")) {
			obs.setSlowRate(Integer.parseInt(tempNode.getAttribute("slowRate")));
		}
		if(tempNode.hasAttribute("humanBonus")) {
			bonus.put("humanBonus", Double.valueOf(tempNode.getAttribute("humanBonus")));
		}
		if(tempNode.hasAttribute("dwarfBonus")) {
			bonus.put("dwarfBonus", Double.valueOf(tempNode.getAttribute("dwarfBonus")));
		}
		if(tempNode.hasAttribute("elfBonus")) {
			bonus.put("elfBonus", Double.valueOf(tempNode.getAttribute("elfBonus")));
		}
		if(tempNode.hasAttribute("hobbitBonus")) {
			bonus.put("hobbitBonus", Double.valueOf(tempNode.getAttribute("hobbitBonus")));
		}
		
		obs.setBonusSlowRates(bonus);
		
		// Map-hoz beallitjuk az uj obstacle-t
		ArrayList<Obstacle> obsList;
		obsList = map.getObstacles();
		obsList.add(obs);
		map.setObstacles(obsList);
	}
	
	/**
	 * Torony letrehozo fuggveny
	 * Saruman.addTower()-t nem hasznaltuk mert ugy nem lehet beallitani az ertekeit
	 * @param tempNode
	 * @param cell
	 */
	private void xmlTower(Element tempNode, Cell cell) {
		Tower tower;
		// Bonusz sebzes a megfelelo fajok ellen
		HashMap<String, Integer> bonus;
		
		// Cell foglaltsag beallitasa
		tower = new Tower(cell, map);
		cell.setBusy(true);
		bonus = new HashMap<String, Integer>();
		
		if(tempNode.hasAttribute("power")) {
			tower.setFirePower(Integer.parseInt(tempNode.getAttribute("power")));
		}
		if(tempNode.hasAttribute("attackSpeed")) {
			tower.setAttackSpeed(Integer.parseInt(tempNode.getAttribute("attackSpeed")));
		}
		if(tempNode.hasAttribute("range")) {
			tower.setRange(Integer.parseInt(tempNode.getAttribute("range")));
		}
		if(tempNode.hasAttribute("fogActive")) {
			Boolean fogActive = false;
			if(tempNode.getAttribute("fogActive").equals("true")){
				fogActive = true;
			}
			tower.setFogActive(fogActive);
		}
		if(tempNode.hasAttribute("humanBonus")) {
			bonus.put("humanBonus", Integer.parseInt(tempNode.getAttribute("humanBonus")));
		}
		if(tempNode.hasAttribute("dwarfBonus")) {
			bonus.put("dwarfBonus", Integer.parseInt(tempNode.getAttribute("dwarfBonus")));
		}
		if(tempNode.hasAttribute("elfBonus")) {
			bonus.put("elfBonus", Integer.parseInt(tempNode.getAttribute("elfBonus")));
		}
		if(tempNode.hasAttribute("hobbitBonus")) {
			bonus.put("hobbitBonus", Integer.parseInt(tempNode.getAttribute("hobbitBonus")));
		}
		tower.setBonusPowers(bonus);
		
		// Maphoz hozza adjuk a tornyot
		ArrayList<Tower> towerList;
		towerList = map.getTowers();
		towerList.add(tower);
		map.setTowers(towerList);
	}
	
	/**
	 * A Round letrehozasa es ertekeinek beallitasa
	 * @param tempNode
	 */
	private void xmlRound(Element tempNode) {
		round = new Round();
		
		if(tempNode.hasAttribute("enemyNumber")) {
			round.setEnemyNumber(Integer.parseInt(tempNode.getAttribute("enemyNumber")));
		}
		if(tempNode.hasAttribute("enemyAddingTime")) {
			round.setEnemyAddingTime(Integer.parseInt(tempNode.getAttribute("enemyAddingTime")));
		}
		if(tempNode.hasAttribute("enemyNumberMultiplier")) {
			round.setEnemyNumberMultiplier(Double.valueOf(tempNode.getAttribute("enemyNumberMultiplier")));
		}
		if(tempNode.hasAttribute("enemyAddingTimeMultiplier")) {
			round.setEnemyAddingTimeMultiplier(Double.valueOf(tempNode.getAttribute("enemyAddingTimeMultiplier")));
		}
		if(tempNode.hasAttribute("roundTime")) {
			round.setRoundTime(Integer.parseInt(tempNode.getAttribute("roundTime")));
		}
		if(tempNode.hasAttribute("maxRounds")) {
			round.setMaxRounds(Integer.parseInt(tempNode.getAttribute("maxRounds")));
		}
		
		// Maphoz beallitjuk a Round referenciat
		map.setRound(round);
	}
	
	/**
	 * A beolvasott cellak kozotti osszerendelesek vegzi el
	 * a cellHelpers kollekcio segitsegevel
	 */
	private void xmlCellConnections() {
		// Minden CellHelper-en vegig megyunk
		for (CellHelper cellHelper : cellHelpers) {
			// Szomszed elerese
			String neighbourCellIdString = Integer.toString(cellHelper.neighbourCellId);
			Cell neighbourCell = CellIDs.get(neighbourCellIdString);
			
			cellHelper.cell.getNeighbours().put(
					cellHelper.direction, 
					new Cell.CellEntry<Cell, Boolean>(
							neighbourCell,
							new Boolean(cellHelper.neighbourEnabled)
							)
					);
			
		}
	}
}
