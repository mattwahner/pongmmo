package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {
	
	public BufferedImage pongTitleScreen = null;
	public BufferedImage pongMainScreenNewGame = null;
	public BufferedImage pongMainScreenExit = null;
	
	public Resources(){
		try {
			BufferedImage pong = ImageIO.read(new File("res/pong.png"));
			pongTitleScreen = pong.getSubimage(0, 0, 1024, 768);
			pongMainScreenNewGame = pong.getSubimage(1024, 0, 1024, 768);
			pongMainScreenExit = pong.getSubimage(2048, 0, 1024, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
