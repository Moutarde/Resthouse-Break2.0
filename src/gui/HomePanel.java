/**
 * 
 */
package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Resources;

/**
 * @author Nicolas
 *
 */
public class HomePanel extends JPanel {

	private static final long serialVersionUID = -59115957354504598L;

	public HomePanel() {
		super();
		
		this.setSize(270, 270);
        
        this.setLayout(new BorderLayout());
        
        JLabel img = new JLabel(Resources.HOME.getImageIcon());
	    
	    JPanel buttons = new JPanel();
	    buttons.add(new JButton(UserInterface.getLang().getString("newgame")));
	    buttons.add(new JButton(UserInterface.getLang().getString("quit")));
	    
	    this.add(img, BorderLayout.NORTH);
	    this.add(buttons, BorderLayout.SOUTH);
	}
	
}
