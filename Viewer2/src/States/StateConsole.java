package States;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Main.Commands;
import Misc.Graphics;
import static java.lang.System.out;

public class StateConsole extends State{
	
	int tickCount = 0;
	String intro = "User > ";
	static String typed = "";
	static ArrayList<String> txt = new ArrayList<String>();
	
	public void tick() {
		tickCount++;
		if(tickCount>60)
			tickCount = 0;
	}
	
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == KeyEvent.VK_DELETE)
			if(typed.length() > 0) {
				typed = typed.substring(0,typed.length()-1);
				return;
			}
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			txt.add(intro+typed);
			if(typed.length() > 0) {
				out.println(typed.length());
				for( String s:Commands.onCommand(typed.split(" ")).split("\n") ) {
					txt.add( s );
				}
				out.println(typed.split(" ")[0]);
				typed = "";
				return;
			}
		}
		typed += e.getKeyChar();
	}
	
	public void render(Graphics g) {
		String und = "";
		if(tickCount<30) und+="_";
//		g.setColor(Color.gray); g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.setFont( "Serif",Font.PLAIN,15 );
		int z = 0;
		for(;z<txt.size();z++)
			g.drawString(txt.get(z), 0, g.fontSize*(z+1));
		
		g.drawString(intro+typed+und, 0, g.fontSize*(z+1));
	}
}
