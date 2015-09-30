package bonkers.cau.sims.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import bonkers.cau.sims.receiver.VolumeReceiver;

/**
 * Created by dongbin on 2015-09-30.
 */
public class VolumeService extends Service{
    private IntentFilter filter;
    private VolumeReceiver mReceiver;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new VolumeReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(mReceiver, this.filter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}
