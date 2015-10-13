package bonkers.cau.sims.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bonkers.cau.sims.WakeLockUtils;
import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;
import bonkers.cau.sims.service.EarphoneService;
import bonkers.cau.sims.service.ShakeService;
import bonkers.cau.sims.service.TouchService;

/**
 * Created by dongbin on 2015-09-30.
 */
public class VolumeReceiver extends BroadcastReceiver {
    private String strPackage = "";
    private Context mContext;

    //    private Intent mIntent;
    private Intent touchIntent, shakeIntent, earphoneIntent;
    private final int maxVolume=10,minVolume=0;
    private int currentVolume,oldVolume;
    private boolean flag=false;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TimerTask touchTask, shakeTask, earphoneTask,myTask;
    Timer timer;

    ListDBManager dbManager;
    ArrayList<ListData> listDataArrList;
    PackageManager packagemanager;

    // max volume ring : 7, media : 15


    private int getMaxVolume() {
        return maxVolume;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

//        mIntent =  intent;
        WakeLockUtils wakeLockUtils=new WakeLockUtils(mContext);
        wakeLockUtils.unLock();


        touchIntent = new Intent(mContext, TouchService.class);
        shakeIntent = new Intent(mContext, ShakeService.class);
        earphoneIntent = new Intent(mContext, EarphoneService.class);

        dbManager = new ListDBManager(mContext);
        listDataArrList = dbManager.selectAll();
        packagemanager = mContext.getPackageManager();

        timer = new Timer();
        timerSetting();

        prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();

        flag = prefs.getBoolean("volumeFlag", true);
        oldVolume = prefs.getInt("volume",-1);

        if(flag) {
            // 두번 중 한번은 버림
            editor.putBoolean("volumeFlag",false);
            editor.commit();
            return;
        }

        // 벨소리, 미디어 모두 포괄
        int currentVolume = (Integer) intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        int delta = currentVolume - oldVolume;

        String data1="";

        if (delta > 0) {
            // volume up
            data1 = "volume up";
        } else if (delta < 0) {
            // volume downn
            data1 = "volume down";
        } else if(delta == 0){
            // volume max,min
            if(currentVolume==0){
                //volume min
                data1 = "volume down";
            }else{
                //volume max
                data1 = "volume up";
            }
        }
        if(excludeApp()==false){
            if(checkData1(data1)) {
                touchIntent.putExtra("data1",data1);
                shakeIntent.putExtra("data1",data1);
                earphoneIntent.putExtra("data1",data1);
                mContext.startService(touchIntent);
                mContext.startService(shakeIntent);
                mContext.startService(earphoneIntent);
                timer.schedule(myTask, 2000);
            }
        }

        editor.putInt("volume", currentVolume);
        editor.putBoolean("volumeFlag",true);
        editor.commit();

    }
    public void timerSetting(){
        myTask = new TimerTask() {
            public void run() {
                mContext.stopService(touchIntent);
                mContext.stopService(shakeIntent);
                mContext.stopService(earphoneIntent);
            }
        };
    }

    public boolean checkData1(String data1){

        for (ListData list : listDataArrList) {
            if (list.getmData1().equals(data1)) {
                return true;
            }
        }
        return false;
    }

    public boolean excludeApp(){

        Boolean onOff=false;

        SharedPreferences test = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        ArrayList<String> excludedAppList = new ArrayList<String>();
        excludedAppList.addAll(test.getStringSet("excludedAppList",null));

        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> proceses = manager.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo  process : proceses ){

            if(process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){

                strPackage = process.processName;

                if(excludedAppList!=null){
                    for(int i=0;i<excludedAppList.size();i++){
                        if(excludedAppList.get(i).equals(strPackage)){
                            onOff = true;
                        }
                    }
                }else{
                    Toast.makeText(mContext, "No Exclude AppList", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return onOff;
    }
}
