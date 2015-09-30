package bonkers.cau.sims.boadcast;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
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

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        mReceiver = new EarphoneReceiver(intent);

        filter = new IntentFilter(intent.ACTION_HEADSET_PLUG);

        registerReceiver(mReceiver, filter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();

    }


}