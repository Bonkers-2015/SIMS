package bonkers.cau.sims;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;


public class ScreenService extends Service {
   // private AppSettings appSettings;
    private IntentFilter filter;
    private ScreenReceiver screenReceiver;

    public IBinder onBind(Intent paramIntent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy() {
        unregisterReceiver(this.screenReceiver);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        Log.i("Volume Unlock", "Start screen service");
        this.screenReceiver = new ScreenReceiver();
        this.filter = new IntentFilter();
        this.filter.addAction("android.intent.action.SCREEN_ON");
        this.filter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(this.screenReceiver, this.filter);

        return 1;
    }

    public void onTaskRemoved(Intent paramIntent) {

    }
}
