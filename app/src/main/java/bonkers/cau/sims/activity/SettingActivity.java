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

        // "��ȭ���� �� ��� ��Ȱ��ȭ"�� üũ �Ǿ�������
        if (checkUseCall.isChecked()) {
            // 150806 KGM, Telephon manager ��ȭ������ Ȯ��
            // TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    @Override
    // App ȭ�鿡�� �������
    protected void onStop() {
        super.onStop();

        // UI ���� ����
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        // Editor �ҷ�����
        SharedPreferences.Editor editor = pref.edit();
        CheckBox checkUseCall = (CheckBox) findViewById(R.id.checkUseCall);
        CheckBox checkUseEarphone = (CheckBox) findViewById(R.id.checkUseEarphone);

        editor.putBoolean("checkUseCall", checkUseCall.isChecked());
        editor.putBoolean("checkUseEarphone", checkUseEarphone.isChecked());

        editor.commit();
    }

    /*
    // �� ���� ������
    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        // TelephonyManager.CALL_STATE_RINGING : ���Ҹ� �︮�� ��
        // TelephonyManager.CALL_STATE_OFFHOOK : ��ȭ ����
        // TelephonyManager.CALL_STATE_IDLE : ��ȭ ��, ���Ҹ� �︮�� �߿� ��ȭ ����

        public void onCallStateChanged(int state, String incomingNumber) {
            // ��ȭ����
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                // ���� ��Ȱ��ȭ
            }
            // ��ȭ����
            else if (state == TelephonyManager.CALL_STATE_IDLE) {
                // ���� Ȱ��ȭ
            }
        };
    };
    */
}