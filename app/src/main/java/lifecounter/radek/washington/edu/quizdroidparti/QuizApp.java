package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.Application;
import android.app.DownloadManager;
import android.util.Log;

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

    // A repository of locally stored questions and topics.
    private LocalTopicRepo localRepo;

    //Make sure QuizApp is implemented as a singleton.
    private static QuizApp instance = null;

    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("No more than one instance of QuizApp is allowed");
        }
        localRepo = new LocalTopicRepo();
        topics = localRepo.getTopicList();
    }

    @Override
    //Sends out a log message that signals successful creation of QuizApp.
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "QuizApp is successfully created!");

        /*File myFile = new File(getFilesDir().getAbsolutePath(), "/data.json");
        String json = null;

        if (myFile.exists()) {
            try {
                FileInputStream fis = openFileInput("data.json");
                json = readJSONFile();
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
        }*/
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

    /**
     *
     */
}
