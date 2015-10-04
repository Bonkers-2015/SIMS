package bonkers.cau.sims.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import bonkers.cau.sims.LaunchMain;

/**
 * Created by dongbin on 2015-09-21.
 */
public class EarphoneReceiver extends BroadcastReceiver {

    String data1 = "EMPTY";
    Intent mIntent;
    Boolean flag;

    public EarphoneReceiver(Intent intent){
        mIntent = intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        data1 = mIntent.getStringExtra("data1");


        SharedPreferences prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        flag = prefs.getBoolean("earphoneFlag", false);

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    // Eaphone unplugged
                    editor.putBoolean("earphoneFlag", true);
                    editor.commit();
                    break;
                case 1:
                    // Eaphone plugged
                    if(flag) {
                        LaunchMain main = new LaunchMain();
                        main.launch(context, data1, "plug in");
                    }
                    editor.putBoolean("earphoneFlag", false);
                    editor.commit();

                    break;
                default:
            }
        }
    }
}
