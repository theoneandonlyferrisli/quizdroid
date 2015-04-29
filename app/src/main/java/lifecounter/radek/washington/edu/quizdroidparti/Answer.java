package lifecounter.radek.washington.edu.quizdroidparti;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Answer extends ActionBarActivity {
    private Bundle qna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        qna = getIntent().getExtras();

        int numCorrect = qna.getInt("correct");
        final int current = qna.getInt("current");
        int correctAnswer = qna.getInt("correctAnswer") + 1;
        int selected = qna.getInt("selected") + 1;
        final int total = qna.getInt("numQ");

        if (correctAnswer == selected)
            numCorrect++;

        qna.putInt("current", current + 1);
        qna.putInt("correct", numCorrect);

        TextView result1 = (TextView) findViewById(R.id.correct_answer);
        result1.setText("The correct choice is the no." + correctAnswer +
                ", and you answered no." + selected);

        TextView result2 = (TextView) findViewById(R.id.num_correct);
        result2.setText("You have " + numCorrect + " out of " + total + " correct!");

        final Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current + 1 < 2) {
                    Intent next = new Intent(Answer.this, Quiz.class);
                    next.putExtras(qna);
                    startActivity(next);
                } else {
                    next.setText("FINISH");
                    Intent finish = new Intent(Answer.this, TopicList.class);
                    startActivity(finish);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
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
