package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//android 부팅 시 어플 자동 실행
public class StartReceiver extends BroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent){

    if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){

        ComponentName cName = new ComponentName(context.getPackageName(),MainService.class.getName());

        ComponentName svcName = context.startService(new Intent().setComponent(cName));

        if(svcName == null){

            Log.d("error","No");

        }
        else{

            Log.d("Ok","Good");
        }
       }
   }


}
