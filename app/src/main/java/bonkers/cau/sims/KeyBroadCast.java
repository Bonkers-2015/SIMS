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
        //db ����
        dbManager = new ListDBManager(context);
        listDataArrList = dbManager.selectAll();
        //���ø����̼� ������ �޾ƿ��°ž�
        PackageManager packagemanager = context.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

        SharedPreferences prefs = context.getSharedPreferences("myPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        oldVolume=prefs.getInt("oldVolume",0);

        //���ý� �ʱ� �� ������ �޾ƿ���
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            wakeLock.acquire();

            oldVolume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
            editor.putInt("oldVolume",oldVolume);
            editor.commit();
        }

        volume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");

        //�÷��� ������ ��������
        if (volume > oldVolume ||(oldVolume==maxVolume&&oldVolume==volume)) {
            oldVolume = volume;
            lauchApp(packagemanager,context,appList,"data0");

        //���̳ʽ� ������ ��������
        } else if (volume < oldVolume||(oldVolume==minVolume&&oldVolume==volume)) {
            oldVolume = volume;
            lauchApp(packagemanager,context,appList,"data1");
        }

        editor.putInt("oldVolume", oldVolume);
        editor.commit();
    }


    //���� ����
    void lauchApp(PackageManager packagemanager,Context context,List<ApplicationInfo> appList,String data){
        for (ListData list : listDataArrList) {
            Log.d(list.getmData1(), list.getmData1());
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




