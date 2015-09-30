package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;

import bonkers.cau.sims.R;

// KGM 150809
public class SettingActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        // 150806 KGM, Checkbox
        CheckBox checkUseCall = (CheckBox) findViewById(R.id.checkUseCall);
        CheckBox checkUseEarphone = (CheckBox) findViewById(R.id.checkUseEarphone);

        Boolean isCallChecked = pref.getBoolean("checkUseCall", false);
        Boolean isEarphoneChecked = pref.getBoolean("checkUseEarphone", false);

        checkUseCall.setChecked(isCallChecked);
        checkUseEarphone.setChecked(isEarphoneChecked);

        // "통화중일 때 기능 비활성화"에 체크 되어있으면
        if (checkUseCall.isChecked()) {
            // 150806 KGM, Telephon manager 통화중인지 확인
            // TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    @Override
    // App 화면에서 사라질때
    protected void onStop() {
        super.onStop();

        // UI 상태 저장
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        // Editor 불러오기
        SharedPreferences.Editor editor = pref.edit();
        CheckBox checkUseCall = (CheckBox) findViewById(R.id.checkUseCall);
        CheckBox checkUseEarphone = (CheckBox) findViewById(R.id.checkUseEarphone);

        editor.putBoolean("checkUseCall", checkUseCall.isChecked());
        editor.putBoolean("checkUseEarphone", checkUseEarphone.isChecked());

        editor.commit();
    }

    /*
    // 폰 상태 리스터
    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        // TelephonyManager.CALL_STATE_RINGING : 벨소리 울리는 중
        // TelephonyManager.CALL_STATE_OFFHOOK : 통화 시작
        // TelephonyManager.CALL_STATE_IDLE : 통화 끝, 벨소리 울리는 중에 통화 거절

        public void onCallStateChanged(int state, String incomingNumber) {
            // 통화시작
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                // 어플 비활성화
            }
            // 통화종료
            else if (state == TelephonyManager.CALL_STATE_IDLE) {
                // 어플 활성화
            }
        };
    };
    */
}