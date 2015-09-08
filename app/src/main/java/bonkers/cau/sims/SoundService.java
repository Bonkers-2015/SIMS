package bonkers.cau.sims;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


public class SoundService extends Service {
    private MediaPlayer mediaPlayer;
    MediaPlayer m;
    Context c;


    @Override
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        c=getApplicationContext();
        Notification notification = new Notification(R.mipmap.ic_launcher, "¼­ºñ½º ½ÇÇàµÊ", System.currentTimeMillis());
        notification.setLatestEventInfo(getApplicationContext(), "Screen Service", "Foreground·Î ½ÇÇàµÊ", null);
        startForeground(1, notification);
        play();

        return 0;
    }
    //Play music
    public void play(){
        c = getApplicationContext();
        try {
            stop();
            m = MediaPlayer.create(c,R.raw.nonesound);
            m.setLooping(true);
            m.start();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    //Stop music play
    public void stop()
    {
        try {
            if(m != null)
            {
                m.stop();
                m.release();
                m = null;
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

