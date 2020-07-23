package Misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import States.StateManager;

public class Graphics {
	public int fontSize = 0;
	public Graphics2D g;
	final public boolean defaultScaleable = false;
	public boolean scalable = defaultScaleable;
	double scale = Main.Main.scale;
	
	public Graphics(Graphics2D g) { this.g = g; }
	public void setColor(Color color) { g.setColor(color); }
	public void drawRect(double x, double y, double width, double height) {
		if(scalable) {
			scale = Main.Main.scale;
			x = x*scale;
			y = y*scale;
			width = width*scale;
			height = height*scale;
		}
		g.drawRect((int)x, (int)y, (int)width, (int)height);
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	public void fillRect(double x, double y, double width, double height) {
		if(scalable) {
			scale = Main.Main.scale;
			x = x*scale;
			y = y*scale;
			width = width*scale;
			height = height*scale;
		}
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
	public void drawOval(double x, double y, double width, double height) { 
		if(scalable) {
			scale = Main.Main.scale;
			x = x*scale;
			y = y*scale;
			width = width*scale;
			height = height*scale;
		}
		g.drawOval((int)x, (int)y, (int)width, (int)height);
	}
	public void fillOval(double x, double y, double width, double height) {
		if(scalable) {
			scale = Main.Main.scale;
			x = x*scale;
			y = y*scale;
			width = width*scale;
			height = height*scale;
		}
		g.fillOval((int)x, (int)y, (int)width, (int)height); }
	public void drawCircle(double x, double y, double size) {
		if(scalable) {
			scale = Main.Main.scale;
			x = x*scale;
			y = y*scale;
			size = size*scale;
		}
		g.drawOval((int)(x), (int)(y), (int)size, (int)size);
	}
	public void drawCircle(Point p, double size) {
		if(scalable) {
			scale = Main.Main.scale;
			p.setLocation(p.getX()*scale, p.getY()*scale);
			size = size * 2;
		}
		g.drawOval((int)(p.getX()), (int)(p.getY()), (int)size, (int)size);
	}
	public void drawCenterCircle(double x, double y, double size) { 
		if(scalable) {
			scale = Main.Main.scale;
			x = x*scale;
			y = y*scale;
			size = size*scale;
		}
		g.drawOval((int)(x-size/2), (int)(y-size/2), (int)size, (int)size); 
	}
	public void fillCircle(double x, double y, double size) {
		if(scalable) {
			x = x*scale;
			y = y*scale;
			size = size*scale;
		}
		g.fillOval((int)x, (int)y, (int)size, (int)size);
	}
	public void fillCenterCircle(double x, double y, double size) {
		if(scalable) {
			x = x*scale;
			y = y*scale;
			size = size*scale;
		}
		g.fillOval((int)(x+size/2), (int)(y+size/2), (int)size, (int)size);
	}
	public void fillCircle(Point p, double size) {
		if(scalable) {
			p.setLocation(p.getX()*scale, p.getY()*scale);
			size = size * 2;
		}
		g.fillOval((int)(p.getX()), (int)(p.getY()), (int)size, (int)size);
	}
	public void drawImage(BufferedImage img,double x, double y, double width, double height) {
		if(scalable) {
			x = x*scale;
			y = y*scale;
			width = width*scale;
			height = height*scale;
		}
		g.drawImage(img, (int)x, (int)y, (int)width, (int)height, null);
	}
	public void drawRotatedImage(BufferedImage image,double x, double y, double width, double height, double angle) {
		if(angle%2!=0) {
			BufferedImage rotated = Assets.rotateImageByDegrees(image,angle);
			double tempx = (double)rotated.getWidth()/image.getWidth();
			double tempy = (double)rotated.getHeight()/image.getHeight();
			drawImage(rotated,x-(width*(tempx-1))/2,y-(height*(tempy-1))/2,width*tempx,height*tempy);
		}else
			drawImage(image,x,y,width,height);
	}
	public void drawString(String str, double x, double y) { 
		if(str==null)
			g.drawString("null", (int)x, (int)y);
		else
			g.drawString(str, (int)x, (int)y);
	}
	public void drawOutlinedString(String str, double x, double y) {
		drawOutlinedString(str, x, y, Color.white, Color.black);
	}
	public void drawOutlinedString(String str, double x, double y, Color strColor, Color outlineColor) {
		int borderAmount = 1;
		g.setColor(outlineColor);
		drawString(str, x+borderAmount, y);
		drawString(str, x-borderAmount, y);
		drawString(str, x, y+borderAmount);
		drawString(str, x, y-borderAmount);
		g.setColor(strColor);
		drawString(str, x, y);
	}
	public void drawLine(double x1, double y1, double x2, double y2) {
		if(scalable) {
			x1 = x1*scale;
			y1 = y1*scale;
			x2 = x2*scale;
			y2 = y2*scale;
		}
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	public void drawRLine(double x1, double y1, double x2, double y2) {
		if(scalable) {
			x1 = x1*scale;
			y1 = y1*scale;
			x2 = x2*scale;
			y2 = y2*scale;
		}
		x2 = x2+x1;
		y2 = y2+y1;
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	public void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle) { 
		if(scalable) {
			x = x*scale;
			y = y*scale;
			width = width*scale;
			height = height*scale;
		}
		g.drawArc((int)x, (int)y, (int)width, (int)height, (int)startAngle, (int)arcAngle);
	}
	public void setFont(Font font) {
		g.setFont(font);
	}
	public void setFont(String font,int style,int size) {
		this.fontSize = size;
		g.setFont( new Font(font,style,size) );
	}
	public int getStringLength(String s) { return g.getFontMetrics().stringWidth(s); }
	public int getFontHeight() { return g.getFontMetrics().getHeight(); }
	public FontMetrics getFontMetrics() { return g.getFontMetrics(); }
	public void drawShape(Shape shape) { g.draw(shape); }
	public void drawPoly(Polygon shape) { 
		if(scalable) {
			for(int z=0;z<shape.npoints;z++) {
				shape.xpoints[z] = (int)(shape.xpoints[z] * scale);
				shape.ypoints[z] = (int)(shape.ypoints[z] * scale);
			}
		}
		g.draw(shape);
	}
	public Polygon rotatePolygon(Polygon p1,double angle,Point center) {
		Polygon newPolygon = new Polygon();
		for(int z=0;z<p1.npoints;z++) {
			Point p=new Point(p1.xpoints[z],p1.ypoints[z]);
			p = rotatePoint(p,center,angle);
			newPolygon.addPoint(p.x,p.y);
		}
		return newPolygon;
	}
	public Polygon rotatePolygon(Polygon p1,double angle) {
		Polygon newPolygon = new Polygon();
		Point center=new Point(0,0);
		double tempSumX = 0,tempSumY=0;
		
		for(int z=0;z<p1.npoints;z++) {
			tempSumX +=p1.xpoints[z];
			tempSumY +=p1.ypoints[z];
		}
		center.setLocation(tempSumX/p1.npoints, tempSumY/p1.npoints);
		for(int z=0;z<p1.npoints;z++) {
			Point p=new Point(p1.xpoints[z],p1.ypoints[z]);
			p = rotatePoint(p,center,angle);
			newPolygon.addPoint(p.x,p.y);
		}
		return newPolygon;
	}
	public Point rotatePoint(Point pt, Point center, double angleDeg) {
		double angleRad = Math.toRadians(angleDeg);
		double cosThetha = Math.cos(angleRad); //The angle COS
		double sinThetha = Math.sin(angleRad); //The angle SIN
		double dx = (pt.x - center.x); //Difference (Point in transformed to origo)
		double dy = (pt.y - center.y); //Difference -- || --

		int ptX = center.x + (int) (dx * cosThetha - dy * sinThetha);
		int ptY = center.y + (int) (dx * sinThetha + dy * cosThetha);

		return new Point(ptX, ptY);
	}
}