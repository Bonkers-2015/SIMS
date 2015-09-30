package bonkers.cau.sims.boadcast;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import java.util.ArrayList;

import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;

/**
 * Created by ±è½Â¿í on 2015-08-20.
 */
public class ShakeService extends Service {
    SensorManager mSensorManager;
    ShakeEventListener mSensorListener;
    Sensor mAccelerometer;

    String data1 = "Empty";

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {

                lauchApp(getApplicationContext(),data1, "shake");

            }
        });
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        data1 = intent.getStringExtra("data1");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI, new Handler());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorListener,mAccelerometer);
    }


    void lauchApp(Context context,String data1, String data2) {
        ListDBManager dbManager = new ListDBManager(context);
        ArrayList<ListData> listDataArrList = dbManager.selectAll();

        PackageManager packagemanager = context.getPackageManager();
        for (ListData list : listDataArrList) {
            if (list.getmData1().equals(data1)) {
                if (list.getmData2().equals(data2)) {

                    //¾îÇÃ Á¤º¸ ¹Þ¾Æ¿À±â
                    String appPackageName = list.getmAppPackage();
                    //¾Û½ÇÇà
                    Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    context.startActivity(i);

                }
            }
        }
    }

}