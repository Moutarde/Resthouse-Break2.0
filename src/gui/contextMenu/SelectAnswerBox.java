package gui.contextMenu;

import controller.actions.CloseMenu;
import model.GameModel;
import model.messages.Question;

/**
 * @author Nicolas Kniebihler
 *
 */
public class SelectAnswerBox extends Menu {

    private Question question;

    public SelectAnswerBox(GameModel model, Question question) {
        super("", question.getPossibleAnswers().size(), model);

        this.question = question;
    }

    @Override
    public void selectElement() {
        question.answer(getPointedElementId());
        setChanged();
        notifyObservers(new CloseMenu());
    }

    @Override
    public String getElementString(int index) {
        return question.getPossibleAnswers().get(index);
    }

}
