import java.util.ArrayList;
import java.util.HashMap;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Obstacle.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




public class Obstacle extends Item
{
	private double slowRate;
	private HashMap<String, Double> bonusSlowRates;

	//Constructor
	public Obstacle(double sr, int mm)
	{
		super(mm);
		this.slowRate = sr;
		bonusSlowRates = new HashMap<String,Double>();
	}

	//Getter
	public double getSlowRate()
	{
		return this.slowRate;
	}

	public HashMap<String,Double> getBonusSlowRates()
	{
		return this.bonusSlowRates;
	}

	//Setter
	public void setSlowRate(double sr)
	{
		this.slowRate = sr;
	}

	public void setBonusSlowRates(HashMap<String,Double> bonus)
	{
		this.bonusSlowRates = (HashMap<String,Double>)bonus.clone();
	}

	public boolean upgrade(MagicStone stone)
	{
		return true;
	}
}
