package lifecounter.radek.washington.edu.quizdroidparti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/12/15.
 *
 * This is a class that models a single topic, including
 * a short description, a long description, a title, and a list
 * of Question objects.
 */
public class Topic implements Serializable {
    private String title;
    private String shortDesc;
    private String longDesc;
    private List<Question> questions;
    private int numCorrect;
    private int currentIndex;
    private int lastSelected;

    //Construct a topic object with no questions.
    public Topic(String title, String shortDesc, String longDesc) {
        this(title, shortDesc, longDesc, null);
        questions = new ArrayList<Question>();
    }

    //Construct a topic object with the given short and long descriptions,
    //and list of questions.
    public Topic(String title, String shortDesc,
                 String longDesc, List<Question> questions) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.questions = questions;
        currentIndex = 0;
    }

    //Allows user to add questions to the topic.
    public void addQuestion(Question question) {
        questions.add(question);
    }

    //Allows the user to answer one question.
    public void choose(int choice) {
        if (questions == null || questions.get(currentIndex) == null)
            throw new IllegalStateException("Topic is not properly initialized!");

        Question current = questions.get(currentIndex);
        if (current.getAnswer() == choice)
            numCorrect++;
        currentIndex++;
    }

    /**
     * Returns true if quiz on the current topic is finished and false otherwise.
     */
    public boolean isFinished() {
        return currentIndex == questions.size();
    }

    /**
     * Resets all values to pre-start state.
     */
    public void reset() {
        currentIndex = 0;
        numCorrect = 0;
    }

    /**
     * Getters for all instance variables.
     */

    public String getTitle() {
        return title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getLastSelected() { return lastSelected; }

    public void setLastSelected(int lastSelected) { this.lastSelected = lastSelected; }
}
