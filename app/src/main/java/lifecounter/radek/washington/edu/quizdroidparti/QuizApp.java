package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.Application;
import android.util.Log;

import java.util.List;
/**
 * Created by radek on 5/12/15.
 */
public class QuizApp extends Application {
    private static final String TAG = "QuizApp";

    //Make sure QuizApp is implemented as a singleton.
    private static QuizApp instance = null;

    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("No more than one instance of QuizApp is allowed");
        }
    }

    @Override
    //Sends out a log message that signals successful creation of QuizApp.
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "QuizApp is successfully created!");
    }
}
