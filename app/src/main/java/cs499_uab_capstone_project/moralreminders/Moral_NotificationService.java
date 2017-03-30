package cs499_uab_capstone_project.moralreminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;

public class Moral_NotificationService extends Service {
    public Moral_NotificationService() {
    }
    private NotificationManager notificationManager;
    public void onCreate(){
        super.onCreate();

        Intent main = new Intent(this, Moral_MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, main, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification myNotification = new Notification.Builder(this)
                .setContentTitle("How are you feeling?")
                .setContentText("Time for some inspiration! Check out a new quote!")
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, myNotification);
        System.out.println("BEEP BEEP");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
