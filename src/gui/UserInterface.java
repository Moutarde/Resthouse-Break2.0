/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JPanel;

/**
 * @author Nicolas Kniebihler
 *
 */
public class UserInterface implements Observer {
	private static ResourceBundle lang;

	private JPanel container = new JPanel();

	public UserInterface() {
		setLang(Locale.getDefault());

		initComponents();
	}

	public static ResourceBundle getLang() {
		return lang;
	}

	public static void setLang(Locale l) {
		lang = ResourceBundle.getBundle("lang/Text", l);
	}

	private void initComponents() {
		container.setLayout(new BorderLayout());

		HomePanel home = new HomePanel(this);
		container.add(home, BorderLayout.NORTH);

		container.setPreferredSize(HomePanel.SIZE);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void newGame() {
		GamePanel gp = new GamePanel();
		updateContainers(gp);

		new Thread(gp).start();
	}
	
	public void updateContainers(GamePanel gp) {
		container.removeAll();
		container.add(gp, BorderLayout.NORTH);
		container.revalidate();
		container.setPreferredSize(GamePanel.SIZE);
	}

	public void quit() {
		System.exit(0);
	}
	
	public JPanel getUIContainer() {
		return container;
	}
}
