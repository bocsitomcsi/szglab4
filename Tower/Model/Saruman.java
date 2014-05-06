package Model;

import Model.Cell.CellType;

/**
 * Szaruman.
 */
public class Saruman
{
	/**
	 * A rendelkezesre allo varazsero.
	 */
	private int magicPower;
	/**
	 * Egy torony lerakasanak koltsege.
	 */
	private int towerCost;
	/**
	 * Egy akadaly lerakasanak koltsege.
	 */
	private int obstacleCost;
	/**
	 * Egy varazsko hasznalatanak koltsege.
	 */
	private int magicStoneCost;
	/**
	 * A kivalasztott varazsko.
	 */
	private MagicStone selectedMagicStone;
	/**
	 * A palya.
	 */
	private Map map;

	/**
	 * Konstruktor. Peldanyositashoz ajanlott ezt hasznalni.
	 * A tornyok, akadalyok, varazskovek alapertelmezett koltsei
	 *  predefinialtak. 
	 */
	public Saruman(Map map) {
		this(
				200,	// Varazsero
				50,		// Torony ar
				80,		// Akadaly ar
				100,	// Varazsko ar
				map		// Map referencia
				);
	}

	/**
	 * Konstruktor.
	 * @param mp  A rendelkezesre allo varazsero.
	 * @param tc  Egy torony lerakasanak koltsege.
	 * @param oc  Egy akadaly lerakasanak koltsege.
	 * @param mc  Egy varazsko hasznalatanak koltsege.
	 * @param map  A palya.
	 */
	public Saruman(int mp, int tc, int oc, int mc, Map map)
	{
		this.magicPower = mp;
		this.towerCost = tc;
		this.obstacleCost = oc;
		this.magicStoneCost = mc;
		this.map = map;
	}

/**
	 * Getter a magicPower attributumra.
	 * @return  A magicPower attributum.
	 */
	public int getMagicPower()
	{
		return this.magicPower;
	}

	/**
	 * Getter a towerCost attributumra.
	 * @return  A towerCost attributum.
	 */
	public int getTowerCost()
	{
		return this.towerCost;
	}

	/**
	 * Getter az obstacleCost attributumra.
	 * @return  Az obstacleCost attributum.
	 */
	public int getObstacleCost()
	{
		return this.obstacleCost;
	}

	/**
	 * Getter a magicStoneCost attributumra.
	 * @return  A magicStoneCost attributum.
	 */
	public int getMagicStoneCost()
	{
		return this.magicStoneCost;
	}

	/**
	 * Getter a selectedMagicStone attributumra.
	 * @return  A selectedMagicStone attributum.
	 */
	public MagicStone getSelectedMagicStone() {
		return this.selectedMagicStone;
	}

	/**
	 * Setter a magicPower attributumra.
	 * @param mp  A magicPower attributum kivant erteke.
	 */
	public void setMagicPower(int mp)
	{
		this.magicPower = mp;
	}

	/**
	 * Setter a towerCost attributumra.
	 * @param tc  A towerCost attributum kivant erteke.
	 */
	public void setTowerCost(int tc)
	{
		this.towerCost = tc;
	}

	/**
	 * Setter az obstacleCost attributumra.
	 * @param sc  Az obstacleCost attributum kivant erteke.
	 */
	public void setObstacleCost(int sc)
	{
		this.obstacleCost = sc;
	}

	/**
	 * Setter a magicStoneCost attributumra.
	 * @param mc  A magicStoneCost attributum kivant erteke.
	 */
	public void setMagicStoneCost(int mc)
	{
		this.magicStoneCost = mc;
	}

	/**
	 * A megadott tipusu varazsko letrehozasa. 
	 * A letrehozott varazskovet a selectedMagicStone attributumba tarolja el. 
	 * Ha nincs eleg varazsero a ko letrehozasahoz, akkor hamis ertekkel 
	 *  ter vissza, es nem hozza letre a kovet. 
	 * Ellenkezo esetben igaz ertekkel ter vissza.
	 * @param type  A varazsko tipusa.
	 * @return A varazsko letrehozasanak sikeressege.
	 */
	public boolean createStone(String type)
	{
		// Ha van eleg varazsero akkor letrehozzuk a kovet
		if (magicPower >= magicStoneCost) {
			// Letrehozzuk a megfelelo kovet
			if (type.equals("cyan")) {
				selectedMagicStone = new CyanMagicStone();
			} else if (type.equals("green")) {
				selectedMagicStone = new GreenMagicStone();
			} else if (type.equals("purple")) {
				selectedMagicStone = new PurpleMagicStone();
			}
			// Ha nem letezo varazsko nevet adtak meg akkor false-t
			// adunk vissza
			else {
				return false;
			}

			// Csokkentjuk a varazserot
			magicPower -= magicStoneCost;
			// Ertesitjuk a ControlPanel-t a valtozasrol
			map.notifyControlPanel();

			return true;
		}
		// Ha nincs eleg varazsero false-t adunk vissza
		else {
			return false;
		}
	}

