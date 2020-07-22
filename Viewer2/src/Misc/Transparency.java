package Misc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

public class Transparency {
	public static Image makeColorTransparent
  (Image im,  Color color) {
  ImageFilter filter = new RGBImageFilter() {
    // the color we are looking for... Alpha bits are set to opaque
    public int markerRGB = color.getRGB() | 0xFF000000;

    public final int filterRGB(int x, int y, int rgb) {
      if ( ( rgb | 0xFF000000 ) == markerRGB ) {
        // Mark the alpha bits as zero - transparent
        return 0x00FFFFFF & rgb;
        }
      else {
        // nothing to do
        return rgb;
        }
      }
    }; 

  ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
  return Toolkit.getDefaultToolkit().createImage(ip);
  }
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static BufferedImage l(BufferedImage image, BufferedImage overlay) {
		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(image.getWidth(), overlay.getWidth());
		int h = Math.max(image.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		java.awt.Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		g.dispose();
		return combined;
	}
}