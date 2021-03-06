package controller;

import gui.GameRenderer;
import gui.Renderer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import model.GameModel;

/**
 * @author Nicolas
 *
 * Inspired from Building Games Using the MVC Pattern - Tutorial and Introduction
 * Author:	impaler@obviam.net
 * Date:	2012.02
 * Link:	http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/
 *
 */
public class GameEngine {
    private GameModel model;
    private Renderer renderer;
    private GameController controller;
    private Keyboard keyboard;

    public GameEngine() {
        model = new GameModel();
        model.init();

        renderer = new GameRenderer(model);
        controller = new GameController(model);

        keyboard = new Keyboard();

        model.start();
    }

    /** the update method with the deltaTime in seconds **/
    public void update(float deltaTime) {
        handleEvents();
        controller.update(deltaTime);
    }

    /** this will render the whole world **/
    public void render(Graphics g) {
        renderer.render(g);
    }

    /**
     * @return the keyboard
     */
    public Keyboard getKeyboard() {
        return keyboard;
    }

    private void handleEvents() {
        // LEFT
        if (keyboard.isPressed(KeyEvent.VK_LEFT)) {
            controller.onStartMovingAsked(Direction.LEFT);
        }
        else {
            controller.onStopMovingAsked(Direction.LEFT);
        }

        // RIGHT
        if (keyboard.isPressed(KeyEvent.VK_RIGHT)) {
            controller.onStartMovingAsked(Direction.RIGHT);
        }
        else {
            controller.onStopMovingAsked(Direction.RIGHT);
        }

        // UP
        if (keyboard.isPressedOnce(KeyEvent.VK_UP) && model.isGamePaused()) {
            controller.onMoveMenuSelection(Direction.UP);
        }
        else if (keyboard.isPressed(KeyEvent.VK_UP)) {
            controller.onStartMovingAsked(Direction.UP);
        }
        else {
            controller.onStopMovingAsked(Direction.UP);
        }

        // DOWN
        if (keyboard.isPressedOnce(KeyEvent.VK_DOWN) && model.isGamePaused()) {
            controller.onMoveMenuSelection(Direction.DOWN);
        }
        else if (keyboard.isPressed(KeyEvent.VK_DOWN)) {
            controller.onStartMovingAsked(Direction.DOWN);
        }
        else {
            controller.onStopMovingAsked(Direction.DOWN);
        }

        // ENTER
        if (keyboard.isPressedOnce(KeyEvent.VK_ENTER)) {
            controller.onValidate();
        }

        // ESC --> menu
        if (keyboard.isPressedOnce(KeyEvent.VK_ESCAPE)) {
            controller.onOpenMenu();
        }

        // D --> debug display
        if (keyboard.isPressedOnce(KeyEvent.VK_D)) {
            renderer.changeDebugMode();
        }
    }
}
