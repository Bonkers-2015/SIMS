package bonkers.cau.sims;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by dongbin on 2015-09-30.
 */
public class SettingsContentObserver extends ContentObserver {
    int previousVolume;
    Context context;

    public SettingsContentObserver(Context c, Handler handler) {
        super(handler);
        context=c;

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);


        Toast.makeText(context, ""+previousVolume, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);



        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);


        int delta=previousVolume-currentVolume;

        if(delta>0)
        {
            Toast.makeText(context, "Decreased", Toast.LENGTH_SHORT).show();
//            Logger.d("Decreased");
            previousVolume=currentVolume;
        }
        else if(delta<0)
        {
            Toast.makeText(context, "Increased", Toast.LENGTH_SHORT).show();
//            Logger.d("Increased");
            previousVolume=currentVolume;
        }
    }
}