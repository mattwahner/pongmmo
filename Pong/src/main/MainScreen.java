package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import network.NetClientHandler;
import network.PongServer;

public class MainScreen implements Screen {

	private Pong pong;
	private KeyHandler keyHandler;
	private Resources res;
	
	private PongServer server = null;
	private NetClientHandler nch = null;
	
	private final int START_STATE = 0;
	private final int NEW_GAME_STATE = 1;
	private final int EXIT_STATE = 2;
	private final int MULTI_STATE = 3;
	private final int MULTI_CLIENT_STATE = 4;
	private final int MULTI_SERVER_STATE = 5;
	
	private int state;
	private boolean onCreate = false;
	
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
			else if(keyHandler.e.getPressed()){
				state = MULTI_STATE;
				keyHandler.releaseKeys();
			}
			break;
		case MULTI_STATE:
			if(keyHandler.w.getPressed()){
				state = MULTI_CLIENT_STATE;
				keyHandler.releaseKeys();
			}
			else if(keyHandler.s.getPressed()){
				state = MULTI_SERVER_STATE;
				keyHandler.releaseKeys();
			}
			break;
		case MULTI_CLIENT_STATE:
			if(!onCreate){
				nch = new NetClientHandler(JOptionPane.showInputDialog("Enter an address: "), Integer.parseInt(JOptionPane.showInputDialog("Enter a port: ")));
				nch.handleLogin(JOptionPane.showInputDialog("Enter username: "), "None");
				onCreate = true;
			}
			if(nch.isStarted()) pong.newNetGame(nch, server);
			break;
		case MULTI_SERVER_STATE:
			if(!onCreate){
				int port = Integer.parseInt(JOptionPane.showInputDialog("Enter a port: ")); 
				server = new PongServer(port);
				nch = new NetClientHandler("127.0.0.1", port);
				nch.handleLogin(JOptionPane.showInputDialog("Enter username: "), "None");
				onCreate = true;
			}
			if(keyHandler.space.getPressed()){
				server.startGame();
				pong.newNetGame(nch, server);
				keyHandler.releaseKeys();
			}
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
		case MULTI_STATE:
			g.setColor(Color.WHITE);
			g.drawString("Press w for client", 10, 10);
			g.drawString("Press s for server", 10, 20);
			break;
		case MULTI_CLIENT_STATE:
			if(nch != null){
				g.setColor(Color.WHITE);
				g.drawString("Connected: " + nch.isConnected(), 10, 10);
			}
			break;
		case MULTI_SERVER_STATE:
			if(server != null){
				g.setColor(Color.WHITE);
				g.drawString("Num of players: " + server.getPlayers().size(), 10, 10);
				g.drawString("Press space to start!", 200, 10);
				for(int i = 0; i < server.getPlayers().size(); i++){
					g.drawString(server.getPlayers().get(i).getUsername() + "      Team: " + server.getPlayers().get(i).getTeam(), 10, 20 + (i * 10));
				}
			}
			break;
		case EXIT_STATE:
			g.drawImage(res.mainMenu.getImageByIndex(2), 0, 0, null);
			break;
		default:
			break;
		}
	}

}
