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
	public Key w = new Key(87);
	public Key s = new Key(83);
	public Key c = new Key(67);
	public Key e = new Key(69);
	public Key upArrow = new Key(38);
	public Key downArrow = new Key(40);
	
	public KeyHandler(){
		keys.add(w);
		keys.add(s);
		keys.add(c);
		keys.add(e);
		keys.add(upArrow);
		keys.add(downArrow);
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
