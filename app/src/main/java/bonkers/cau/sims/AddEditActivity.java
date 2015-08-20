package bonkers.cau.sims;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class AddEditActivity extends Activity implements OnClickListener {

    private CharSequence phoneName = null, appName = null, mAppName = null;
    private String returnType, mModelName, pressedData[] = new String[2];
    private RelativeLayout mRLMain;
    private ArrayList<ListData> mArrayListData = new ArrayList<ListData>();
    private ArrayList<Motions> mMotions = new ArrayList<Motions>();
    private ArrayList<Buttons> mButtons = new ArrayList<Buttons>();
    private ArrayList<ListData> listDataArrList;
    private ListDBManager dbManager;
    private Button mButtonMain, mButtonCancle, mButtonSave, mButtonIniti;
    private int index, pressedDataNum = 0;
    private int phoneBtnCount = 3, phoneMotionCount = 3;
    private ImageView mIVMain;
    private Bitmap mainBG;

    // requestCode
    private static final int LAUNCHED_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit);

        // �ӽ÷�) �� "A" ����
        mModelName = "A";

        mRLMain = (RelativeLayout) findViewById(R.id.rl_main);


        // Main Imageview initialize
        mIVMain = new ImageView(AddEditActivity.this);
        RelativeLayout.LayoutParams paramIV = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        paramIV.addRule(RelativeLayout.CENTER_HORIZONTAL);
        paramIV.addRule(RelativeLayout.CENTER_VERTICAL);
        mIVMain.setLayoutParams(paramIV);

        // Main Button initialize
        mButtonMain = (Button) findViewById(R.id.btn_main);
        mButtonMain.setOnClickListener(this);
        mButtonCancle = (Button) findViewById(R.id.btn_cancle);
        mButtonCancle.setOnClickListener(this);
        mButtonSave = (Button) findViewById(R.id.btn_save);
        mButtonSave.setOnClickListener(this);
        mButtonIniti = (Button) findViewById(R.id.btn_initi);
        mButtonIniti.setOnClickListener(this);

        phoneSetting();

        Bundle myBundle = this.getIntent().getExtras();
        int position =  myBundle.getInt("selectedPosition");

        // no select => mSelectedPosition = -1
        if(position>-1) editSetting(position);

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
                    returnType = data.getStringExtra("resultType");
                    Log.d("Name", returnType);

                    if(returnType.equals("app")) {
                        appName = data.getStringExtra("resultText");
                        Log.d("Name", appName.toString());

                        for (int i = 0; i < appList.size(); i++) {
                            mAppName = appList.get(i).loadLabel(packagemanager);
                            if (mAppName.equals(appName)) {
                                mButtonMain.setBackground(appList.get(i).loadIcon(packagemanager));
                                index = i;
                                break;
                            }
                        }
                    }else if(returnType.equals("phone")){
                        phoneName = data.getStringExtra("resultText");
                        Log.d("Name", phoneName.toString());
                        mButtonMain.setBackground(getResources().getDrawable(R.mipmap.human));
                        mButtonMain.setText(phoneName.toString());
                    }

                }
        }
    }


    private void showDialog(String text) {

        AlertDialog.Builder alert = new AlertDialog.Builder(AddEditActivity.this);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //�ݱ�
            }
        });

        alert.setMessage(text);
        alert.show();

    }


    // PhoneModel Setting
    private void phoneSetting() {

        if (mModelName == "A") {

            // �� ����,���� ����
            //��ư ����
            //��� ����
            //�� �̹���

            phoneBtnCount = 3;
            phoneMotionCount = 1;

            setLayout();


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

            mMotions.get(0).name = "shake";
//            mMotions.get(0).motioncode = 0;
            mMotions.get(0).iconName = "shake";
            mMotions.get(0).params.addRule(RelativeLayout.ABOVE, R.id.btn_main);
            mMotions.get(0).params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.btn_main);

        } else if (mModelName == "B") {

        } else if (mModelName == "C") {

        } else {
            //no data
        }


        // Set imageview bitmap   &&   Add ImageView
        mainBG = BitmapFactory.decodeResource(getResources(), R.mipmap.pp);
        mIVMain.setImageBitmap(mainBG);
        mRLMain.addView(mIVMain);

        // Add Main Button
        mRLMain.removeView(mButtonMain);
        mRLMain.addView(mButtonMain);

        // Add Buttons
        for (int i = 0; i < mButtons.size(); i++) {
            mButtons.get(i).setLayoutParams(mButtons.get(i).params);
            mRLMain.addView(mButtons.get(i));
        }

        // Add Motions
        for (int i = 0; i < mMotions.size(); i++) {
            mMotions.get(i).setLayoutParams(mMotions.get(i).params);
            mRLMain.addView(mMotions.get(i));
        }


    }

    private void setLayout() {

        //Button id = 1 ~
        for (int i = 0; i < phoneBtnCount; i++) {
            mButtons.add(new Buttons(this));
//            mButtons.get(i).setId(i);
            mButtons.get(i).setText("B" + i + "");
            mButtons.get(i).setOnClickListener(this);
        }

        //Motion  id = 10 ~
        for (int j = 0; j < phoneMotionCount; j++) {
            mMotions.add(new Motions(this));
//            mButton.get(j).setId(j * 10);
            mMotions.get(j).setText("M" + j + "");
            mMotions.get(j).setOnClickListener(this);
        }
    }

    private void editSetting(int position){

        dbManager= new ListDBManager(getApplicationContext());
        listDataArrList =dbManager.selectAll();

        mButtonMain.setBackground(listDataArrList.get(position).getmIcon());

        for (int i=0;i <mButtons.size();i++){
            if(listDataArrList.get(position).getmData1().equals(mButtons.get(i).getText())){
                  mButtons.get(i).onOff=true;
                mButtons.get(i).setBackgroundColor(Color.BLUE);
            }
            if(listDataArrList.get(position).getmData2().equals(mButtons.get(i).getText())){
                mButtons.get(i).onOff=true;
                mButtons.get(i).setBackgroundColor(Color.BLUE);
            }
        }


       // ���� �̸� ���� �ʿ�
       //        appName = "";

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
        if (v == mButtonMain) {
            Intent i = new Intent(AddEditActivity.this, PopupActivity.class);
            i.putExtra("myName", "superdroid");
            startActivityForResult(i, LAUNCHED_ACTIVITY);

            // Cancle Click
        } else if (v == mButtonCancle) {
            //Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
            //startActivity(cancleintent);
            finish();

            // Save Click
        } else if (v == mButtonSave) {
            boolean isRepeated = false;
            if (OnOffTotalCount == 2 && (appName != null || phoneName != null) ) {
                for (int i = 0; i < mButtons.size(); i++) {
                    if (mButtons.get(i).onOff == true) {
                        pressedData[pressedDataNum] = "B" + i;
                        pressedDataNum = 1;
                    }
                }

                dbManager = new ListDBManager(getApplicationContext());
                mArrayListData = dbManager.selectAll();
                //DB �� ����Ʈ�� ���� ���õ� �������� �ߺ��ƴ��� �˻�
                for (ListData mListData : mArrayListData) {
                    if (pressedData[0].equals(mListData.getmData1()) && pressedData[1].equals(mListData.getmData2())) {
                        showDialog("is already exist");
                        isRepeated = true;
                        break;

                    }

                }
                if (!isRepeated) {
                    ListData mListData = new ListData(index, pressedData[0], pressedData[1]);
                    dbManager.insertData(mListData);


                    Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
                    startActivity(cancleintent);
                    finish();
                }
            }
            else
                showDialog("select two button and app");


        } else if (v == mButtonIniti) {
            mButtons.removeAll(mButtons);
            mMotions.removeAll(mMotions);
            mRLMain.removeAllViews();
            appName = null;
            phoneName = null;
            mButtonMain.setBackground(null);
            phoneSetting();

        }


        // Buttons Click
        for (int i = 0; i < mButtons.size(); i++) {
            if (v == mButtons.get(i)) {
                if (mButtons.get(i).onOff == false) {
                    if (OnOffTotalCount < 2) {
                        mButtons.get(i).onOff = true;
                        mButtons.get(i).setBackgroundColor(Color.BLUE);
                    } else {
                        showDialog("select two button and app");
                    }
                } else {
                    mButtons.get(i).onOff = false;
                    mButtons.get(i).setBackgroundColor(Color.LTGRAY);

                }
            }
        }

        // Motions Cliick
        for (int i = 0; i < mMotions.size(); i++) {
            if (v == mMotions.get(i)) {
                if (mMotions.get(i).onOff == false) {
                    mMotions.get(i).onOff = true;
                    mMotions.get(i).setBackgroundColor(Color.CYAN);
                } else {
                    mMotions.get(i).onOff = false;
                    mMotions.get(i).setBackgroundColor(Color.LTGRAY);

                }
            }
        }

    }
}
