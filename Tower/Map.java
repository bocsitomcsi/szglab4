package Tower;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import Tower.Cell.CellType;

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




/**
 * A palyat reprezentalo osztaly. 
 * Tarolja a palyan talalhato objektumokat es o ertesit mindenkit az ido mulasarol.
 */
public class Map
{
	/**
	 * Az iranyokat tarolo enumeracio.
	 */
	public enum Direction {
		North, West, South, East
	};
	/**
	 * A jatek lehetseges kimeneteleit tarolo enumeracio.
	 */
	public enum GameResult {
		Win, Loose
	};
	/**
	 * A palyan szereplo cellak szomszedainak maximalis szama.
	 */
	private int neighbourNumber;
	/**
	 * Az az idopont, amikor legutoljara ellenseget adtak a palyahoz.
	 */
	private long lastEnemyAddedTime;
	/**
	 * Az aktualis kor kezdetenek idopontja.
	 */
	private long roundStartedTime;
	/**
	 * Az akutalis kor sorszama.
	 */
	private long roundNumber;
	/**
	 * A palyan levo cellak.
	 */
	private ArrayList<Cell> cells;
	/**
	 * A jatekhoz tartozo kor leiroja.
	 */
	private Round round;
	/**
	 * A palyan szereplo ellensegek listaja.
	 */
	private ArrayList<Enemy> enemies;
	/**
	 * A palyan szereplo akadalyok listaja.
	 */
	private ArrayList<Obstacle> obstacles;
	/**
	 * A palyan szereplo tornyok listaja.
	 */
	private ArrayList<Tower> towers;
	/**
	 * Szaruman.
	 */
	private Saruman saruman;
	/**
	 * Ennyi idonkent kerul kod egy toronyra.
	 */
	private int fogApplianceTime;
	/**
	 * Ekkor kerult utoljara kod egy toronyra.
	 */
	private long lastFog;
	/**
	 * A kod ennyivel csokkenti egy torony lotavolsagat.
	 */
	private final static int fogDecreason = 1;
	/**
	 * A kod ennyi ideig csokkenti egy torony lotavolsagat.
	 */
	private final static int fogDuration = 4000;

	/**
	 * Ennyi idonkent ertesulnek az objektumok az ido mulasarol.
	 */
	private final int dt = 50;
	
	/**
	 * Konstruktor.
	 * @param neighbour  A palyan szereplo cellak szomszedainak maximalis szama.
	 */
	public Map(int neighbour) 
	{
		long current = System.currentTimeMillis();

		this.cells = new ArrayList<Cell>();
		this.enemies = new ArrayList<Enemy>();
		this.obstacles = new ArrayList<Obstacle>();
		this.towers = new ArrayList<Tower>();
		
		this.neighbourNumber = neighbour;
		this.lastEnemyAddedTime = current;
		this.roundStartedTime = current;
		
		// Fog tulajdonsagok
		fogApplianceTime = 5000;
		lastFog = current;
		
		// Round letrehozasa
		this.round = new Round();
		roundNumber = 1;
	}

	/**
	 * Getter a neighbourNumber attributumra.
	 * @return  A neighbourNumber attributum.
	 */
	public int getNeighbourNumber()
	{
		return this.neighbourNumber;
	}

	/**
	 * Getter a lastEnemyAddedTime attributumra.
	 * @return  A lastEnemyAddedTime attributum.
	 */
	public long getLastEnemyAddedTime()
	{
		return this.lastEnemyAddedTime;
	}

	/**
	 * Getter a roundStartedTime attributumra.
	 * @return  A roundStartedTime attributum.
	 */
	public long getRoundStartedTime()
	{
		return this.roundStartedTime;
	}

	/**
	 * Getter a cells attributumra.
	 * @return  A cells attributum.
	 */
	public ArrayList<Cell> getCells()
	{
		return this.cells;
	}

	/**
	 * Getter az enemies attributumra.
	 * @return  Az enemies attributum.
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return this.enemies;
	}

	/**
	 * Getter a towers attributumra.
	 * @return  A towers attributum.
	 */
	public ArrayList<Tower> getTowers()
	{
		return this.towers;
	}

	/**
	 * Getter az obstacles attributumra.
	 * @return  Az obstacles attributum.
	 */
	public ArrayList<Obstacle> getObstacles()
	{
		String logString = "Map.getObstacles()";
		Logger.Log(1, logString, this);

		Logger.Log(0, logString, this);

		return this.obstacles;
	}

	/**
	 * Getter a fogDecreason attributumra.
	 * @return  A fogDecreason attributum.
	 */
	public static int getFogdecreason() {
		return fogDecreason;
	}

	/**
	 * Getter a fogDuration attributumra.
	 * @return  A fogDuration attributum.
	 */
	public static int getFogduration() {
		return fogDuration;
	}
	
