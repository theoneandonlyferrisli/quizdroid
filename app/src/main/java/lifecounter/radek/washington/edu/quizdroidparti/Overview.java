package lifecounter.radek.washington.edu.quizdroidparti;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;



public class Overview extends ActionBarActivity {

    private final int NUM_QUESTIONS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Intent incoming = getIntent();
        String topicSelected = incoming.getStringExtra("selectedTopic");

        TextView description = (TextView) findViewById(R.id.topic_description);
        TextView topic = (TextView) findViewById(R.id.topic);

        topic.setText(topicSelected);
        if (topicSelected.equalsIgnoreCase("Math"))
            description.setText("Something that you have to learn from 6 all the way to " +
                    "your college graduation, at which point you decide you've learned way too much of it " +
                    "to be actually useful. You then start looking at your academic life pathetically and " +
                    "wondering how on earth did you spend all those time on something like this (True story).");
        else if (topicSelected.equalsIgnoreCase("Physics"))
            description.setText("A horrendous nightmare in which you have to live through your " +
                    "college (if you are an engineering major, ofc). Sometimes you wonder why life is so cruel when " +
                    "you look at spinning wheel and have darn clue how to make it stop by spinning yourself.");
        else if (topicSelected.equalsIgnoreCase("Marvel Superheroes"))
            description.setText("For all nerds (or quasi-nerds) alike (which I humbly assume " +
                    "you are, given you spend a good chunk of your life like me sitting in front of a computer " +
                    "and writing apps), this is a dream world too distant and beautiful to even think about.");

        TextView numQuestions = (TextView) findViewById(R.id.number_of_questions);
        numQuestions.setText("Number of questions available: " + NUM_QUESTIONS);
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
