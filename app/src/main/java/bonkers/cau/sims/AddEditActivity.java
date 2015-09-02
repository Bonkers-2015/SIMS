package bonkers.cau.sims;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private ArrayList<Buttons> mButtons = new ArrayList<Buttons>();


    private ArrayList<ListData> listDataArrList;
    private ListDBManager dbManager;
    private Button mButtonMain, mButtonCancle, mButtonSave, mButtonIniti;
    private int index, pressedDataNum = 0 , mEditPosition=-1;
    private int phoneBtnCount = 3, phoneMotionCount = 3;
    private ImageView mIVMain;
    private Bitmap mainBG;

    // requestCode
    private static final int LAUNCHED_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit);

        // 임시로) 모델 "A" 전송
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
//        mButtonIniti = (Button) findViewById(R.id.btn_initi);
//        mButtonIniti.setOnClickListener(this);


        Bundle myBundle = this.getIntent().getExtras();
        mEditPosition =  myBundle.getInt("selectedPosition");

        // no select => mEditPosition = -1
        if(mEditPosition==-1) {
            phoneSetting();
            mButtonSave.setText("Save");
        }else{
            // eidt일때만 save버튼 complete로 바꿈
            editSetting();
            mButtonSave.setText("Complete");
        }

    }

    //PopupActivity 의 결과를 전달받기위해 overriding을 함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LAUNCHED_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    returnType = data.getStringExtra("resultType");

                    if(returnType.equals("app")) {
                        appName = data.getStringExtra("resultText");
                        phoneName = null;
                        mButtonMainSetting();

                    }else if(returnType.equals("phone")){
                        phoneName = data.getStringExtra("resultText");
                        appName = null;
                        mButtonMainSetting();
                    }

                }
        }
    }

    private void showDialog(String text) {

        AlertDialog.Builder alert = new AlertDialog.Builder(AddEditActivity.this);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
            }
        });

        alert.setMessage(text);
        alert.show();
    }

    // PhoneModel Setting
    private void phoneSetting() {

        if (mModelName == "A") {

            // volume_up, volume_down, motion, earphone, touch, null
            phoneBtnCount = 6;

            setLayout();

            mButtons.get(0).name = "VolumeUP";
            mButtons.get(0).offImage = R.mipmap.volume_up_off;
            mButtons.get(0).params.addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
            mButtons.get(0).params.addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);

            mButtons.get(1).name = "VolumeDown";
            mButtons.get(1).offImage = R.mipmap.volume_down_off;
            mButtons.get(1).params.addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
            mButtons.get(1).params.addRule(RelativeLayout.CENTER_VERTICAL, R.id.btn_main);

            mButtons.get(2).name = "Motion";
            mButtons.get(2).offImage = R.mipmap.motion_off;
            mButtons.get(2).params.addRule(RelativeLayout.RIGHT_OF, R.id.btn_main);
            mButtons.get(2).params.addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);

            mButtons.get(3).name = "Earphone";
            mButtons.get(3).offImage = R.mipmap.earphone_off;
            mButtons.get(3).params.addRule(RelativeLayout.ABOVE, R.id.btn_main);
            mButtons.get(3).params.addRule(RelativeLayout.ALIGN_LEFT, R.id.btn_main);

            mButtons.get(4).name = "Touch";
            mButtons.get(4).offImage = R.mipmap.touch_off;
            mButtons.get(4).params.addRule(RelativeLayout.BELOW, R.id.btn_main);
            mButtons.get(4).params.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.btn_main);

            mButtons.get(5).name = "NULL";
            mButtons.get(5).offImage = R.mipmap.null_off;
            mButtons.get(5).params.addRule(RelativeLayout.RIGHT_OF, R.id.btn_main);
            mButtons.get(5).params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.btn_main);


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
            mButtons.get(i).setBackground(getResources().getDrawable(mButtons.get(i).offImage));
