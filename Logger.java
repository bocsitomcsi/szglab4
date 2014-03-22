public class Logger {
	static int depths;
	static boolean active;

	public Logger(){
		depths = 0;
		active = false;
	}

	public static void Log(int direction, String text) {
		if(active) {
			if(direction==1){
				for(int i=0; i<depths; i++){
					System.out.print("\t");
				}
				depths++;
				System.out.print("->");
				System.out.print(depths+". "+text+"\n");
			}
			else{
				for(int i=0; i<depths-1; i++){
					System.out.print("\t");
				}
				System.out.print("<-");
				System.out.print(depths+". "+text+"\n");
				depths--;
			}
		}
	}
}
