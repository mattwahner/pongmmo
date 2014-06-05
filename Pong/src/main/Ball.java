package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ball implements Entity {

	private Pong pong;
	private Level level;

	private Rectangle hitbox;
	private Point centerPoint;
	private int x;
	private int y;
	private int width;
	private int height;
	private double speedX;
	private double speedY;
	private int playerBounceCD;
	private int wallBounceCD;

	public Ball(Pong pong, Level level){
		this.pong = pong;
		this.level = level;
	}

	public void init() {
		width = 10;
		height = 10;
		x = (pong.getWidth() / 2) - (width / 2);
		y = (pong.getHeight() / 2) - (height / 2);
		speedX = -5;
		speedY = 0;
		hitbox = new Rectangle(x, y, width, height);
		centerPoint = new Point(x + (width / 2), y + (height / 2));
		playerBounceCD = 3;
		wallBounceCD = 3;
	}

	public void tick() {
		x += speedX;
		y += speedY;
		if(playerBounceCD <= 0){
			ArrayList<Entity> entities = level.getEntities();
			for(Entity e : entities){
				if(e instanceof Player || e instanceof MPPlayer){
					if(((Player) e).isIntersecting(hitbox)){
						Point pCenterPoint = ((Player) e).getCenterPoint();
						int pHeight = ((Player) e).getHeight();
						pHeight = (pHeight / 2) / 5;
						speedY = (int) (centerPoint.distance(pCenterPoint) / pHeight);
						if(pCenterPoint.getY() > centerPoint.getY())
							speedY *= -1;
						speedX *= -1;
						if(speedX > 0) speedX += 0.5;
						else speedX -= 0.5;
						playerBounceCD = 3;
					}
				}
			}
		}else{
			playerBounceCD--;
		}
		if(wallBounceCD <= 0){
			if(y < 0 || (y + height) > pong.getHeight()){
				speedY *= -1;
				if(speedY > 0) speedY += 0.5;
				else speedY -= 0.5;
				wallBounceCD = 3;
			}
		}else{
			wallBounceCD--;
		}
		if(x < 0 || x > pong.getWidth()){
			pong.gameOver();
		}
		hitbox.setRect(x, y, width, height);
		centerPoint.setLocation(x + (width / 2), y + (height / 2));
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);

	}

}
