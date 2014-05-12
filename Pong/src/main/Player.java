package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Player implements Entity {

	private Pong pong;
	private Color color;
	private KeyHandler.Key upButton;
	private KeyHandler.Key downButton;
	
	private Rectangle hitbox;
	private Point centerPoint;
	private int x;
	private int y;
	private int height;
	private int width;
	private int speed;
	
	public Player(Pong pong, Color color, KeyHandler.Key upButton, KeyHandler.Key downButton, int startX){
		this.pong = pong;
		this.color = color;
		this.upButton = upButton;
		this.downButton = downButton;
		x = startX;
	}
	
	public void init() {
		height = 100;
		width = 20;
		speed = 10;
		y = (pong.getHeight() / 2) - (height / 2);
		hitbox = new Rectangle(x, y, width, height);
		centerPoint = new Point(x + (width / 2), y + (height / 2));
	}

	public void tick() {
		if(upButton.getPressed() && y > 0){
			y -= speed;
		}
		else if(downButton.getPressed() && (y + height) < pong.getHeight()){
			y += speed;
		}
		hitbox.setRect(x, y, width, height);
		centerPoint.setLocation(x + (width / 2), y + (height / 2));
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean isIntersecting(Rectangle r){
		return hitbox.intersects(r);
	}
	
	public Point getCenterPoint(){
		return centerPoint;
	}

}
