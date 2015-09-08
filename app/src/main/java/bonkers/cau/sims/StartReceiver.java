package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//android ���� �� ���� �ڵ� ����
public class StartReceiver extends BroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent){

       String action = intent.getAction();
       if(action.equals("anroid.intent.action.BOOT_COMPLETED")){
           Intent i = new Intent(context, MainService.class);
           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(i);
       }
   }
}
