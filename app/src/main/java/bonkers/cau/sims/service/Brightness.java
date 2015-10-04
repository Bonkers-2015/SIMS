package bonkers.cau.sims.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import bonkers.cau.sims.R;
import bonkers.cau.sims.lighting.quickstart.Lighting;

public class Brightness extends Service implements SeekBar.OnSeekBarChangeListener {
    SeekBar bar;
    Lighting mLighting;
    private View mView;
    private WindowManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mLighting = new Lighting();
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.activity_brightness, null);
        bar = (SeekBar)mView.findViewById(R.id.seekbar_brightness); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(mView, mParams);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        mLighting.setBrightness(progress*2);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
