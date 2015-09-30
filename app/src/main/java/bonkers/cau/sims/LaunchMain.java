package bonkers.cau.sims;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;

import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;

/**
 * Created by dongbin on 2015-10-01.
 */
public class LaunchMain {

    public void launch(Context context, String data1, String data2){

        ListDBManager dbManager = new ListDBManager(context);
        ArrayList<ListData> listDataArrList = dbManager.selectAll();
        PackageManager packagemanager = context.getPackageManager();

        AdditionFunctions additionFunctions = new AdditionFunctions();

        for (ListData list : listDataArrList) {
            if (list.getmData1().equals(data1)) {
                if (list.getmData2().equals(data2)) {

                    if(list.getmAppName()!=null){
                        //어플 정보 받아오기
                        String appPackageName = list.getmAppPackage();
                        //앱실행
                        Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                        i.addCategory(Intent.CATEGORY_LAUNCHER);
                        context.startActivity(i);
                    }else if(list.getmPhoneName()!=null){
                        //전화걸기

                        Intent intent;
                        // 바로 걸기
//                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+list.getmPhoneNumber()));

                        // 번호 띄우기
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+list.getmPhoneNumber()));

                        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                        try {
                            pi.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }

                    }else if(list.getmAdditionName()!=null){
                        //addition기능 수행
                        additionFunctions.launchAddition(context,list.getmAdditionName());
                    }

                }
            }

        }
    }
}
