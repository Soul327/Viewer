package States;

import java.awt.Color;
import java.awt.event.KeyEvent;

import Misc.Graphics;
import Misc.MouseManager;

public abstract class State {
	
	public boolean visible = false, mouseOverState = false,selected = false;
	public int x=0, y=0, width=1, height=1;
	public double xPer = 0, yPer = 0, widthPer = 1, heightPer = 1;
	int mx,my;
	
	public void stick() {
		mx = MouseManager.mouseX;
		my = MouseManager.mouseY;
		if(mx>x & mx<x+width & my>y & my<y+height)
			mouseOverState = true;
		else
			mouseOverState = false;
		
		if(MouseManager.leftPressed & mouseOverState)
			selected = true;
		if(MouseManager.leftPressed & !mouseOverState)
			selected = false;
		tick();
	}
	public void srender(Graphics g) {
		width = (int) (Main.Main.width*widthPer);
		height = (int) (Main.Main.height*heightPer);
		x = (int) (Main.Main.width*xPer);
		y = (int) (Main.Main.height*yPer);
		render(g);
	}
	
	public void tick() {};
	public void render(Graphics g) {}
	public void debugRender(Graphics g) {
		g.setColor(Color.orange);
		g.drawRect(x, y, width, height);
	}
	public void scroll(int scrollAmount) {}
	public void keyTyped(KeyEvent e) {}
}
