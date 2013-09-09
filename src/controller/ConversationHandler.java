package controller;

import gui.contextMenu.Menu;
import gui.contextMenu.SelectAnswerBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.GameModel;
import model.messages.Message;
import model.messages.Question;
import model.npc.NPC;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ConversationHandler extends MenuHandler {
    private int currentStep;
    private List<Message> currentSpeech = new ArrayList<Message>();

    private boolean isSpeaking = false;

    public ConversationHandler(GameModel model) {
        super(model);
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
        Message m = currentSpeech.get(currentStep);

        model.setNewMessage(m);

        if (m instanceof Question) {
            m.addObserver(this);
            Menu selectAnswerBox = new SelectAnswerBox(model, (Question)m);
            selectAnswerBox.addObserver(this);
            selectAnswerBox.display(true);
            model.setSelectAnswerBox(selectAnswerBox);
        }
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

    @Override
    public void update(Observable obs, Object action) {
        if (isSpeaking) {
            if (obs instanceof Question) {
                ((Question)obs).getAnswerActionIFP().execute(model.getMenu(), this);
            }
            else {
                super.update(obs, action);
            }
        }
    }
}
