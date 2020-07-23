package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Misc.Graphics;
import Misc.Util;

public class Test {
	public static void main(String args[]) throws IOException {
		//File file = new File("database\\database-please-no.txt");
		//Util.parseDataBase(file,"temp\\",10_000_000);
		
		int width=500, height=500;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// paint both images, preserving the alpha channels
		Graphics g = new Graphics((Graphics2D)image.getGraphics());
		g.setColor(Color.white);
		g.fillRect(0, 0, 300, 300);
		ImageIO.write(image, "PNG", new File("combined.png"));
	}
}