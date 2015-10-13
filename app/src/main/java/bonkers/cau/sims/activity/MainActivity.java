package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import bonkers.cau.sims.R;
import bonkers.cau.sims.lighting.PHHomeService;
import bonkers.cau.sims.service.ScreenService;
import bonkers.cau.sims.service.VolumeService;

public class MainActivity extends Activity  {
    String model = Build.MODEL;
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, ScreenService.class));
        Intent lightIntent = new Intent(getApplicationContext(), PHHomeService.class);
        startService(lightIntent);
        startService(new Intent(getApplicationContext(), ScreenService.class));


        SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // STREAM_RING : ���Ҹ� , STREAM_MUSIC : �̵��
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_RING);

        editor.putInt("volume", currentVolume);
        editor.putBoolean("volumeFlag", true);
        editor.commit();

        startService(new Intent(this, VolumeService.class));

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
