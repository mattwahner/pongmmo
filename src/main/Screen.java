package main;

import java.awt.Graphics;

public interface Screen  {

	public void init();
	public void tick();
	public void render(Graphics g);
	
}
