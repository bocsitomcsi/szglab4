package Tower;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import Tower.Cell.CellEntry;
import Tower.Cell.CellType;
import Tower.Map.Direction;

public class Program {
	// Segedosztaly a cellak beolvasasahoz az
	// osszerendelesek miatt
	public static class CellHelper {
		// A cella akire vonatkozik
		Cell cell;	
		// A cella melyik iranyban levo szomszedja
		Direction direction; 
		// A cella direction iranyban levo szomszedjanak id-ja
		int neighbourCellId; 
		// A cella direction iranyban levo neighbourCellId szomszedjanak elerhetosege
		boolean neighbourEnabled; 
		
		public CellHelper(Cell cell, Direction direction, int neighbourCellId, boolean neighbourEnabled) {
			this.cell = cell;
			this.direction = direction;
			this.neighbourCellId = neighbourCellId;
			this.neighbourEnabled = neighbourEnabled;
		}
	}
	static ArrayList<CellHelper> cellHelpers = 
			new ArrayList<CellHelper>();
	
	static String testcase;
	static String inputfile;
	static String outputfile;
	static HashMap<String, Cell> CellIDs;
	
	static Map map;
	static Saruman saruman;
	static Round round;
	
	// Az aktualis parancs delay ideje eddig tart
	public static long delayEnd;
	public static int testcaseNumber = 0;
	public static double sliceShootProbability = -1;
	
