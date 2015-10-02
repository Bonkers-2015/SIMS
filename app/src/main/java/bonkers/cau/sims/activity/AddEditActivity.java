package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bonkers.cau.sims.AdditionFunctions;
import bonkers.cau.sims.Buttons;
import bonkers.cau.sims.MyTextView;
import bonkers.cau.sims.R;
import bonkers.cau.sims.database.AddEditListData;
import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;


public class AddEditActivity extends Activity implements OnClickListener {

    private static final int LAUNCHED_ACTIVITY = 1;
    private static final int VOLUME_UP=0,VOLUME_DOWN=1,SHAKE=2,PLUGIN=3,TOUCH=4;
    private static final int MAIN_APP=0,MAIN_PHONE=1,MAIN_IOT=2,MAIN_ADDITION =3;
    private boolean editState = true;

    private CharSequence additionName = null, phoneName = null, appName = null, mAppName = null,mAppPackage=null;
    private String phoneNumber, returnType, mModelName, pressedData[] = new String[2];
    private ArrayList<ListData> mArrayListData = new ArrayList<ListData>();
    private ArrayList<ListData> listDataArrList;
    private ArrayList<Buttons> mButtons = new ArrayList<Buttons>();
    private ListDBManager dbManager;
    private Button  mBtnCancle, mBtnSave;
    private int index, pressedDataNum = 0, mEditPosition = -1;

    ListViewAdapter mAdapter;
    ListView mListView;

    AdditionFunctions additionFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        additionFunctions = new AdditionFunctions();

        mEditPosition = getIntent().getIntExtra("selectedPosition",-1);

        // no select => editState = false
        if(mEditPosition == -1)
            editState = false;

        listviewiniti(this);

        MyTextView mTxtSave = (MyTextView)findViewById(R.id.txt_save);

