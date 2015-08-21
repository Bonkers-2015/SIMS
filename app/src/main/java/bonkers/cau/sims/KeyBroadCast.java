package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by redpe_000 on 2015-08-11.
 */
public class KeyBroadCast extends BroadcastReceiver {
    private ListDBManager dbManager;
    private ArrayList<ListData> listDataArrList;
    int oldVolume, volume = 0;
    private final int maxVolume=10,minVolume=0;
    int appIndexNum;
    String appPackageName;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("okokok","okokok");
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.PARTIAL_WAKE_LOCK, "MY TAG" );
        wakeLock.acquire();
        //db 생성
        dbManager = new ListDBManager(context);
        listDataArrList = dbManager.selectAll();
        //어플리케이션 내욜을 받아오는거야
        PackageManager packagemanager = context.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

        SharedPreferences prefs = context.getSharedPreferences("myPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        oldVolume=prefs.getInt("oldVolume",0);

        //부팅시 초기 값 볼륩을 받아오기
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            wakeLock.acquire();

            oldVolume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
            editor.putInt("oldVolume",oldVolume);
            editor.commit();
        }

        volume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");

        //플러스 볼륨을 눌렀을떄
        if (volume > oldVolume ||(oldVolume==maxVolume&&oldVolume==volume)) {
            oldVolume = volume;
            lauchApp(packagemanager,context,appList,"data0");

        //마이너스 볼륨을 눌렀을때
        } else if (volume < oldVolume||(oldVolume==minVolume&&oldVolume==volume)) {
            oldVolume = volume;
            lauchApp(packagemanager,context,appList,"data1");
        }

        editor.putInt("oldVolume", oldVolume);
        editor.commit();
    }


    //어플 실행
    void lauchApp(PackageManager packagemanager,Context context,List<ApplicationInfo> appList,String data){
        for (ListData list : listDataArrList) {
            Log.d(list.getmData1(), list.getmData1());
            if (list.getmData1().equals(data)) {

                //어플 정보 받아오기
                appIndexNum = list.getIndexNum();
                appPackageName = appList.get(appIndexNum).packageName;
                //앱실행
                Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);

            }
        }
    }
}




