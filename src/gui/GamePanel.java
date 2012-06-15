/**
 * 
 */
package gui;

import gui.characters.CharacLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Resource;

/**
 * @author Nicolas
 *
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = -8860204251354754377L;
	
	public static final Dimension SIZE = new Dimension(300, 300);

	private JPanel screen;
	private JLabel background;
	private ImageIcon currentRoom;
	private ArrayList<CharacLabel> characters;
	private CharacLabel player;

	public GamePanel(Resource startRoom) {
		this.currentRoom = startRoom.getImageIcon();
		
		this.setSize(SIZE);
		
		this.setLayout(new BorderLayout());
		
		this.initComponents();

		this.add(screen, BorderLayout.CENTER);
	}

	private void initComponents() {
		this.screen = new JPanel();
		
		this.screen.setBackground(Color.black);
		this.screen.setSize(SIZE);
		this.screen.setLayout(new BorderLayout());

		this.background = new JLabel(this.currentRoom);
		
		this.screen.add(this.background, BorderLayout.CENTER);
	}

}
