package lifecounter.radek.washington.edu.quizdroidparti;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class Overview extends ActionBarActivity {

    private final int NUM_QUESTIONS = 2;
    private final int NUM_CHOICES = 4;

    private String[] questions;
    private String[] firstQuestionChoices;
    private String[] secondQuestionChoices;
    private int[] answers;

    /*private String[] mathFirstQuestionChoices;
    private String[] mathSecondQuestionChoices;
    private int[] mathAnswers;

    private String[] physicsFirstQuestionChoices;
    private String[] physicsSecondQuestionChoices;
    private int[] physicsAnswers;

    private String[] marvelFirstQuestionChoices;
    private String[] marvelSecondQuestionChoices;
    private int[] marvelAnswers;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Intent incoming = getIntent();
        final String topicSelected = incoming.getStringExtra("selectedTopic");

        TextView description = (TextView) findViewById(R.id.topic_description);
        TextView topic = (TextView) findViewById(R.id.topic);

        //Set topic description and initialize the arrays with questions, choices,
        // and answers according to user's choice of topic.
        topic.setText(topicSelected);
        if (topicSelected.equalsIgnoreCase("Math")) {
            description.setText("Something that you have to learn from 6 all the way to " +
                    "your college graduation, at which point you decide you've learned way too much of it " +
                    "to be actually useful. You then start looking at your academic life pathetically and " +
                    "wondering how on earth did you spend all those time on something like this (True story).");

            questions = new String[] {"What's the answer of 13 * 13?", "Is 2 a prime number?"};
            firstQuestionChoices = new String[] {"199", "169", "99", "Please use a calculator and hit yourself in the head!"};
            secondQuestionChoices = new String[] {"Yes", "No", "This is a philosophy question", "My math sucks"};
            answers = new int[] {1, 0};
        } else if (topicSelected.equalsIgnoreCase("Physics")) {
            description.setText("A horrendous nightmare in which you have to live through your " +
                    "college (if you are an engineering major, ofc). Sometimes you wonder why life is so cruel when " +
                    "you look at spinning wheel and have darn clue how to make it stop by spinning yourself.");
            questions = new String[] {"What's the speed of sound?", "Second Newton's Law?"};
            firstQuestionChoices = new String[] {"3 * 10^8 m/s", "343 m/s", "1200 m/s", "Admit it! You don't know!"};
            secondQuestionChoices = new String[] {"F = mg", "V = D / t", "F = ma", "E = mc^2"};
            answers = new int[] {1, 2};
        } else if (topicSelected.equalsIgnoreCase("Marvel Superheroes")) {
            description.setText("For all nerds (or quasi-nerds) alike (which I humbly assume " +
                    "you are, given you spend a good chunk of your life like me sitting in front of a computer " +
                    "and writing apps), this is a dream world too distant and beautiful to even think about.");
            questions = new String[] {"Who is Thor?", "IronMan vs Darth Vader?"};
            firstQuestionChoices = new String[] {"Stark's dad", "A narcissistic good-looking guy", "A scientist who mutated", "Superman's cousin"};
            secondQuestionChoices = new String[] {"Darth Vader is not Marvel-made", "Vader rules!", "LOL.", "I quit!"};
            answers = new int[] {1, 1};
        }
        TextView numQuestions = (TextView) findViewById(R.id.number_of_questions);
        numQuestions.setText("Number of questions available: " + NUM_QUESTIONS);


        Button begin = (Button) findViewById(R.id.BEGIN);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Overview.this, Quiz.class);
                Bundle qna = new Bundle();
                qna.putStringArray("Q", questions);
                qna.putStringArray("Q1", firstQuestionChoices);
                qna.putStringArray("Q2", secondQuestionChoices);
                qna.putIntArray("A", answers);

                qna.putInt("numQ", NUM_QUESTIONS);
                qna.putInt("correct", 0);
                qna.putInt("current", 0);
                qna.putInt("numC", NUM_CHOICES);
                qna.putString("topic", topicSelected);

                next.putExtras(qna);
                startActivity(next);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