	/**
	 * Setter a neighbourNumber attributumra.
	 * @param b  A neighbourNumber attributum kivant erteke.
	 */
	public void setNeighbourNumber(int neighbour)
	{
		this.neighbourNumber = neighbour;
	}

	/**
	 * Setter a lastEnemyAddedTime attributumra.
	 * @param b  A lastEnemyAddedTime attributum kivant erteke.
	 */
	public void setLastEnemyAddedTime(long lastEnemy)
	{
		this.lastEnemyAddedTime = lastEnemy;
	}

	/**
	 * Setter a roundStartedTime attributumra.
	 * @param b  A roundStartedTime attributum kivant erteke.
	 */
	public void setRoundStartedTime(long rt)
	{
		this.roundStartedTime = rt;
	}

	/**
	 * Setter a cells attributumra.
	 * @param b  A cells attributum kivant erteke.
	 */
	public void setCells(ArrayList<Cell> cell)
	{
		this.cells = cell;
	}

	/**
	 * Setter az enemies attributumra.
	 * @param b  Az enemies attributum kivant erteke.
	 */
	public void setEnemies(ArrayList<Enemy> enemy)
	{
		this.enemies = enemy;
	}

	/**
	 * Setter a towers attributumra.
	 * @param b  A towers attributum kivant erteke.
	 */
	public void setTowers(ArrayList<Tower> tower)
	{
		this.towers = (ArrayList<Tower>) tower;
	}

	/**
	 * Setter az obstacles attributumra.
	 * @param b  Az obstacles attributum kivant erteke.
	 */
	public void setObstacles(ArrayList<Obstacle> obstacle)
	{
		this.obstacles = obstacle;
	}

	public void setSaruman(Saruman s){
		this.saruman = s;
	}

	/**
	 * Letrehoz egy Enemy leszarmazottat a parameterkent kapott 
	 *  ertekeknek megfeleloen, es az enemies listahoz adja. 
	 * Ehhez a megfelelo Enemy leszarmazott osztaly konstruktorat hivja meg.
	 * @param type  Az ellenseg tipusa.
	 * @param pos  Az ellenseg pozicioja.
	 */
	public void addEnemy(String type, Cell pos)
	{
		String logString = "Map.addEnemy(type, position)";
		Logger.Log(1, logString, this);
		
		Enemy enemy;
		// Letrehozzuk a megfelelo ellenseget
		if (type.equals("human")) {
			enemy = new Human();
			Logger.AddName(enemy, "HumanID");
		} else if (type.equals("elf")) {
			enemy = new Elf();
			Logger.AddName(enemy, "ElfID");
		} else if (type.equals("dwarf")) {
			enemy = new Dwarf();
			Logger.AddName(enemy, "DwarfID");
		} else if (type.equals("hobbit")) { 
			enemy = new Hobbit();
			Logger.AddName(enemy, "HobbitID");
		}
		// Ha nem letezo ellenseg tipust kaptunk akkor 
		// nem hozunk letre semmit
		else {
			return;
		}

		// Beallitjuk az ellenseg poziciojat es hozzaadjuk a map-hoz
		enemy.setPosition(pos);
		enemies.add(enemy);
		
		Logger.Log(0, logString, this);
	}

	/**
	 * Noveli Saruman varazserejet a changeMagicPowerBy metoduson keresztul, 
	 *  felhasznalva a kapott enemy magic attributumat. 
	 * Vegul az enemy-t eltavolitja az enemies listabol.
	 * @param enemy  Az eltavolitando ellenseg.
	 */
	public void removeEnemy(Enemy enemy)
	{
		String logString = "Map.removeEnemy(enemy)";
		Logger.Log(1, logString, this);

		// Noveljuk saruman varazserejet
		saruman.changeMagicPowerBy(enemy.getMagic());
		// Eltavolitjuk az ellenseget
		enemies.remove(enemy);

		Logger.Log(0, logString, this);
	}

	/**
	 * Hozzaadja a paramterkent kapott tornyot a towers listahoz.
	 * @param tower  A palyahoz adando torony.
	 */
	protected void addTower(Tower tower) {
		String logString = "Map.addTower(tower)";
		Logger.Log(1, logString, this);

		// Hozzadjuk a tornyot a listahoz
		towers.add(tower);

		Logger.Log(0, logString, this);
	}

	/**
	 * Hozzaadja a paramterkent kapott akadalyt az obstacles listahoz.
	 * @param obstacle  A palyahoz adando akadaly.
	 */
	protected void addObstacle(Obstacle obstacle) {
		String logString = "Map.addObstacle(obstacle)";
		Logger.Log(1, logString, this);

		// Hozzaadjuk az akadalyt a listahoz
		obstacles.add(obstacle);

		Logger.Log(0, logString, this);
	}

