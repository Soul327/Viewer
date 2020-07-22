package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import javax.swing.JOptionPane;

import Misc.Graphics;
import Misc.KeyManager;
import Misc.MouseManager;
import Misc.Util;

public class Viewer {
	static int selected = 100,listWidth = 0;
	static int mx,my;
	static double scrollList = 0;
	static double scrollData = 0;
	public static ArrayList<Address> list = new ArrayList<Address>();
	static double scrollSpeed = 2;
	
	static int listFontSize = 15;
	String font = "Serif";
	
	public Viewer() {
		Util.loadFile();
		//Util.printDir();
	}
	public static void scroll(int sc) {
		if(mx>listWidth) scrollData-=sc*scrollSpeed;
		if(mx>0 & mx<listWidth) scrollList-=sc*scrollSpeed;
		
	}
	public void tick() {
		if(KeyManager.keyRelease(KeyEvent.VK_UP)) selected--;
		if(KeyManager.keyRelease(KeyEvent.VK_DOWN)) selected++;
		if(KeyManager.keyRelease(KeyEvent.VK_M)) Util.sel();
		if(KeyManager.keyRelease(KeyEvent.VK_RIGHT)) Util.nextFile();
		if(KeyManager.keyRelease(KeyEvent.VK_LEFT)) Util.lastFile();
		if(KeyManager.keyRelease(KeyEvent.VK_SPACE)) {
			String command = JOptionPane.showInputDialog("Enter a command");
			String[] args = command.split(" ");
			Commands.onCommand(args);
		}
		//Lock selection
		if(selected<0) selected = 0;
		if(selected>list.size()-1) selected = list.size()-1;
		
	}
	public void render(Graphics g) {
		mx = MouseManager.mouseX;
		my = MouseManager.mouseY;
		
		g.setFont( font,Font.PLAIN,listFontSize );
		listWidth = g.getStringLength(" 255.255.255.255 ");
		
		for(int x=0;x<list.size();x++) {
			
			Address a = list.get(x);
			if((x+1)*(g.fontSize)+scrollList-2>Main.height)
				continue;
			//Check selected
			if(selected == x) {
				if(!a.updated) a.getValues();
				
				//Draw select rec
				g.setColor(Color.gray); g.fillRect(0, x*g.fontSize+scrollList, listWidth, g.fontSize);
				g.setColor(Color.white); g.drawRect(0, x*g.fontSize+scrollList, listWidth, g.fontSize);
				
				//Draw data window
				//renderData(g,a);
			}
			
			//If mouse is over list
			if(mx>0 & mx<listWidth)
				//If mouse is over text
				if(my>x*(g.fontSize)+scrollList & my<(x+1)*(g.fontSize)+scrollList){
					g.setColor(Color.lightGray); g.fillRect(0, x*g.fontSize+scrollList, listWidth, g.fontSize);
					g.setColor(Color.white); g.drawRect(0, x*g.fontSize+scrollList, listWidth, g.fontSize);
					//Check click
					if(MouseManager.leftPressed)
						selected = x;
				}
			
			g.setFont( font,Font.PLAIN,listFontSize );
			//Draw list
			g.drawOutlinedString(a.name, 5, (x+1)*(g.fontSize)+scrollList-2);
		}
		//Draw data window
		for(int x=0;x<list.size();x++)
			if(x==selected)
				renderData(g,list.get(x));
		
		//Draw file info
		g.setFont( font,Font.PLAIN,listFontSize );
		g.setColor(Color.gray);
		g.fillRect(0, Main.height-20, g.getStringLength(Util.file.getName()+"  ")+2.5, 20);
		g.drawOutlinedString(Util.file.getName(), 5, Main.height-5);
		
		if(mx>0 & mx<listWidth) {
			//Scroll
			if(KeyManager.keys[KeyEvent.VK_9]) scrollList-=1;
			if(KeyManager.keys[KeyEvent.VK_0] & scrollList<0) scrollList+=1;
		}
		renderScroll(g,listWidth+5,5,10,Main.height-10, (double)selected/(list.size()-1) );
	}
	public void renderData(Graphics g,Address a) {
		g.setFont( font,Font.PLAIN,listFontSize );
		if(a.test!=null)
			for(int y=0;y<a.test.size();y++) {
				String write = a.test.get(y);
				Color color = Color.white;
				if(Main.ezViewMode == 1) {
					if(write.contains("OS: ")) {
						if(write.toLowerCase().contains("windows")) color = new Color( 24, 190, 250);
						if(write.toLowerCase().contains("osx")) color = new Color(15, 154, 110);
						if(write.toLowerCase().contains("linux")) color = new Color(201, 145, 32);
					} else {
						if(write.toLowerCase().contains("tcp")) color = new Color( 255, 150, 150);
						if(write.toLowerCase().contains("udp")) color = new Color( 150, 150, 255);
					}
				}
				g.drawOutlinedString(write, listWidth+20, (y+1)*(g.fontSize)+scrollData, color, Color.black);
			}
		//Mouse over data window
		if(mx>listWidth) {
			if(KeyManager.keys[KeyEvent.VK_9]) scrollData--;
			if(KeyManager.keys[KeyEvent.VK_0]  & scrollData<0) scrollData++;
			if(KeyManager.keyRelease(KeyEvent.VK_F5)) a.updated = false;
		}
	}
	public void renderScroll(Graphics g,int x,int y,int width,int height,double scroll) {
		if(MouseManager.leftPressed)
			if(mx>x & mx<x+width & my>y & my<y+height) {
				scroll = (double)(my-5)/height;
				selected = (int)Math.round(scroll*(list.size()-1));
				scrollList = -selected;
			}
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		g.fillRect(x, y+(double)scroll*height-5, width, 10);
	}
}