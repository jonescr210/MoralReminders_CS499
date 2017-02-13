package cs499_uab_capstone_project.moralreminders.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import cs499_uab_capstone_project.moralreminders.service.tasks.InspiratorAlarmTask;

/**
 *
 */
public class InspiratorScheduleService extends Service {


    public class ServiceBinder extends Binder {
        public InspiratorScheduleService getService(){
            return InspiratorScheduleService.this;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ScheduleService", "Received start id " + startId + ": " + intent);

        return START_STICKY;
    }


    private final IBinder mBinder = new ServiceBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public void setAlarm(Calendar c) {
        new InspiratorAlarmTask(c, this).run();
    }
}
