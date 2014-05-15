package main;

import java.awt.Graphics;

public class MainScreen implements Screen {

	private Pong pong;
	private KeyHandler keyHandler;
	private Resources res;
	
	private final int START_STATE = 0;
	private final int NEW_GAME_STATE = 1;
	private final int EXIT_STATE = 2;
	
	private int state;
	
	public MainScreen(Pong pong, KeyHandler keyHandler, Resources res){
		this.pong = pong;
		this.keyHandler = keyHandler;
		this.res = res;
	}
	
	public void init(){
		state = 0;
	}
	
	public void tick(){
		switch(state){
		case START_STATE:
			if(keyHandler.space.getPressed()){
				state = NEW_GAME_STATE;
				keyHandler.releaseKeys();
			}
			break;
		case NEW_GAME_STATE:
			if(keyHandler.downArrow.getPressed()){
				state = EXIT_STATE;
				keyHandler.releaseKeys();
			}
			else if(keyHandler.space.getPressed()) pong.newGame();
			break;
		case EXIT_STATE:
			if(keyHandler.upArrow.getPressed()){
				state = NEW_GAME_STATE;
				keyHandler.releaseKeys();
			}
			else if(keyHandler.space.getPressed()) System.exit(0);
			break;
		default:
			state = START_STATE;
			break;
		}
	}
	
	public void render(Graphics g){
		switch(state){
		case START_STATE:
			g.drawImage(res.mainMenu.getImageByIndex(0), 0, 0, null);
			break;
		case NEW_GAME_STATE:
			g.drawImage(res.mainMenu.getImageByIndex(1), 0, 0, null);
			break;
		case EXIT_STATE:
			g.drawImage(res.mainMenu.getImageByIndex(2), 0, 0, null);
			break;
		default:
			break;
		}
	}

}
