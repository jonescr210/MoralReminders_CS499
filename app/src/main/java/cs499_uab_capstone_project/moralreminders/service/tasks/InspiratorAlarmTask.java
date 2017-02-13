package cs499_uab_capstone_project.moralreminders.service.tasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import cs499_uab_capstone_project.moralreminders.service.InspiratorNotificationService;

import java.util.Calendar;

/**
 * AlarmTask Thread for running alarm task.
 */
public class InspiratorAlarmTask implements Runnable {

    /**
     * Reference to Calendar object, see {@link Calendar}
     */
    private final Calendar mCalendar;

    /**
     * Reference to Android AlarmManager, see {@link AlarmManager}
     */
    private final AlarmManager mAlarmManager;

    /**
     * Context object for retrieving alarm manager service.
     */
    private final Context mContext;


    public InspiratorAlarmTask(Calendar calendar, Context context) {
        this.mContext = context;
        this.mCalendar = calendar;
        this.mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void run() {

        Intent intent = new Intent(mContext, InspiratorNotificationService.class);
        intent.putExtra(InspiratorNotificationService.INTENT_NOTIFY, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);

        mAlarmManager.set(AlarmManager.RTC, mCalendar.getTimeInMillis(), pendingIntent);
    }
}