	/**
	 * Visszaadja a kapott torony lotavolsagan beluli ellenfelek listajat. 
	 * Ehhez vegigmegy az enemies listan, es ha olyan ellenfelet talal 
	 *  benne, aki a torony lotavolsagan belul van akkor azt hozzafuzi
	 *  a visszaadando listahoz. 
	 * Ha nem talal ilyen ellenfelet akkor null-t ad vissza.
	 * @param tower  A torony akinek a hatotavolsagan beluli ellenfeleket kell visszaadni.
	 * @return  A kapott torony lotavolsagan beluli ellenfelek listaja.
	 *  Ha nincs ellenfel a torony lotavolsagan belul akkor null-t ad vissza.
	 */
	public ArrayList<Enemy> getEnemiesInRange(Tower tower)
	{
		// Lekerjuk a lotavolsagon beluli cellakat egy halmazba
		Set<Cell> cellsInRange = new HashSet<Cell>();
		getNeighbourCellsInRange(
				tower.getPosition(), 
				tower.getRange(), 
				cellsInRange
				);
		
		// Eltavolitjuk azokat a cellakat, amelyek terep tipusuak
		// mert azokon nem lehet ellenseg
		for (Cell cell : cellsInRange) {
			if (cell.getCellType() == CellType.Terrain) {
				cellsInRange.remove(cell);
			}
		}
		
		// Vegigmegyunk az ellensegek listajan
		ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			// Ha az ellenseg olyan cellan van, amely benne van a listankban,
			// akkor hozzadjuk az ellenseget az enemiesInRange-hez
			if (cellsInRange.contains(enemy.getPosition())) {
				enemiesInRange.add(enemy);
			}
		}
		
		// Ha talaltunk ellenseget lotavolsagon belul,
		// akkor visszaadjuk a listat
		if (!enemiesInRange.isEmpty()) {
			return enemiesInRange;
		}
		
		// Kulonben null-t adunk vissza
		return null;
		
		
		
