package Tower;
import java.util.HashMap;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Elf.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




/**
 * Elf ellenseg.
 */
public class Elf extends Enemy
{
	/**
	 * Konstruktor.
	 * @param hp  eletero.
	 * @param as  Az aktualis sebesseg.
	 * @param os  Az eredeti, lassitas nelkuli sebesseg.
	 * @param m  Az ellenseg halalakor szarumanhoz kerulo varazsero.
	 * @param lt  Az az idopont amikor az ellenseg legutoljara lepett.
	 */
	public Elf(int hp, int as, int os, int m, long lt) {
		super(hp, as, os, m, lt);
	}

	public boolean damage(int power, HashMap<String,Integer> bonus)
	{
		return true;
	}
	
	public void move()
	{
	
	}
}
