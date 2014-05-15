package main;

import java.awt.Graphics;

public class MainScreen implements Screen {

	private Pong pong;
	private KeyHandler keyHandler;
	private Resources res;
	
	public MainScreen(Pong pong, KeyHandler keyHandler, Resources res){
		this.pong = pong;
		this.keyHandler = keyHandler;
		this.res = res;
	}
	
	public void init(){}
	
	public void tick(){
		if(keyHandler.space.getPressed()){
			pong.newGame();
		}
	}
	
	public void render(Graphics g){
		g.drawImage(res.pong, 0, 0, null);
	}

}
