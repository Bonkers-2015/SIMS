package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import bonkers.cau.sims.boadcast.KeyBroadCast;
import bonkers.cau.sims.R;
import bonkers.cau.sims.boadcast.EarphoneService;
import bonkers.cau.sims.boadcast.ScreenService;
import bonkers.cau.sims.boadcast.ShakeService;

public class MainActivity extends Activity  {
    String model = Build.MODEL;
    TextView textView;
    BroadcastReceiver myReceiver = new KeyBroadCast();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, ScreenService.class));
        startService(new Intent(this, ShakeService.class));
        startService(new Intent(this, EarphoneService.class));


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
