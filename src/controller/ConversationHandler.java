package controller;

import java.util.ArrayList;
import java.util.List;

import model.GameModel;
import model.messages.Message;
import model.npc.NPC;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ConversationHandler {
    private GameModel model;

    private int currentStep;
    private List<Message> currentSpeech = new ArrayList<Message>();

    private boolean isSpeaking = false;

    public ConversationHandler(GameModel model) {
        this.model = model;
    }

    public void start() {
        model.setGamePaused(true);
        isSpeaking = true;
        currentStep = 0;

        NPC npc = model.getPlayer().getFrontNPC();

        currentSpeech.addAll(npc.getSpeech());

        displayCurrentMessage();
    }

    public void displayCurrentMessage() {
        model.setNewMessage(currentSpeech.get(currentStep));
    }

    public void continueSpeech() {
        assert !currentSpeech.isEmpty() : "Trying to continue an empty speech";

        currentStep++;
        if (currentStep >= currentSpeech.size()) {
            currentStep = 0;
            currentSpeech.clear();
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
}
