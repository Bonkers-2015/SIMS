package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.util.ArrayList;

/**
 * Created by redpe_000 on 2015-08-11.
 */
public class KeyBroadCast extends BroadcastReceiver {
    private ListDBManager dbManager;
    private ArrayList<ListData> listDataArrList;
    int volume = 0;
    int oldVolume;
    private final int maxVolume=10,minVolume=0;
    int isShaked=0;
    String appPackageName;

    @Override
    public void onReceive(Context context, Intent intent) {


        context.startService(new Intent(context, TouchService.class));
        context.startService(new Intent(context, ShakeService.class));
        //db 생성
        dbManager = new ListDBManager(context);
        listDataArrList = dbManager.selectAll();
        //어플리케이션 내욜을 받아오는거야
        PackageManager packagemanager = context.getPackageManager();
        SharedPreferences prefs = context.getSharedPreferences("myPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        oldVolume=prefs.getInt("oldVolume",0);
        volume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");

        //플러스 볼륨을 눌렀을떄
        if (volume > oldVolume) {
            //if문해서 터치인지 검사
            oldVolume = volume;
            //마이너스 볼륨을 눌렀을때
        } else if (volume <= oldVolume) {
            oldVolume = volume;
        }
        else
            oldVolume = volume;

        editor.putInt("oldVolume", oldVolume);
        editor.commit();
    }

}




