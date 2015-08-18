package bonkers.cau.sims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by redpe_000 on 2015-08-11.
 */
public class KeyBroadCast extends BroadcastReceiver {
    static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private ListDBManager dbManager;
    private ArrayList<ListData> listDataArrList;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbManager= new ListDBManager(context);
        listDataArrList =dbManager.selectAll();
        PackageManager packagemanager = context.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);
        boolean plus,minus=false;


        int oldVolume=0;
        int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        if(volume>oldVolume) {
            oldVolume=volume;
            for (ListData data:listDataArrList) {
                data.getmData1();
                data.getmData2();

            }
            PackageManager manager = context.getPackageManager();
            Intent i = manager.getLaunchIntentForPackage("com.android.settings");
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);

        }
    }
}



