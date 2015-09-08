package bonkers.cau.sims;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements SensorEventListener {
=======
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity  {
>>>>>>> origin/parkbeomseok
    String model = Build.MODEL;
    TextView textView;
    BroadcastReceiver myReceiver = new KeyBroadCast();

<<<<<<< HEAD
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

=======
>>>>>>> origin/parkbeomseok
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
=======
        startService(new Intent(this, ScreenService.class));
        Intent serviceIntent = new Intent(this, ShakeService.class);
        startService(serviceIntent);
>>>>>>> origin/parkbeomseok

        TimerTask myTask = new TimerTask() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(myTask, 1000);

        textView = (TextView) findViewById(R.id.model);
        textView.setText(model);

<<<<<<< HEAD
        packageList();
    }

     //package list
    public void packageList() {

        long keyPressedTime = 0;
        Toast toast;
        Context context = null;

        PackageManager pm = this.getPackageManager();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo pack : packs) {

            Log.i("TAG", pack.applicationInfo.loadLabel(pm).toString());
            Log.i("TAG", pack.packageName);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    // 이벤트발생!!
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
=======
>>>>>>> origin/parkbeomseok
    }
}
