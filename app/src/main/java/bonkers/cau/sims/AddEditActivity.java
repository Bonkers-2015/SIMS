package bonkers.cau.sims;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddEditActivity extends Activity implements OnClickListener {

    private CharSequence touchPath = null,touchName, additionName = null, phoneName = null, appName = null, mAppName = null,mAppPackage=null;
    private String phoneNumber, returnType, mModelName, pressedData[] = new String[2];
    private RelativeLayout mRLMain;
    private ArrayList<ListData> mArrayListData = new ArrayList<ListData>();
    private ArrayList<ListData> listDataArrList;
    private ArrayList<Buttons> mButtons = new ArrayList<Buttons>();
    private ListDBManager dbManager;
    private Button mBtnMain, mBtnCancle, mBtnSave, mBtnIniti;
    private TextView mTxtMain,mTxtTouch=null;
    private ImageView mIvMain;
    private int index, pressedDataNum = 0, mEditPosition = -1;
    private int phoneBtnCount = 3, phoneMotionCount = 3;
    PackageManager packagemanager;
    List<ApplicationInfo> appList;
    private Bitmap mainBG;

    // requestCode
    private static final int LAUNCHED_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit);


        Bundle myBundle = this.getIntent().getExtras();
        mEditPosition = myBundle.getInt("selectedPosition");

        // no select => mEditPosition = -1
        if (mEditPosition == -1) {
            setLayout();
            mBtnSave.setText("Save");
        } else {
            // eidt�϶��� save��ư complete�� �ٲ�
            editSetLayout();
            mBtnSave.setText("Edit");
        }
    }

    //PopupActivity �� �����? ���޹ޱ����� overriding�� ��
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LAUNCHED_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    returnType = data.getStringExtra("resultType");

                    if (returnType.equals("app")) {
                        appName = data.getStringExtra("resultText");
                        phoneName = null;
                        additionName = null;
                        mBtnMainSetting();

                    } else if (returnType.equals("phone")) {
                        phoneName = data.getStringExtra("resultText");

                        // phoneName >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");
                        phoneNumber = temp[1];

                        appName = null;
                        additionName = null;
                        mBtnMainSetting();
                    }else if(returnType.equals("addition")){
                        additionName = data.getStringExtra("resultText");
                        appName = null;
                        phoneName = null;
                        mBtnMainSetting();
                    }
                    //��ġ�ΰ��?
                    else if (returnType.equals("touch")){
                        touchName =data.getStringExtra("resultText");
                        touchPath =data.getStringExtra("resultPath");
                        mTxtTouch=(TextView)findViewById(R.id.btn_touch_txt);
                        mButtons.get(2).onOff = true;
                        mButtons.get(2).button.setBackground(getResources().getDrawable(mButtons.get(2).onImage));
                        mTxtTouch.setText(touchName);
                          Toast.makeText(getApplicationContext(), touchName, Toast.LENGTH_LONG).show();
                    }

                }
        }
    }

    private void showDialog(String text) {

        AlertDialog.Builder alert = new AlertDialog.Builder(AddEditActivity.this);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setMessage(text);
        alert.show();
    }

    private void setLayout() {

        mRLMain = (RelativeLayout) findViewById(R.id.rl_main);
        mBtnMain = (Button) findViewById(R.id.btn_main);
        mBtnMain.setOnClickListener(this);
        mTxtMain = (TextView)findViewById(R.id.btn_main_txt);

        mBtnCancle = (Button) findViewById(R.id.btn_cancle);
        mBtnCancle.setOnClickListener(this);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);

        mIvMain = (ImageView) findViewById(R.id.IV_main);
        mIvMain.setOnClickListener(this);


