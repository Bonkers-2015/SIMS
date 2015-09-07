package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
    int isShaked=0;
    String appPackageName;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("mylog","button selected");
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
<<<<<<< HEAD

=======
        isShaked=prefs.getInt("shacked",0);
        Log.d("oldVolume",Integer.toString(oldVolume));
        Log.d("isShaked",Integer.toString(isShaked));
        //부팅시 초기 값 볼륩을 받아오기
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
>>>>>>> f90826051d59cb4ae31c7af2f5f988540f758277



        volume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        isShaked=prefs.getInt("isShacked",0);
        Log.d("mylog", Integer.toString(isShaked));
        Log.d("mylog","oldVolume="+Integer.toString(oldVolume));
        Log.d("mylog","volume="+Integer.toString(volume));
        //플러스 볼륨을 눌렀을떄
        if ((volume > oldVolume)&&(isShaked==1) ||((oldVolume==maxVolume)&&(oldVolume==volume)&&(isShaked==1))) {
            oldVolume = volume;
            lauchApp(packagemanager, context, appList, "data0");

        //마이너스 볼륨을 눌렀을때
<<<<<<< HEAD
        } else if ((volume < oldVolume)&&(isShaked==1) ||((oldVolume==minVolume)&&(oldVolume==volume)&&(isShaked==1))) {
            oldVolume = volume;
            lauchApp(packagemanager, context, appList, "data1");
;
        }
        else {
=======
        } else if (volume < oldVolume&&(isShaked==1)||((oldVolume==minVolume)&&(oldVolume==volume)&&(isShaked==1))) {
>>>>>>> f90826051d59cb4ae31c7af2f5f988540f758277
            oldVolume = volume;
        }
<<<<<<< HEAD
=======
        else
            oldVolume = volume;

>>>>>>> f90826051d59cb4ae31c7af2f5f988540f758277
        editor.putInt("oldVolume", oldVolume);
        editor.commit();
    }


    //어플 실행
    void lauchApp(PackageManager packagemanager,Context context,List<ApplicationInfo> appList,String data){
        for (ListData list : listDataArrList) {
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




