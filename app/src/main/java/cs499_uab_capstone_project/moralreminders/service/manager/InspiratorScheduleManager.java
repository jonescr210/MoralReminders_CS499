package cs499_uab_capstone_project.moralreminders.service.manager;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cs499_uab_capstone_project.moralreminders.service.InspiratorScheduleService;

import java.util.Calendar;

/**
 *
 */
public class InspiratorScheduleManager{

    /**
     * Object of {@link cs499_uab_capstone_project.moralreminders.service.InspiratorScheduleService}
     * to initialize notification and alarm system.
     */
    private InspiratorScheduleService mScheduleService;

    /**
     * Context object see {@link Context}
     */
    private Context mContext;

    private boolean isBound;

    /**
     * Default Constructor.
     */
    public InspiratorScheduleManager(Context context) {
        mContext = context;
    }

    public void bindService() {
        mContext.bindService(new Intent(mContext, InspiratorScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mScheduleService = ((InspiratorScheduleService.ServiceBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mScheduleService = null;
        }
    };

    /**
     * Set Alarm inside service
     * @param calendar Calendar object with date required by alarm.
     */
    public void setAlarmForNotification(Calendar calendar) {
        mScheduleService.setAlarm(calendar);
    }

    /**
     * Method to kill service and release connection and resources
     */
    public void unBindService() {
        if (isBound) {
            mContext.unbindService(mConnection);
            isBound = false;
        }
    }
}
