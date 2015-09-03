package bonkers.cau.sims;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by redpe_000 on 2015-08-24.
 */
public class WakeLockUtils
{
    private PowerManager powerManager = null;

    public WakeLockUtils(Context paramContext)
    {
        // 여기도 고침
        this.powerManager = ((PowerManager)paramContext.getSystemService(Context.POWER_SERVICE));

    }

    public boolean checkScreenOn()
    {
        boolean bool = true;
        if (!this.powerManager.isScreenOn()) {

            bool = false;
        }
        return bool;

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