//            mButtons.get(i).setText(mButtons.get(i).name);
            mRLMain.addView(mButtons.get(i));
        }

    }

    private void setLayout() {

        for (int i = 0; i < phoneBtnCount; i++) {
            mButtons.add(new Buttons(this));
            mButtons.get(i).setText(mButtons.get(i).name);
            mButtons.get(i).setOnClickListener(this);
        }
    }

    private void editSetting(){

        dbManager= new ListDBManager(getApplicationContext());
        listDataArrList =dbManager.selectAll();

        appName = listDataArrList.get(mEditPosition).getmAppName();
        if(listDataArrList.get(mEditPosition).getmPhoneName() == null)
            phoneName = null;
        else
            phoneName = listDataArrList.get(mEditPosition).getmPhoneName()+" / "+listDataArrList.get(mEditPosition).getmPhoneNumber() ;

        mButtonMainSetting();
        phoneSetting();

            // mButtons setting
        for (int i=0;i <mButtons.size();i++){
            if(listDataArrList.get(mEditPosition).getmData1().equals(mButtons.get(i).name)){
                mButtons.get(i).onOff=true;
                mButtons.get(i).setBackground(getResources().getDrawable(mButtons.get(i).onImage));
            }
            if(listDataArrList.get(mEditPosition).getmData2().equals(mButtons.get(i).name)){
                mButtons.get(i).onOff=true;
                mButtons.get(i).setBackground(getResources().getDrawable(mButtons.get(i).onImage));
            }
            mButtons.get(i).setEnabled(false);
        }


    }

    private  void mButtonMainSetting(){

        // mButtonMain setting
        if(appName != null) {
            //어플 목록을 불러옴
            PackageManager packagemanager = this.getPackageManager();
            List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

            for (int i = 0; i < appList.size(); i++) {
                mAppName = appList.get(i).loadLabel(packagemanager);
                if (mAppName.equals(appName)) {
                    mButtonMain.setBackground(appList.get(i).loadIcon(packagemanager));
                    mButtonMain.setText(appName);
                    index = i;
                    break;
                }
            }

        }else if(phoneName != null) {
            mButtonMain.setBackground(getResources().getDrawable(R.mipmap.human));
            mButtonMain.setText(phoneName.toString());
        }
    }

    // CharSequence to split.
    private String[] split(CharSequence cs, String delimiter){

        String[] temp;
        // given string will be split by the argument delimiter provided.
        temp = cs.toString().split(delimiter);

        return temp;
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
            Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
            startActivity(cancleintent);
            finish();

            // Save Click
        } else if (v == mButtonSave) {
            boolean isRepeated = false;
            if (OnOffTotalCount == 2 && (appName != null || phoneName != null) ) {
                for (int i = 0; i < mButtons.size(); i++) {
                    if (mButtons.get(i).onOff == true) {
                        pressedData[pressedDataNum] = mButtons.get(i).name;
                        pressedDataNum = 1;
                    }
                }

                dbManager = new ListDBManager(getApplicationContext());
                mArrayListData = dbManager.selectAll();


                //add
                if(mEditPosition ==-1){
                    for (ListData mListData : mArrayListData) {
                        //DB 의 리스트와 현재 선택된 아이탬이 중복됐는지 검사
                        if (pressedData[0].equals(mListData.getmData1()) && pressedData[1].equals(mListData.getmData2())) {
                            isRepeated = true;
                            showDialog("is already exist");
                            return;
                        }
                    }
                    if(appName!=null){
                        ListData listAppData = new ListData(index, pressedData[0], pressedData[1], appName.toString(), null, null);
                        dbManager.insertAppData(listAppData);
                    }else{
                        // phoneName >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");
                        ListData listPhoneData = new ListData(index, pressedData[0], pressedData[1], null, temp[0], temp[1]);

                        dbManager.insertPhoneData(listPhoneData);
                    }

                //edit
                }else{
                    if(appName!=null){
                        ListData listAppData = new ListData(index, pressedData[0], pressedData[1], appName.toString(), null, null);
                        dbManager.updateAppData(listAppData, mArrayListData.get(mEditPosition).getId());

                    }else{
                        //  >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");
                        ListData listPhoneData = new ListData(index, pressedData[0], pressedData[1], null, temp[0], temp[1]);

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

        // Buttons Click
        for (int i = 0; i < mButtons.size(); i++) {
            if (v == mButtons.get(i)) {
                if (mButtons.get(i).onOff == false) {
                    if (OnOffTotalCount < 2) {
                        mButtons.get(i).onOff = true;
                        mButtons.get(i).setBackground(getResources().getDrawable( mButtons.get(i).onImage));
                    } else {
                        showDialog("select two button and app");
                    }
                } else {
                    mButtons.get(i).onOff = false;
                    mButtons.get(i).setBackground(getResources().getDrawable( mButtons.get(i).offImage));

                }
            }
        }

    }
}
