package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ParcelFileDescriptor;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.CellSignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


public class TopicList extends ActionBarActivity {
    private DownloadManager downloadManager;
    private List<Topic> topics;
    private ListView topicList;
    private static final int SETTINGS_RESULT = 1;
    private static final int OFF = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        // Register Receiver aka Listen if the DownloadManager from Android OS publishes a "Download has complete"-like broadcast
        //      -note that  DownloadManager is a system service that can be accessed anywhere.
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
        registerReceiver(receiver, filter);

        // Check the device's network connection.
        if (!networkIsAvailable(this) || !hasSignal(this)) {
            if (airplaneModeIsOn(this)) {
                onHandlingairplaneModeOn(this);
            } else {
                Toast.makeText(this, "Not connected to network!", Toast.LENGTH_SHORT).show();
            }
        }

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

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

            Log.i("MyApp BroadcastReceiver", "onReceive of registered download reciever");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("MyApp BroadcastReceiver", "download complete!");
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = downloadManager.query(query);
                    if(c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d("DM Sample","Status Check: "+status);
                        switch(status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // The download-complete message said the download was "successfu" then run this code
                                ParcelFileDescriptor file;
                                StringBuffer strContent = new StringBuffer("");

                                try {
                                    // Get file from Download Manager (which is a system service as explained in the onCreate)
                                    file = downloadManager.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                    // YOUR CODE HERE [convert file to String here]



                                    // YOUR CODE HERE [write string to data/data.json]
                                    //      [hint, i wrote a writeFile method in MyApp... figure out how to call that from inside this Activity]

                                    // convert your json to a string and echo it out here to show that you did download it



                                    /*
                                    String jsonString = ....myjson...to string().... chipotle burritos.... blah
                                    Log.i("MyApp - Here is the json we download:", jsonString);
                                    */

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // YOUR CODE HERE! Your download has failed! Now what do you want it to do? Retry? Quit application? up to you!
                                break;
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == SETTINGS_RESULT) {
            DownloadService.startOrStopAlarm(this, true);
        }

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
            Intent PreferenceActivity = new Intent(TopicList.this, UserSettingActivity.class);
            startActivityForResult(PreferenceActivity, SETTINGS_RESULT);
            assert (SETTINGS_RESULT != -1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadService.startOrStopAlarm(this, false);
        unregisterReceiver(receiver);
    }

    // Checks if there is network available.
    private boolean networkIsAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Checks if the user is currently in airplane mode.
    private boolean airplaneModeIsOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, OFF) != OFF;
    }

    // Checks if the cell currently has signal.
    private boolean hasSignal(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperator() != null &&
                telephonyManager.getNetworkOperatorName() != "";
    }

    /**
     * Alerts the user if the device is in airplane mode and asks the user
     * if they'd like to turn airplane mode off; takes the user to settings
     * if they agree to do so, displays an error message otherwise.
     */
    private void onHandlingairplaneModeOn(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Airplane mode is on. Would you like to turn it off?")
                .setTitle("No Internet Connection");

        builder.setPositiveButton("Turn if off", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

}
