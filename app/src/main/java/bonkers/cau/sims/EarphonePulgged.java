package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class EarphonePulgged extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("state")) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d("111", "Headset is unplugged");
                        break;
                    case 1:
                        Log.d("111", "Headset is plugged");
                        break;
                    default:
                        Log.d("111", "I have no idea what the headset state is");
                }
            }
        }


    }
}