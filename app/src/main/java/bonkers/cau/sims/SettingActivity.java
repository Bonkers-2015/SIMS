package bonkers.cau.sims;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.CheckBox;


public class SettingActivity extends ActionBarActivity {

    CheckBox checkActive;
    CheckBox checkVideo;
    CheckBox checkCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 150806 KGM, Checkbox
        checkActive = (CheckBox) findViewById(R.id.checkActive);
        checkVideo = (CheckBox) findViewById(R.id.checkVideo);
        checkCall = (CheckBox) findViewById(R.id.checkCall);

        // "핸드폰 켜 있을때 기능 비활성화"에 체크 되어있으면
        if (checkActive.isChecked()) {

            // 150806 KGM, Power manager 핸드폰 켜져 있는지 확인
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();

            // 켜져있을때
            if (isScreenOn) {
                // 어플 비활성화
            }
            // 꺼져있을때
            else {
                // 어플 활성화
            }
        }

        // "동영상 볼 때 기능 비활성화"에 체크 되어있으면
        if (checkVideo.isChecked()) {

        }

        // "통화중일 때 기능 비활성화"에 체크 되어있으면
        if (checkCall.isChecked()) {
            // 150806 KGM, Telephon manager 통화중인지 확인
            TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    // 폰 상태 리스터
    private PhoneStateListener phoneStateListener = new PhoneStateListener()
    {
        // TelephonyManager.CALL_STATE_RINGING : 벨소리 울리는 중
        // TelephonyManager.CALL_STATE_OFFHOOK : 통화 시작
        // TelephonyManager.CALL_STATE_IDLE : 통화 끝, 벨소리 울리는 중에 통화 거절

        public void onCallStateChanged(int state, String incomingNumber)
        {
            // 통화시작
            if(state == TelephonyManager.CALL_STATE_RINGING) {
                // 어플 비활성화
            }
            // 통화종료
            else if(state == TelephonyManager.CALL_STATE_IDLE) {
                // 어플 활성화
            }
        };
    };
}
