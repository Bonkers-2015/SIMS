//android background service class
package bonkers.cau.sims.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

//실행 되었을 때 백그라운드 실행되는 Class
public class MainService extends Service implements Runnable {

    public static final String TAG = "MyService";

    //반복횟수
    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        Thread myThread = new Thread(this);
        myThread.start();
    }

    //thread implement
    public void run() {
        while(true){
            try{
             //   Log.i(TAG, "my service called  #" + count);
                count++;

                Thread.sleep(5000);
            } catch(Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid){
        //TODO Auto-generaterd method stud
        return super.onStartCommand(intent, flags, startid);

    }
    @Override
    public void onDestroy(){
        //TODO Auto-generated method stud
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {

        // TODO Autu-generated method stub
        return null;
    }

    /*
    }*/
}
