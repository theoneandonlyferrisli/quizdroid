package lifecounter.radek.washington.edu.quizdroidparti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by apple on 5/19/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "entered onReceive() from AlarmReceiver");


        // This is where we start our DownloadService class! aka tell our IntentService to start the download!
        Intent downloadServiceIntent = new Intent(context, DownloadService.class);
        context.startService(downloadServiceIntent);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String url = sharedPreferences.getString("prefUserURL", "");

        Toast.makeText(context, "Download from: \"URL\"", Toast.LENGTH_SHORT).show();
    }

}
