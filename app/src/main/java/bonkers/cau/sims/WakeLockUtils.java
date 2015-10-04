package bonkers.cau.sims;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by redpe_000 on 2015-08-24.
 */
public class WakeLockUtils {
    private PowerManager powerManager = null;

    public WakeLockUtils(Context paramContext) {

        this.powerManager = ((PowerManager) paramContext.getSystemService(Context.POWER_SERVICE));

    }

    public boolean checkScreenOn() {
        boolean bool = true;
        if (!this.powerManager.isScreenOn()) {

            bool = false;
        }
        return bool;

    }

    public void unLockScreen() {
        PowerManager.WakeLock localWakeLock =
                this.powerManager.newWakeLock
                        (PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                PowerManager.ON_AFTER_RELEASE, "hello");
        localWakeLock.acquire();
        localWakeLock.release();
    }
    public void unLock() {
        PowerManager.WakeLock localWakeLock =
                this.powerManager.newWakeLock
                        (PowerManager.SCREEN_DIM_WAKE_LOCK, "hello");
        localWakeLock.acquire();
        localWakeLock.release();
    }
}

