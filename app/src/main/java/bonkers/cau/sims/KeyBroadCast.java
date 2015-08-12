package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by redpe_000 on 2015-08-11.
 */
public class KeyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent intent) {
        int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        Log.d("finish", Integer.toString(volume));

        if (volume == 7) {


        }else if (volume == 6) {
            Log.d("start", "start");
        }
    }
}
