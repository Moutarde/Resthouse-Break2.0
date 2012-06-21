/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Nicolas
 *
 */
public class UserInterface extends JFrame implements Observer {

	private static final long serialVersionUID = -1180186872270103912L;

	private static ResourceBundle lang;

	private JPanel container;

	public UserInterface() {
		setLang(Locale.getDefault());

		setTitle("Resthouse Break");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		initComponents();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static ResourceBundle getLang() {
		return lang;
	}

	public static void setLang(Locale l) {
		lang = ResourceBundle.getBundle("resources/lang/Text", l);
	}

	private void initComponents() {
		container = new JPanel();

		container.setLayout(new BorderLayout());

		HomePanel home = new HomePanel(this);
		container.add(home, BorderLayout.NORTH);

		container.setPreferredSize(HomePanel.SIZE);

		setContentPane(container);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void newGame() {
		container.removeAll();
		GamePanel gp = new GamePanel();
		container.add(gp, BorderLayout.NORTH);
		container.revalidate();

		container.setPreferredSize(GamePanel.SIZE);
		
		pack();
		setLocationRelativeTo(null);

		new Thread(gp).start();
	}

	public void quit() {
		System.exit(0);
	}

}
