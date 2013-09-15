package controller;

import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import gui.contextMenu.SelectAnswerBox;
import gui.contextMenu.StoreMenu;
import gui.contextMenu.TransactionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.GameModel;
import model.items.Item;
import model.messages.Message;
import model.messages.OpenStore;
import model.messages.Question;
import model.npc.NPC;
import model.player.Player;
import controller.actions.CloseMenu;
import controller.actions.IMenuAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ConversationHandler extends MenuHandler {
    private int currentStep;
    private List<Message> currentSpeech = new ArrayList<Message>();
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

        currentSpeech.addAll(speaker.getSpeech());

        displayCurrentMessage();
    }

    public void displayCurrentMessage() {
        Message m = speaker.getSpeech().get(currentStep);

        model.setNewMessage(m);

        if (m instanceof Question) {
            m.addObserver(this);
            Menu selectAnswerBox = new SelectAnswerBox(model, (Question)m);
            selectAnswerBox.addObserver(this);
            selectAnswerBox.display(true);
            model.setSelectAnswerBox(selectAnswerBox);
        }
        else if (m instanceof OpenStore) {
            Menu storeMenu = new StoreMenu(model, speaker, model.getPlayer());
            storeMenu.addObserver(this);
            storeMenu.display(true);
            model.setStoreMenu(storeMenu);
        }
    }

    public void continueSpeech() {
        assert speaker != null : "Trying to continue a speech without speaker";

        currentStep++;
        if (currentStep >= speaker.getSpeech().size()) { // Speech finished
            currentStep = 0;
            speaker = null;
            isSpeaking = false;
            model.hideMessage();
            model.setGamePaused(false);
        }
        else {
            displayCurrentMessage();
        }
    }

    public boolean isSpeaking() {
        return isSpeaking;
    }

    // TRANSACTION MENU

    public void showTransactionMenu(Item item, Player seller, Player buyer) {
        Menu transactionMenu = new TransactionMenu(item, seller, buyer);
        transactionMenu.addObserver(this);
        transactionMenu.display(true);
        model.setTransactionMenu(transactionMenu);
    }

    @Override
    public void update(Observable obs, Object action) {
        if (isSpeaking) {
            if (obs instanceof Question) {
                ((IMenuAction)action).execute(model.getMenu(), this);
            }
            else {
                super.update(obs, action);
                if (obs instanceof StoreMenu && action instanceof CloseMenu) {
                    continueSpeech();
                }
                else if (obs instanceof TransactionMenu && action instanceof CloseMenu) {
                    ((BagMenu)model.getStoreMenu()).updateContent();
                }
            }
        }
    }

    @Override
    public void validate() {
        Menu prioritaryDisplayedMenu = model.getPrioritaryDisplayedMenu();
        if (prioritaryDisplayedMenu != null) {
            prioritaryDisplayedMenu.selectElement();
        }
        else {
            continueSpeech();
        }
    }

}
