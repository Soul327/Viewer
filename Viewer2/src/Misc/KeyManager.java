package Misc;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	public static boolean[] keys;
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public void tick(){
		
	}
	static boolean keyLog[]=new boolean[256];
	
	public static boolean keyRelease(int key){
		if(KeyManager.keys[key]){
			if(!keyLog[key]){
				keyLog[key]=true;
				return true;
			}
		}else if(!KeyManager.keys[key]){
			keyLog[key]=false;
		}
		return false;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = true;
		}catch(Exception ee) {}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = false;
		}catch(Exception ee) {}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//KeyManager.e=e;
	}

}
