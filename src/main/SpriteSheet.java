package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private ArrayList<BufferedImage> subImages = new ArrayList<BufferedImage>();
	
	public SpriteSheet(String imgStr, int index, int width){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imgStr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < index; i++){
			subImages.add(img.getSubimage(i * width, 0, width, img.getHeight()));
		}
	}
	
	public BufferedImage getImageByIndex(int index){
		return subImages.get(index);
	}
	
}
