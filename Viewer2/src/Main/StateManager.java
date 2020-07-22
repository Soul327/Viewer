package Main;

import Misc.Graphics;

public class StateManager {
	Viewer viewer = new Viewer();
	
	public void tick() {
		viewer.tick();
	}
	public void render(Graphics g) {
		viewer.render(g);
	}
}