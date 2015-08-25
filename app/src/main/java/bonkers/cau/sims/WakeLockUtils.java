package bonkers.cau.sims;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

/**
 * Created by redpe_000 on 2015-08-24.
 */
public class WakeLockUtils { private PowerManager powerManager = null;

    public WakeLockUtils(Context paramContext)
    {
        this.powerManager = ((PowerManager)paramContext.getSystemService("power"));
    }

    public boolean checkScreenOn()
    {
        boolean bool = true;
        if (!this.powerManager.isScreenOn()) {
            label32:
            bool = false;
        }

/*        if (Build.VERSION.SDK_INT >= 20) {
            if (!this.powerManager.isInteractive()) {
                break label32;
            }
        }
        for (;;)
        {
            return bool;
            if (!this.powerManager.isScreenOn()) {
                label32:
                bool = false;
            }
        }*/
    }

    public void unLockScreen()
    {
        PowerManager.WakeLock localWakeLock = this.powerManager.newWakeLock(268435482, "UNLOCK_SCREEN");
        if (!localWakeLock.isHeld()) {
            localWakeLock.acquire();
        }
        localWakeLock.release();
    }
}

