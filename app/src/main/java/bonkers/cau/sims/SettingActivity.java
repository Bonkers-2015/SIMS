package bonkers.cau.sims;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 150806 KGM, Power manager �ڵ��� ���� �ִ��� Ȯ��
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        // ����������
        if(isScreenOn) {

        }

        // ����������
        else {

        }

        // 150806 KGM, Telephon manager ��ȭ������ Ȯ��
        TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        teleManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private PhoneStateListener phoneStateListener = new PhoneStateListener()
    {
        // TelephonyManager.CALL_STATE_RINGING : ���Ҹ� �︮�� ��
        // TelephonyManager.CALL_STATE_OFFHOOK : ��ȭ ����
        // TelephonyManager.CALL_STATE_IDLE : ��ȭ ��, ���Ҹ� �︮�� �߿� ��ȭ ����

        public void onCallStateChanged(int state, String incomingNumber)
        {
            // ��ȭ����
            if(state == TelephonyManager.CALL_STATE_RINGING) {

            }
            // ��ȭ����
            else if(state == TelephonyManager.CALL_STATE_IDLE) {

            }
        };
    };
}
