package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;


public class ButtomService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        if(Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())){
            KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if(KeyEvent.KEYCODE_VOLUME_UP == event.getKeyCode()){

            Intent Templement = new Intent(context, MainActivity.class);
                context.startActivity(Templement);


            }
        }

    }


}
