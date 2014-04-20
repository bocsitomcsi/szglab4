package Tower;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import Tower.Cell.CellType;

public class Program {
	static String testcase;
	static String inputfile;
	static String outputfile;
	static HashMap<String, Cell> CellIDs;
	
	static Map map;
	static Saruman saruman;
	static Round round;

	public static void main(String[] args) {
		try {
			File tc = new File(args[0]);
			DocumentBuilder casedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document tcdoc = casedBuilder.parse(tc);
			if(!tcdoc.getDocumentElement().getNodeName().equals("testCase")){
				System.out.println("The file input is wrong");
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
		CellIDs = new HashMap<String, Cell>();
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
							System.out.println("Wrong file: " + inputfile);
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
					xmlSave();
				} else if (nodeName.equals("addTower")) {
					if(saruman.getMagicPower() > saruman.getTowerCost()){
						saruman.changeMagicPowerBy((-1)*saruman.getTowerCost());
						Cell cellatid = CellIDs.get(((Element)tempNode).getAttribute("CellId"));
						Tower tower = new Tower(cellatid,map);
					
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
					if(saruman.getMagicPower() > saruman.getObstacleCost()){
						saruman.changeMagicPowerBy((-1)*saruman.getObstacleCost());
						Cell cellatid = CellIDs.get(((Element)tempNode).getAttribute("CellId"));
						Obstacle obs = new Obstacle(cellatid);
					
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
						Cell cellatid = CellIDs.get(((Element)tempNode).getAttribute("CellId"));
						//TODO: get tower on the cell with the ID
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
		
		CellIDs.put(Node.getAttribute("id"), cell);
		
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
	
	private static void xmlSave(){
		if(testcase == "1-startGame"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "2-putTower"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "3-putObstacle"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "4-createMagicStone"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			    <map>
    				<cell id="1" type="road"/>
    				<saruman magicPower="200" magicStoneCost="50" magicStone="purple"/>
				</map>
			    */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "5-upgradeTower"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "6-upgradeObstacle"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "7-enemyMoves"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "8-enemyAtCrossMoves"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "9-towerShoots"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "10-enemySliced"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "11-putFog"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "12-enemyObstacle"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "13-Victory"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="road" westCell="3" southCell="1" southCellEnabled="false"/>
    				<cell id="3" type="terrain" eastCell="2">
        				<tower power="10" attackSpeed="500" range="1"/>
    				</cell>
    				<saruman magicPower="300"/>
    				<round enemyNumber="0" enemyAddingTime="1000" enemyNumberMultiplier="1.5" enemyAddingTimeMultiplier="2" roundTime="1" maxRounds="1"/>
				</map>
			    */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		} else if(testcase == "14-Loss"){
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("xml/"+outputfile), "utf-8"));
			    
			    writer.write("<map>");
			    //TODO: Rendes sorrendben kiirni
			    writer.write("</map>");
			    /*Elvart kimenet:
			   	<map>
    				<cell id="1" type="road" northCell="2" northCellEnabled="true"/>
    				<cell id="2" type="endpoint" southCell="1" southCellEnabled="false">
        				<enemy type="elf" health="40" actualSpeed="1000" magic="50"/>
    				</cell>
    				<saruman magicPower="300"/>
				</map>
			    */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			   try {
				   writer.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			}
		}
	}
}
