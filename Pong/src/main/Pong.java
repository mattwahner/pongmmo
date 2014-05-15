package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Pong extends Canvas implements Runnable {

	private static final long serialVersionUID = 8484419508934349888L;
	private static final String NAME = "Pong";
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	
	private KeyHandler keyHandler;
	
	private boolean running = false;
	
	private Level level;
	private Screen screen;
	private Resources res;
	
	public void start(){
		running = true;
		new Thread(this).run();
	}
	
	public void stop(){
		running = false;
	}
	
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		long timeMillis = System.currentTimeMillis();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int tickCount = 0;
		int frameCount = 0;
		
		while(running){
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			while(unprocessed >= 1){
				tick();
				tickCount++;
				unprocessed--;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			render();
			frameCount++;
			if(timeMillis + 1000 < System.currentTimeMillis()){
				timeMillis = System.currentTimeMillis();
				System.out.println("FPS: " + frameCount + " \tTicks: " + tickCount);
				frameCount = 0;
				tickCount = 0;
			}
		}
	}
	
	public void gameOver(){
		screen = new GameOverScreen(this, keyHandler);
		level = null;
	}
	
	public void newGame(){
		level = new Level(this, keyHandler);
		level.init();
		screen = null;
	}
	
	private void init(){
		res = new Resources();
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		screen = new MainScreen(this, keyHandler, res);
		level = null;
	}
	
	private void tick(){
		if(level != null)
			level.tick();
		else
			screen.tick();
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			this.requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(level != null)
			level.render(g);
		else
			screen.render(g);
		
		bs.show();
		g.dispose();
	}
	
	public static void main(String[] args){
		Pong pong = new Pong();
		pong.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		pong.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		pong.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		JFrame frame = new JFrame();
		frame.setTitle(NAME);
		frame.setLayout(new BorderLayout());
		frame.add(pong);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		pong.start();
	}

}
