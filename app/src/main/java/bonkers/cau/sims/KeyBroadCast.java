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

/* Created by redpe_000 on 2015-08-11 */
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
        dbManager = new ListDBManager(context);
        listDataArrList = dbManager.selectAll();
        PackageManager packagemanager = context.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);
        SharedPreferences prefs = context.getSharedPreferences("myPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        oldVolume=prefs.getInt("oldVolume",0);

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

        }

        volume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        isShaked=prefs.getInt("isShacked",0);
        Log.d("mylog", Integer.toString(isShaked));
        Log.d("mylog","oldVolume="+Integer.toString(oldVolume));
        Log.d("mylog","volume="+Integer.toString(volume));
        //�÷��� ������ ��������
        if ((volume > oldVolume)&&(isShaked==1) ||((oldVolume==maxVolume)&&(oldVolume==volume)&&(isShaked==1))) {
            oldVolume = volume;
            lauchApp(packagemanager, context, appList, "data0");

        } else if ((volume < oldVolume)&&(isShaked==1) ||((oldVolume==minVolume)&&(oldVolume==volume)&&(isShaked==1))) {
            oldVolume = volume;
            lauchApp(packagemanager, context, appList, "data1");
        }
        else
            oldVolume = volume;

        editor.putInt("oldVolume", oldVolume);
        editor.commit();
    }

    void lauchApp(PackageManager packagemanager,Context context,List<ApplicationInfo> appList,String data){
        for (ListData list : listDataArrList) {
            if (list.getmData1().equals(data)) {
                //���� ���� �޾ƿ���
                appIndexNum = list.getIndexNum();
                appPackageName = appList.get(appIndexNum).packageName;
                //�۽���
                Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);
            }
        }
    }
}




