package Misc;

import java.awt.Point;

import Main.StateManager;

public class Mat {
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt( Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2) );
	}
	public static int limit(int x) {
		if(x>255) x=255;
		return x;
	}
	public static double getAngle(double x1,double y1, double x2, double y2) {
		double 
			px = x2-x1,
			py = y2-y1,
			angle = Math.atan2( py,px);
		return angle;
	}
}
