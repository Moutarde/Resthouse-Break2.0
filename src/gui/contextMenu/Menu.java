package gui.contextMenu;

import gui.Displayable;
import gui.UserInterface;

/**
 * @author Nicolas Kniebihler
 *
 */
public abstract class Menu extends Displayable {
    private String name;
    private int nbElements;

    private int pointedElementId = 0;

    protected boolean isInitialized = false;

    public Menu(String name, int nbElements) {
        this.name = name;
        this.nbElements = nbElements;
    }

    public void init() {
        assert !isInitialized : "Menu is already initialized";
        isInitialized = true;
    }

    public void clean() {
        assert isInitialized : "Menu not initialized";
        isInitialized = false;
    }

    public int getNbElements() {
        return nbElements;
    }

    public void setNbElements(int i) {
        this.nbElements = i;
    }

    public int getPointedElementId() {
        assert isInitialized : "Menu not initialized";

        return pointedElementId;
    }

    public void changePointedElement(int i) {
        assert isInitialized : "You can't modify a non-init Menu";

        int index = pointedElementId + i;
        if (index < 0) {
            index = nbElements - 1;
        }
        else if (index >= nbElements) {
            index = 0;
        }

        pointedElementId = index;
    }

    @Override
    public void display(boolean value) {
        assert isInitialized : "You can't display a non-init Menu";

        super.display(value);
        pointedElementId = 0;
    }

    public String getContent() {
        assert isInitialized : "Menu not initialized";

        String content = name.isEmpty() ? "" : UserInterface.getLang().getString(name) + " :\n";
        for (int i = 0 ; i < nbElements ; ++i) {
            content += "  ";
            if (i == pointedElementId) {
                content += "> ";
            }
            content += getElementString(i) + "\n";
        }

        return content;
    }

    public void close() {
        assert isInitialized : "You can't close a non-init Menu";

        display(false);
        clean();
    }

    public abstract void selectElement();
    public abstract String getElementString(int index);
}
