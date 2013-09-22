package controller;

import gui.contextMenu.Menu;
import gui.contextMenu.StoreMenu;
import gui.contextMenu.TransactionMenu;
import model.GameModel;
import model.items.Item;
import model.npc.NPC;
import model.player.Player;
import controller.actions.ShowMessage;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ConversationHandler extends MenuHandler {

    private int currentStep;
    private NPC speaker;

    private boolean isSpeaking = false;

    public ConversationHandler(GameModel model) {
        super(model);
    }

    public void start() {
        model.setGamePaused(true);
        isSpeaking = true;
        currentStep = 0;

        speaker = model.getPlayer().getFrontNPC();

        displayCurrentMessage();
    }

    private void displayCurrentMessage() {
        ShowMessage action = new ShowMessage(speaker.getSpeech().get(currentStep));
        action.execute(model.getMessageBox(), this);
    }

    public void continueSpeech() {
        assert speaker != null : "Trying to continue a speech without speaker";

        currentStep++;
        if (currentStep >= speaker.getSpeech().size()) { // Speech finished
            currentStep = 0;
            speaker = null;
            isSpeaking = false;
            model.setGamePaused(false);
        }
        else {
            displayCurrentMessage();
        }
    }

    public boolean isSpeaking() {
        return isSpeaking;
    }

    // STORE

    public void showStore() {
        Menu storeMenu = new StoreMenu(speaker, model.getPlayer());
        storeMenu.addObserver(this);
        storeMenu.display(true);
        model.setStoreMenu(storeMenu);
    }

    public void updateStore() {
        assert model.getStoreMenu() instanceof StoreMenu : "store menu is not a StoreMenu";
        ((StoreMenu)model.getStoreMenu()).updateContent();
    }

    // TRANSACTION MENU

    public void showTransactionMenu(Item item, Player seller, Player buyer) {
        Menu transactionMenu = new TransactionMenu(item, seller, buyer);
        transactionMenu.addObserver(this);
        transactionMenu.display(true);
        model.setTransactionMenu(transactionMenu);
    }

}
