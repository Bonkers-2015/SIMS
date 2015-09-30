package bonkers.cau.sims.boadcast;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;


public class EarphoneService extends Service {

    private IntentFilter filter;
    private EarphoneReceiver mReceiver;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new EarphoneReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        this.filter = new IntentFilter(AudioManager.ACTION_HEADSET_PLUG);
        this.filter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(mReceiver, this.filter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();

    }


}