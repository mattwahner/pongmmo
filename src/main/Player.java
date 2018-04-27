package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Player implements Entity {

	public static final int SPEED = 10;
	public static final int HEIGHT = 100;
	
	private Pong pong;
	private Color color;
	private KeyHandler.Key upButton;
	private KeyHandler.Key downButton;
	
	private Rectangle hitbox;
	private Point centerPoint;
	private int x;
	private int y;
	private int width;
	
	public Player(Pong pong, Color color, KeyHandler.Key upButton, KeyHandler.Key downButton, int startX){
		this.pong = pong;
		this.color = color;
		this.upButton = upButton;
		this.downButton = downButton;
		x = startX;
	}
	
	public void init() {
		width = 20;
		y = (pong.getHeight() / 2) - (HEIGHT / 2);
		hitbox = new Rectangle(x, y, width, HEIGHT);
		centerPoint = new Point(x + (width / 2), y + (HEIGHT / 2));
	}

	public void tick() {
		if(upButton.getPressed() && y > 0){
			y -= SPEED;
		}
		else if(downButton.getPressed() && (y + HEIGHT) < pong.getHeight()){
			y += SPEED;
		}
		centerHitboxes();
	}
	
	public void centerHitboxes(){
		hitbox.setRect(x, y, width, HEIGHT);
		centerPoint.setLocation(x + (width / 2), y + (HEIGHT / 2));
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, HEIGHT);
	}
	
	public int getHeight(){
		return HEIGHT;
	}
	
	public boolean isIntersecting(Rectangle r){
		return hitbox.intersects(r);
	}
	
	public Point getCenterPoint(){
		return centerPoint;
	}
	
	public KeyHandler.Key getUpButton(){
		return upButton;
	}
	
	public KeyHandler.Key getDownButton(){
		return downButton;
	}
	
	public void setY(int y){
		this.y = y;
	}

}
