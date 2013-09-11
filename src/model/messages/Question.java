package model.messages;

import java.util.ArrayList;
import java.util.List;

import controller.actions.IMenuAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Question extends Message {

    private List<String> possibleAnswers = new ArrayList<String>();
    private List<IMenuAction> actions = new ArrayList<IMenuAction>();
    private int answerId = -1;

    public Question(String str, List<String> possibleAnswers, List<IMenuAction> actions) {
        super(str);
        assert possibleAnswers.size() == actions.size() : "Amount of answers and actions are not equal";
        this.possibleAnswers.addAll(possibleAnswers);
        this.actions.addAll(actions);
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void answer(int id) {
        this.answerId = id;
        setChanged();
        notifyObservers(getAnswerActionIFP());
    }

    public IMenuAction getAnswerActionIFP() {
        if (answerId != -1 && answerId < actions.size()) {
            return actions.get(answerId);
        }
        else {
            return null;
        }
    }

}
