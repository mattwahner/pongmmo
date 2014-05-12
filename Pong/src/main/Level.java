package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Level {
	
	private Pong pong;
	private KeyHandler keyHandler;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Level(Pong pong, KeyHandler keyHandler){
		this.pong = pong;
		this.keyHandler = keyHandler;
	}
	
	public void init(){
		entities.add(new Player(pong, Color.CYAN, keyHandler.w, keyHandler.s, pong.getWidth() / 10));
		entities.add(new Player(pong, Color.MAGENTA, keyHandler.upArrow, keyHandler.downArrow, (int) (pong.getWidth() * (9.0 / 10.0))));
		entities.add(new Ball(pong, this));
		for(Entity e : entities){
			e.init();
		}
	}
	
	public void tick(){
		for(Entity e : entities){
			e.tick();
		}
	}
	
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
}
