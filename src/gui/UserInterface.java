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

import model.Resource;
import controller.GameController;

/**
 * @author Nicolas
 *
 */
public class UserInterface extends JFrame implements Observer {

	private static final long serialVersionUID = -1180186872270103912L;

	private static ResourceBundle lang;

	private GameController controller;

	private JPanel container;

	public UserInterface(GameController controller) {
		setLang(Locale.getDefault());

		this.controller = controller;

		this.setTitle("Resthouse Break");
		this.setSize(HomePanel.SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		initComponents();

		this.setVisible(true);
	}

	public static ResourceBundle getLang() {
		return lang;
	}

	public static void setLang(Locale l) {
		lang = ResourceBundle.getBundle("resources/lang/Text", l);
	}

	private void initComponents() {
		this.container = new JPanel();

		this.container.setLayout(new BorderLayout());

		HomePanel home = new HomePanel(this);
		this.container.add(home, BorderLayout.NORTH);

		this.setContentPane(this.container);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void newGame() {
		this.setSize(GamePanel.SIZE);
		this.container.removeAll();
		GamePanel gp = new GamePanel();
		this.container.add(gp, BorderLayout.CENTER);
		this.container.revalidate();
	}

	public void quit() {
		System.exit(0);
	}

}
