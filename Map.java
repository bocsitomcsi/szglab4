import java.util.ArrayList;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Map.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




public class Map
{
	private int neighbourNumber;
	private long lastEnemyAddedTime;
	private long roundStartedTime;
	private ArrayList<Cell> cells;
	private Round round;
	private ArrayList<Enemy> enemys;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Tower> towers;
	private Saruman saruman;

	//Constructor
	public Map(int neighbour, long lastEnemy, long rt) 
	{
		this.neighbourNumber = neighbour;
		this.lastEnemyAddedTime = lastEnemy;
		this.roundStartedTime = rt;
		
		this.cells = new ArrayList<Cell>();
		this.enemys = new ArrayList<Enemy>();
		this.obstacles = new ArrayList<Obstacle>();
		this.towers = new ArrayList<Tower>();
		/*this.round = new Round();*/ //Itt kellenenek fix ertekek?????!!!
		
	}

	//Getter
	public int getNeighbourNumber()
	{
		return this.neighbourNumber;
	}

	public long getLastEnemyAddedTime()
	{
		return this.lastEnemyAddedTime;
	}

	public long getRoundStartedTime()
	{
		return this.roundStartedTime;
	}

	public ArrayList<Cell> getCells()
	{
		return this.cells;
	}

	public ArrayList<Enemy> getEnemys()
	{
		return this.enemys;
	}

	public ArrayList<Tower> getTowers()
	{
		return this.towers;
	}

	public ArrayList<Obstacle> getObstacles()
	{
		return this.obstacles;
	}

	//Setter
	public void setNeighbourNumber(int neighbour)
	{
		this.neighbourNumber = neighbour;
	}

	public void setLastEnemyAddedTime(long lastEnemy)
	{
		this.lastEnemyAddedTime = lastEnemy;
	}

	public void setRoundStartedTime(long rt)
	{
		this.roundStartedTime = rt;
	}

	public void setCells(ArrayList<Cell> cell)
	{
		this.cells = (ArrayList<Cell>)cell.clone();
	}

	public void setEnemys(ArrayList<Enemy> enemy)
	{
		this.enemys = (ArrayList<Enemy>)enemy.clone();
	}

	public void setTowers(ArrayList<Tower> tower)
	{
		this.towers = (ArrayList<Tower>) tower;
	}

	public void setObstacles(ArrayList<Obstacle> obstacle)
	{
		this.obstacles = (ArrayList<Obstacle>)obstacle.clone();
	}

	//Methods
	public void addEnemy(String type, Cell pos)
	{
		//Cell needs a toString() function for logger
		//In the meantime, it just writes a hardcoded integer (1)
		String logString = "Map.addEnemy(" + type + ", " + 1 + ")";
		Logger.Log(1, logString);
		Enemy enemy;

		if(type=="human") {
			enemy = new Human(100, 5, 5, 30, 0);
		} else if(type=="elf") {
			enemy = new Elf(100, 5, 5, 30, 0);
		} else if(type=="dwarf") {
			enemy = new Dwarf(100, 5, 5, 30, 0);
		} else { // it's a hobbit
			enemy = new Hobbit(100, 5, 5, 30, 0);
		}

		enemy.setPosition(pos);
		enemys.add(enemy);
		Logger.Log(0, logString);
	}

	public void removeEnemy(Enemy enemy)
	{
		enemys.remove(enemy);
	}

	public ArrayList<Enemy> getEnemysInRange(Tower tower)
	{
		ArrayList<Enemy> enemy = new ArrayList<Enemy>();
		return enemy;
	}

	protected void addTower(Tower tower) {
		String logString = "Map.addTower(" + 1 + ")";
		Logger.Log(1, logString);

		towers.add(tower);

		Logger.Log(0, logString);
	}

	protected void addObstacle(Obstacle obstacle) {
		String logString = "Map.addObstacle(" + 1 + ")";
		Logger.Log(1, logString);

		obstacles.add(obstacle);

		Logger.Log(0, logString);
	}

	public void simulateWorld()
	{
	
	}
}
