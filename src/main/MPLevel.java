package main;

import java.awt.Color;
import java.util.ArrayList;

import network.NetClientHandler;

public class MPLevel extends Level {

	private NetClientHandler nch;
	
	public MPLevel(Pong pong, KeyHandler keyHandler, NetClientHandler nch) {
		super(pong, keyHandler);
		this.nch = nch;
	}
	
	public void init(){
		if(nch.getTeam().equals("Left")){
			ArrayList<Entity> entities = getEntities();
			entities.add(new MPPlayer(getPong(), Color.CYAN, getKeyHandler().w, getKeyHandler().s, getPong().getWidth() / 10, nch));
			entities.add(new MPPlayer(getPong(), Color.MAGENTA, (int) (getPong().getWidth() * (9.0 / 10.0)), nch));
			entities.add(new Ball(getPong(), this));
			setEntities(entities);
		}
		else if(nch.getTeam().equals("Right")){
			ArrayList<Entity> entities = getEntities();
			entities.add(new MPPlayer(getPong(), Color.CYAN, getPong().getWidth() / 10, nch));
			entities.add(new MPPlayer(getPong(), Color.MAGENTA, getKeyHandler().w, getKeyHandler().s, (int) (getPong().getWidth() * (9.0 / 10.0)), nch));
			entities.add(new Ball(getPong(), this));
			setEntities(entities);
		}
		for(Entity e : getEntities()){
			e.init();
		}
	}

}
