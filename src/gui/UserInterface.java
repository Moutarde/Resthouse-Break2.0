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

		this.setSize(270, 270);
        this.setTitle("Resthouse Break");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        initComponents();
        
        this.setVisible(true);
	}

	private void initComponents() {
        container = new JPanel();
        
        container.setLayout(new BorderLayout());

	    container.add(new HomePanel(), BorderLayout.NORTH);
        
        this.setContentPane(container);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public static ResourceBundle getLang() {
		return lang;
	}

	public static void setLang(Locale l) {
		lang = ResourceBundle.getBundle("resources/lang/Text", l);
	}

}
