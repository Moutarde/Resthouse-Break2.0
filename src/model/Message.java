package model;

import gui.GamePanel;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Message {
	String currentString = "";
	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 20;
	
	public Message() {
	}
	
	public String getString() {
		return currentString;
	}
	
	public void setString(String str) {
		currentString = str;
	}
	
	public boolean isEmpty() {
		return currentString.isEmpty();
	}
	
	public void draw(Graphics g) {
		int position = GamePanel.SIZE.height - GamePanel.TEXT_ZONE_HEIGHT;
		g.setColor(Color.white);
		g.fillRect(0, position, GamePanel.SIZE.width, GamePanel.SIZE.height);
		g.setColor(Color.black);
		g.drawString(currentString, OFFSET_X, position + OFFSET_Y);
	}
}
