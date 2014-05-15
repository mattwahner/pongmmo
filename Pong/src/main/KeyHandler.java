package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

	public class Key{
		
		private boolean pressed;
		private int keyCode;
		
		public Key(int keyCode){
			pressed = false;
			this.keyCode = keyCode;
		}
		
		public void setPressed(boolean pressed){
			this.pressed = pressed;
		}
		
		public boolean getPressed(){
			return pressed;
		}
		
		public int getKeyCode(){
			return keyCode;
		}
		
	}
	
	private ArrayList<Key> keys = new ArrayList<Key>();
	public final Key w = new Key(87);
	public final Key s = new Key(83);
	public final Key c = new Key(67);
	public final Key e = new Key(69);
	public final Key upArrow = new Key(38);
	public final Key downArrow = new Key(40);
	public final Key space = new Key(32);
	
	public KeyHandler(){
		keys.add(w);
		keys.add(s);
		keys.add(c);
		keys.add(e);
		keys.add(upArrow);
		keys.add(downArrow);
		keys.add(space);
	}
	
	public void releaseKeys(){
		for(Key k : keys){
			k.pressed = false;
		}
	}
	
	public void keyPressed(KeyEvent ke) {
		for(Key k : keys){
			if(ke.getKeyCode() == k.getKeyCode()){
				k.setPressed(true);
			}
		}
	}

	public void keyReleased(KeyEvent ke) {
		for(Key k : keys){
			if(ke.getKeyCode() == k.getKeyCode()){
				k.setPressed(false);
			}
		}
	}

	public void keyTyped(KeyEvent ke) {}

}
