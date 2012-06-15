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
import controler.GameControler;

/**
 * @author Nicolas
 *
 */
public class UserInterface extends JFrame implements Observer {

	private static final long serialVersionUID = -1180186872270103912L;

	private static ResourceBundle lang;

	private GameControler controler;

	private JPanel container;

	public UserInterface(GameControler controler) {
		setLang(Locale.getDefault());

		this.controler = controler;

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
		Resource startRoom = this.controler.newGame();
		
		this.setSize(GamePanel.SIZE);
		this.container.removeAll();
		GamePanel gp = new GamePanel(startRoom);
		this.container.add(gp, BorderLayout.CENTER);
		this.container.revalidate();
	}

	public void quit() {
		System.exit(0);
	}

}
