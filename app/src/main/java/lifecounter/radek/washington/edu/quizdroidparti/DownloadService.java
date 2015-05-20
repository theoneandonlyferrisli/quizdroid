package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.net.URI;

/**
 * A class that manages download service.
 */
public class DownloadService extends IntentService {
    private DownloadManager downloadManager;
    private long enqueue;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString("prefUserURL", "");

        Log.i("DownloadService", "should be downloading here");

        // Star the download
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        enqueue = downloadManager.enqueue(request);

    }

    public static void startOrStopAlarm(Context context, boolean on) {
        Log.i("DownloadService", "startOrStopAlarm on = " + on);

        Intent alarmReceiverIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (on) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String refreshIntervalString = sharedPreferences.getString("prefUserFreq", "1");
            int refreshInterval = Integer.parseInt(refreshIntervalString) * 60000;

            Log.i("DownloadService", "setting alarm to " + refreshInterval);

            // Start the alarm manager to repeat
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshInterval, pendingIntent);
        }
        else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();

            Log.i("DownloadService", "Stopping alarm");
        }
    }
}
