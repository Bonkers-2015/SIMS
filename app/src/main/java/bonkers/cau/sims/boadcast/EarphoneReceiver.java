package bonkers.cau.sims.boadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;

/**
 * Created by dongbin on 2015-09-21.
 */
public class EarphoneReceiver extends BroadcastReceiver {

    String data1 = "EMPTY";
    Intent mIntent;

    public EarphoneReceiver(Intent intent){
        mIntent = intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        data1 = mIntent.getStringExtra("data1");

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    // Eaphone unplugged
                    break;
                case 1:
                    // Eaphone plugged
                    lauchApp(context,data1, "plug in");
                    break;
                default:
            }
        }
    }
    void lauchApp(Context context,String data1, String data2) {
        ListDBManager dbManager = new ListDBManager(context);
        ArrayList<ListData> listDataArrList = dbManager.selectAll();

        PackageManager packagemanager = context.getPackageManager();
        for (ListData list : listDataArrList) {
            if (list.getmData1().equals(data1)) {
                if (list.getmData2().equals(data2)) {

                    //어플 정보 받아오기
                    String appPackageName = list.getmAppPackage();
                    //앱실행
                    Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    context.startActivity(i);

                }
            }
        }
    }


}
