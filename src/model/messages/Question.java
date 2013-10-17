package model.messages;

import java.util.ArrayList;
import java.util.List;

import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Question extends Message {

    private List<String> possibleAnswers = new ArrayList<String>();
    private List<List<IAction>> actionLists = new ArrayList<List<IAction>>();
    private int answerId = -1;

    public Question(String str, List<String> possibleAnswers, List<List<IAction>> actionLists) {
        super(str);
        assert possibleAnswers.size() == actionLists.size() : "Amount of answers and actions are not equal";
        this.possibleAnswers.addAll(possibleAnswers);
        this.actionLists.addAll(actionLists);
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void answer(int id) {
        this.answerId = id;
        setChanged();
        notifyObservers(getAnswerActionIFP());
    }

    public List<IAction> getAnswerActionIFP() {
        if (answerId != -1 && answerId < actionLists.size()) {
            return actionLists.get(answerId);
        }
        else {
            return null;
        }
    }

}
