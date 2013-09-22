package gui.contextMenu;

import model.messages.Question;

/**
 * @author Nicolas Kniebihler
 *
 */
public class SelectAnswerBox extends Menu {

    private Question question;

    public SelectAnswerBox(Question question) {
        super("", question.getPossibleAnswers().size());

        this.question = question;
    }

    @Override
    public void selectElement() {
        question.answer(getPointedElementId());
        setChanged();
        notifyObservers(question.getAnswerActionIFP());
        display(false);
    }

    @Override
    public String getElementString(int index) {
        return question.getPossibleAnswers().get(index);
    }

    @Override
    public void close() {
        return;
    }

}
