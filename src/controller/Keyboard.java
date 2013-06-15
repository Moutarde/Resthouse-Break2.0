package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TreeMap;

public class Keyboard implements KeyListener {

    private final TreeMap<Integer, Boolean> keys;
    private final TreeMap<Integer, Boolean> pressed;
    private int lastCode;
    private char lastKeyName;

    Keyboard() {
        this.keys = new TreeMap<Integer, Boolean>();
        this.pressed = new TreeMap<Integer, Boolean>();
        this.lastKeyName = ' ';
        this.lastCode = -1;
    }

    public boolean isPressed(int key) {
        if (this.keys.containsKey(key)) {
            return this.keys.get(key);
        } else {
            return false;
        }
    }

    public boolean isPressedOnce(int key) {
        if (this.keys.containsKey(key) && this.keys.get(key)) {
            if (!this.pressed.containsKey(key) || !this.pressed.get(key)) {
                this.pressed.put(key, true);
                return true;
            }
        }
        return false;
    }

    public int getKeyCode() {
        return this.lastCode;
    }

    public char getKeyName() {
        return this.lastKeyName;
    }

    public boolean used() {
        for (Boolean b : this.keys.values()) {
            if (b == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.lastCode = e.getKeyCode();
        this.lastKeyName = e.getKeyChar();
        this.keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.lastCode = -1;
        this.lastKeyName = ' ';
        this.keys.put(e.getKeyCode(), false);
        this.pressed.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
