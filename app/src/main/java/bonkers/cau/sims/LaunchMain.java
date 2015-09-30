package bonkers.cau.sims;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

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
                        // 전화걸기
                    }else if(list.getmAdditionName()!=null){
                        //addition기능 수행
                        additionFunctions.launchAddition(context,list.getmAdditionName());
                    }

                }
            }

        }
    }
}
