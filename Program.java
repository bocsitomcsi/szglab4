import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Program {

	public static void main(String[] args){
		
		//Konzolrol valo beolvasas
		Scanner scanner = new Scanner (System.in);
		Map map = new Map(4, 0, 0);
		
		while (true) {
			//Use-case menu kiiratasa
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

			//Megvizsgalja, hogy a felhasznalo nem szamtol eltero karaktert adott meg
			while (!scanner.hasNextInt()) {
				System.out.println("Nem lehet karaktert megadni!!!");
				System.out.print("Adjon meg egy ervenyes szamot: "); 

				scanner.next();
			}

			int selection = scanner.nextInt();

			//Kilepesi pont biztositasa
			if (selection == 11) {
				break;
			}

			//Az egyes szamok eseten meghivodo esemeny lancolatok
			switch (selection){

				case 1:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Ellenseg letrehozasa");

					ArrayList<Cell> cells = new ArrayList<Cell>();
					Cell cell = new Cell(false, map, Cell.CellType.Road);
					cells.add(cell);
					map.setCells(cells);

					String enemyType;
					while(true) {
						System.out.println("Letrehozando ellenseg [human, elf, dwarf, hobbit]: ");
						//Ask the user, don't explicitly set it
						enemyType = "human";
						if(!(enemyType=="human" || enemyType=="elf" || enemyType=="dwarf" || enemyType=="hobbit")) {
							System.out.println("Helytelen ertek");
						} else {
							break;
						}
					}

					Logger.active = true;
					map.addEnemy(enemyType, cell);
					Logger.active = false;

					//Ha lefutott a use-case var az enterre a felhasznalotol
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

				case 6:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Akadaly lerakasa");

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

				case 7:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Varazsko letrehozasa");

					Saruman saruman = new Saruman(100, 100, 100, 100, map);
					String stoneType;
					while(true) {
						System.out.println("Letrehozando ko [cyan, green, purple]: ");
						//Ask the user, don't explicitly set it
						stoneType = "purple";
						if(!(stoneType=="cyan" || stoneType=="green" || stoneType=="purple")) {
							System.out.println("Helytelen ertek");
						} else {
							break;
						}
					}

					Logger.active = true;
					saruman.createStone(stoneType);
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

				case 8:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Torony fejlesztes");

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

				case 9:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("****************************");   
					System.out.println("Akadaly fejlesztes");

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

				//Ha a menuben jelzettol eltero sorszamot adna meg a felhasznalo 	
				default:
					for(int i=0; i<5; i++){System.out.println();}
					System.out.println("Rossz sorszamot adott meg!!");
					System.out.println("****************************");
			};
		}
	}
}
