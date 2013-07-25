package gui;

import javax.swing.JFrame;

/**
 * @author Nicolas Kniebihler
 *
 * With this UI, the game opens itself in a window.
 */
public class UserInterfaceWithExternalWindow extends UserInterface {
    private JFrame window = new JFrame();

    public UserInterfaceWithExternalWindow() {
        super();

        window.setTitle("Resthouse Break");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.setContentPane(getUIContainer());

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void updateContainers(GamePanel gp) {
        super.updateContainers(gp);

        window.pack();
        window.setLocationRelativeTo(null);
    }
}
