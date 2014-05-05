package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Model.Map;
import Model.Saruman;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * A map referenciaja
	 */
	Map map;
	
	
	private JFrame frame;
	
	private JButton btnStart;
	private JButton btnExit;
	private JLabel labelTower;
	private JLabel labelSaruman;
	private JLabel labelSauron;
	private JLabel labelMagicPower;
	private JLabel labelTowerCost;
	private JLabel labelObstacleCost;
	private JLabel labelStoneCost;
	private JLabel labelEnemy;
	private JLabel labelRound;
	private JLabel labelPurpleStone;
	private JLabel labelGreenStone;
	private JLabel labelCyanStone;
	private JLabel labelObstacle;
	
	private JPanel SarumanPanel;
	private JPanel SauronPanel;
	private JPanel TowerPanel;
	private JPanel ObstaclePanel;
	private JPanel StonesPanel;
	private JPanel CombinedItemPanel;
	private JPanel CombinedStonesPanel;
	private JPanel ChoosableItemPanel;
	private JPanel InformationPanel;
	private JPanel GameInformationPanel;
		
	/**
	 * Konstruktor. 
	 * Letrehozza a panelen talalhato komponenseket.
	 * @param map A map referenciaja
	 * @param f A Frame referenciaja
	 */
	public ControlPanel(Map map, JFrame f) {
		this.map = map;
		this.frame = f;
		
		// ControlPanel alap elrendezes beallitasa
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.LEADING);
		this.setLayout(flow);
		
		// Default border beallitasa
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		// Komponensek letrehozasa, layout, default ertekek

		// Ikonokat tartalmazo labelek letrehozasa es az 
		// AncestorListener-ek hozzarendelese amik majd a 
		// JLabel-ek betoltodesekor beallitjak a labelek ikonjat
		
		/***Sarumant megjelenito label***/
		SarumanPanel = new JPanel(new BorderLayout());
		
		labelSaruman = new JLabel();
		labelSaruman.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("images/objects/saruman.png"));
					// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
				Image imageResized = image.getScaledInstance(160,200, Image.SCALE_FAST);
				labelSaruman.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
				
		labelMagicPower = new JLabel("Var�zser�: ");
		labelMagicPower.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelMagicPower.setHorizontalAlignment(SwingConstants.CENTER);
		
		SarumanPanel.setBorder(loweredbevel);
		SarumanPanel.add(labelSaruman,BorderLayout.NORTH);
		SarumanPanel.add(labelMagicPower, BorderLayout.CENTER);
		/************************************/
		
		
		CombinedItemPanel = new JPanel(new FlowLayout());
		CombinedItemPanel.setBorder(loweredbevel);
		
		//TODO A toronyhoz es obstaclehoz addMousListener metodus definialasa
		/***A tornyot megjelenito label***/
		TowerPanel = new JPanel(new BorderLayout());

		labelTower = new JLabel();
		labelTower.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("images/objects/tower.png"));
					// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
				Image imageResized = image.getScaledInstance(100, 100, Image.SCALE_FAST);
				labelTower.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
		
		labelTowerCost = new JLabel("Torony �ra: ");
		labelTowerCost.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelTowerCost.setHorizontalAlignment(SwingConstants.CENTER);
		
		TowerPanel.add(labelTower,BorderLayout.NORTH);
		TowerPanel.add(labelTowerCost, BorderLayout.CENTER);

		CombinedItemPanel.add(TowerPanel);
		
		labelTower.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
				JOptionPane.showMessageDialog(null,"Ez a torony");
		    }
		});
		/************************************/
		
		/***Az akadalyt megjelenito label***/
		ObstaclePanel = new JPanel(new BorderLayout());
		
		labelObstacle = new JLabel();
		labelObstacle.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("images/objects/obstacle.png"));
					// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
				Image imageResized = image.getScaledInstance(100, 100, Image.SCALE_FAST);
				labelObstacle.setIcon(new ImageIcon(imageResized));
			}
	
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}
	
			@Override
			public void ancestorRemoved(AncestorEvent event) {
	
			}
		});

		labelObstacleCost = new JLabel("Akad�ly �ra: ");
		labelObstacleCost.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelObstacleCost.setHorizontalAlignment(SwingConstants.CENTER);
		
		ObstaclePanel.add(labelObstacle,BorderLayout.NORTH);
		ObstaclePanel.add(labelObstacleCost, BorderLayout.CENTER);
		
		labelObstacle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
				JOptionPane.showMessageDialog(null,"Ez az akadaly");
		    }
		});
		
		CombinedItemPanel.add(ObstaclePanel);
		/*************************************/
		
		/***Kovek megjelenitese***/
		StonesPanel = new JPanel(new FlowLayout());
		CombinedStonesPanel = new JPanel(new BorderLayout());
		CombinedStonesPanel.setBorder(loweredbevel);
		
		/***A lila varazskovet megjelenito label***/
		labelPurpleStone = new JLabel();
		labelPurpleStone.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("images/objects/purplestone.png"));
					// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
				Image imageResized = image.getScaledInstance(50,50, Image.SCALE_FAST);
				labelPurpleStone.setIcon(new ImageIcon(imageResized));
			}	
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});

		labelPurpleStone.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
				JOptionPane.showMessageDialog(null,"Ez a lilak�");
		    }
		});
		
		StonesPanel.add(labelPurpleStone);
		/**************************************/
		
		/***A zold varazskovet megjelenito label***/
		labelGreenStone = new JLabel();
		labelGreenStone.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("images/objects/greenstone.png"));
					// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
				Image imageResized = image.getScaledInstance(50,50,Image.SCALE_FAST);
				labelGreenStone.setIcon(new ImageIcon(imageResized));
			}
	
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}
	
			@Override
			public void ancestorRemoved(AncestorEvent event) {
	
			}
		});
		
		labelGreenStone.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
				JOptionPane.showMessageDialog(null,"Ez a z�ldk�");
		    }
		});
		
		StonesPanel.add(labelGreenStone);
		/**************************************/
		
		/***A cian varazskovet megjelenito label***/
		labelCyanStone = new JLabel();
		labelCyanStone.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("images/objects/cyanstone.png"));
					// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
				Image imageResized = image.getScaledInstance(50,50,Image.SCALE_FAST);
				labelCyanStone.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
		
		labelCyanStone.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
				JOptionPane.showMessageDialog(null,"Ez a ci�n k�");
		    }
		});
		
		StonesPanel.add(labelCyanStone);
		/***************************************/
		
		labelStoneCost = new JLabel("K� �ra:");
		labelStoneCost.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelStoneCost.setHorizontalAlignment(SwingConstants.CENTER);
		
		CombinedStonesPanel.add(StonesPanel, BorderLayout.NORTH);
		CombinedStonesPanel.add(labelStoneCost, BorderLayout.CENTER);
		/****************************************/

		ChoosableItemPanel = new JPanel(new BorderLayout());
		ChoosableItemPanel.setBorder(loweredbevel);
		ChoosableItemPanel.add(CombinedItemPanel, BorderLayout.NORTH);
		ChoosableItemPanel.add(CombinedStonesPanel, BorderLayout.CENTER);
		
		/***Informaciospanel megjelenitese***/
		InformationPanel = new JPanel(new BorderLayout());
		InformationPanel.setBorder(loweredbevel);
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.HORIZONTAL);
		
		labelRound = new JLabel("K�r: ");
		labelRound.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		labelEnemy = new JLabel("Ellens�gek sz�ma: ");
		labelEnemy.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		InformationPanel.add(labelRound, BorderLayout.NORTH);
		InformationPanel.add(separator, BorderLayout.CENTER);
		InformationPanel.add(labelEnemy, BorderLayout.SOUTH);
		/***************************************/
		
		/***Start es Stop gombok esemenykezeloinek beregisztralasa***/
		JPanel btnStartPanel = new JPanel(new FlowLayout());
		
		btnStart = new JButton("Ind�t�s");
		btnStart.setPreferredSize(new Dimension(90,60));
		btnStart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onStartButtonClicked();
					}
				});
		
		JPanel btnExitPanel = new JPanel(new FlowLayout());
		btnExit = new JButton("Kil�p�s");
		btnExit.setPreferredSize(new Dimension(90,60));
		btnExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onExitButtonClicked();
					}
				});
		
		btnStartPanel.add(btnStart);
		btnExitPanel.add(btnExit);
		/***************************************/
		
		GameInformationPanel = new JPanel(new BorderLayout());
		GameInformationPanel.setBorder(loweredbevel);
		
		GameInformationPanel.add(btnStartPanel, BorderLayout.NORTH);
		GameInformationPanel.add(btnExitPanel, BorderLayout.CENTER);
		
		GameInformationPanel.add(InformationPanel, BorderLayout.SOUTH);
		GameInformationPanel.setPreferredSize(new Dimension(270,220));
				
		/***Sauront megjelenito label***/
		SauronPanel = new JPanel(new BorderLayout());
				
		labelSauron = new JLabel();
		labelSauron.addAncestorListener(new AncestorListener() {
					@Override
					public void ancestorAdded(AncestorEvent arg0) {
						BufferedImage image = null;
						try {
							image = ImageIO.read(new File("images/objects/sauron.png"));
							// Ha nem sikerul betolteni a kepet, akkor hibat dobunk
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
						Image imageResized = image.getScaledInstance(180,220, Image.SCALE_FAST);
						labelSauron.setIcon(new ImageIcon(imageResized));
					}

					@Override
					public void ancestorMoved(AncestorEvent event) {
						
					}

					@Override
					public void ancestorRemoved(AncestorEvent event) {

					}
				});
		
		SauronPanel.setBorder(loweredbevel);
		SauronPanel.add(labelSauron,BorderLayout.CENTER);
		/************************************/
		
		this.add(SarumanPanel);
		this.add(ChoosableItemPanel);
		this.add(GameInformationPanel);
		this.add(SauronPanel);
	}
	
	
	
	/**
	 * A map adatainak megvaltozasakor hivodik meg.
	 * Frissiti az adatokat a Control Panelen.
	 */
	public void modelChanged() {
		Saruman saruman = map.getSaruman();
		// Varazsero frissitese
		labelMagicPower.setText("Var�zser�: " + saruman.getMagicPower());
		
		// Tower, obstacle es magicstone arainak frissitese
		labelTowerCost.setText("�r: " + saruman.getTowerCost());
		labelObstacleCost.setText("�r: " + saruman.getObstacleCost());
		labelStoneCost.setText("�r: " + saruman.getMagicStoneCost());
		
		// Kor sorszamanak frissitese
		labelRound.setText("K�r: " + map.getRoundNumber());
		
		// Ellens�gek sz�m�nak visszadad�sa
		labelEnemy.setText("Ellens�gek sz�ma: " + map.getEnemies().size());
	}
	
	/**
	 * Start gomb megnyomasat kezelo fuggveny.
	 */
	public void onStartButtonClicked() {
		// Elinditjuk a jatekot
		map.simulateWorld();
	}
	
	/**
	 * Exit gomb megnyomasat kezelo fuggveny.
	 */
	public void onExitButtonClicked() {
		// Kilepunk az alkalmazasbol
		System.exit(1);
	}
}
