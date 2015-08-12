package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;


public class ButtonService extends BroadcastReceiver {

public ButtonService(){

    super();
}


    @Override
    public void onReceive(Context context, Intent intent) {

         if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

            //버튼 선택
            if (KeyEvent.KEYCODE_VOLUME_UP == event.getKeyCode()) {

                if (KeyEvent.ACTION_DOWN == event.getAction()) {

                    Intent Templement = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
                    Templement.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Templement);


                }
            }

        }


    }

}