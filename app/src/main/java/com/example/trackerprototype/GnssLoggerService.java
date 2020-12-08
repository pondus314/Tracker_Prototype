package com.example.trackerprototype;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class GnssLoggerService extends Service {
    private static final String TAG = "GNSSLOGGERSERVICE";

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    private static final String NOTIF_CHANNEL_NAME = "GnssLoggerServiceChannel";

    private FileLogger mFileLogger;
    private GnssContainer mGnssContainer;
    private NotificationChannel mNotificationChannel;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, LoggingActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
//                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Tracking your position for George ❤️")
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build());
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        mNotificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(mNotificationChannel);

        mFileLogger = new FileLogger(getApplicationContext());
        mGnssContainer = new GnssContainer(getApplicationContext(), mFileLogger);
        mGnssContainer.registerAll();
        mFileLogger.startNewLog();
        startForeground();
    }

    @Override
    public void onDestroy() {
        mFileLogger.close();
        mGnssContainer.unregisterAll();
        super.onDestroy();
    }
}