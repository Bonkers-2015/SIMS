package bonkers.cau.sims.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import bonkers.cau.sims.LaunchMain;
import bonkers.cau.sims.receiver.ShakeEventListener;

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

                LaunchMain main = new LaunchMain();
                main.launch(getApplicationContext(), data1, "shake");
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

}