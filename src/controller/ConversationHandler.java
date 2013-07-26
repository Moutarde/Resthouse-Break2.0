package controller;

import gui.UserInterface;

import java.util.ArrayList;
import java.util.List;

import model.GameModel;
import model.npc.NPC;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ConversationHandler {
    private GameModel model;

    private int currentStep;
    private List<String> currentSpeech = new ArrayList<String>();
    private String speakerName;

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
        speakerName = npc.getName();

        displayCurrentMessage();
    }

    public void displayCurrentMessage() {
        model.setNewMessage(speakerName + " : " + UserInterface.getLang().getString(currentSpeech.get(currentStep)));
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
