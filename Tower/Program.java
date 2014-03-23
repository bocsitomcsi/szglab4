package Tower;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Program {

	public static void main(String[] args){
		Logger.Default();

		//Read from console
		Scanner scanner = new Scanner (System.in);

		//One instance
		Map map = new Map(4, 0, 0);
		Saruman saruman = new Saruman(100, 100, 100, 100, map);
		Cell cell;
		map.setSaruman(saruman);

		Logger.AddName(map, "MapID");
		Logger.AddName(saruman, "SarumanID");

		
		while (true) {
			//Use-case menu print
			System.out.println("[1]  Ellenseg letrehozasa");
			System.out.println("[2]  Ellenseg celba er");
			System.out.println("[3]  Ellenseg talalkozik egy masik ellenseggel");
			System.out.println("[4]  Ellenseg akadalyra lep");
			System.out.println("[5]  Torony lerakas");
			System.out.println("[6]  Akadaly lerakasa");
			System.out.println("[7]  Varazsko letrehozasa");
			System.out.println("[8]  Torony fejlesztes");
			System.out.println("[9]  Akadaly fejlesztes");
			System.out.println("[10] Torony lo");
			System.out.println("[11] Kilepes");

			System.out.print("Melyik Use-Case-t szeretne? : ");

			//Check the wrong characters
			while (!scanner.hasNextInt()) {
				System.out.println("Nem lehet karaktert megadni!!!");
				System.out.print("Adjon meg egy ervenyes szamot: "); 

				scanner.next();
			}

			int selection = scanner.nextInt();

			//Exit point
			if (selection == 11) {
				break;
			}

			//Use-case pair with numbers
			switch (selection){

				case 1:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Ellenseg letrehozasa");

					/*****Create enemy USE-CASE*****/
					ArrayList<Cell> cells = new ArrayList<Cell>();
					cell = new Cell(false, map, Cell.CellType.Road);
					cells.add(cell);
					map.setCells(cells);

					String enemyType;
					while(true) {
						System.out.print("Letrehozando ellenseg [human | elf | dwarf | hobbit]: ");

						enemyType = scanner.next();
						if(!(enemyType.equals("human") || enemyType.equals("elf") || enemyType.equals("dwarf") || enemyType.equals("hobbit"))) {
							System.out.println("Helytelen ertek");
						} else {
							break;
						}
					}

					Logger.SetActive(true);
					map.addEnemy(enemyType, cell);
					Logger.SetActive(false);
					/*******************************/

					//Waiting for the ENTER
					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 2:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Ellenseg celba er");

					/*
					* Ide jon a use-case
					*/

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 3:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Ellenseg talalkozik egy masik ellenseggel");

					/*
					* Ide jon a use-case
					*/

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 4:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Ellenseg akadalyra lep");

					/*
					* Ide jon a use-case
					*/

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 5:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Torony lerakas");

					saruman = new Saruman(100, 100, 100, 100, map);
					Logger.AddName(saruman, "sarumanID");
					cell = new Cell(false, map, Cell.CellType.Terrain);

					Logger.active = true;
					saruman.addTower(cell);
					Logger.active = false;

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 6:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Akadaly lerakasa");

					saruman = new Saruman(100, 100, 100, 100, map);
					Logger.AddName(saruman, "sarumanID");
					cell = new Cell(false, map, Cell.CellType.Road);

					Logger.active = true;
					saruman.addObstacle(cell);
					Logger.active = false;

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 7:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Varazsko letrehozasa");

					/****Create magic stone USE-CASE****/
					String stoneType;
					
					while(true) {
						System.out.print("Letrehozando ko [cyan | green | purple]: ");
						
						stoneType = scanner.next();
						if(!(stoneType.equals("cyan") || stoneType.equals("green") || stoneType.equals("purple"))) {
							System.out.println("Helytelen ertek");
						} else {
							break;
						}
					}
					
					while(true) {
						System.out.print("Van eleg varazs ero letrehozni [igen | nem] ");
						
						stoneType = scanner.next();
						if(stoneType.equals("igen")) {
							
							Logger.SetActive(true);
							saruman.createStone(stoneType);
							Logger.SetActive(false);
							break;
						} 
						else if (stoneType.equals("nem")) {
							break;
						}
						else {
							System.out.println("Helytelen ertek");
						}
					}
					
					/**************************************/
					
					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 8:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Torony fejlesztes");

					/****TowerUpgrade*****/
					String result1;
					saruman.createStone("green");
					cell = new Cell(false, map, Cell.CellType.Road);
					Tower t1 = new Tower(1, 1, 1, 1, 1, cell, map);
					Logger.AddName(t1, "TowerID");

					while(true) {
						System.out.print("Van varazsko letrehozva [igen | nem] ");

						result1 = scanner.next();
						if(result1.equals("igen")) {

							Logger.SetActive(true);

							while(true) {
								System.out.print("Fejleszthetõ még a torony (max 5 ko) [igen | nem] ");
								result1 = scanner.next();

								if(result1.equals("igen")) {
									saruman.upgradeItem(t1);
									break;
								}
								else if(result1.equals("nem")) {
									break;
								}
								else {
									System.out.println("Helytelen ertek");
								}
							}
							Logger.SetActive(false);
							break;
						} 
						else if (result1.equals("nem")) {
							break;
						}
						else {
							System.out.println("Helytelen ertek");
						}
					}
					/*********************/

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 9:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Akadaly fejlesztes");

					/****TowerUpgrade*****/
					String result2;
					saruman.createStone("purple");
					Obstacle o1 = new Obstacle(1.0, 1, null);
					Logger.AddName(o1,"ObstacleID");

					while(true) {
						System.out.print("Van varazsko letrehozva [igen | nem] ");
						
						result2 = scanner.next();
						if(result2.equals("igen")) {
							
							Logger.SetActive(true);							
							
							while(true) {
								System.out.print("Fejleszthetõ még az akadaly (max 5 ko) [igen | nem] ");
								result2 = scanner.next();
								
								if(result2.equals("igen")) {
									saruman.upgradeItem(o1);
									break;
								}
								else if(result2.equals("nem")) {
									break;
								}
								else {
									System.out.println("Helytelen ertek");
								}
							}
							Logger.SetActive(false);
							break;
						} 
						else if (result2.equals("nem")) {
							break;
						}
						else {
							System.out.println("Helytelen ertek");
						}
					}
					/*********************/

					try{
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				case 10:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Torony lo");

					/*
					* Ide jon a use-case
					*/

					try {
						System.out.println("Kerem nyomjon ENTERT!");
						System.in.read();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");
					break;

				//If the user set an invalid number  	
				default:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("Rossz sorszamot adott meg!!");
					System.out.println("****************************");
			};
		}
	}
}
