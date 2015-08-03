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


public class AddEditActivity extends Activity implements OnClickListener{

    private int phoneBtnCount = 3, phoneMotionCount = 3;


    private int index;
    private String mModelName;
    private CharSequence appName;
    private String pressedData[] = new String[2];
    private int pressedDataNum = 0;
    private RelativeLayout mRelativeLayout;
    private ArrayList<Button> mMotion = new ArrayList<Button>();
    private ArrayList<RelativeLayout.LayoutParams> mMotionParam = new ArrayList<RelativeLayout.LayoutParams>();
    private ListDBManager dbManager;
    private Button mButtonMain,mButtonCancle, mButtonSave;



    private ArrayList<Buttons> mButtons = new ArrayList<Buttons>();


    // requestCode
    private static final int LAUNCHED_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // (임시로) 모델 "A" 전송
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

    //PopupActivity 의 결과를 전달받기위해 overriding을 함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //어플 목록을 불러옴
        PackageManager packagemanager = this.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

        switch (requestCode) {
            case LAUNCHED_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    appName = data.getStringExtra("resultText");


                    for (int i = 0; i < appList.size(); i++) {
                        CharSequence test = appList.get(i).loadLabel(packagemanager);
                        if (test.equals(appName)) {
                            mButtonMain.setBackground(appList.get(i).loadIcon(packagemanager));
                            index = i;
                            break;
                        }
                    }

                }
        }
    }

    // Don't press btn more than 2
    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(AddEditActivity.this);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
            }
        });
        alert.setMessage("Don't press btn more than 2");
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

            // 폰 가로,세로 비율
            //버튼 갯수
            //모션 갯수
            //폰 이미지

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
            //데이터가 없습니다.
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

        // Main Click
        if(v==mButtonMain){
            Intent i = new Intent(AddEditActivity.this, PopupActivity.class);
                i.putExtra("myName", "superdroid");
                startActivityForResult(i, LAUNCHED_ACTIVITY);

        // Cancle Click
        }else if(v==mButtonCancle){
            Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
            startActivity(cancleintent);
            finish();

        // Save Click
        }else if(v==mButtonSave){
            for (int i = 0; i < mButtons.size(); i++) {
                    if (mButtons.get(i).onOff == true) {
                        pressedData[pressedDataNum] = "data"+i;
                        pressedDataNum=1;
                    }
                }
                 dbManager= new ListDBManager(getApplicationContext());
                ListData mListData = new ListData(index, pressedData[0], pressedData[1]);
                dbManager.insertData(mListData);
        }

        // Button OnOff Total Count
        for(int i=0; i<mButtons.size();i++){
            if (mButtons.get(i).onOff == true) {
                OnOffTotalCount++;
            }
        }

        for(int i=0; i<mButtons.size();i++){
            if(v == mButtons.get(i)){
                if (mButtons.get(i).onOff == false) {
                    if (OnOffTotalCount<2) {
                        mButtons.get(i).onOff = true;
                        mButtons.get(i).setBackgroundColor(Color.BLUE);
                    } else
                        showDialog();

                } else {
                    mButtons.get(i).onOff = false;
                    mButtons.get(i).setBackgroundColor(Color.LTGRAY);

                }
            }
        }
    }
}
