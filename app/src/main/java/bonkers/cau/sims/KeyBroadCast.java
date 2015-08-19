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
//
//        KeyEvent Xevent = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
//
//        Log.d("broadcastt", Integer.toString(Xevent.getAction()));

//
//        if ((Xevent.getAction() == KeyEvent.ACTION_DOWN)){
//        }
//        else if ((Xevent.getAction() == KeyEvent.ACTION_UP)) {
//        }


//        int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        int volume = (Integer) intent.getExtras().get("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE");

         Log.d("VolumeButton", Integer.toString(volume));

        String action = intent.getAction();
        Log.d("HomeButton","onReceive: action: " + action);
//
//        Log.d("broadcastt", Integer.toString(volume));
//
//        if (volume == KeyEvent.KEYCODE_VOLUME_DOWN) ;
//        if (volume == 7) {
//
//
//        } else if (volume == 6) {
//            Log.d("start", "start");
//        }
    }
}

