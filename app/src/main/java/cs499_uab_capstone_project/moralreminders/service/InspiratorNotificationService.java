package cs499_uab_capstone_project.moralreminders.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import cs499_uab_capstone_project.moralreminders.Moral_MainActivity;
import cs499_uab_capstone_project.moralreminders.R;
import cs499_uab_capstone_project.moralreminders.logic.RandomGenerator;

/**
 *
 */
public class InspiratorNotificationService extends Service {

    public static final int INTERVAL = 500;
    /**
     * NotificationManager for showing notification to user.
     * See {@link NotificationManager}
     */
    private NotificationManager mNotificationManager;

    /**
     * Notification ID.
     */
    private static final int NOTIFICATION_ID = 101;

    /**
     * Intent extra key.
     */
    public static final String INTENT_NOTIFY = "inspirator.service.INTENT_NOTIFY";

    private final IBinder mBinder = new ServiceBinder();

    public class ServiceBinder extends Binder {
        public InspiratorNotificationService getService() {
            return InspiratorNotificationService.this;
        }
    }


    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            showNotification();
        }

        return START_NOT_STICKY;
    }

    /**
     * Method to create, show and handle user click on notification.
     */
    private void showNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_message))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setLights(RandomGenerator.getRandomColorId(this), INTERVAL, INTERVAL)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(new long[]{INTERVAL, INTERVAL, INTERVAL, INTERVAL})
                        .setColor(ContextCompat.getColor(this, R.color.colorAccent));

        Intent resultIntent = new Intent(this, Moral_MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Moral_MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
