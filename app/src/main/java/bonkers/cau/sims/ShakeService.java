package bonkers.cau.sims;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 김승욱 on 2015-08-20.
 */
public class ShakeService extends Service {
    SensorManager mSensorManager;
    ShakeEventListener mSensorListener;
    Sensor mAccelerometer;
    String appPackageName;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();


<<<<<<< HEAD
=======
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();



>>>>>>> origin/bnitech
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                lauchApp(getApplicationContext(), "motion");

            }
        });
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI, new Handler());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    void lauchApp(Context context, String data) {
        Log.d("mylog","lauchApp");
        ListDBManager dbManager = new ListDBManager(context);
        ArrayList<ListData> listDataArrList = dbManager.selectAll();
        //어플리케이션 내욜을 받아오는거야
        PackageManager packagemanager = context.getPackageManager();
        for (ListData list : listDataArrList) {
            if (list.getmData2().equals(data)) {

                //어플 정보 받아오기
                appPackageName = list.getmAppPackage();
                //앱실행
                Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);

            }
        }
    }

}