package bonkers.cau.sims;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ±è½Â¿í on 2015-08-20.
 */
public class ShakeService extends Service  {
    SensorManager mSensorManager;
    ShakeEventListener mSensorListener;
    Sensor mAccelerometer;
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
        SharedPreferences prefs = getSharedPreferences("myPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
=======

        Toast.makeText(ShakeService.this, "Shake!", Toast.LENGTH_SHORT).show();
>>>>>>> f90826051d59cb4ae31c7af2f5f988540f758277


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
<<<<<<< HEAD
                Log.d("mylog","isShaked");
                TimerTask myTask = new TimerTask() {
                    public void run() {
                        SharedPreferences prefs = getSharedPreferences("myPrefs",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.commit();

                    }
                };
                SharedPreferences prefs = getSharedPreferences("myPrefs",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("isShacked", 1);
=======


                SharedPreferences prefs = getSharedPreferences("myPrefs",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("shacked", 1);
>>>>>>> f90826051d59cb4ae31c7af2f5f988540f758277
                editor.commit();
                Log.d("isShaked", Integer.toString(prefs.getInt("shacked",0)));


            }
        });
    }



    public int onStartCommand(Intent intent, int flags, int startId){

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


}