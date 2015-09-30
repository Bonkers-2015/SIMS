package bonkers.cau.sims.boadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dongbin on 2015-09-21.
 */
public class EarphoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Toast.makeText(context, "Headset is unplugged", Toast.LENGTH_SHORT).show();
                    Log.d("EarphoneService", "Headset is unplugged");
                    break;
                case 1:
                    Toast.makeText(context, "Headset is plugged" , Toast.LENGTH_SHORT).show();
                    Log.d("EarphoneService", "Headset is plugged");
                    break;
                default:
                    Log.d("EarphoneService", "I have no idea what the headset state is");
            }
        }

    }
}
