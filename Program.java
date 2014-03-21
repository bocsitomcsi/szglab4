import java.io.IOException;
import java.util.Scanner;

public class Program {

	public static void main(String[] args){
		
		//Konzolr�l val� beolvas�s
		Scanner scanner = new Scanner (System.in);
		
		while (true) {
				//Use-case men� ki�rat�sa
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
		        
		        //Megvizsg�lja, hogy a felhaszn�l� nem sz�mt�l elt�r� karaktert adott meg
		        while (!scanner.hasNextInt()) {
		        	System.out.println("Nem lehet karaktert megadni!!!");
		        	System.out.print("Adjon meg egy ervenyes szamot: "); 
		        	
		        	scanner.next();
		        }

		        int selection = scanner.nextInt();

		        //Kil�p�si pont biztos�t�sa
		        if (selection == 11) {
		                break;
		        }

		        //Az egyes sz�mok eset�n megh�v�d� esem�ny l�nculatok
		        switch (selection){
		             
		           case 1:
		        	for(int i=0; i<5; i++){System.out.println();}
		        	System.out.println("****************************");   
		        	System.out.println("Ellenseg letrehozasa");
		        	
		        	/*
		        	 * Ide j�n a use-case
		        	 */
		        	
		        	//Ha lefutott a use-case v�r az enterre a felhaszn�l�t�l
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
			        	 * Ide j�n a use-case
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
			        	 * Ide j�n a use-case
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
			        	 * Ide j�n a use-case
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
			        	 * Ide j�n a use-case
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
			        	 * Ide j�n a use-case
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
			        	
			        	/*
			        	 * Ide j�n a use-case
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
	                	
		           case 8:
		        	   for(int i=0; i<5; i++){System.out.println();}
			        	System.out.println("****************************");   
			        	System.out.println("Torony fejlesztes");
			        	
			        	/*
			        	 * Ide j�n a use-case
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
			        	 * Ide j�n a use-case
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
			        	 * Ide j�n a use-case
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
	               
	               //Ha a men�ben jelzett�l elt�r� sorsz�mot adna meg a felhaszn�l� 	
		           default:
		        	   for(int i=0; i<5; i++){System.out.println();}
		        	   System.out.println("Rossz sorszamot adott meg!!");
		        	   System.out.println("****************************");
		       };
		}

	}

}
