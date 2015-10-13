package bonkers.cau.sims.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import bonkers.cau.sims.R;
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
        Notification notification = new Notification(R.mipmap.ic_launcher, "SIMS가 실행중입니다", System.currentTimeMillis());
        notification.setLatestEventInfo(getApplicationContext(), "SIMS", "버튼을 감지중입니다.", null);
        startForeground(1, notification);

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
