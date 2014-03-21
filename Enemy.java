import java.util.HashMap;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Enemy.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




public abstract class Enemy
{
	private int healthPoint;
	private int actualSpeed;
	private int originalSpeed;
	private int magic;
	private boolean isDead;
	private long lastTime;
	public static HashMap<String, Integer> enemySpeeds;
	private Cell position;

	//Constructor
	public Enemy(int hp, int as, int os, int m, boolean dead, long lt)
	{
		this.healthPoint = hp;
		this.actualSpeed = as;
		this.originalSpeed = os;
		this.magic = m;
		this.isDead = dead;
		this.lastTime = lt;
		
		//Itt fel is t�lts�k a static adattagot?
		
		//itt is hasonl� progbl�ma mint item-ben
	}
	
	//Getter
	public int getHealthPoint()
	{
		return this.healthPoint;
	}
	
	public int getActualSpeed()
	{
		return this.actualSpeed;
	}
	
	public int getOriginalSpeed()
	{
		return this.originalSpeed;
	}
	
	public int getMagic()
	{
		return this.magic;
	}
	
	public long getLastTime()
	{
		return this.lastTime;
	}
	
	public boolean getIsDead()
	{
		return this.isDead;
	}
	
	public Cell getPosition()
	{
		return this.position;
	}

	//Setter
	public void setHealthPoint(int hp)
	{
		this.healthPoint = hp;
	}
	
	public void getActualSpeed(int as)
	{
		this.actualSpeed = as;
	}
	
	public void getOriginalSpeed(int os)
	{
		this.originalSpeed = os;
	}
	
	public void getMagic(int m)
	{
		this.magic = m;
	}
	
	public void getLastTime(long lt)
	{
		this.lastTime = lt;
	}
	
	public void getIsDead(boolean dead)
	{
		this.isDead = dead;
	}
	
	public abstract boolean damage(int power, HashMap<String , Integer> bonus);
	
	public void tick()
	{
	
	}
	
	public abstract void move();
}
