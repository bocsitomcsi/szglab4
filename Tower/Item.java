package Tower;
import java.util.ArrayList;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Item.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




/**
 * A palyara rakhato objektumok osszefoglalo absztrakt ososztaly.
 */
public abstract class Item
{
	/**
	 * Az objektumra rakhato varazskovek maximalis szama.
	 */
	protected int maxMagicStoneNumber;
	/**
	 * Az objektum pozicioja.
	 */
	protected Cell position;
	/**
	 * Az objektumra helyezett varazskovek listaja.
	 */
	protected ArrayList<MagicStone> magicStones;
	
	/**
	 * Konstruktor.
	 * @param mm  Az objektumra rakhato varazskovek maximalis szama.
	 * @param pos  Az objektum pozicioja.
	 */
	public Item(int mm, Cell pos)
	{
		this.maxMagicStoneNumber = mm;
		this.magicStones = new ArrayList<MagicStone>();
		this.position = pos;
	}

	/**
	 * Getter a maxMagicStoneNumber attributumra.
	 * @return  A maxMagicStoneNumber attributum.
	 */
	public int getMaxMagicStoneNumber()
	{
		return this.maxMagicStoneNumber;
	}

	/**
	 * Getter a position attributumra.
	 * @return  A position attributum.
	 */
	public Cell getPosition()
	{
		String logString = "Item.getPosition()";
		Logger.Log(1, logString, this);

		Logger.Log(0, logString, this);
		return this.position;
	}
/**
	 * Getter a magicStones attributumra.
	 * @return  A magicStones attributum.
	 */
	public ArrayList<MagicStone> getMagicStone()
	{
		return this.magicStones;
	}
	
	/**
	 * Setter a maxMagicStoneNumber attributumra.
	 * @param b  A maxMagicStoneNumber attributum kivant erteke.
	 */
	public void setMaxMagicStoneNumber(int mm)
	{
		this.maxMagicStoneNumber = mm;
	}
	
	/**
	 * Setter a magicStones attributumra.
	 * @param b  A magicStones attributum kivant erteke.
	 */
	public void setMagicStone(ArrayList<MagicStone> ms)
	{
		this.magicStones = ms;
	}
	
	/**
	 * Fejleszti az objektumot.
	 * @param stone  A varazsko.
	 * @return A fejlesztes sikeressege.
	 */
	public abstract boolean upgrade(MagicStone stone);
}



