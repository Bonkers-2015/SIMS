package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//android ���� �� ���� �ڵ� ����
public class StartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Intent i = new Intent(context, MainService.class);
            context.startService(i);

        }

    }
}