		/*
		String logString = "Map.getEnemiesInRange(tower)";
		Logger.Log(1, logString, this);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

		tower.getPosition();
		for(Enemy enemy: enemies) {
			enemy.getPosition();
			enemyList.add(enemy);
		}
		Logger.Log(0, logString, this);
		return enemyList;
		*/
	}

	/**
	 * Rekurziv metodus, amely a kapott cellabol kiindulva elmenti 
	 *  a set kollekcioba a cell-bol range darab lepessel elerheto
	 *  cellakat.
	 * @param cell A kiindulo cella.
	 * @param range A tavolsag, avagy a lepesek szama, 
	 * 	amennyit meg lephetunk a cellabol.
	 * @param set A halmaz, amibe a range lepesbol elerheto cellakat mentjuk.
	 */
	private void getNeighbourCellsInRange(Cell cell, int range, Set<Cell> set) {
		// Vegigmegyunk a cella szomszedain
		for (Entry<Cell, Boolean> neighbour : cell.getNeighbours().values()) {
			Cell neighbourCell = neighbour.getKey();
			// Ha a tavolsag nagyobb 0-nal, akkor
			// a kozvetlen szomszedot elmentjuk.
			if (range > 0) {
				set.add(neighbourCell);
			}
			// Ha a tavolsag 1-nel is nagyobb, akkor a kozvetlen szomszedon
			// is meg kell hivni a fuggvenyt de 1-el kisebb tavolsaggal
			if (range > 1) {
				getNeighbourCellsInRange(neighbourCell, range - 1, set);
			}
		}
	}
	
	/**
	 * A palyan levo ellensegeket, es tornyokat ertesiti az ido mulasarol.
	 * Ehhez a towers es enemies listakban tarolt objektumok tick fuggvenyet hivja meg. 
	 * Idonkenkent karbantartja az aktualis kort, hozzad ellenfeleket a palyahoz es
	 *  kodoket rak a tornyokra. 
	 * Minden iteracio vegen ellenorzi, hogy veget ert-e a jatek.
	 */
	public GameResult simulateWorld()
	{
		boolean gameRunning = true;
		GameResult gameResult = GameResult.Loose;
		
		// GAME LOOP
		while (gameRunning) {
			long currentTime = System.currentTimeMillis();
			int random;
			
			// ELLENSEGEK ES TORNYOK ERTESITESE
			for (Enemy enemy : enemies) {
				enemy.tick();
			}
			for (Tower tower : towers) {
				tower.tick();
			}
			
			// FOG ELHELYEZESE
			// Ha eltelt a legutobbi kod elhelyezese ota a megfelelo ido,
			// akkor elhelyezunk egy kodot egy toronyra
			if (currentTime - lastFog >= fogApplianceTime) {
				// Vegigiteralunk a tornyokon
				ArrayList<Tower> fogTowers = new ArrayList<Tower>();
				for (Tower tower : towers) {
					// Ha nincs kod a tornyon akkor betesszuk a kod mentes
					// tornyok listajaba
					if (!tower.getFogActive()) {
						fogTowers.add(tower);
					}
				}
				
				// Ha van kod mentes torony
				if (!fogTowers.isEmpty()) {
					// Kivalasztunk egyet veletlenszeruen
					random = (int)(Math.random() * fogTowers.size());
					Tower fogTower = fogTowers.get(random);
					
					// Elhelyezzuk rajta a kodot
					fogTower.applyFog(fogDecreason, fogDuration);
					
					// Elmentjuk az elhelyezes idejet
					lastFog = currentTime;
				}
			}
			
			// ROUND KARBANTARTAS
			// Ha veget ert az aktualis kor es ez nem az utolso volt akkor,
			// atlepunk a kovetkezo korbe
			if (currentTime - roundStartedTime >= round.roundTime && 
					roundNumber < round.maxRounds) {
				// Leptetjuk a kort
				round.enemyNumber *= round.enemyNumberMultiplier;
				round.enemyAddingTime *= round.enemyAddingTimeMultiplier;
				// Noveljuk az aktualis kor sorszamat
				roundNumber++;
				// Beallitjuk az uj kor kezdetenek idejet
				roundStartedTime = currentTime;
			}
			
			// ENEMY HOZZAADAS
			// Ha meg tart az aktualis kor es eljott 
			// az ellenseg hozzaadasanak ideje
			if (currentTime - roundStartedTime < round.roundTime &&
					currentTime - lastEnemyAddedTime >= round.enemyAddingTime) {
				// A round-ban tarolt enemyNumber erteknek megfeleloen
				// hozaadunk enemyNumber darab ellenseget a palyahoz
				for (int i = 0; i < round.enemyNumber; i++) {
					// Kivalasztunk veletlenszeruen egy ellenseg tipust
					String[] enemyTypes = {"dwarf", "elf", "hobbit", "human"};
					random = (int)(Math.random() * enemyTypes.length);
					String enemyType = enemyTypes[random];
					
					// Kigyujtjuk a StartPoint tipusu cellakat
					ArrayList<Cell> startPoints = new ArrayList<Cell>();
					for (Cell cell : cells) {
						if (cell.getCellType() == CellType.StartPoint) {
							startPoints.add(cell);
						}
					}
					// Kivalasztunk egyet veletlenszeruen
					random = (int)(Math.random() * startPoints.size());
					Cell startPoint = startPoints.get(random);
					
					// Hozzaadjuk az ellenseget a palyahoz
					addEnemy(enemyType, startPoint);
				}
				
				// Beallitjuk a legutoljara hozzaadott ellenseg idejet
				lastEnemyAddedTime = currentTime;
			}
			
			// JATEK VEGE CHECK
			// Ha elfogytak az ellensegek es nincs tobb kor,
			// akkor a jatek veget ert gyozelemmel
			if (enemies.isEmpty() && currentTime - roundStartedTime >= round.roundTime) {
				gameRunning = false;
				gameResult = GameResult.Win;
			}
			// Ha nincs gyozelem, akkor megnezzuk, hogy vesztettunk-e
			else {
				// Vegigiteralunk az ellensegeken es megnezzuk,
				// hogy van-e valaki vegponton
				for (Enemy enemy : enemies) {
					// Ha valaki vegponton van akkor jelezzuk a vereseget
					if (enemy.getPosition().getCellType() == CellType.EndPoint) {
						gameRunning = false;
						gameResult = GameResult.Loose;
						break;
					}
				}
			}
			
			// Ha meg nem ert veget a jatek akkor varunk dt idot
			if (gameRunning) {
				try {
					Thread.sleep(dt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Visszaterunk az eredmennyel
		return gameResult;
				
		/*
		String logString = "Map.simulateWorld()";
		Logger.Log(1, logString, this);

		boolean isEndPoint = false;
		String answerText;
		Scanner scanner = new Scanner (System.in);

		while(true) {
			for(Enemy enemy: enemies) {
				enemy.getPosition().getCellType();

				// Felhasznaloi interakcio
				if(Program.usecaseNumber==2) {
					while(true) {
						System.out.print("Celmezore lepett [igen, nem]: ");
						answerText = scanner.next();
						if(answerText.equals("igen")) {
							isEndPoint=true;
							break;
						} else if(answerText.equals("nem")) {
							isEndPoint=false;
							break;
						} else {
							System.out.println("Helytelen ertek");
							continue;
						}
					}
				}
			}

			if(isEndPoint) {
					break;
			}
		}
		Logger.Log(0, logString, this);
		return null;
		*/
	}
}
