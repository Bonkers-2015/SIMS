package bonkers.cau.sims.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import bonkers.cau.sims.service.SoundService;

public class ScreenReceiver extends BroadcastReceiver {
    public void onReceive(Context paramContext, Intent paramIntent) {
        if (paramIntent.getAction() == "android.intent.action.SCREEN_ON") {

            paramContext.stopService(new Intent(paramContext, SoundService.class));
        }

        if (paramIntent.getAction() == "android.intent.action.SCREEN_OFF") {
            paramContext.startService(new Intent(paramContext, SoundService.class));
        }

    }
}
