package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.view.Menu;
import android.widget.Toast;

/**
 * A class that manages download service.
 */
public class DownloadService extends IntentService {
    private DownloadManager downloadManager;
    private long enqueue;
    private static final int DURATION = 1;

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
        Toast.makeText(DownloadService.this, "Download from: \"URL\"", Toast.LENGTH_SHORT).show();
    }
}
