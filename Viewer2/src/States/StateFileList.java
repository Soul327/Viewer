package States;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import Misc.Assets;
import Misc.Graphics;
import Misc.KeyManager;
import Misc.MouseManager;

public class StateFileList extends State {
	File file;
	File sel = null;
	File contents[];
	String selectedStr = null;
	
	int scroll = 0;
	
	public StateFileList() {
		load(new File("C:\\"));
	}
	public void load(File file) {
		this.file = file;
		contents = file.listFiles();
		scroll = 0;
	}
	
	public void tick() {
		if(KeyManager.keyRelease(KeyEvent.VK_BACK_SPACE))
			load(file.getParentFile());
	}
	
	boolean held = false;
	
	public void scroll(int scrollAmount) {
		scroll -= scrollAmount;
		if(scroll>0) scroll = 0;
	}
	
	public void render(Graphics g) {
		g.setFont( "Serif",Font.PLAIN,15 );
		if(selectedStr!=null) sel = new File(selectedStr);
		if(contents!=null)
			for(int x=0;x<contents.length;x++) {
				boolean hover = false;
				if(mx>this.x & mx<this.x+this.width & my<(x+1)*g.fontSize+scroll & my>x*g.fontSize+scroll) hover = true;
				
				if(hover & MouseManager.leftPressed & selected & !held) {
					if(sel == contents[x]) {
						if( contents[x].isDirectory() ) {
							load(sel);
							break;
						}
					}else
						sel = contents[x];
					held = true;
				}
				if(!MouseManager.leftPressed)
					held = false;
				
				if(sel == contents[x]) {
					g.setColor(Color.gray);
					g.fillRect(g.fontSize*1.5, (x+.25)*g.fontSize+scroll, g.getStringLength(contents[x].getName())+10, g.fontSize);
					g.setColor(Color.white);
					g.drawRect(g.fontSize*1.5, (x+.25)*g.fontSize+scroll, g.getStringLength(contents[x].getName())+10, g.fontSize);
				}
				if(hover) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(g.fontSize*1.5, (x+.25)*g.fontSize+scroll, g.getStringLength(contents[x].getName())+10, g.fontSize);
					g.setColor(Color.white);
					g.drawRect(g.fontSize*1.5, (x+.25)*g.fontSize+scroll, g.getStringLength(contents[x].getName())+10, g.fontSize);
				}
				
				Icon icon = FileSystemView.getFileSystemView().getSystemIcon(contents[x]);
				if(icon!=null) {
					BufferedImage image = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
					java.awt.Graphics gg = image.createGraphics();
					// paint the Icon to the BufferedImage.
					icon.paintIcon(null, gg, 0,0);
					gg.dispose();
					g.drawImage(image, 5, (x+.25)*g.fontSize+scroll, g.fontSize, g.fontSize);
				}
				g.drawOutlinedString(contents[x].getName(), g.fontSize+5, (x+1)*g.fontSize+scroll);
			}
}
	
}
