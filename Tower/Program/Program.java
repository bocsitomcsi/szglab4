package Program;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.Cell;
import Model.Cell.CellType;
import Model.Map;
import Model.Saruman;
import View.CellView;
import View.ControlPanel;
import View.EnemyView;
import View.ObstacleView;
import View.TowerView;

public class Program {
	static Map map;
	static Saruman saruman;
	static JLabel resultLabel;
	
	/**
	 * Kep fajlok betoltese a View osztalyokba
	 */
	private static void loadViewImages(JFrame frame) {
		try {
			CellView.setImageTerrain(ImageIO.read(new File("images/cells/terrain.png")));
			CellView.setImageRoad(ImageIO.read(new File("images/cells/road.png")));
			CellView.setImageEndPoint(ImageIO.read(new File("images/cells/endpoint.png")));
			
			EnemyView.setImageDwarf(ImageIO.read(new File("images/enemies/dwarf.png")));
			EnemyView.setImageElf(ImageIO.read(new File("images/enemies/elf.png")));
			EnemyView.setImageHuman(ImageIO.read(new File("images/enemies/human.png")));
			EnemyView.setImageHobbit(ImageIO.read(new File("images/enemies/hobbit.png")));
			
			TowerView.setImageTower(ImageIO.read(new File("images/objects/tower.png")));
			TowerView.setImageTowerWithFog(ImageIO.read(new File("images/objects/towerWithFog.png")));
			
			ObstacleView.setImageObstacle(ImageIO.read(new File("images/objects/obstacle.png")));
			
			// Ha nem sikerul betolteni egy kepet, akkor hibat dobunk
			// es kilep az alkalmazas
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					frame, 
					"An image file not found!",
					"Error",
					JOptionPane.ERROR_MESSAGE
					);
			System.exit(-1);
		}
	}
	
	/**
	 * A gui felepitese es megjelenitese
	 */
	public static void createGui() {
		// Frame letrehozasa
		JFrame frame = new JFrame("Tower Defense");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(870, 700));
		
		// Kepek betoltese a View osztalyokhoz
		loadViewImages(frame);
		
		// Map inicializalasa es beolvasasa
		createAndLoadMap(frame);
		
		// CONTROL PANEL LETREHOZASA
		ControlPanel controlPanel = new ControlPanel(map, frame);
		controlPanel.modelChanged();
		map.setControlPanel(controlPanel);
		
		// MAP PANEL LETREHOZASA
		// Panel letrehozasa
		JPanel mapPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(map.getRowNumber(), map.getColumnNumber());
		mapPanel.setLayout(gridLayout);
		
		// CellView-k hozzaadasa a mapPanel-hez
		for (int i = 0; i < map.getRowNumber(); i++) {
			for (int j = 0; j < map.getColumnNumber(); j++) {
				for (final Cell cell : map.getCells()) {
					if (cell.getRowId() == i && cell.getColumnId() == j) {
						mapPanel.add(cell.getView());
						
						//Egerrel valo kijelolesre add listenert
						cell.getView().addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e)  
							{  
								if(cell.getCellType() == CellType.Road && map.getObstacleSelected() && !cell.getBusy())
								{
									saruman.addObstacle(cell);
									resultLabel.setText("Akadaly letrehozva");
									cell.getView().modelChanged();
								}
								else if(cell.getCellType() == CellType.Terrain && map.getTowerSelected() && !cell.getBusy())
								{
									saruman.addTower(cell);
									resultLabel.setText("Torony letrehozva");
									cell.getView().modelChanged();
								}
								else if(cell.getTower()!=null && !map.getStoneSelected().equals("none")){
									if(saruman.getMagicPower() > saruman.getMagicStoneCost()){
										saruman.createStone(map.getStoneSelected());
										Boolean result = saruman.upgradeItem(cell.getTower());
										if(result==true){
											resultLabel.setText("Torony fejlesztve");
										}
										else{
											saruman.changeMagicPowerBy(saruman.getMagicStoneCost());
											resultLabel.setText("Tornyot nem lehet tovabb fejleszteni");
										}
									}
									else{
										resultLabel.setText("Nincs eleg varazsero");
									}
								}
								else if(cell.getObstacle()!=null && !map.getStoneSelected().equals("none")){
									if(saruman.getMagicPower() > saruman.getMagicStoneCost()){
										saruman.createStone(map.getStoneSelected());
										Boolean result = saruman.upgradeItem(cell.getObstacle());
										if(result==true){									
											resultLabel.setText("Akadaly fejlesztve");
										}
										else{
											saruman.changeMagicPowerBy(saruman.getMagicStoneCost());
											resultLabel.setText("Akadalyt nem lehet tovabb fejleszteni");
										}
									}
									else{
										resultLabel.setText("Nincs eleg varazserod");
									}
								}
								else if(cell.getTower()!=null){
									/*
									int humanBonus = cell.getTower().getBonusPowers().get("human");
									int elfBonus = cell.getTower().getBonusPowers().get("elf");
									int hobbitBonus = cell.getTower().getBonusPowers().get("hobbit");
									int dwarfBonus = cell.getTower().getBonusPowers().get("dwarf");
									resultLabel.setText("Torony: tuzero: "+cell.getTower().getFirePower()+
											" sebesseg: " + cell.getTower().getAttackSpeed()+
											" tav: " + cell.getTower().getRange()+
											" bonusz: ("+ 
											" ember: " + humanBonus +
											" tunde: " + elfBonus +
											" hobbit: " + hobbitBonus +
											" torpe: " + dwarfBonus +")"
											);
									*/
									resultLabel.setText("Torony: tuzero: "+cell.getTower().getFirePower()+
											" sebesseg: " + cell.getTower().getAttackSpeed()+
											" tav: " + cell.getTower().getRange()
											);
								}
								else if(cell.getObstacle()!=null){
									/*
									Double humanBonus = cell.getObstacle().getBonusSlowRates().get("human");
									Double elfBonus = cell.getObstacle().getBonusSlowRates().get("elf");
									Double hobbitBonus = cell.getObstacle().getBonusSlowRates().get("hobbit");
									Double dwarfBonus = cell.getObstacle().getBonusSlowRates().get("dwarf");
									resultLabel.setText("Akadaly: lassitas: "+cell.getObstacle().getSlowRate()+
											" bonusz: ("+ 
											" ember: " + humanBonus +
											" tunde: " + elfBonus +
											" hobbit: " + hobbitBonus +
											" torpe: " + dwarfBonus +")");
									*/
									resultLabel.setText("Akadaly: lassitas: "+cell.getObstacle().getSlowRate()
											);
								}
								else
								{
									resultLabel.setText("Nem helyes opcio");
								}
							}
							});
						break;
					}
				}
			}
		}
		
		JPanel resultPanel = new JPanel();
		resultLabel = new JLabel();
		
		resultPanel.add(resultLabel);
		resultLabel.setText("Az inditas gombal kezdodhet a jatek!");
		
		// Control es Map panelek hozzaadasa a frame-hez
		frame.add(controlPanel, BorderLayout.NORTH);
		frame.add(mapPanel, BorderLayout.CENTER);
		frame.add(resultPanel, BorderLayout.SOUTH);
		// Frame megjelenitese
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		// GUI felepitese egy szalon
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createGui();
			}
		});
	}

	private static void createAndLoadMap(JFrame frame) {
		map = new Map(4);
		saruman = new Saruman(map);
		map.setSaruman(saruman);
		boolean loadResult = map.loadFromFile("maps/map1.xml");
		// Ha nem sikerul a beolvasas hibat dobunk es kilepunk
		if (!loadResult || (map.getRowNumber() == 0 && map.getColumnNumber() == 0)) {
			JOptionPane.showMessageDialog(
					frame, 
					"Couldn't load the map!",
					"Error",
					JOptionPane.ERROR_MESSAGE
					);
			System.exit(-1);
		}
	}
	
	public static void setLabelText(String text){
		resultLabel.setText(text);
	}
}
