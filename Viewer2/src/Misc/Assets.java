package Misc;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Assets {
	public static BufferedImage[] assets=new BufferedImage[100];
	public static BufferedImage[][] ani=new BufferedImage[10][50];
	public static ArrayList<ArrayList<BufferedImage>> anim = new ArrayList<ArrayList<BufferedImage> >(); 
	
	public static void init(){}
	//Divide up animation sheets & sprite sheets
	public static void name(String s,int width, int height, int numberOfImages) {
		BufferedImage image = ImageLoader.loadImage(s);
		SpriteSheet sheet= new SpriteSheet(image);
		int widthPixel = image.getWidth()/width;
		int heightPixel = image.getHeight()/height;
		
		ArrayList<BufferedImage> a = new ArrayList<BufferedImage>();
		for(int y=0;y<height & numberOfImages>0;y++)
			for(int x=0;x<width & numberOfImages>0;x++) {
				a.add(sheet.crop(x*widthPixel, y*heightPixel, widthPixel, heightPixel));
				numberOfImages--;
			}
		anim.add(a);
	}
	
	public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
		
		double rads = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
		int w = img.getWidth();
		int h = img.getHeight();
		int newWidth = (int) Math.floor(w * cos + h * sin);
		int newHeight = (int) Math.floor(h * cos + w * sin);
		
		BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotated.createGraphics();
		AffineTransform at = new AffineTransform();
		at.translate((newWidth - w) / 2, (newHeight - h) / 2);
		
		int x = w / 2;
		int y = h / 2;
		
		at.rotate(rads, x, y);
		g2d.setTransform(at);
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		
		return rotated;
	}
}

class ImageLoader {
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Graphics not found. Please place graphics in \n"+new File("").getAbsolutePath(), "Fatal Error Occured", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}

class SpriteSheet {
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int x, int y, int width, int height){
		return sheet.getSubimage(x, y, width, height);
	}
}