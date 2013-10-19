package model.messages;

import java.util.Observable;

import model.npc.NPC;
import controller.actions.ShowMessage;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Conversation extends Observable {

    private int currentStep;
    private NPC speaker;

    private boolean isSpeaking = false;

    public void start(NPC speaker) {
        isSpeaking = true;
        currentStep = 0;

        this.speaker = speaker;

        displayCurrentMessage();
    }

    private void displayCurrentMessage() {
        setChanged();
        notifyObservers(new ShowMessage(speaker.getSpeech().get(currentStep)));
    }

    public void continueSpeech() {
        assert speaker != null : "Trying to continue a speech without speaker";

        currentStep++;
        if (currentStep >= speaker.getSpeech().size()) { // Speech finished
            currentStep = 0;
            speaker = null;
            isSpeaking = false;
        }
        else {
            displayCurrentMessage();
        }
    }

    public boolean isSpeaking() {
        return isSpeaking;
    }

    public NPC getSpeaker() {
        return speaker;
    }
}