//        mButtonIniti = (Button) findViewById(R.id.btn_initi);
//        mButtonIniti.setOnClickListener(this);

        mButtons.add(0, new Buttons("volume up", "volumeUP", (Button) findViewById(R.id.btn_volume_up)));
        mButtons.get(0).offImage = R.mipmap.volume_up_off;
        mButtons.get(0).onImage = R.mipmap.volume_up_on;
        mButtons.add(1, new Buttons("volume dn", "volumeDown", (Button) findViewById(R.id.btn_volume_down)));
        mButtons.get(1).offImage = R.mipmap.volume_down_off;
        mButtons.get(1).onImage = R.mipmap.volume_down_on;
        mButtons.add(2, new Buttons("", "touch", (Button) findViewById(R.id.btn_touch), (TextView) findViewById(R.id.btn_touch_txt)));
        mButtons.get(2).offImage = R.mipmap.touch_off;
        mButtons.get(2).onImage = R.mipmap.touch_on;
        mButtons.add(3, new Buttons("", "motion", (Button) findViewById(R.id.btn_motion), (TextView) findViewById(R.id.btn_motion_txt)));
        mButtons.get(3).offImage = R.mipmap.motion_off;
        mButtons.get(3).onImage = R.mipmap.motion_on;
        mButtons.add(4, new Buttons("", "earphone", (Button) findViewById(R.id.btn_earphone)));
        mButtons.get(4).offImage = R.mipmap.earphone_off;
        mButtons.get(4).onImage = R.mipmap.earphone_on;

        for (int i = 0; i < mButtons.size(); i++) {
//            mButtons.get(i).button.setText(mButtons.get(i).title);
            mButtons.get(i).button.setOnClickListener(this);
        }
    }

    private void editSetLayout() {

        dbManager = new ListDBManager(getApplicationContext());
        listDataArrList = dbManager.selectAll();

        appName = listDataArrList.get(mEditPosition).getmAppName();
        if (listDataArrList.get(mEditPosition).getmPhoneName() == null)
            phoneName = null;
        else
            phoneName = listDataArrList.get(mEditPosition).getmPhoneName() + " / " + listDataArrList.get(mEditPosition).getmPhoneNumber();

        setLayout();
        mBtnMainSetting();

        // mButtons setting
        for (int i = 0; i < mButtons.size(); i++) {
            if (listDataArrList.get(mEditPosition).getmData1().equals(mButtons.get(i).name)) {
                mButtons.get(i).onOff = true;
                mButtons.get(i).button.setBackground(getResources().getDrawable(mButtons.get(i).onImage));

                if (mButtons.get(i).textView != null) {
                    mButtons.get(i).textView.setText("CHECK");
                }
            }
            if (listDataArrList.get(mEditPosition).getmData2().equals(mButtons.get(i).name)) {
                mButtons.get(i).onOff = true;
                mButtons.get(i).button.setBackground(getResources().getDrawable(mButtons.get(i).onImage));

                if (mButtons.get(i).textView != null) {
                    mButtons.get(i).textView.setText("CHECK");
                }
            }
        }
    }

    private void mBtnMainSetting() {

        mBtnMain.setBackgroundColor(Color.WHITE);
        mTxtMain.setText("");
        mBtnMain.setText("");

        // mButtonMain setting
        if (appName != null) {
            //���� �����? �ҷ���
            packagemanager = this.getPackageManager();
            appList = packagemanager.getInstalledApplications(0);

            for (int i = 0; i < appList.size(); i++) {
                mAppName = appList.get(i).loadLabel(packagemanager);
                if (mAppName.equals(appName)) {
                    mBtnMain.setBackground(appList.get(i).loadIcon(packagemanager));
                    mTxtMain.setText(appName);
                    mAppPackage=appList.get(i).packageName;
                    index = i;
                    break;
                }
            }

        } else if (phoneName != null) {
            mBtnMain.setBackground(getResources().getDrawable(R.mipmap.human));
            mBtnMain.setText(phoneName.toString());

        }else if(additionName != null){
            mBtnMain.setBackground(getResources().getDrawable(R.mipmap.addition));
            mTxtMain.setText(additionName.toString());

        }
    }

    // CharSequence to split.
    private String[] split(CharSequence cs, String delimiter) {

        String[] temp;
        // given string will be split by the argument delimiter provided.
        temp = cs.toString().split(delimiter);

        return temp;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
//                case KeyEvent.KEYCODE_BACK:
                    // �ܸ����� BACK��ư
//                    return true;
                case KeyEvent.KEYCODE_MENU:
                    // �ܸ����� �޴���ư

                    // 150804 Kim Gwang Min : Setting Button Event
                    Intent intentSetting = new Intent(AddEditActivity.this, SettingActivity.class);
                    startActivity(intentSetting);
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        int OnOffTotalCount = 0;

        // Button OnOff Total Count
        for (int i = 0; i < mButtons.size(); i++) {
            if (mButtons.get(i).onOff == true) {
                OnOffTotalCount++;
            }
        }

        // Main Click
        if (v == mIvMain || v == mBtnMain) {
            Intent i = new Intent(AddEditActivity.this, PopupActivity.class);
            startActivityForResult(i, LAUNCHED_ACTIVITY);

            // Cancle Click
        } else if (v == mBtnCancle) {
            Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
            startActivity(cancleintent);
            finish();

            // Save Click
        } else if (v == mBtnSave) {
            boolean isRepeated = false;
            if (OnOffTotalCount == 2 && (appName != null || phoneName != null)) {
                for (int i = 0; i < mButtons.size(); i++) {
                        if (mButtons.get(i).onOff == true) {
                        pressedData[pressedDataNum] = mButtons.get(i).name;
                        pressedDataNum = 1;
                    }
                }

                dbManager = new ListDBManager(getApplicationContext());
                mArrayListData = dbManager.selectAll();


                //add
                if (mEditPosition == -1) {
                    for (ListData mListData : mArrayListData) {
                        //DB �� ����Ʈ�� ���� ���õ� �������� �ߺ��ƴ��� �˻�
                        if (pressedData[0].equals(mListData.getmData1()) && pressedData[1].equals(mListData.getmData2())) {
                            isRepeated = true;
                            showDialog("is already exist");
                            return;
                        }
                    }
                    if (appName != null) {
                        if(pressedData[1].equals("touch")) {
                            ListData listAppData = new ListData(index, pressedData[0], touchPath.toString(), appName.toString(), mAppPackage.toString(), null, null);
                            dbManager.insertAppData(listAppData);
                        }
                        else if(pressedData[1].equals("motion")){
                            ListData listAppData = new ListData(index, pressedData[0], "motion", appName.toString(), mAppPackage.toString(), null, null);
                            dbManager.insertAppData(listAppData);

                        }
                    } else {
                        // phoneName >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");
                        ListData listPhoneData = new ListData(index, pressedData[0], pressedData[1], null,null, temp[0], temp[1]);

                        dbManager.insertPhoneData(listPhoneData);
                    }

                    //edit
                } else {
                    for (ListData mListData : mArrayListData) {
                        //DB �� ����Ʈ�� ���� ���õ� �������� �ߺ��ƴ��� �˻�
                        if (pressedData[0].equals(mListData.getmData1()) && pressedData[1].equals(mListData.getmData2())) {

                            //main�� ������ ���ϴ� ���? ����
                            if (pressedData[0].equals(mArrayListData.get(mEditPosition).getmData1()) && pressedData[1].equals(mArrayListData.get(mEditPosition).getmData2())) {
                                continue;
                            } else {
                                isRepeated = true;
                                showDialog("is already exist");
                                return;
                            }
                        }
                    }

                    if (appName != null) {
                        ListData listAppData = new ListData(index, pressedData[0], pressedData[1], appName.toString(),mAppPackage.toString(), null, null);
                        dbManager.updateAppData(listAppData, mArrayListData.get(mEditPosition).getId());

                    } else {
                        //  >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");
                        ListData listPhoneData = new ListData(index, pressedData[0], pressedData[1], null,null, temp[0], temp[1]);

                        dbManager.updatePhoneData(listPhoneData, mArrayListData.get(mEditPosition).getId());
                    }
                }
                Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
                startActivity(cancleintent);
                finish();
            } else
                showDialog("select two button and app");

//        } else if (v == mButtonIniti) {
//            mButtons.removeAll(mButtons);
//            mRLMain.removeAllViews();
//            appName = null;
//            phoneName = null;
//            mButtonMain.setBackground(null);
//            mButtonMain.setText("MAIN");
//            phoneSetting();

        }

        //touch��ư ��������
        if (v == mButtons.get(2).button) {
            Intent touchIntent = new Intent(AddEditActivity.this, PopupTouchActivity.class);
            startActivityForResult(touchIntent, LAUNCHED_ACTIVITY);
        }
        // Buttons Click
        for (int i = 0; i < mButtons.size(); i++) {
            if (v == mButtons.get(i).button) {
                if (mButtons.get(i).onOff == false) {
                    if (OnOffTotalCount < 2) {
                        mButtons.get(i).onOff = true;
                        mButtons.get(i).button.setBackground(getResources().getDrawable(mButtons.get(i).onImage));

                        if (mButtons.get(i).textView != null) {
                            mButtons.get(i).textView.setText("CHECK");
                        }

                    } else {
                        showDialog("select two button and app");
                    }
                } else {
                    mButtons.get(i).onOff = false;
                    mButtons.get(i).button.setBackground(getResources().getDrawable(mButtons.get(i).offImage));

                    if (mButtons.get(i).textView != null) {
                        mButtons.get(i).textView.setText("");
                    }
                }
            }
        }

    }
}