	public static void main(String[] args) {
		try {
			// Beolvassa a beadott testcase<number>.xml-t es az xml-t parseolja
			File tc = new File(args[0]);
			if (args[0].contains("testcase1.xml")) {
				testcaseNumber = 1;
			}
			DocumentBuilder casedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document tcdoc = casedBuilder.parse(tc);
			// Ha a gyoker nem testCase
			if(!tcdoc.getDocumentElement().getNodeName().equals("testCase")){
				System.out.println("The file input is wrong");
				System.exit(-1);
			}
			
			// A neve a tesztnek
			testcase = (tcdoc.getDocumentElement()).getAttribute("name");
			Node commands = tcdoc.getFirstChild();
			command(commands);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/* 
	Az xml commandjat olvassa ki es hajtja vegre 
	*/
	private static void command(Node commands){
		CellIDs = new HashMap<String, Cell>();
		NodeList command = commands.getChildNodes();
		NodeList nodeList = command.item(1).getChildNodes();
		// Minden egyes commandon vegigmegyunk
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// Megnezzuk hogy Element_Node-e
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = tempNode.getNodeName();
				if(nodeName.equals("load")) {
					inputfile = ((Element)tempNode).getAttribute("file");
					try{
						// Beolvassuk commandban megadott xml fajlt xml parse-olva
						File file = new File("Tower/xml/" + inputfile);
						DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document doc = dBuilder.parse(file);
						if(!doc.getDocumentElement().getNodeName().equals("map")) {
							System.out.println("Wrong file: " + inputfile);
							System.exit(-1);
						}
						if (doc.hasChildNodes()) {
							// A felepito fuggveny meghivasa
							printNote(doc.getChildNodes());
							
							// Utolsokent, osszerendeljuk a cellakat
							xmlCellConnections();
						}
						// Ha ez az elso testcase akkor meg kell varnunk amig letrejon egy elf
						if (testcaseNumber == 1) {
							long currentTime = System.currentTimeMillis();
							delayEnd = currentTime + 1500;
							System.out.println("simulate called at " + currentTime);
							map.simulateWorld();
							System.out.println("simulate ended at " + currentTime);
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				} else if (nodeName.equals("save")) {
					Node delayNode = tempNode.getAttributes().getNamedItem("delay");
					// Ha van delay, akkor kesleltetjuk a mentest
					if (delayNode != null) {
						long delayTime = Integer.parseInt(delayNode.getNodeValue());
						delayEnd = System.currentTimeMillis() + delayTime;
						map.simulateWorld();
					}
					
					// Ha mentes parancsot kap meghivja a mento fuggvenyt
					outputfile = ((Element)tempNode).getAttribute("file");
					xmlSave();
				} else if (nodeName.equals("addTower")) {
					// Hozza ad egy tornyot a megfelelo pozicioban
					Cell cellatid = CellIDs.get(((Element)tempNode).getAttribute("cellId"));
					saruman.addTower(cellatid);
					ArrayList<Tower> towers = map.getTowers();
					for(Tower tower : towers){
						if(tower.getPosition().equals(cellatid)){
							tower.setFirePower(10);
							tower.setAttackSpeed(500);
						}
					}
				} else if (nodeName.equals("addObstacle")) {
					// Hozza ad egy akadalyt a megfelelo pozicioban
					Cell cellatid = CellIDs.get(((Element)tempNode).getAttribute("cellId"));
					saruman.addObstacle(cellatid);
					ArrayList<Obstacle> obstacles = map.getObstacles();
					for(Obstacle obs : obstacles){
						if(obs.getPosition().equals(cellatid)){
							obs.setSlowRate(10);
						}
					}
				} else if (nodeName.equals("createStone")) {
					// Letrehoz egy varazskovet
					saruman.createStone(((Element)tempNode).getAttribute("type"));
				} else if (nodeName.equals("upgradeTower")) {
					// Meghivja a megfelelo torony upgradejet, a megadott cellaban
					if(saruman.getSelectedMagicStone() != null){
						Cell cellatid = CellIDs.get("1");
						ArrayList<Tower> towers = map.getTowers();
						for(Tower tower : towers){
							if(tower.getPosition().equals(cellatid)){
								MagicStone ms = saruman.getSelectedMagicStone();
								ms.setAttackSpeed(-100);
								ms.setFirePower(0);
								ms.setRange(-1);
								HashMap<String, Integer> bonus = new HashMap<String, Integer>();
								bonus.put("humanBonus",1);
								bonus.put("elfBonus",2);
								bonus.put("hobbitBonus",3);
								ms.setBonusPowers(bonus);
								saruman.upgradeItem(tower);
							}
						}
					}
					else {
						System.out.println("Sarumannak nincs letrehozott varazskove, hogy tornyot fejlesszen.");
					}
				} else if (nodeName.equals("upgradeObstacle")) {
					// Meghivja a megadott cellaban az akadaly upgrade fuggvenyet
					if(saruman.getSelectedMagicStone() != null){
						Cell cellatid = CellIDs.get("1");
						ArrayList<Obstacle> obstacles = map.getObstacles();
						for(Obstacle obs : obstacles){
							if(obs.getPosition().equals(cellatid)){
								MagicStone ms = saruman.getSelectedMagicStone();
								ms.setSlowRate(1);
								HashMap<String, Double> bonus = new HashMap<String, Double>();
								bonus.put("humanBonus",2.0);
								bonus.put("elfBonus",3.0);
								bonus.put("hobbitBonus",4.0);
								ms.setBonusSlowRate(bonus);
								saruman.upgradeItem(obs);
							}
						}
					}
					else {
						System.out.println("Sarumannak nincs letrehozott varazskove, hogy akadalyt fejlesszen.");
					}
				}
			}
		}
	}
	
	/*
	Egyesevel vegig megy az xml elemeken es meghivja a megfelelo letrehozo fuggvenyt
	*/
	private static void printNote(NodeList nodeList) {
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

	/*
	Letrehozza a map peldanyt es beallitja a megfelelo attributumokat
	*/
	private static void xmlMap(Element tempNode) {
		int nn = 4;
		
		if(tempNode.getAttribute("neightbourNumber") != "") {
			nn = Integer.parseInt(tempNode.getAttribute("neightbourNumber"));
		}
		map = new Map(nn);
		
		if(tempNode.getAttribute("sliceShootProbability") != "") {
			sliceShootProbability = Double.parseDouble(tempNode.getAttribute("sliceShootProbability"));
		} else {
			sliceShootProbability = 0;
		}
		//TODO: FOGdecression fog appliance and fog duration
	}

	/*
	 * Letrehozza a Cell peldanyokat es hozzaadja a maphoz
	 */
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
	
	/*
	 * A saruman-t letrehozo fuggveny a megfelelo ertekekkel
	 */
	private static void xmlSaruman(Element tempNode) {
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
	 * Enemy letrehozasa megfelelo ertekekkel
	 * A map.addEnemy()-t azert nem hasznaltuk, mert azon keresztul nem tudjuk
	 * beallitani az enemy-nek az ertekeit
	 */
	private static void xmlEnemy(Element tempNode, Cell cell) {
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
	
	/*
	 * Akadaly letrehozasa megfelelo ertekekkel
	 * Saruman.addObstacle()-t nem hasznaltuk mert az ertekeit nem lehet beallitani ugy
	 */
	private static void xmlObstacle(Element tempNode, Cell cell) {
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
	
	/*
	 * Torony letrehozo fuggveny
	 * Saruman.addTower()-t nem hasznaltuk mert ugy nem lehet beallitani az ertekeit
	 */
	private static void xmlTower(Element tempNode, Cell cell) {
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
	
	/*
	 * A Round letrehozasa es ertekeinek beallitasa
	 */
	private static void xmlRound(Element tempNode) {
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
	
	/*
	 * A beolvasott cellak kozotti osszerendelesek vegzi el
	 * a cellHelpers kollekcio segitsegevel
	 */
	private static void xmlCellConnections() {
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
	
	/*
	 * Kimentesre szolgalo fuggveny
	 */
	private static void xmlSave(){
		// Megfelelo testcase kimento fuggveny fut le
		if(testcase.equals("1-startGame")){
			Writer writer = null;

			try {
				// A beolvasott fajlba irunk
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    // Az xml felepitese es az ertekek kikerese
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\">\n");
			    
			    //TODO: Enemy .getClass nem jo es a enemy hozza adasa meg nincs megcsinalva a jatek inditasanal
			    ArrayList<Enemy> enemyList = map.getEnemies();
			    for(Enemy enemy : enemyList){
			    	if(enemy.getPosition().equals(cell)){
			    		writer.write("\t\t<enemy type=\"" + enemy.toString()
				    			+ "\" health=\"" + enemy.getHealthPoint()
				    			+ "\" actualSpeed=\"" + enemy.getActualSpeed()
				    			+ "\" magic=\"" + enemy.getMagic()
				    			+ "\"/>\n");
			    	}	
			    }
			    
			    writer.write("\t</cell>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" southCell=\"1\" westCell=\"3\" southCellEnabled=\"false\"/>\n");
			    
			    cell = CellIDs.get("3");
			    writer.write("\t<cell id=\"3\" type=\"" + cell.getCellType().toString()
			    		+ "\" eastCell=\"2\"/>\n");
			    
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\"/>\n");
			    
			    // A Double erteknek a elvalasztojat pontra allitja es formazza az alakjat
			    Round round = map.getRound();
			    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
			    otherSymbols.setDecimalSeparator('.');
			    otherSymbols.setGroupingSeparator('.'); 
			    DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
			    writer.write("\t<round enemyNumber=\"" + round.getEnemyNumber()
			    		+ "\" enemyAddingTime=\"" + round.getEnemyAddingTime()
			    		+ "\" enemyNumberMultiplier=\"" + df.format(round.getEnemyNumberMultiplier())
			    		+ "\" enemyAddingTimeMultiplier=\"" + df.format(round.getEnemyAddingTimeMultiplier())
			    		+ "\" roundTime=\"" + round.getRoundTime()
			    		+"\" maxRounds=\"" + round.getMaxRounds()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="startpoint" northCell="2" northCellEnabled="true">
        				<enemy type="elf" health="40" actualSpeed="1000" magic="50"/>
    				</cell>
    				<cell id="2" type="road" southCell="1" westCell="3" southCellEnabled="false"/>
					<cell id="3" type="terrain" eastCell="2"/>
					<saruman magicPower="300"/>
    				<round enemyNumber="1" enemyAddingTime="1000" enemyNumberMultiplier="1.5" enemyAddingTimeMultiplier="2" roundTime="1" maxRounds="3"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("2-putTower")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    // Az xml felepitese es az ertekek kikerese
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\"/>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" southCell=\"1\" westCell=\"3\" southCellEnabled=\"false\"/>\n");
			    
			    cell = CellIDs.get("3");
			    writer.write("\t<cell id=\"3\" type=\"" + cell.getCellType().toString()
			    		+ "\" eastCell=\"2\">\n");
			    
			    ArrayList<Tower> towers = map.getTowers();
			    for(Tower tower : towers){
			    	writer.write("\t\t<tower power=\"" + tower.getFirePower()
			    			+ "\" attackSpeed=\"" + tower.getAttackSpeed()
			    			+ "\" range=\"" + tower.getRange()
			    			+ "\"/>\n");
			    }
			    
			    writer.write("\t</cell>\n");
			    
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\" towerCost=\"" + saruman.getTowerCost()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="road" southCell="1" westCell="3" southCellEnabled="false"/>
    				<cell id="3" type="terrain" eastCell="2">
        				<tower power="10" attackSpeed="500" range="1"/>
    				</cell>
    				<saruman magicPower="250" towerCost="50"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("3-putObstacle")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    // Az xml felepitese es az ertekek kikerese
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\"/>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" southCell=\"1\" westCell=\"3\" southCellEnabled=\"false\">\n");
			    
			    ArrayList<Obstacle> obstacles = map.getObstacles();
			    
			    // A Double erteknek a elvalasztojat pontra allitja es formazza az alakjat
			    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
			    otherSymbols.setDecimalSeparator('.');
			    otherSymbols.setGroupingSeparator('.'); 
			    DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
			    
			    for(Obstacle obs : obstacles){
			    	writer.write("\t\t<obstacle slowRate=\"" + df.format(obs.getSlowRate())
			    			+ "\"/>\n");
			    }
			    
			    writer.write("\t</cell>\n");
			    
			    cell = CellIDs.get("3");
			    writer.write("\t<cell id=\"3\" type=\"" + cell.getCellType().toString()
			    		+ "\" eastCell=\"2\"/>\n");
			    
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\" obstacleCost=\"" + saruman.getObstacleCost()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="road" southCell="1" westCell="3" southCellEnabled="false">
        				<obstacle slowRate="10"/>
    				</cell>
    				<cell id="3" type="terrain" eastCell="2"/>
    				<saruman magicPower="250" obstacleCost="50"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("4-createMagicStone")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    // Az xml felepitese es az ertekek kikerese
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\"/>\n");
			    
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\" magicStoneCost=\"" + saruman.getMagicStoneCost()
			    		+ "\" magicStone=\"" + saruman.getSelectedMagicStone().getName()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="road"/>
    				<saruman magicPower="200" magicStoneCost="50" magicStone="purple"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("5-upgradeTower")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    // Az xml felepitese es az ertekek kikerese
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\">\n");
			    
			    ArrayList<Tower> towers = map.getTowers();
			    for(Tower tower : towers){
			    	HashMap<String, Integer> bonus = tower.getBonusPowers();
			    	writer.write("\t\t<tower power=\"" + tower.getFirePower()
			    			+ "\" attackSpeed=\"" + tower.getAttackSpeed()
			    			+ "\" range=\"" + tower.getRange()
			    			+ "\" humanBonus=\"" + bonus.get("humanBonus")
			    			+ "\" dwarfBonus=\"" + bonus.get("dwarfBonus")
			    			+ "\" elfBonus=\"" + bonus.get("elfBonus")
			    			+ "\" hobbitBonus=\"" + bonus.get("hobbitBonus")
			    			+ "\"/>\n");
			    }
			    
			    writer.write("\t</cell>\n");
			    
			    MagicStone stone = saruman.getSelectedMagicStone();
			    String stonename;
			    if(stone == null){
			    	stonename = "";
			    } else {
			    	stonename = saruman.getSelectedMagicStone().getName();
			    }
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\" magicStone=\"" + stonename
			    		+ "\" towerCost=\"" + saruman.getTowerCost()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="terrain">
        				<tower power="10" attackSpeed="600" range="1" humanBonus="2" dwarfBonus="1" elfBonus="3" hobbitBonus="4"/>
    				</cell>
    				<saruman magicPower="300" magicStone="" towerCost="50"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("6-upgradeObstacle")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    // Az xml felepitese es az ertekek kikerese
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\">\n");
			    
			    ArrayList<Obstacle> obstacles = map.getObstacles();
			    
			    // A Double erteknek a elvalasztojat pontra allitja es formazza az alakjat
			    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
			    otherSymbols.setDecimalSeparator('.');
			    otherSymbols.setGroupingSeparator('.'); 
			    DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
			    
			    for(Obstacle obs : obstacles){
				    HashMap<String, Double> bonus = obs.getBonusSlowRates();
			    	writer.write("\t\t<obstacle slowRate=\"" + df.format(obs.getSlowRate())
			    			+ "\" humanBonus=\"" + df.format(bonus.get("humanBonus"))
			    			+ "\" dwarfBonus=\"" + df.format(bonus.get("dwarfBonus"))
			    			+ "\" elfBonus=\"" + df.format(bonus.get("elfBonus"))
			    			+ "\" hobbitBonus=\"" + df.format(bonus.get("hobbitBonus"))
			    			+ "\"/>\n");
			    }
			    
			    writer.write("\t</cell>\n");

			    MagicStone stone = saruman.getSelectedMagicStone();
			    String stonename;
			    if(stone == null){
			    	stonename = "";
			    } else {
			    	stonename = saruman.getSelectedMagicStone().getName();
			    }
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\" magicStone=\"" + stonename
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="road">
       					<obstacle slowRate="10" humanBonus="2" dwarfBonus="1" elfBonus="3" hobbitBonus="4"/>
    				</cell>
    				<saruman magicPower="250" magicStone=""/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("7-enemyMoves")){
			Writer writer = null;
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\"/>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" southCell=\"1\" southCellEnabled=\"false\">\n");
			    
			    Cell cell2 = CellIDs.get("2");
			    Enemy enemy = null;
			    if (map.getEnemies().get(0).getPosition() == cell2) {
					enemy = map.getEnemies().get(0);
					writer.write("\t\t<enemy type=\"" + enemy.toString()
			    			+ "\" health=\"" + enemy.getHealthPoint()
			    			+ "\" actualSpeed=\"" + enemy.getActualSpeed()
			    			+ "\" magic=\"" + enemy.getMagic()
			    			+ "\"/>\n");
				}
			    
			    writer.write("\t</cell>\n");

			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="road" southCell="1" southCellEnabled="false">
        				<enemy type="elf" health="40" actualSpeed="1000" magic="50"/>
    				</cell>
    				<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("8-enemyAtCrossMoves")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");

			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\"/>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" southCell=\"1\" westCell=\"3\" eastCell=\"4\" "
			    		+ "southCellEnabled=\"false\" eastCellEnabled=\"true\" westCellEnabled=\"false\"/>\n");
			    
			    cell = CellIDs.get("3");
			    writer.write("\t<cell id=\"3\" type=\"" + cell.getCellType().toString()
			    		+ "\" eastCell=\"2\" eastCellEnabled=\"true\"/>\n");
			    
			    cell = CellIDs.get("4");
			    writer.write("\t<cell id=\"4\" type=\"" + cell.getCellType().toString()
			    		+ "\" westCell=\"2\" westCellEnabled=\"false\">\n");
			    
			    Cell cell4 = CellIDs.get("4");
			    Enemy enemy = null;
			    if (map.getEnemies().get(0).getPosition() == cell4) {
					enemy = map.getEnemies().get(0);
					writer.write("\t\t<enemy type=\"" + enemy.toString()
			    			+ "\" health=\"" + enemy.getHealthPoint()
			    			+ "\" actualSpeed=\"" + enemy.getActualSpeed()
			    			+ "\" magic=\"" + enemy.getMagic()
			    			+ "\"/>\n");
				}
			    
			    writer.write("\t</cell>\n");

			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			   <map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="road" southCell="1" westCell="3" eastCell="4" southCellEnabled="false" eastCellEnabled="true" westCellEnabled="false"/>
    				<cell id="3" type="road" eastCell="2" eastCellEnabled="true"/>
    				<cell id="4" type="road" westCell="2" westCellEnabled="false">
        				<enemy type="elf" health="40" actualSpeed="1000" magic="50"/>
    				</cell>
    				<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("9-towerShoots")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map sliceShootProbability=\"0\">\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\">\n");
			    
			    Cell cell1 = CellIDs.get("1");
			    Enemy enemy = null;
			    if (map.getEnemies().get(0).getPosition() == cell1) {
					enemy = map.getEnemies().get(0);
					writer.write("\t\t<enemy type=\"" + enemy.toString()
			    			+ "\" health=\"" + enemy.getHealthPoint()
			    			+ "\" actualSpeed=\"" + enemy.getActualSpeed()
			    			+ "\" magic=\"" + enemy.getMagic()
			    			+ "\"/>\n");
				}
			    
			    writer.write("\t</cell>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" westCell=\"3\"  southCell=\"1\" "
			    		+ "southCellEnabled=\"false\"/>\n");
			    
			    cell = CellIDs.get("3");
			    writer.write("\t<cell id=\"3\" type=\"" + cell.getCellType().toString()
			    		+ "\" eastCell=\"2\">\n");
			    
			    writer.write("\t\t<tower power=\"10\" attackSpeed=\"500\" range=\"1\"/>\n");
			    
			    writer.write("\t</cell>\n");
			    

			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map sliceShootProbability="0">
    				<cell id="1" type="road" northCell="2" northCellEnabled="true">
        				<enemy type="elf" health="30" actualSpeed="1000" magic="50"/>
    				</cell>
    				<cell id="2" type="road" westCell="3" southCell="1" southCellEnabled="false"/>
    				<cell id="3" type="terrain" eastCell="2">
        				<tower power="10" attackSpeed="500" range="1"/>
    				</cell>
    				<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("10-enemySliced")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map sliceShootProbability="1">
    				<cell id="1" type="road" northCell="2" northCellEnabled="true">
        				<enemy type="elf" health="15" actualSpeed="1000" magic="50"/>
        				<enemy type="elf" health="15" actualSpeed="1000" magic="50"/>
    				</cell>
    				<cell id="2" type="road" westCell="3" southCell="1" southCellEnabled="false"/>
    				<cell id="3" type="terrain" eastCell="2">
        				<tower power="10" attackSpeed="500" range="1"/>
    				</cell>
   					<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("11-putFog")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map fogApplianceTime="100" fogDecreason="5" fogDuration="5000">
    				<cell id="1" type="terrain" eastCell="2">
        				<tower power="10" attackSpeed="500" range="3" rangeDecreaseByFog="1" fogActive="true"/>
    				</cell>
    				<saruman magicPower="250"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("12-enemyObstacle")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="road" southCell="1" westCell="3" southCellEnabled="false">
        				<obstacle slowRate="10"/>
        				<enemy type="elf" health="40" actualSpeed="900" magic="50"/>
    				</cell>
    				<cell id="3" type="terrain" eastCell="2"/>
    				<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("13-Victory")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");
			    
			    Cell cell = CellIDs.get("1");
			    writer.write("\t<cell id=\"1\" type=\"" + cell.getCellType().toString()
			    		+ "\" northCell=\"2\" northCellEnabled=\"true\">\n");
			    
			    ArrayList<Enemy> enemies = map.getEnemies();
			    for(Enemy enemy : enemies){
			    	if (enemy.getPosition() == cell) {
			    		writer.write("\t\t<enemy type=\"" + enemy.toString()
			    			+ "\" health=\"" + enemy.getHealthPoint()
			    			+ "\" actualSpeed=\"" + enemy.getActualSpeed()
			    			+ "\" magic=\"" + enemy.getMagic()
			    			+ "\"/>\n");
			    	}
			    }
			    
			    writer.write("\t</cell>\n");
			    
			    cell = CellIDs.get("2");
			    writer.write("\t<cell id=\"2\" type=\"" + cell.getCellType().toString()
			    		+ "\" westCell=\"3\"  southCell=\"1\" "
			    		+ "southCellEnabled=\"false\"/>\n");
			    
			    cell = CellIDs.get("3");
			    writer.write("\t<cell id=\"3\" type=\"" + cell.getCellType().toString()
			    		+ "\" eastCell=\"2\">\n");
			    
			    ArrayList<Tower> towers = map.getTowers();
			    for(Tower tower : towers){
			    	if(tower.getPosition().equals(cell)){
			    		// Torony range hiba volt xml-ben
			    		tower.setRange(1);
			    		writer.write("\t\t<tower power=\"" + tower.getFirePower()
				    			+ "\" attackSpeed=\"" + tower.getAttackSpeed()
				    			+ "\" range=\"" + tower.getRange()
				    			+ "\"/>\n");
			    	}
			    }
			    
			    writer.write("\t</cell>\n");
			    

			    // Saruman mannajanak az utolso korbeni valtozasara nem szamoltunk az xmlben
			    saruman.changeMagicPowerBy(-50);
			    writer.write("\t<saruman magicPower=\"" + saruman.getMagicPower()
			    		+ "\"/>\n");
			    
			    // A Double erteknek a elvalasztojat pontra allitja es formazza az alakjat
			    Round round = map.getRound();
			    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
			    otherSymbols.setDecimalSeparator('.');
			    otherSymbols.setGroupingSeparator('.'); 
			    DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
			    // A round enemyNumber nem valtozik gyilkolasra, xml beli hiba
			    round.setEnemyNumber(0);
			    writer.write("\t<round enemyNumber=\"" + round.getEnemyNumber()
			    		+ "\" enemyAddingTime=\"" + round.getEnemyAddingTime()
			    		+ "\" enemyNumberMultiplier=\"" + df.format(round.getEnemyNumberMultiplier())
			    		+ "\" enemyAddingTimeMultiplier=\"" + df.format(round.getEnemyAddingTimeMultiplier())
			    		+ "\" roundTime=\"" + round.getRoundTime()
			    		+"\" maxRounds=\"" + round.getMaxRounds()
			    		+ "\"/>\n");
			    
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true">
    				</cell>
    				<cell id="2" type="road" westCell="3" southCell="1" southCellEnabled="false"/>
    				<cell id="3" type="terrain" eastCell="2">
        				<tower power="10" attackSpeed="500" range="1"/>
    				</cell>
    				<saruman magicPower="300"/>
    				<round enemyNumber="0" enemyAddingTime="1000" enemyNumberMultiplier="1.5" enemyAddingTimeMultiplier="2" roundTime="1" maxRounds="1"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		} else if(testcase.equals("14-Loss")){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("Tower/xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>\n");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true">
    				</cell>
    				<cell id="2" type="endpoint" southCell="1" southCellEnabled="false">
        				<enemy type="elf" health="40" actualSpeed="1000" magic="50"/>
    				</cell>
    				<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		}
	}
}
