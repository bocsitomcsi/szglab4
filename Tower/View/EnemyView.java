package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Model.Dwarf;
import Model.Elf;
import Model.Enemy;
import Model.Hobbit;
import Model.Human;

/**
 * Egy ellenseget megjelenito osztaly.
 */
public class EnemyView {
	/**
	 * Az enemy objektum, amit az osztaly megjelenit.
	 */
	private Enemy model;
	/**
	 * Az ellenseget abrazolo kep. 
	 * A konstruktorban kell beallitani
	 *  a model tipusahoz(dwarf, elf, hobbit, human) tartozo
	 *  kepre.
	 */
	private BufferedImage imageEnemy;
	/**
	 * A torpot abrazolo kep.
	 */
	private static BufferedImage imageDwarf;
	/**
	 * Az embert abrazolo kep.
	 */
	private static BufferedImage imageHuman;
	/**
	 * Az elfet abrazolo kep.
	 */
	private static BufferedImage imageElf;
	/**
	 * A hobbitot abrazolo kep.
	 */
	private static BufferedImage imageHobbit;
	
	/**
	 * Konstruktor.
	 * @param model Az ellenseg objektum, amit meg kell majd jeleniteni.
	 * @param enemyType Az abrazolando ellenseg tipusa.
	 */
	public EnemyView(Enemy model, String enemyType) {
		this.model = model;

		// Beallitjuk az imageEnemy valtozot az abrazolando 
		// ellensegnek megfelelo kepre
		if (enemyType.equals("human")) {
			this.imageEnemy = imageHuman;
		} else if (enemyType.equals("elf")) {
			this.imageEnemy = imageElf;
		} else if (enemyType.equals("dwarf")) {
			this.imageEnemy = imageDwarf;
		} else if (enemyType.equals("hobbit")) {
			this.imageEnemy = imageHobbit;
		}
	}
	
	/**
	 * Setter az imageDwarf attributumra.
	 * @param image  Az imageDwarf attributum kivant erteke.
	 */
	public static void setImageDwarf(BufferedImage image)
	{
		imageDwarf = image;
	}
	
	/**
	 * Setter az imageElf attributumra.
	 * @param image  Az imageElf attributum kivant erteke.
	 */
	public static void setImageElf(BufferedImage image)
	{
		imageElf = image;
	}
	
	/**
	 * Setter az imageHuman attributumra.
	 * @param image  Az imageHuman attributum kivant erteke.
	 */
	public static void setImageHuman(BufferedImage image)
	{
		imageHuman = image;
	}
	
	/**
	 * Setter az imageHobbit attributumra.
	 * @param image  Az imageHobbit attributum kivant erteke.
	 */
	public static void setImageHobbit(BufferedImage image)
	{
		imageHobbit = image;
	}
	
	/**
	 * Kirajzolja a kapott az ellenseget a Graphics objektumra.
	 */
	public void draw(Graphics g) {
		// Kep, szelessegenek, magassaganak megadasa a cellahoz viszonyitva
		double wr = 0.75;
		double hr = 0.75;
		// A Graphics objektum mereteinek meghatarozasa
		int w = (int) g.getClipBounds().getWidth();
		int h = (int) g.getClipBounds().getHeight();
		
		// Padding szamitasa a cella szeleitol
		int wp = (int) (((1.0 - wr) / 2.0) * ((double)w));
		int hp = (int) (((1.0 - hr) / 2.0) * ((double)h));
		
		// Kep kirajzolasa
		g.drawImage(
				imageEnemy, 
				wp, 
				hp, 
				(int) ((double) w * wr), 
				(int) ((double) h * hr), 
				null
				);
	}
}