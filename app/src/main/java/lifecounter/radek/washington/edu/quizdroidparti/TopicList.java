package lifecounter.radek.washington.edu.quizdroidparti;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.*;


public class TopicList extends ActionBarActivity {

    private List<Topic> topics;
    private ListView topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        // Access the application object
        final QuizApp app = (QuizApp)getApplication();

        topicList = (ListView) findViewById(R.id.list_of_topics);
        topics = app.getLocalTopicList();

        // Get the topic title array from local repository.
        final String[] topicArray = app.getTopicArray();

        // Attach short description to topic titles.
        for (int i = 0; i < topicArray.length; i++)
            topicArray[i] = topicArray[i] + " (" + topics.get(i).getShortDesc() + ")";

        // Set up the adapter for the ListView containing a list of topics for
        // users to choose from.
        ArrayAdapter<String> topicListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, topicArray);
        topicList.setAdapter(topicListAdapter);

        // Set up the OnItemClickListener so that the corresponding activity is brought
        // up as the user clicks a particular item.
        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next = new Intent(TopicList.this, ViewFramework.class);
                next.putExtra("selectedTopic", topicArray[position]);
                app.setCurrentTopic(topics.get(position));
                startActivity(next);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_list, menu);
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
