package bonkers.cau.sims.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import bonkers.cau.sims.lighting.PHHomeService;
import bonkers.cau.sims.service.ScreenService;

public class BootReceiver extends BroadcastReceiver {
    Intent mIntent;

    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mIntent = new Intent(context, PHHomeService.class);
        context.startService(mIntent);
        context.startService(new Intent(context, ScreenService.class));
    }
}
