package bonkers.cau.sims;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class AddEditActivity extends Activity implements OnClickListener {

    private int phoneBtnCount = 3, phoneMotionCount = 3;


    private int index;
    private String mModelName;
    private CharSequence appName = null;
    private CharSequence mAppName = null;
    private String pressedData[] = new String[2];
    private int pressedDataNum = 0;
    private RelativeLayout mRelativeLayout;
    private ArrayList<ListData> mArrayListData = new ArrayList<ListData>();
    private ArrayList<Button> mMotion = new ArrayList<Button>();
    private ArrayList<RelativeLayout.LayoutParams> mMotionParam = new ArrayList<RelativeLayout.LayoutParams>();
    private ListDBManager dbManager;
    private Button mButtonMain, mButtonCancle, mButtonSave;
    private int errorCheck=0;


    private ArrayList<Buttons> mButtons = new ArrayList<Buttons>();


    // requestCode
    private static final int LAUNCHED_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // �ӽ÷�) �� "A" ����
        mModelName = "A";
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_main);

        mButtonMain = (Button) findViewById(R.id.btn_main);
        mButtonMain.setOnClickListener(this);
        mButtonCancle = (Button) findViewById(R.id.btn_cancle);
        mButtonCancle.setOnClickListener(this);
        mButtonSave = (Button) findViewById(R.id.btn_save);
        mButtonSave.setOnClickListener(this);

        setLayout();
        phoneSetting();

    }

    //PopupActivity �� ����� ���޹ޱ����� overriding�� ��
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //���� ����� �ҷ���
        PackageManager packagemanager = this.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

        switch (requestCode) {
            case LAUNCHED_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    appName = data.getStringExtra("resultText");


                    for (int i = 0; i < appList.size(); i++) {
                        mAppName = appList.get(i).loadLabel(packagemanager);
                        if (mAppName.equals(appName)) {
                            mButtonMain.setBackground(appList.get(i).loadIcon(packagemanager));
                            index = i;
                            break;
                        }
                    }

                }
        }
    }


    private void showDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(AddEditActivity.this);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //�ݱ�
            }
        });

        // errorCheck 0 -> No ERROR
        // errorCheck 1 -> "Don't press btn more than 2 !"
        // errorCheck 2 -> "Only two btn and one App !"

        if(errorCheck == 1) {
            alert.setMessage("Don't press btn more than 2 !");
        }else if (errorCheck == 2) {
            alert.setMessage("Only two btn and one App ! ");
        }

        alert.show();

    }

    private void setLayout() {

        //Button id = 1 ~
        for (int i = 0; i < phoneBtnCount; i++) {
            mButtons.add(new Buttons(this));
//            mButtons.get(i).setId(i);
            mButtons.get(i).setText("" + i + "");
            mButtons.get(i).setOnClickListener(this);

//        //Motion button id = 10 ~
//        for (int j = 0; j < phoneMotionCount; j++) {
//            mMotion.add(new Button(this));
//            mButton.get(j).setId(j * 10);
//            mMotionParam.add(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }


    // PhoneModel Setting
    private void phoneSetting() {

        if (mModelName == "A") {

            // �� ����,���� ����
            //��ư ����
            //��� ����
            //�� �̹���

            phoneBtnCount = 3;
//            phoneMotionCount = 2;

            mButtons.get(0).name = "UP";
            mButtons.get(0).keycode = KeyEvent.KEYCODE_VOLUME_UP;
            mButtons.get(0).iconName = "volume_up";
//            mButtons.get(0).setBackgroundResource(R.mipmap.volume_up);


            mButtons.get(0).params.addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
            mButtons.get(0).params.addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);
            mButtons.get(1).params.addRule(RelativeLayout.RIGHT_OF, R.id.btn_main);
            mButtons.get(1).params.addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);
            mButtons.get(2).params.addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
            mButtons.get(2).params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.btn_main);

        } else if (mModelName == "B") {

        } else if (mModelName == "C") {

        } else {
            //�����Ͱ� �����ϴ�.
        }

        // Add Button
        for (int i = 0; i < mButtons.size(); i++) {
            mButtons.get(i).setLayoutParams(mButtons.get(i).params);
            mRelativeLayout.addView(mButtons.get(i));
        }
    }

    @Override
    public void onClick(View v) {
        int OnOffTotalCount=0;

        // Button OnOff Total Count
        for (int i = 0; i < mButtons.size(); i++) {
            if (mButtons.get(i).onOff == true) {
                OnOffTotalCount++;
            }
        }

        // Main Click
        if (v == mButtonMain) {
            Intent i = new Intent(AddEditActivity.this, PopupActivity.class);
            i.putExtra("myName", "superdroid");
            startActivityForResult(i, LAUNCHED_ACTIVITY);

            // Cancle Click
        } else if (v == mButtonCancle) {
            Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
            startActivity(cancleintent);
            errorCheck = 0;
            finish();

            // Save Click
        } else if (v == mButtonSave) {
            boolean isRepeated = false;
            if (OnOffTotalCount == 2 && appName != null) {
                for (int i = 0; i < mButtons.size(); i++) {
                    if (mButtons.get(i).onOff == true) {
                        pressedData[pressedDataNum] = "data" + i;
                        pressedDataNum = 1;
                    }
                }

                dbManager = new ListDBManager(getApplicationContext());
                mArrayListData = dbManager.selectAll();
                for (ListData mListData : mArrayListData) {
                    if (pressedData[0] == mListData.getmData1() && pressedData[1] == mListData.getmData2()) {
                        errorCheck=2;
                        showDialog();
                        isRepeated = true;

                    }

                }
                if (!isRepeated) {
                    ListData mListData = new ListData(index, pressedData[0], pressedData[1]);
                    dbManager.insertData(mListData);
                }

                Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
                startActivity(cancleintent);
                errorCheck = 0;
                finish();
            }
            else
                errorCheck=2;
                showDialog();


        }



        for (int i = 0; i < mButtons.size(); i++) {
            if (v == mButtons.get(i)) {
                if (mButtons.get(i).onOff == false) {
                    if (OnOffTotalCount < 2) {
                        mButtons.get(i).onOff = true;
                        mButtons.get(i).setBackgroundColor(Color.BLUE);
                    } else {
                        errorCheck=1;
                        showDialog();
                    }
                } else {
                    mButtons.get(i).onOff = false;
                    mButtons.get(i).setBackgroundColor(Color.LTGRAY);

                }
            }
        }
    }
}
