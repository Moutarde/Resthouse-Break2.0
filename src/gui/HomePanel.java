/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Resource;

/**
 * @author Nicolas
 *
 */
public class HomePanel extends JPanel {

	private static final long serialVersionUID = -59115957354504598L;
	
	public static final Dimension SIZE = new Dimension(270, 270);

	private UserInterface gui;
	private JButton newgame;
	private JButton quit;

	public HomePanel(UserInterface gui) {
		super();
		
		this.gui = gui;

		this.setSize(SIZE);

		this.setLayout(new BorderLayout());

		JLabel img = new JLabel(Resource.HOME.getImageIcon());

		JPanel buttons = new JPanel();
		this.newgame = new JButton(UserInterface.getLang().getString("newgame"));
		this.newgame.addActionListener(new ButtonListener());
		this.quit = new JButton(UserInterface.getLang().getString("quit"));
		this.quit.addActionListener(new ButtonListener());
		buttons.add(this.newgame);
		buttons.add(this.quit);

		this.add(img, BorderLayout.NORTH);
		this.add(buttons, BorderLayout.SOUTH);
	}

	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == newgame) {
				gui.newGame();
			}
			if(e.getSource() == quit) {
				gui.quit();
			}
		}
	}

}
