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
    public void onReceive(Context context, Intent intent) {


        int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_TYPE");


        Log.d("broadcastt", Integer.toString(volume));



    }
}





/* @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage("com.android.settings");
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
   */