package bonkers.cau.sims;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity  {
    String model = Build.MODEL;
    TextView textView;
    BroadcastReceiver myReceiver = new KeyBroadCast();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        startService(new Intent(this, ScreenService.class));
        startService(new Intent(this, SoundService.class));
=======

>>>>>>> origin/master
        Intent serviceIntent = new Intent(this, ShakeService.class);
        startService(serviceIntent);

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

    }
}
