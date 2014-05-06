package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Model.Obstacle;

/**
 * Egy akadalyt megjelenito osztaly.
 */
public class ObstacleView {
	/**
	 * Az akadaly objektum, amit az osztaly megjelenit.
	 */
	private Obstacle model;
	/**
	 * Az akadalyt abrazolo kep.
	 */
	private static BufferedImage imageObstacle;
	
	/**
	 * Konstruktor.
	 * @param model Az akadaly objektum, amit meg kell majd jeleniteni.
	 */
	public ObstacleView(Obstacle model) {
		this.setModel(model);
	}
	
	/**
	 * Setter az imageObstacle attributumra.
	 * @param image  Az imageObstacle attributum kivant erteke.
	 */
	public static void setImageObstacle(BufferedImage image)
	{
		imageObstacle = image;
	}
	
	/**
	 * Kirajzolja az akadalyt a kapott Graphics objektumra.
	 */
	public void draw(Graphics g) {
		// Kep, szelessegenek, magassaganak megadasa a cellahoz viszonyitva
		double wr = 0.9;
		double hr = 0.9;
		// A Graphics objektum mereteinek meghatarozasa
		int w = (int) g.getClipBounds().getWidth();
		int h = (int) g.getClipBounds().getHeight();
		
		// Padding szamitasa a cella szeleitol
		int wp = (int) (((1.0 - wr) / 2.0) * ((double)w));
		int hp = (int) (((1.0 - hr) / 2.0) * ((double)h));
		
		// Akadaly kirajzolasa
		g.drawImage(
				imageObstacle, 
				wp, 
				hp, 
				(int) ((double) w * wr), 
				(int) ((double) h * hr), 
				null
				);
	}

	public Obstacle getModel() {
		return model;
	}

	public void setModel(Obstacle model) {
		this.model = model;
	}
}