package Tower;

import java.util.HashMap;

public class Logger {
	static int depths;
	static boolean active;
	static HashMap<Object, String> hm;

	public static void Default(){
		depths = 0;
		active = false;
		hm = new HashMap<Object, String>();
	}
	
	public static void SetActive(boolean act){
		active = act;
	}
	
	public static void AddName(Object obj, String name){
		hm.put(obj,name);
	}
	
	public static void Log(int direction, String text, Object obj) {
		if(active) {
			if(direction==1){
				for(int i=0; i<depths; i++){
					System.out.print("\t");
				}
				depths++;
				System.out.print("->");
				System.out.print(depths+". "+text+" : "+hm.get(obj)+"\n");
			}
			else{
				for(int i=0; i<depths-1; i++){
					System.out.print("\t");
				}
				System.out.print("<-");
				System.out.print(depths+". "+text+" : "+hm.get(obj)+"\n");
				depths--;
			}
		}
	}
}
