package bonkers.cau.sims;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 150806 KGM, the telephon manager 통화중 상태 확인
        TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private PhoneStateListener phoneStateListener = new PhoneStateListener()
    {
        // TelephonyManager.CALL_STATE_RINGING : 벨소리 울리는 중
        // TelephonyManager.CALL_STATE_OFFHOOK : 통화 시작
        // TelephonyManager.CALL_STATE_IDLE : 통화 끝, 벨소리 울리는 중에 통화 거절

        public void onCallStateChanged(int state, String incomingNumber)
        {

        };
    };
}
