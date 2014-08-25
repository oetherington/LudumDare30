package ld30.com.main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener, FocusListener{
	public boolean[] keys;
	
	public InputHandler() {
		keys = new boolean[300];
		
		for(int i = 0; i < keys.length; i++) keys[i] = false;
	}
	
	public void focusGained(FocusEvent e) {}

	public void focusLost(FocusEvent e) {
		for(int i = 0; i < keys.length; i++) keys[i] = false;
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code >=0 && code < keys.length) keys[code] = true;
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code >=0 && code < keys.length) keys[code] = false;
	}

	public void keyTyped(KeyEvent e) {}
}