package bonkers.cau.sims.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import bonkers.cau.sims.service.ScreenService;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

            context.startService(new Intent(context, ScreenService.class));
    }
}
