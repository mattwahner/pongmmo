package main;

import java.awt.Color;

import network.NetClientHandler;
import main.KeyHandler.Key;

public class MPPlayer extends Player {

	private NetClientHandler nch;
	
	public MPPlayer(Pong pong, Color color, int startX, NetClientHandler nch){
		super(pong, color, null, null, startX);
		this.nch = nch;
	}
	
	public MPPlayer(Pong pong, Color color, Key upButton, Key downButton, int startX, NetClientHandler nch) {
		super(pong, color, upButton, downButton, startX);
		this.nch = nch;
	}
	
	public void tick(){
		if(getUpButton() != null){
			if(getUpButton().getPressed()) nch.handlePlayerMove(nch.getTeam(), -1);
		}
		if(getDownButton() != null){
			if(getDownButton().getPressed()) nch.handlePlayerMove(nch.getTeam(), 1);
		}
		if((nch.getTeam().equals("Left") && getUpButton() != null) || (nch.getTeam().equals("Right") && getUpButton() == null)) setY(nch.getPlayerLeftPos());
		if((nch.getTeam().equals("Left") && getUpButton() == null) || (nch.getTeam().equals("Right") && getUpButton() != null)) setY(nch.getPlayerRightPos());
		centerHitboxes();
	}

}
