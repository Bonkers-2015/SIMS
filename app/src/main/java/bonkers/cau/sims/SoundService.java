package bonkers.cau.sims;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;


public class SoundService
        extends Service
{
    private MediaPlayer mediaPlayer;

    private void keepSoundServiceNotification()
    {
        NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this);
        Intent localIntent = new Intent(this, MainActivity.class);
        localIntent.addFlags(335544320);
        PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, localIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        localBuilder.setContentTitle("Volume Unlock Service").setContentText("Click to configure").setContentInfo("Keep app run stable").setSmallIcon(2130837600).setPriority(1).setContentIntent(localPendingIntent);
        Notification localNotification = localBuilder.build();
        localNotification.flags = (0x30 | localNotification.flags);
        startForeground(1235, localNotification);
    }

    private void playBack()
    {

            this.mediaPlayer = MediaPlayer.create(this, 2131099649);
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setLooping(true);
            this.mediaPlayer.setWakeMode(this, 1);
            return;

    }

    private void stopPlay()
    {
        if (this.mediaPlayer != null)
        {
            if (this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
            }
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    public IBinder onBind(Intent paramIntent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy()
    {
        stopPlay();
        stopForeground(true);
        super.onDestroy();
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
        if (this.mediaPlayer == null) {
            playBack();
        }
        if (!this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
                {
                    SoundService.this.mediaPlayer.start();
                }
            });
        }
        keepSoundServiceNotification();
        return 2;
    }
}

