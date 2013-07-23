import gui.UserInterface;
import gui.UserInterfaceWithExternalWindow;

import java.awt.BorderLayout;

import javax.swing.JApplet;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Game extends JApplet {

	private static final long serialVersionUID = -7803629994015778818L;

	@Override
	public void init() {
		UserInterface gui = new UserInterface();
		getContentPane().setLayout(new BorderLayout()); 
		getContentPane().add(gui.getUIContainer(), BorderLayout.CENTER);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new UserInterfaceWithExternalWindow();
	}

}
