package View;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Model.Map;
import Model.Saruman;
import Model.Tower;


public class ControlPanel extends JPanel {
	/**
	 * A map referenciaja
	 */
	Map map;
	
	
	private JFrame frame;
	
	private JButton btnStart;
	private JButton btnExit;
	private JLabel labelTower;
	private JLabel labelSaruman;
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
	
	
	/**
	 * Konstruktor. 
	 * Letrehozza a panelen talalhato komponenseket.
	 * @param map A map referenciaja
	 * @param f A Frame referenciaja
	 */
	public ControlPanel(Map map, JFrame f) {
		this.map = map;
		this.frame = f;
		
		// Komponensek letrehozasa, layout, default ertekek

		// Ikonokat tartalmazo labelek letrehozasa es az 
		// AncestorListener-ek hozzarendelese amik majd a 
		// JLabel-ek betoltodesekor beallitjak a labelek ikonjat
		
		// Sarumant megjelenito label
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
				Image imageResized = image.getScaledInstance(labelSaruman.getWidth(), labelSaruman.getHeight(), Image.SCALE_SMOOTH);
				labelSaruman.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
		
		// A tornyot megjelenito label
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
				Image imageResized = image.getScaledInstance(labelTower.getWidth(), labelTower.getHeight(), Image.SCALE_SMOOTH);
				labelTower.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
		
		// Az akadalyt megjelenito label
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
				Image imageResized = image.getScaledInstance(labelObstacle.getWidth(), labelObstacle.getHeight(), Image.SCALE_SMOOTH);
				labelObstacle.setIcon(new ImageIcon(imageResized));
			}
	
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}
	
			@Override
			public void ancestorRemoved(AncestorEvent event) {
	
			}
		});
		
		// A lila varazskovet megjelenito label
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
				Image imageResized = image.getScaledInstance(labelPurpleStone.getWidth(), labelPurpleStone.getHeight(), Image.SCALE_SMOOTH);
				labelPurpleStone.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
		
		// A zold varazskovet megjelenito label
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
				Image imageResized = image.getScaledInstance(labelGreenStone.getWidth(), labelGreenStone.getHeight(), Image.SCALE_SMOOTH);
				labelGreenStone.setIcon(new ImageIcon(imageResized));
			}
	
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}
	
			@Override
			public void ancestorRemoved(AncestorEvent event) {
	
			}
		});
		
		// A cian varazskovet megjelenito label
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
				Image imageResized = image.getScaledInstance(labelCyanStone.getWidth(), labelCyanStone.getHeight(), Image.SCALE_SMOOTH);
				labelCyanStone.setIcon(new ImageIcon(imageResized));
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}
		});
		
		// GUI epites
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_1 = new JSeparator();
		
		labelMagicPower = new JLabel("Magic power: ");
		labelMagicPower.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		labelTowerCost = new JLabel("Cost: ");
		
		labelObstacleCost = new JLabel("Cost: ");
		labelObstacleCost.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelRound = new JLabel("ROUND: ");
		labelRound.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		labelEnemy = new JLabel("ENEMY: ");
		labelEnemy.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		labelStoneCost = new JLabel("Cost:");
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		
		// Start es Stop gombok esemenykezeloinek beregisztralasa
		btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onStartButtonClicked();
			}
		});
		
		btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onExitButtonClicked();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelMagicPower))
						.addComponent(labelSaruman, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(labelPurpleStone, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(labelStoneCost)
										.addComponent(labelGreenStone, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
								.addComponent(labelTowerCost))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(44)
									.addComponent(labelCyanStone, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
								.addComponent(labelObstacleCost))
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(labelTower, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(labelObstacle, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelRound)
						.addComponent(labelEnemy))
					.addGap(60)
					.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnExit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(137))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(labelSaruman, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelMagicPower)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(separator, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(labelTower, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(labelTowerCost))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(labelObstacle, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(labelObstacleCost)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(labelCyanStone, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addComponent(labelPurpleStone, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addComponent(labelGreenStone, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addComponent(labelStoneCost))
								.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(labelRound)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(labelEnemy)
									.addGap(92))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
								.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 138, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		setLayout(groupLayout);
	}
	
	
	
	/**
	 * A map adatainak megvaltozasakor hivodik meg.
	 * Frissiti az adatokat a Control Panelen.
	 */
	public void modelChanged() {
		Saruman saruman = map.getSaruman();
		// Varazsero frissitese
		labelMagicPower.setText("Magic power: " + saruman.getMagicPower());
		
		// Tower, obstacle es magicstone arainak frissitese
		labelTowerCost.setText("Cost: " + saruman.getTowerCost());
		labelObstacleCost.setText("Cost: " + saruman.getObstacleCost());
		labelStoneCost.setText("Cost: " + saruman.getMagicStoneCost());
		
		// Kor sorszamanak frissitese
		labelRound.setText("ROUND: " + map.getRoundNumber());
		
		// TODO: Ellensegek szamanak frissitese
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
