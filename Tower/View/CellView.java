package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import Model.Cell;
import Model.Cell.CellType;
import Model.Enemy;
import Model.Obstacle;
import Model.Tower;

/**
 * Egy cellat megjelenito osztaly.
 */
public class CellView extends JPanel{

	private static final long serialVersionUID = 1L;
	/**
	 * A cella objektum, amit az osztaly megjelenit.
	 */
	private Cell model;
	/**
	 * A terepet abrazolo kep.
	 */
	private static BufferedImage imageTerrain;
	/**
	 * Az utat abrazolo kep.
	 */
	private static BufferedImage imageRoad;
	/**
	 * A vegzet hegyet abrazolo kep.
	 */
	private static BufferedImage imageEndPoint;
	/**
	 * A cella szegelyenek vastagsaga.
	 */
	private static int strokeWidth = 4;
	
	/**
	 * Konstruktor.
	 * @param model A cella objektum, amit meg kell majd jeleniteni.
	 */
	public CellView(Cell model) {
		super();
		this.model = model;
	}
	
	/**
	 * Setter az imageTerrain attributumra.
	 * @param image  Az imageTerrain attributum kivant erteke.
	 */
	public static void setImageTerrain(BufferedImage image)
	{
		imageTerrain = image;
	}
	
	/**
	 * Setter az imageRoad attributumra.
	 * @param image  Az imageRoad attributum kivant erteke.
	 */
	public static void setImageRoad(BufferedImage image)
	{
		imageRoad = image;
	}
	
	/**
	 * Setter az imageEndPoint attributumra.
	 * @param image  Az imageEndPoint attributum kivant erteke.
	 */
	public static void setImageEndPoint(BufferedImage image)
	{
		imageEndPoint = image;
	}
	
	/**
	 * Ertesiti a CellView-t, hogy a model megvaltozott. 
	 * A CellView ujra fog rajzolodni.
	 */
	public void modelChanged() {
		repaint();
	}
	
	/**
	 * Kirajzolja a cellat es a rajta talalhato objektumokat a
	 *  kapott Graphics objektumra.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		// Kep, szelessegenek, magassaganak megadasara szolgalo valtozok
		// a cellahoz viszonyitva
		double wr;
		double hr;
		// Komponens szelessege es magassaga
		int w = getWidth();
		int h = getHeight();
		// Padding a cella szeleitol
		int wp;
		int hp;
		
		// CELLA KIRAJZOLASA
		// Beallitjuk a hatterkepet a cella tipusanak megfeleloen
		BufferedImage imageBackground = null;
		
		// Ha terep, akkor a hatter terep lesz
		if (model.getCellType() == CellType.Terrain) {
			imageBackground = imageTerrain;
		}
		// Ha nem terep, akkor a hatter ut lesz
		else {
			imageBackground = imageRoad;
		}
		
		// Kirajzoljuk a hatteret
		g.drawImage(imageBackground, 0, 0, getWidth(), getHeight(), null);
		
		// Kirajzoljuk a szegelyt
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(strokeWidth));
		g2.setColor(Color.BLACK);
		
		g2.drawLine(0, 0, getWidth(), 0);
		g2.drawLine(getWidth(), 0, getWidth(), getHeight());
		g2.drawLine(0, getHeight(), getWidth(), getHeight());
		g2.drawLine(0, 0, 0, getHeight());
		
		// Ha a cella egy vegpont akkor kirajzoljuk a vegzet hegyet
		if (model.getCellType() == CellType.EndPoint) {
			// Kep, szelessegenek, magassaganak megadasa a cellahoz viszonyitva
			wr = 0.9;
			hr = 0.9;
			
			// Padding szamitasa a cella szeleitol
			wp = (int) (((1.0 - wr) / 2.0) * ((double)w));
			hp = (int) (((1.0 - hr) / 2.0) * ((double)h));
			
			// Vegpont kirajzolasa
			g.drawImage(
					imageEndPoint, 
					wp, 
					hp, 
					(int) ((double) w * wr), 
					(int) ((double) h * hr), 
					null
					);
		}
		
		// Ha a cella terep, akkor kirajzoljuk a rajta levo tornyot
		if (model.getCellType() == CellType.Terrain) {
			// TORONY RAJZOLAS
			// Elkerjuk az abrazolando cellan levo tornyot
			Tower tower = model.getTower();
			
			// Ha van torony a cellan, akkor kirajzoljuk
			if (tower != null) {
				tower.getView().draw(g);
			}
		}
		// Ha a cella ut, akkor kirajzoljuk a rajta levo akadalyt
		// es az ellensegeket
		else {
			// AKADALY RAJZOLAS
			// Elkerjuk az abrazolando cellan levo akadalyt
			Obstacle obstacle = model.getObstacle();
			
			// Ha van akadaly a cellan, akkor kirajzoljuk
			if (obstacle != null) {
				obstacle.getView().draw(g);
			}
			
			// ELLENSEGEK RAJZOLASA
			// Elkerjuk az abrazolando cellan levo ellensegeket
			ArrayList<Enemy> enemies = model.getEnemys();
			
			// Ha vannak a cellan ellensegek, akkor mindegyiket kirajzoljuk
			if (enemies != null) {
				for (Enemy enemy : enemies) {
					enemy.getView().draw(g);
				}
			}
		}
	}
}