        if (editState == false) {
            setLayout();
            mTxtSave.setText("save");
        } else {
            // eidt�϶��� save��ư complete�� �ٲ�
            editSetLayout();
            mTxtSave.setText("edit");
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                //Extra�� Intent�� ��Ҵ�.

                Intent intent = new Intent();
                AddEditListData mData = mAdapter.mAddEditListdata.get(position);

                if (position == MAIN_APP) {
                    intent = new Intent(AddEditActivity.this, PopupAppActivity.class);
                    startActivityForResult(intent, LAUNCHED_ACTIVITY);

                } else if (position == MAIN_PHONE) {
                    intent = new Intent(AddEditActivity.this, PopupPhoneActivity.class);
                    startActivityForResult(intent, LAUNCHED_ACTIVITY);

                } else if (position == MAIN_IOT) {
                    additionName = "iot";
                    appName = null;
                    phoneName = null;
                    listviewSetting(position);
                } else {
                    for (int i = 0; i < additionFunctions.nameList.size(); i++) {
                        if (position == i + MAIN_ADDITION) {
                            additionName = additionFunctions.nameList.get(i);
                            appName = null;
                            phoneName = null;
                        }
                    }

                    listviewSetting(position);
                }

                mAdapter.notifyDataSetChanged();

            }
        });
    }

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
                        listviewSetting(MAIN_APP);
                    } else if (returnType.equals("phone")) {
                        phoneName = data.getStringExtra("resultText");
                        appName = null;
                        additionName = null;
                        listviewSetting(MAIN_PHONE);
                    } else if (returnType.equals("touch")){
                        CharSequence touchPath, touchName;
                        touchPath = data.getStringExtra("resultText");
                        touchName = "touch : "+touchPath;

                        mButtons.get(SHAKE).setOnOFF(false);
                        mButtons.get(PLUGIN).setOnOFF(false);
                        mButtons.get(TOUCH).setOnOFF(true);
                        mButtons.get(TOUCH).setName(touchName.toString());

                          Toast.makeText(getApplicationContext(), touchName.toString(), Toast.LENGTH_SHORT).show();
                    } else if(returnType.equals("iot")){
//                            additionName = "iot";
//                            appName = null;
//                            phoneName = null;
//                            listviewSetting(MAIN_IOT);
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

        mBtnCancle = (Button) findViewById(R.id.btn_cancle);
        mBtnCancle.setOnClickListener(this);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);

        mButtons.add(VOLUME_UP, new Buttons("volume up", (Button) findViewById(R.id.btn_volume_up)));
        mButtons.get(VOLUME_UP).setImage(getResources().getDrawable(R.mipmap.volume_up_on), getResources().getDrawable(R.mipmap.volume_up_off));
        mButtons.add(VOLUME_DOWN, new Buttons("volume down", (Button) findViewById(R.id.btn_volume_down)));
        mButtons.get(VOLUME_DOWN).setImage(getResources().getDrawable(R.mipmap.volume_down_on), getResources().getDrawable(R.mipmap.volume_down_off));
        mButtons.add(SHAKE, new Buttons("shake", (Button) findViewById(R.id.btn_shake)));
        mButtons.get(SHAKE).setImage(getResources().getDrawable(R.mipmap.shake_on), getResources().getDrawable(R.mipmap.shake_off));
        mButtons.add(PLUGIN, new Buttons("plug in", (Button) findViewById(R.id.btn_plugin)));
        mButtons.get(PLUGIN).setImage(getResources().getDrawable(R.mipmap.plugin_on), getResources().getDrawable(R.mipmap.plugin_off));
        mButtons.add(TOUCH, new Buttons("touch", (Button) findViewById(R.id.btn_touch)));
        mButtons.get(TOUCH).setImage(getResources().getDrawable(R.mipmap.touch_on), getResources().getDrawable(R.mipmap.touch_off));

        for (int i = 0; i < mButtons.size(); i++) {
            mButtons.get(i).getButton().setOnClickListener(this);
        }
    }

    private void editSetLayout() {
        int mainPosition = 0;
        dbManager = new ListDBManager(getApplicationContext());
        listDataArrList = dbManager.selectAll();

        if (listDataArrList.get(mEditPosition).getmAppName() != null) {
            appName = listDataArrList.get(mEditPosition).getmAppName();
            mainPosition = MAIN_APP;
        } else if (listDataArrList.get(mEditPosition).getmPhoneName() != null) {
            phoneName = listDataArrList.get(mEditPosition).getmPhoneName() + " / " + listDataArrList.get(mEditPosition).getmPhoneNumber();
            mainPosition = MAIN_PHONE;
        }else if(listDataArrList.get(mEditPosition).getmAdditionName() != null) {
            additionName = listDataArrList.get(mEditPosition).getmAdditionName();

            if(additionName.equals("iot")){
                mainPosition = MAIN_IOT;
            }

            for (int i = 0; i < additionFunctions.nameList.size(); i++) {
                if (additionName.equals(additionFunctions.nameList.get(i))) {
                    mainPosition = i + MAIN_ADDITION;
                }
            }
        }

        setLayout();
        listviewSetting(mainPosition);

        // mButtons setting
        for (int i = 0; i < mButtons.size(); i++) {
            if (listDataArrList.get(mEditPosition).getmData1().equals(mButtons.get(i).getName())) {
                mButtons.get(i).setOnOFF(true);
            }

            String temp[] = split(listDataArrList.get(mEditPosition).getmData2(), " : ");

            if (temp[0].equals(mButtons.get(i).getName())) {
                mButtons.get(i).setOnOFF(true);
            }
        }
    }

    private void listviewiniti(Context context){

        mListView = (ListView)findViewById(R.id.listview_main);
        mAdapter = new ListViewAdapter(this);

        mAdapter.addItem("lauch app");
        mAdapter.addItem("phone call");
        mAdapter.addItem("IOT");
        for (String name : additionFunctions.nameList) {
            mAdapter.addItem(name);
        }

        mListView.setAdapter(mAdapter);
    }

    private void listviewCheckSetting(int position){

        AddEditListData mData = mAdapter.mAddEditListdata.get(position);

        for(int i=0; i < mAdapter.mAddEditListdata.size(); i++){
            mData = mAdapter.mAddEditListdata.get(i);
            if(i == position){
                mData.mOnOff = true;
                continue;
            }
            mData.mOnOff = false;
        }

    }

    private void listviewSetting(int position) {

        listviewCheckSetting(position);

        AddEditListData mData = mAdapter.mAddEditListdata.get(position);

        if(position == MAIN_APP){

            PackageManager packagemanager = this.getPackageManager();
            List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);;

            for (int i = 0; i < appList.size(); i++) {
                mAppName = appList.get(i).loadLabel(packagemanager);
                if (mAppName.equals(appName)) {

                    mData.mIcon = appList.get(i).loadIcon(packagemanager);
                    mAppPackage=appList.get(i).packageName;
                    index = i;
                    break;
                }
            }

        }else if(position == MAIN_PHONE){

            // phoneName >>> "phoneName / phoneNumber"
            String temp[] = split(phoneName, " / ");
            phoneNumber = temp[1];

            mData.mIcon = getResources().getDrawable(R.mipmap.human);
            mData.mSubTitle = temp[0];

        }

        mAdapter.notifyDataSetChanged();
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
                case KeyEvent.KEYCODE_BACK:
                    return true;
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
            if (mButtons.get(i).getOnOFF() == true) {
                OnOffTotalCount++;
            }
        }

       if (v == mBtnCancle) {
            Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
            startActivity(cancleintent);
            finish();

            // Save Click
        } else if (v == mBtnSave) {
            if (OnOffTotalCount == 2 && (appName != null || phoneName != null || additionName != null)) {
                for (int i = 0; i < mButtons.size(); i++) {
                        if (mButtons.get(i).getOnOFF() == true) {
                        pressedData[pressedDataNum] = mButtons.get(i).getName();
                        pressedDataNum = 1;
                    }
                }

                dbManager = new ListDBManager(getApplicationContext());
                mArrayListData = dbManager.selectAll();

                //add
                if (editState == false) {
                    for (ListData mListData : mArrayListData) {
                        //DB �� ����Ʈ�� ���� ���õ� �������� �ߺ��ƴ��� �˻�
                        if (pressedData[0].equals(mListData.getmData1()) && pressedData[1].equals(mListData.getmData2())) {
                            showDialog("is already exist");
                            pressedDataNum =0;
                            return;
                        }
                    }

                    if (appName != null) {
                        ListData listAppData = new ListData(index, pressedData[0], pressedData[1], appName.toString(),mAppPackage.toString(), null, null, null);
                        dbManager.insertAppData(listAppData);

                    } else if(phoneName != null) {
                        //  >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");

                        ListData listPhoneData = new ListData(index, pressedData[0], pressedData[1], null,null, temp[0], temp[1], null);
                        dbManager.insertPhoneData(listPhoneData);

                    } else if(additionName != null){
                        ListData listAdditionData = new ListData(index, pressedData[0], pressedData[1], null,null, null, null, additionName.toString());
                        dbManager.insertAdditionData(listAdditionData);

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
                                showDialog("is already exist");
                                pressedDataNum =0;
                                return;
                            }
                        }
                    }

                    if (appName != null) {
                        ListData listAppData = new ListData(index, pressedData[0], pressedData[1], appName.toString(),mAppPackage.toString(), null, null, null);
                        dbManager.updateAppData(listAppData, mArrayListData.get(mEditPosition).getId());

                    } else if(phoneName != null) {
                        //  >>> "phoneName / phoneNumber"
                        String temp[] = split(phoneName, " / ");

                        ListData listPhoneData = new ListData(index, pressedData[0], pressedData[1], null,null, temp[0], temp[1], null);
                        dbManager.updatePhoneData(listPhoneData, mArrayListData.get(mEditPosition).getId());

                    } else if(additionName != null){
                        ListData listAdditionData = new ListData(index, pressedData[0], pressedData[1], null,null, null, null, additionName.toString());
                        dbManager.updateAdditionData(listAdditionData, mArrayListData.get(mEditPosition).getId());

                    }
                }
                Intent cancleintent = new Intent(AddEditActivity.this, ListActivity.class);
                startActivity(cancleintent);
                finish();
            } else
                showDialog("select two Choice and one Action ");

        }

        if (v == mButtons.get(VOLUME_UP).getButton()) {
            mButtons.get(VOLUME_UP).setOnOFF(true);
            mButtons.get(VOLUME_DOWN).setOnOFF(false);
        }else if (v == mButtons.get(VOLUME_DOWN).getButton()) {
            mButtons.get(VOLUME_UP).setOnOFF(false);
            mButtons.get(VOLUME_DOWN).setOnOFF(true);
        }


        if (v == mButtons.get(SHAKE).getButton()) {
            mButtons.get(SHAKE).setOnOFF(true);
            mButtons.get(PLUGIN).setOnOFF(false);
            mButtons.get(TOUCH).setOnOFF(false);
        }else if (v == mButtons.get(PLUGIN).getButton()) {
            mButtons.get(SHAKE).setOnOFF(false);
            mButtons.get(PLUGIN).setOnOFF(true);
            mButtons.get(TOUCH).setOnOFF(false);
        }else if (v == mButtons.get(TOUCH).getButton()) {
            Intent touchIntent = new Intent(AddEditActivity.this, TouchActivity.class);
            startActivityForResult(touchIntent, LAUNCHED_ACTIVITY);
        }

    }

    private class ViewHolder {
        public MyTextView mMainText;
        public ImageView mMainImage;
        public MyTextView mSubText;
        public ImageView mCheckImage;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<AddEditListData> mAddEditListdata = new ArrayList<AddEditListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public ListViewAdapter(Context mContext, ArrayList<String> mylist) {
            super();
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mAddEditListdata.size();
        }

        @Override
        public Object getItem(int position) {
            return mAddEditListdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_add_edit_item, null);

                holder.mMainText = (MyTextView) convertView.findViewById(R.id.txt_main);
                holder.mMainImage = (ImageView) convertView.findViewById(R.id.imageview_main);
                holder.mCheckImage = (ImageView) convertView.findViewById(R.id.imageview_check);
                holder.mSubText = (MyTextView) convertView.findViewById(R.id.txt_sub);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AddEditListData mData = mAddEditListdata.get(position);

            holder.mMainText.setText(mData.mTitle);
            holder.mSubText.setText(mData.mSubTitle);

            if(mData.mOnOff == true){
                holder.mMainImage.setVisibility(View.VISIBLE);
                holder.mMainImage.setImageDrawable(mData.mIcon);
                holder.mSubText.setVisibility(View.VISIBLE);
                holder.mCheckImage.setVisibility(View.VISIBLE);
                holder.mCheckImage.setImageDrawable(mData.mCheck);
                holder.mMainText.setTextColor(Color.argb(255,94,46,198));
                holder.mSubText.setTextColor(Color.argb(255, 94, 46, 198));
            }else{
                holder.mMainImage.setVisibility(View.GONE);;
                holder.mCheckImage.setVisibility(View.GONE);
                holder.mMainText.setTextColor(Color.BLACK);
                holder.mSubText.setVisibility(View.GONE);
            }

            return convertView;
        }

        public void addItem(CharSequence mTitle) {
            AddEditListData addInfo = new AddEditListData();
            addInfo.mTitle = mTitle;
            addInfo.mIcon = null;
            addInfo.mSubTitle = "";
            addInfo.mCheck = getResources().getDrawable(R.mipmap.check);
            addInfo.mOnOff = false;

            mAddEditListdata.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mAddEditListdata.remove(position);
            dataChange();
        }


        public void dataChange() {
            mAdapter.notifyDataSetChanged();
        }
    }
}
