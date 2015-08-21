package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ButtonService extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.CATEGORY_HOME)) ;
        {

            Intent j = new Intent(context, MainService.class);
            context.startActivity(j);



        }

    }

}