import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;

public class Comparator {

	public static void main(String[] args) {
		// Hibakezeles
		if (args.length < 2) {
			System.out.println("Keves argumentumot adott meg!");
			return;
		}
		if (args.length > 2) {
			System.out.println("Tul sok argumentumot adott meg!");
			return;
		}
				
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
			BufferedReader br2 = new BufferedReader(new FileReader(args[1]));
			boolean success = true;
			
			// Vegigmegyunk a fajlokon
			int i = 1;
			while (true) {
				String line1 = br1.readLine();
				String line2 = br2.readLine();
				if (line1 == null || line2 == null) {
					break;
				}
				
				String line1Lower = line1.toLowerCase().replace(" ", "").replace("\t", "");
				String line2Lower = line2.toLowerCase().replace(" ", "").replace("\t", "");;
				
				if (!line1Lower.equals(line2Lower)) {
					success = false;
					System.out.println("Kulonbozes a(z) " + i + ". sornal.");
					System.out.println(args[0] + " at line " + i + ": " + line1Lower);
					System.out.println(args[1] + " at line " + i + ": " + line2Lower);
				}
				i++;
			}
			
			if (success) {
				System.out.println("PASS! A 2 fajl megegyezik.");
			}
			else {
				System.out.println("FAIL! A 2 fajl kulonbozik.");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Az egyik fajl nem talalhato!");
		} catch (IOException e) {
			System.out.println("Olvasasi hiba!");
		}
	}

}
