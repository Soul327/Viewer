package States;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Main;
import Misc.Graphics;

public class StateManager {
	ArrayList<State> states = new ArrayList<State>();
	
	public StateManager() {
		State s;
		
		s = new StateViewer();
		s.visible = true;
		s.xPer = .10;
		s.widthPer = .75;
		s.heightPer = 1;
		states.add(s);
		
		s = new StateConsole();
		s.visible = true;
		s.xPer = .75;
		s.widthPer = .5;
		s.heightPer = 1;
		states.add(s);
		
		s = new StateFileList();
		s.visible = true;
		s.xPer = 0;
		s.widthPer = .10;
		s.heightPer = 1;
		states.add(s);
	}
	public void tick() {
		for(State s:states) {
			s.stick();
		}
	}
	public void render(Graphics g) {
		for(State s:states) {
			if(s.visible) {
				BufferedImage image = new BufferedImage(s.width, s.height, BufferedImage.TYPE_INT_ARGB);
				
				Graphics i = new Graphics((Graphics2D)image.getGraphics());
				s.srender(i);
				//s.debugRender(i);
				if(s.selected) i.setColor(Color.white);
				else i.setColor(Color.gray);
				i.drawRect(0, 0, s.width-1, s.height-1);
				g.drawImage(image, s.x, s.y, s.width, s.height);
			}
		}
	}
	public void scroll(int scrollAmount) {
		for(State s:states)
			if(s.mouseOverState)
				s.scroll(scrollAmount);
	}
	public void keyTyped(KeyEvent e) {
		for(State s:states)
			if(s.selected)
				s.keyTyped(e);
	}
}