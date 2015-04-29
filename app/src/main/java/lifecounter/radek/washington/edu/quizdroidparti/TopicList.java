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


public class TopicList extends ActionBarActivity {

    private String[] topics = new String[]{"Math", "Physics", "Marvel Superheroes"};
    private ListView topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        topicList = (ListView) findViewById(R.id.list_of_topics);

        // Set up the adapter for the ListView containing a list of topics for
        // users to choose from.
        ArrayAdapter<String> topicListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, topics);
        topicList.setAdapter(topicListAdapter);

        // Set up the OnItemClickListener so that the corresponding activity is brought
        // up as the user clicks a particular item.
        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next = new Intent(TopicList.this, Overview.class);
                next.putExtra("selectedTopic", topics[position]);
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
