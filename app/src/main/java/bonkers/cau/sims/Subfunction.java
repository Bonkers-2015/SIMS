package bonkers.cau.sims;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

/**
 * Created by KGM on 2015-09-19.
 */
public class Subfunction {
    /*  Wifi On/Off  */
    public void toggleWiFi(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    /*  Bluetooth On/Off  */
    public void setBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        } else if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }
}
