package lifecounter.radek.washington.edu.quizdroidparti;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Quiz extends ActionBarActivity {

    private Bundle qna;
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        qna = this.getIntent().getExtras();
        String[] questions = qna.getStringArray("Q");
        String[] choices = qna.getStringArray("Q1");
        int[] answers = qna.getIntArray("A");
        current = qna.getInt("current");

        final Button submit = (Button) findViewById(R.id.submit);
        submit.setVisibility(View.INVISIBLE);
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(questions[current]);

        RadioGroup options = (RadioGroup)findViewById(R.id.choices);

        for (int i = 0; i < qna.getInt("numC"); i++) {
            String choice = choices[i];
            ((RadioButton) options.getChildAt(i)).setText(choice);
        }

        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = (RadioButton) findViewById(checkedId);
                int selected = Integer.parseInt((String)checked.getTag());
                qna.putString("a1", "" + selected);
                qna.putInt("current", current++);
                submit.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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