	/**
	 * Hozzaad egy tornyot a parameterkent kapott cellahoz. 
	 * Ehhez letrehoz egy uj tornyot, es azt hozzafuzi a map towers listajahoz. 
	 * Ha nincs eleg varazsero a torony letrehozasahoz, vagy 
	 *  a megadott cella mar foglalt vagy ut, akkor hamis ertekkel 
	 *  ter vissza, es nem hozza letre a tornyot. 
	 * Ellenkezo esetben igaz ertekkel ter vissza. 
	 * A varazserot (magicPower) a hozzaadas sikeressegetol fuggoen csokkenti.
	 * @param pos  A torony pozicioja.
	 * @return A torony lerakasanak sikeressege.
	 */
	public boolean addTower(Cell pos)
	{
		// Ha van eleg varazsero es a tornyot le lehet rakni a megadott cellara,
		// akkor letrehozzuk a tornyot
		if (magicPower >= towerCost && pos.getCellType() == CellType.Terrain && !pos.getBusy()) {
			// Hozzadjuk a tornyot
			Tower tower = new Tower(pos, map);
			map.addTower(tower);

			// Csokkentjuk a varazserot
			magicPower -= towerCost;
			// Ertesitjuk a ControlPanel-t a valtozasrol
			map.notifyControlPanel();
			// Jelezzuk a cella foglaltsagat
			pos.setBusy(true);

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Hozzaad egy akadalyt  a parameterkent kapott cellahoz. 
	 * Ehhez letrehoz egy uj akadalyt, es azt hozzafuzi az obstacles listahoz. 
	 * Ha nincs eleg varazsero az akadaly letrehozasahoz, vagy 
	 *  a megadott cella nem egy darab ut, akkor hamis ertekkel ter vissza, 
	 *  es nem hozza letre az akadalyt. 
	 * Ellenkezo esetben igaz ertekkel ter vissza. 
	 * A varazserot (magicPower) a hozzaadas sikeressegetol fuggoen csokkenti.
	 * @param pos  Az akadaly pozicioja.
	 * @return Az akadaly lerakasanak sikeressege.
	 */
	public boolean addObstacle(Cell pos)
	{
		// Ha van eleg varazsero es az akadalyt le lehet rakni a megadott cellara,
		// akkor letrehozzuk az akadalyt
		if (magicPower >= obstacleCost && pos.getCellType() == CellType.Road && !pos.getBusy()) {
			// Hozzadjuk az akadalyt
			Obstacle obstacle = new Obstacle(pos);
			map.addObstacle(obstacle);

			// Csokkentjuk a varazserot
			magicPower -= obstacleCost;
			// Ertesitjuk a ControlPanel-t a valtozasrol
			map.notifyControlPanel();
			// Jelezzuk a cella foglaltsagat
			pos.setBusy(true);

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Meghivja az item-nek az upgrade fuggvenyet atadva a kivalasztott varazskovet. 
	 * Ha az upgrade igaz ertekkel ter vissza, akkor a fejlesztes sikeres volt 
	 *  es a selectedMagicStone referenciat null-ra allitja.
	 * @param item  A fejlesztendo objektum.
	 * @return A fejlesztes sikeressege.
	 */
	public boolean upgradeItem(Item item)
	{
		if(getSelectedMagicStone() != null){
		
			// Fejlesztjuk az item-et
			boolean result = item.upgrade(selectedMagicStone);

			// Ha sikeres volt a fejlesztes akkor megsemmisitjuk a kovet
			if (result) {
				selectedMagicStone = null;
			}

			return result;
		}
		
		return false;
	}

	/**
	 * A kapott erteket hozzaadja a magicPower attributumhoz.
	 * @param power  A varazserohoz hozzaadando ertek.
	 */
	public void changeMagicPowerBy(int power)
	{
		magicPower += power;
		// Ertesitjuk a ControlPanel-t a valtozasrol
		map.notifyControlPanel();
	}
}
