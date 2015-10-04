package bonkers.cau.sims;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.util.ArrayList;

import bonkers.cau.sims.lighting.quickstart.Lighting;

/*  Created by KGM on 2015-09-19.  */
public class AdditionFunctions{
    Lighting mLighting;
    public ArrayList<String> nameList;

    public AdditionFunctions(){
        nameList = new ArrayList<String>();
        listSetting();
    }


    public  void launchAddition(Context context, String name){
        if(name.equals(nameList.get(0))){
            set_WiFif(context);
        }else if(name.equals(nameList.get(1))){
            set_Bluetooth();
        }else if(name.equals(nameList.get(2))){
            set_GPS(context);
        }else if(name.equals(nameList.get(3))){
            set_SilentMod(context);
        }else if(name.equals(nameList.get(4))){
            set_VibrateMod(context);
        }else if(name.equals(nameList.get(5))){
            set_RingMod(context);
        }else if(name.equals(nameList.get(6))) {
            //Screen Shot
        }
        else if(name.equals(nameList.get(7))) {
            mLighting=new Lighting();
            mLighting.randomLights();
        }else if(name.equals(nameList.get(8))) {
            mLighting=new Lighting();
            mLighting.onOffLights();
        }else if(name.equals(nameList.get(9))) {
            //empty
        }
    }

    public void listSetting(){
        nameList.add("Wifi on/off");
        nameList.add("bluetooth on/off");
        nameList.add("GPS on/off");
        nameList.add("silent mode");
        nameList.add("vibrate mode");
        nameList.add("ring mode");
        nameList.add("Screen Shot");
        nameList.add("light color");
        nameList.add("light on/off");
        nameList.add("   ");
    }

    /*  Wifi On/Off  */
    public void set_WiFif(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    /*  Bluetooth On/Off  */
    public void set_Bluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        } else if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    /*  GPS on/off  */
    public void set_GPS(Context mContext) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        mContext.sendBroadcast(intent);

        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);
        } else {
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);
        }
    }

    /*  Silent mode 무음모드로 변경  */
    public void set_SilentMod(Context mContext) {
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    /*  Silent mode 진동모드로 변경  */
    public void set_VibrateMod(Context mContext) {
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    /*  Silent mode 소리모드로 변경  */
    public void set_RingMod(Context mContext) {
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    /*  Screenshot  */
    // 미완성
}