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
import android.widget.Toast;

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


        Toast.makeText(ShakeService.this, "Shake!", Toast.LENGTH_SHORT).show();


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {


                SharedPreferences prefs = getSharedPreferences("myPrefs",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("shacked", 1);
                editor.commit();
                Log.d("isShaked", Integer.toString(prefs.getInt("shacked",0)));


            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        Log.d("onstart start","service start");
/*        Toast.makeText(ShakeService.this, "Service Started", Toast.LENGTH_LONG).show();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                Toast.makeText(ShakeService.this, "Shake!", Toast.LENGTH_SHORT).show();
            }
        });*/

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