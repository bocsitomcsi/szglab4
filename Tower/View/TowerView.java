package View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import Model.Tower;

/**
 * Egy tornyot megjelenito osztaly.
 */
public class TowerView {
	/**
	 * A torony objektum, amit az osztaly megjelenit.
	 */
	private Tower model;
	/**
	 * A tornyot abrazolo kep.
	 */
	private static BufferedImage imageTower;
	/**
	 * A kodos tornyot abrazolo kep.
	 */
	private static BufferedImage imageTowerWithFog;	
	
	/**
	 * Konstruktor.
	 * @param model A torony objektum, amit meg kell majd jeleniteni.
	 */
	public TowerView(Tower model) {
		this.model = model;
	}
	
	/**
	 * Setter az imageTower attributumra.
	 * @param image  Az imageTower attributum kivant erteke.
	 */
	public static void setImageTower(BufferedImage image)
	{
		imageTower = image;
	}
	
	/**
	 * Setter az imageTowerWithFog attributumra.
	 * @param image  Az imageTowerWithFog attributum kivant erteke.
	 */
	public static void setImageTowerWithFog(BufferedImage image)
	{
		imageTowerWithFog = image;
	}
	
	/**
	 * Kirajzolja a tornyot a kapott Graphics objektumra.
	 */
	public void draw(Graphics g) {
		// Kep, szelessegenek, magassaganak megadasa a cellahoz viszonyitva
		double wr = 0.7;
		double hr = 0.9;
		// A Graphics objektum mereteinek meghatarozasa
		int w = (int) g.getClipBounds().getWidth();
		int h = (int) g.getClipBounds().getHeight();
		
		// Padding szamitasa a cella szeleitol
		int wp = (int) (((1.0 - wr) / 2.0) * ((double)w));
		int hp = (int) (((1.0 - hr) / 2.0) * ((double)h));
		
		// Kirajzolando kep kivalasztasa a tornyon levo
		// kod fuggvenyeben
		BufferedImage image;
		if (model.getFogActive()) {
			image = imageTowerWithFog;
		}
		else {
			image = imageTower;
		}
		
		// Torony kirajzolasa
		g.drawImage(
				image, 
				wp, 
				hp, 
				(int) ((double) w * wr), 
				(int) ((double) h * hr), 
				null
				);
	}
}