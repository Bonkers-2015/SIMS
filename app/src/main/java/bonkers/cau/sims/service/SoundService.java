package bonkers.cau.sims.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import bonkers.cau.sims.R;


public class SoundService extends Service {
    MediaPlayer mediaPalyer;
    Context context;


    @Override
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        context=getApplicationContext();
        Notification notification = new Notification(R.mipmap.ic_launcher, "¼­ºñ½º ½ÇÇàµÊ", System.currentTimeMillis());
        notification.setLatestEventInfo(getApplicationContext(), "Screen Service", "Foreground·Î ½ÇÇàµÊ", null);
        startForeground(1, notification);
        play();

        return 0;
    }
    //Play music
    public void play(){
        context = getApplicationContext();
        try {
            stop();
            mediaPalyer = MediaPlayer.create(context,R.raw.nonesound);
            mediaPalyer.setLooping(true);
            mediaPalyer.start();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    //Stop music play
    public void stop()
    {
        try {
            if(mediaPalyer != null)
            {
                mediaPalyer.stop();
                mediaPalyer.release();
                mediaPalyer = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

