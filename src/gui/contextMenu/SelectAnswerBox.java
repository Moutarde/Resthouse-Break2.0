package gui.contextMenu;

import model.messages.Question;

/**
 * @author Nicolas Kniebihler
 *
 */
public class SelectAnswerBox extends Menu {

    private Question question;

    public SelectAnswerBox() {
        super("", 0);
    }

    public void init(Question question) {
        setNbElements(question.getPossibleAnswers().size());

        this.question = question;

        super.init();
    }

    @Override
    public void clean() {
        this.question = null;

        super.clean();
    }

    @Override
    public void selectElement() {
        assert isInitialized : "Menu not initialized";

        question.answer(getPointedElementId());
        setChanged();
        notifyObservers(question.getAnswerActionIFP());

        close();
    }

    @Override
    public String getElementString(int index) {
        assert isInitialized : "Menu not initialized";

        return question.getPossibleAnswers().get(index);
    }

    @Override
    public void close() {
        assert isInitialized : "Menu not initialized";

        super.close();
    }

}
