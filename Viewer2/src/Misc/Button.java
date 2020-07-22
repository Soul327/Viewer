package Misc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Button {
	
	Polygon poly;
	Color color = new Color(150,150,150);
	String name = "Button Name";
	Point p = new Point();
	
	public Button(Polygon poly, String name) {
		this.poly=poly;
		this.name = name;
		getCenter();
	}
	public void getCenter() {
		int sumX=0,sumY=0;
		for(int x=0;x<poly.npoints;x++) {
			sumX+=poly.xpoints[x];
			sumY+=poly.ypoints[x];
		}
		p.setLocation(sumX/poly.npoints, sumY/poly.npoints);
	}
	public boolean hover() {
		if(poly.contains(MouseManager.mouseX,MouseManager.mouseY)) 
			return true;
		return false;
	}
	public boolean leftClick() {
		if(poly.contains(MouseManager.mouseX,MouseManager.mouseY) & MouseManager.leftPressed) 
			return true;
		return false;
	}
	public boolean rightClick() {
		if(poly.contains(MouseManager.mouseX,MouseManager.mouseY) & MouseManager.rightPressed) 
			return true;
		return false;
	}
	public void render(Graphics2D g) {
		
		if(poly.contains(MouseManager.mouseX,MouseManager.mouseY)) {
			if(MouseManager.leftPressed) g.setColor(changeColor(color,-30));
			else g.setColor(changeColor(color,-10));
			
			g.fillPolygon(poly);
			g.setColor(Color.white);
			g.drawPolygon(poly);
		}else {
			g.setColor(color);
			g.fillPolygon(poly);
			g.setColor(Color.darkGray);
			g.drawPolygon(poly);
		}
		g.drawString(name,p.x-g.getFontMetrics().stringWidth(name)/2,p.y+g.getFontMetrics().getHeight()/4);
	}
	public int limit(int x) {
		if(x<0) x=0;
		if(x>255) x=255;
		return x;
	}
	public Color changeColor(Color color,int x) {
		return new Color(
				limit(color.getRed()+x),
				limit(color.getGreen()+x),
				limit(color.getBlue()+x));
	}
}
