package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.AlarmManager;
import android.app.Application;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
/**
 * Created by radek on 5/12/15.
 */
public class QuizApp extends Application {
    private static final String TAG = "QuizApp";
    private Topic current;
    private List<Topic> topics;

    private DownloadManager downloadManager;
    private long enqueue;
    private boolean alarmIsSet;
    private String url;
    private int interval;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    // A repository of locally stored questions and topics.
    private LocalTopicRepo localRepo;
    private JsonTopicRepo jsonTopicRepo;

    //Make sure QuizApp is implemented as a singleton.
    private static QuizApp instance = null;

    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("No more than one instance of QuizApp is allowed");
        }
        localRepo = new LocalTopicRepo();
        jsonTopicRepo = new JsonTopicRepo();
        topics = new ArrayList<Topic>();
    }

    @Override
    //Sends out a log message that signals successful creation of QuizApp.
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "QuizApp is successfully created!");

        File myFile = new File(getFilesDir().getAbsolutePath(), "/data.json");
        String json = "";

        if (myFile.exists()) {
            try {
                FileInputStream fis = openFileInput("data.json");
                json = readJSONFile(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                InputStream inputStream = getAssets().open("data.json");
                json = readJSONFile(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONArray jsonData = new JSONArray(json);
                for (int i = 0; i < jsonData.length(); i++) {
                    Topic topic = new Topic();
                    JSONObject jsonTopic = new JSONObject(jsonData.get(i).toString());
                    String title = jsonTopic.getString("title");
                    topic.setTitle(title);
                    String desc = jsonTopic.getString("desc");
                    topic.setShortDesc(desc);
                    topic.setLongDesc(desc);
                    JSONArray jsonQuestions = new JSONArray(jsonTopic.get("questions").toString());
                    List<Question> questions = new ArrayList<Question>();

                    for (int j = 0; j < jsonQuestions.length(); j++) {
                        Question question = new Question();
                        JSONObject jsonQuestion = new JSONObject(jsonQuestions.get(j).toString());

                        question.setText(jsonQuestion.getString("text"));
                        question.setAnswer(jsonQuestion.getInt("answer"));
                        JSONArray options = jsonQuestion.getJSONArray("answers");

                        for (int k = 0; k < options.length(); k++)
                            question.addOption(options.get(k).toString());

                        questions.add(question);
                    }
                    topic.setQuestions(questions);
                    topics.add(topic);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        DownloadService.startOrStopAlarm(this, true);

        alarmIsSet = false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        url = sharedPreferences.getString("url", "http://tednewardsandbox.site44.com/questions.json");
        interval = Integer.parseInt(sharedPreferences.getString("interval", "1")) * 60000;
        alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);


        BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(QuizApp.this, url, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Making Toast");
            }
        };

        registerReceiver(alarmReceiver, new IntentFilter("readingJSONFiles.radek"));

        Intent intent = new Intent();
        intent.setAction("readingJSONFiles.radek");
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        setUrl(url, interval);

        jsonTopicRepo.setTopicList(topics);
        topics = localRepo.getTopicList();
    }

    /**
     * A method that returns a list of locally stored topics.
     *
     * @return a list of locally stored topics
     */
    public List<Topic> getLocalTopicList() {
        return topics;
    }

    /**
     * Returns the topic the user chose.
     */
    public Topic getCurrentTopic() {
        if (current == null)
            throw new IllegalStateException("You have not yet set the current topic!");
        return current;
    }

    /**
     * Sets the current topic to be the topic that the user picked.
     */
    public void setCurrentTopic(Topic current) {
        this.current = current;
    }
    /**
     *
     * @return the number of topics
     */
    public int getNumTopics() {
        return topics.size();
    }

    /**
     *
     * @return a String array of topic titles
     */
    public String[] getTopicArray() {
        String[] topicArray = new String[getNumTopics()];
        for (int i = 0; i < getNumTopics(); i++)
            topicArray[i] = topics.get(i).getTitle();
        return topicArray;
    }

    public void writeToFile(String data) {
        try {
            Log.i(TAG, "writing downloaded to file");

            File file = new File(getFilesDir().getAbsolutePath(), "data.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    // reads InputStream of JSON file and returns the file in JSON String format
    public String readJSONFile(InputStream inputStream) throws IOException {
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

    public void setUrl(String url, int interval) {
        this.url = url;
        this.interval = interval;
        if (alarmIsSet) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            alarmIsSet = false;
        }
        alarmIsSet = true;
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, interval, pendingIntent);
    }
}
