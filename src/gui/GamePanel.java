package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import controller.GameEngine;

/**
 * @author Nicolas
 *
 * Inspired from Building Games Using the MVC Pattern - Tutorial and Introduction
 * Author:	impaler@obviam.net
 * Date:	2012.02
 * Link:	http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/
 *
 */
public class GamePanel extends JPanel implements Runnable {

    private static final long serialVersionUID = -8860204251354754377L;
    private static final boolean debugFrameRate = false;

    public static final Dimension SIZE = new Dimension(400, 400);
    public static final int TEXT_ZONE_HEIGHT = 60;
    public static final Dimension MENU_SIZE = new Dimension(120, 280);
    public static final Dimension SUBMENU_SIZE = new Dimension(150, 120);
    public static final Dimension INSPECT_ITEM_BOX_SIZE = new Dimension(100, 100);
    public static final Dimension SELECT_ANSWER_BOX_SIZE = new Dimension(70, 70);
    public static final Dimension STORE_MENU_SIZE = new Dimension(150, 200);
    public static final Dimension STORE_MENU_DETAILS_SIZE = new Dimension(100, 100);
    public static final Dimension TRANSACTION_MENU_SIZE = new Dimension(100, 100);
    public static final Dimension ITEMS_OWNED_MENU_SIZE = new Dimension(100, 100);

    private GameEngine engine;
    private JPanel screen;

    public GamePanel() {
        super();

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                requestFocus();
            }
        });

        engine = new GameEngine();
        addKeyListener(engine.getKeyboard());

        setPreferredSize(SIZE);

        setLayout(new BorderLayout());

        initComponents();

        add(screen, BorderLayout.CENTER);
    }

    private void initComponents() {
        screen = new JPanel();

        screen.setBackground(Color.black);
        screen.setPreferredSize(SIZE);
    }

    public JPanel getScreen() {
        return screen;
    }

    @Override
    public void run() {
        // Set up the graphics stuff, double-buffering.
        MediaTracker tracker = new MediaTracker(this);
        BufferedImage image = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        tracker.addImage(image, 0);
        Graphics g = image.getGraphics();
        Graphics appletGraphics = screen.getGraphics();

        requestFocus();

        long delta = 0l;
        long timeToSleep = 0l;
        long frameRate = 0l;

        // Game loop.
        while (true) {
            long lastTime = System.nanoTime();

            g.setColor(Color.black);
            g.fillRect(0, 0, SIZE.width, SIZE.height);

            // Update the state (convert to seconds)
            engine.update((float)(delta / 1000000000.0));
            // Render the world
            engine.render(g);

            if (debugFrameRate) {
                g.setColor(Color.red);
                g.drawString("frameRate = " + frameRate, 5, appletGraphics.getFontMetrics().getHeight());
            }

            // Draw the entire results on the screen.
            appletGraphics.drawImage(image, 0, 0, null);

            try {
                tracker.waitForID(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Lock the frame rate
            delta = System.nanoTime() - lastTime;
            if (delta < 20000000l) {
                try {
                    timeToSleep = (20000000l - delta) / 1000000l;
                    Thread.sleep(timeToSleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Frame rate too slow");
            }

            if (debugFrameRate) {
                long newFrameRate = 1000000000l / (System.nanoTime() - lastTime);
                frameRate = Math.abs(frameRate - newFrameRate) > 0.5 ? newFrameRate : frameRate;
            }
        }
    }
}
