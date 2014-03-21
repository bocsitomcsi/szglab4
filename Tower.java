import java.util.ArrayList;
import java.util.HashMap;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Tower.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




public class Tower extends Item
{
	private int firePower;
	private int attackSpeed;
	private int range;
	private HashMap<String, Integer> bonusPowers;
	private long lastTime;
	private Map map;	//itt hogy fogja a konkr�t egy db mappot megkapni?
	
	//Constructor
	public Tower(int power, int as, int r, long lt, int mm, ArrayList<MagicStone> ms)
	{
		super(mm,ms);
		firePower = power;
		attackSpeed = as;
		range = r;
		bonusPowers = new HashMap<String,Integer>();
		lastTime = lt;
	}
	
	//Getters
	public int getFirePower()
	{
		return this.firePower;
	}
	
	public int getattackSpeed()
	{
		return this.attackSpeed;
	}
	
	public int getRange()
	{
		return this.range;
	}

	public long getLastTime()
	{
		return this.lastTime;
	}
	
	public HashMap<String,Integer> getBonusPowers()
	{
		return this.bonusPowers;
	}
	
	//Setters
	public void setFirePower(int power)
	{
		this.firePower = power;
	}
	
	public void setAttackSpeed(int as)
	{
		this.attackSpeed = as;
	}
	
	public void setRange(int r)
	{
		this.range = r;
	}
	
	public void setlasttime(long lt)
	{
		this.lastTime = lt;
	}
	
	public void setBonusPowers(HashMap<String,Integer> bonus)
	{
		this.bonusPowers = (HashMap<String,Integer>)bonus.clone();
	}
	
	//Methods
	public boolean upgrade(MagicStone stone)
	{
		return true;
	}
	
	public void shoot()
	{
	
	}
	
	public void tick()
	{
	
	}
}
