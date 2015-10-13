package bonkers.cau.sims.lighting;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;

import java.util.List;

/**
 * PHHomeActivity - The starting point in your own Hue App.
 * 
 * For first time use, a Bridge search (UPNP) is performed and a list of all available bridges is displayed (and clicking one of them shows the PushLink dialog allowing authentication).
 * The last connected Bridge IP Address and Username are stored in SharedPreferences.
 * 
 * For subsequent usage the app automatically connects to the last connected bridge.
 * When connected the MyApplicationActivity Activity is started.  This is where you should start implementing your Hue App!  Have fun!
 * 
 * For explanation on key concepts visit: https://github.com/PhilipsHue/PhilipsHueSDK-Java-MultiPlatform-Android
 * 
 *
 */
public class PHHomeService extends Service implements OnItemClickListener {

    private PHHueSDK phHueSDK;
    public static final String TAG = "QuickStart";

    
    private boolean lastSearchWasIPScan = false;

    @Override
    public void onCreate() {
        super.onCreate();
        // Gets an instance of the Hue SDK.
        phHueSDK = PHHueSDK.create();
        // Set the Device Name (name of your app). This will be stored in your bridge whitelist entry.
        phHueSDK.setAppName("QuickStartApp");
        phHueSDK.setDeviceName(Build.MODEL);
        // Register the PHSDKListener to receive callbacks from the bridge.
        phHueSDK.getNotificationManager().registerSDKListener(listener);


        String lastIpAddress   = "192.168.0.14";
        String lastUsername    = "f845cda39c1fd971eae384696fc0b3";

        // Automatically try to connect to the last connected IP Address.  For multiple bridge support a different implementation is required.
        if (lastIpAddress !=null && !lastIpAddress.equals("")) {
            PHAccessPoint lastAccessPoint = new PHAccessPoint();
            lastAccessPoint.setIpAddress(lastIpAddress);
            lastAccessPoint.setUsername(lastUsername);

            if (!phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                phHueSDK.connect(lastAccessPoint);
            }
        }
        else {  // First time use, so perform a bridge search.
            doBridgeSearch();
        }
    }






    // Local SDK Listener
    private PHSDKListener listener = new PHSDKListener() {

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPoint) {

            if (accessPoint != null && accessPoint.size() > 0) {
                    phHueSDK.getAccessPointsFound().clear();
                    phHueSDK.getAccessPointsFound().addAll(accessPoint);

                   
            } 
            
        }
        
        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
            Log.w(TAG, "On CacheUpdated");

        }

        @Override
        public void onBridgeConnected(PHBridge b, String username) {
            phHueSDK.setSelectedBridge(b);
            phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
            phHueSDK.getLastHeartbeat().put(b.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            Log.w(TAG, "Authentication Required.");
            phHueSDK.startPushlinkAuthentication(accessPoint);
           
        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {

            
            Log.v(TAG, "onConnectionResumed" + bridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(),  System.currentTimeMillis());
            for (int i = 0; i < phHueSDK.getDisconnectedAccessPoint().size(); i++) {

                if (phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache().getBridgeConfiguration().getIpAddress())) {
                    phHueSDK.getDisconnectedAccessPoint().remove(i);
                }
            }

        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            Log.v(TAG, "onConnectionLost : " + accessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(accessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(accessPoint);
            }
        }
        
        @Override
        public void onError(int code, final String message) {
            Log.e(TAG, "on Error Called : " + code + ":" + message);

            if (code == PHHueError.NO_CONNECTION) {
                Log.w(TAG, "On No Connection");
            } 
            else if (code == PHHueError.AUTHENTICATION_FAILED || code==PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
            } 
            else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(TAG, "Bridge Not Responding . . . ");


            } 
            else if (code == PHMessageType.BRIDGE_NOT_FOUND) {

                if (!lastSearchWasIPScan) {  // Perform an IP Scan (backup mechanism) if UPNP and Portal Search fails.
                    phHueSDK = PHHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);               
                    lastSearchWasIPScan=true;
                }
               
            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                Log.e(TAG, "ParsingError : " + parsingError.getMessage());
            }      
        }
    };

    /**
     * Called when option is selected.
     *
     * @return boolean Return false to allow normal menu processing to proceed,  true to consume it here.
     */


    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener !=null) {
            phHueSDK.getNotificationManager().unregisterSDKListener(listener);
        }
        phHueSDK.disableAllHeartbeat();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    public void doBridgeSearch() {
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        // Start the UPNP Searching of local bridges.
        sm.search(true, true);
    }
    

}