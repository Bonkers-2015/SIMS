package bonkers.cau.sims.boadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;

import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;

/**
 * Created by dongbin on 2015-09-30.
 */
public class VolumeReceiver extends BroadcastReceiver {
    private final int maxVolume=10,minVolume=0;
    private ListDBManager dbManager;
    private ArrayList<ListData> listDataArrList;
    private int currentVolume,oldVolume;
    private boolean flag=false;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {

        prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();

        flag = prefs.getBoolean("flag", true);
        oldVolume = prefs.getInt("volume",-1);

        if(flag) {
            // 두번 중 한번은 버림
            editor.putBoolean("flag",false);
            editor.commit();
            return;
        }

        // 벨소리, 미디어 모두 포괄
        int currentVolume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        int delta = currentVolume - oldVolume;

        if (delta > 0) {
            Toast.makeText(context, "Increased", Toast.LENGTH_SHORT).show();
        } else if (delta < 0) {
            Toast.makeText(context, "Decreased", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "No!", Toast.LENGTH_SHORT).show();
        }

        editor.putInt("volume", currentVolume);
        editor.putBoolean("flag",true);
        editor.commit();
    }
}
