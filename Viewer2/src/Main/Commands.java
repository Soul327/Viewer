package Main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Misc.KeyManager;
import Misc.Util;

public class Commands extends Thread{
	//public void onCommand(CommandSender sender, Command command, String alias, String[] args){}
	public static String onCommand(String[] args) {
		String re = "No command found";
		if(args.length>=1)
			if(args[0].equalsIgnoreCase("help")) {
				if(args.length==1) {
					re ="help <command>\n" +
							"search <query>\n" +
							"parseFile <filelocation> <subfile size>\n" + 
							"set <varName> <value>";
				}
				if(args.length==2) {
					if(args[1].equalsIgnoreCase("set")) {
						re = "<varName>\nfontSize ezView";
					}
				}
			}
		if(args.length==2)
			if(args[0].equalsIgnoreCase("search")) {
				Util.findEntry(args[1]);
				re = "Searching for "+args[1];
			}
		if(args.length==3) {
			if(args[0].equalsIgnoreCase("parseFile"))
				Util.parseDataBase(new File(args[1]),Integer.parseInt(args[2]));
			if(args[0].equalsIgnoreCase("set")) {
				if(args[1].equalsIgnoreCase("fontSize")) {
					int fontSize = Integer.parseInt(args[2]);
					Viewer.listFontSize = fontSize;
					re = "Set font to "+fontSize;
				}
				if(args[1].equalsIgnoreCase("ezViewMode")) {
					int fontSize = Integer.parseInt(args[2]);
					Main.ezViewMode = fontSize;
					re = "Set view mode to "+fontSize;
				}
			}
		}
		return re;
	}
	public void run() {
		new Thread() {
			public void run(){
				Scanner scanner = new Scanner(System.in);
				while(true) {
					System.out.print("User > ");
					String line = scanner.nextLine();
					System.out.println(onCommand( line.split(" ")) );
				}
			}
		}.start();
		
		new Thread() {
			public void run(){
				try {
					while(true)
						if(KeyManager.keyRelease(KeyEvent.VK_SPACE)) 
							onCommand(JOptionPane.showInputDialog("Enter a command").split(" "));
				}catch(NullPointerException e) {
					System.out.println("java.lang.NullPointerException");
				}
			}
		}.start();
	}
	public static void main(String args[]) {
		new Commands().start();
		//int y = 0; for(int x=100; x>y; y++ ) System.out.println(x);
	}
}
