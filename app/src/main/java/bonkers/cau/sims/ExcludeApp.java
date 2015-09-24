package bonkers.cau.sims;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;


public class ExcludeApp extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {

        //현재 실행중인 Application 정보 받아오기.
        String strPackage = "";
        Context mContext;
        mContext = context;


      //  context.startService(new Intent(context, ScreenService.class));
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> proceses = manager.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo  process : proceses ){

            if(process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){

                    strPackage = process.processName;
                    Log.d("Arsenal", strPackage);

            }

        }

    }


}
