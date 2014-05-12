package main;

import java.awt.Color;
import java.awt.Graphics;

public class Screen  {

	private Pong pong;
	private KeyHandler keyHandler;
	
	public Screen(Pong pong, KeyHandler keyHandler){
		this.pong = pong;
		this.keyHandler = keyHandler;
	}
	
	public void tick(){
		if(keyHandler.c.getPressed()){
			pong.newGame();
		}
		if(keyHandler.e.getPressed()){
			System.exit(0);
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("Game Over!", 100, 100);
		g.drawString("Press c to restart.", 100, 200);
		g.drawString("Press e to exit.", 100, 300);
	}
	
}
