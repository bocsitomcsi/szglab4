import java.util.HashMap;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Dwarf.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




public class Dwarf extends Enemy
{
	public Dwarf(int hp, int as, int os, int m, boolean dead, long lt) {
		super(hp, as, os, m, dead, lt);
	}

	public boolean damage(int power, HashMap<String,Integer> bonus)
	{
		return true;
	}
	
	public void move()
	{
	
	}
}
