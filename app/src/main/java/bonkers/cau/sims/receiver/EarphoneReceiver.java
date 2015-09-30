package bonkers.cau.sims.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import bonkers.cau.sims.LaunchMain;

/**
 * Created by dongbin on 2015-09-21.
 */
public class EarphoneReceiver extends BroadcastReceiver {

    String data1 = "EMPTY";
    Intent mIntent;

    public EarphoneReceiver(Intent intent){
        mIntent = intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        data1 = mIntent.getStringExtra("data1");

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    // Eaphone unplugged
                    break;
                case 1:
                    // Eaphone plugged

                    LaunchMain main = new LaunchMain();
                    main.launch(context, data1, "plug in");

                    break;
                default:
            }
        }
    }
}
