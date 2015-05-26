package lifecounter.radek.washington.edu.quizdroidparti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/12/15.
 *
 * This is a class that represents a question, including
 * the question text, arrays of options, and a
 * correct answer.
 */
public class Question {
    private int answer;
    private String text;
    private List<String> options;

    /**
     * Constructs an empty Question object.
     */
    public Question() {
        this(null, 0, null);
    }

    /**
     * Constructs an empty Question object with given question text, options,
     */
    public Question(String text, int answer, List<String> options) {
        this.options = options;
        this.text = text;
        this.answer = answer;
    }

    /**
     * Getters for all instance variables.
     */
    public int getAnswer() {
        return answer;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void addOption(String option) {
        if (options == null) {
            options = new ArrayList<String>();
        } else {
            options.add(option);
        }
    }
}
