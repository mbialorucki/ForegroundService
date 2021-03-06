package pl.bialorucki.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by maciej on 24.01.17.
 */

public class ForegroundService extends Service {

    public  static final String START_ACTION = "pl.bialorucki.foregroundservice.START";
    public  static final String STOP_ACTION = "pl.bialorucki.foregroundservice.STOP";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(START_ACTION)) {

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            //intent for stop service
            Intent stopIntent = new Intent(this, ForegroundService.class);
            stopIntent.setAction(STOP_ACTION);
            PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_alert);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.title))
                    .setTicker(getString(R.string.ticker))
                    .setContentText(getString(R.string.content_text))
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_menu_close_clear_cancel, getString(R.string.close), stopPendingIntent)
                    .build();

            startForeground(101, notification);
        } else {
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }
}
