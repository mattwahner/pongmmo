package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {
	
	public BufferedImage pong = null;
	
	public Resources(){
		try {
			pong = ImageIO.read(new File("res/pong.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
