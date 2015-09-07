package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PopupActivity extends Activity {
    private ListView mListView = null;
    private PUListAdapter menuAdapter = null,appAdapter = null, phoneAdapter= null, additionAdapter= null;
    private int popupType=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // -1�� menu(�ʱ�)����, 0�� "app" , 1�� "phone", 2�� "addition"
        popupType=-1;


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_popup);

        //addEdit���� intent�� ����
        Intent intent = getIntent();
        //intent extra�� ������ myName �� �ش��ϴ� ���� ������
        String receivedText = intent.getStringExtra("myName");

<<<<<<< HEAD
        mListView = (ListView) findViewById(R.id.popup_list);
        menuAdapter = new PUListAdapter(this);
        appAdapter = new PUListAdapter(this);
        phoneAdapter = new PUListAdapter(this);
        additionAdapter = new PUListAdapter(this);
        mListView.setAdapter(menuAdapter);

        menuAdapter.addItem(getResources().getDrawable(R.mipmap.applist), "App");
        menuAdapter.addItem(getResources().getDrawable(R.mipmap.phone), "Phone");
        menuAdapter.addItem(getResources().getDrawable(R.mipmap.addition), "Addition");
=======
        //실행가능한 앱목록 받아오는부분.
        PackageManager packagemanager = this.getPackageManager();
        List<ApplicationInfo> installedApps = getApplicationContext().getPackageManager().getInstalledApplications(PackageManager.PERMISSION_GRANTED);
        List<ApplicationInfo> launchableInstalledApps = new ArrayList<ApplicationInfo>();
        for(int i =0; i < installedApps.size(); i++){
            if(getApplicationContext().getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packageName) != null){
                //If you're here, then this is a launch-able app
                launchableInstalledApps.add(installedApps.get(i));

            }
        }

>>>>>>> origin/Isco

        mListView.setAdapter(menuAdapter);

<<<<<<< HEAD
=======
        mListView = (ListView) findViewById(R.id.popup_list);
        puAdapter = new PUListAdapter(this);
        pAdapter = new PUListAdapter(this);
        mListView.setAdapter(puAdapter);
        for (int i = 0; i < launchableInstalledApps.size(); i++) {
            puAdapter.addItem(launchableInstalledApps.get(i).loadIcon(packagemanager),
                    launchableInstalledApps.get(i).loadLabel(packagemanager));


        }
>>>>>>> origin/Isco
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

<<<<<<< HEAD
                PopupListdata mData;

                if(popupType ==-1) {
                    mData = menuAdapter.mPopupListdata.get(position);
                    if ("App" == mData.mTitle) {
                        popupType = 0;
                        getList("app");
                        mListView.setAdapter(appAdapter);

                    } else if ("Phone" == mData.mTitle) {
                        popupType = 1;
                        getList("phone");
                        mListView.setAdapter(phoneAdapter);

                    } else if ("Addition" == mData.mTitle) {
                        popupType = 2;
                        getList("addtion");
                        mListView.setAdapter(additionAdapter);
                    }
                }else {
                    //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                    //Extra�� Intent�� ��Ҵ�.
=======
                } else {
                    //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                    //Extra�� Intent�� ��Ҵ�.
>>>>>>> origin/Isco
                    Intent intent = new Intent();

<<<<<<< HEAD
                    if (popupType == 0) {
                        mData = appAdapter.mPopupListdata.get(position);
                        intent.putExtra("resultText", mData.mTitle);
                        intent.putExtra("resultType", "app");
                    } else if (popupType == 1) {
                        mData = phoneAdapter.mPopupListdata.get(position);
                        intent.putExtra("resultText", mData.mTitle);
                        intent.putExtra("resultType", "phone");
                    } else if (popupType == 2) {
                        mData = additionAdapter.mPopupListdata.get(position);
                        intent.putExtra("resultText", mData.mTitle);
                        intent.putExtra("resultType", "addition");
                    }
                    // ������ Intent�� �����ϰ� finish()�Լ��� ����
                    //B Activity�� �����Ŵ�� ���ÿ� ����� Intent�� �����Ͽ���.
=======
                    // ������ Intent�� �����ϰ� finish()�Լ��� ����
                    //B Activity�� �����Ŵ�� ���ÿ� ����� Intent�� �����Ͽ���.
>>>>>>> origin/Isco
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }

<<<<<<< HEAD
    public void getList(String menu) {

        if(menu == "app"){
            PackageManager packagemanager = this.getPackageManager();
            List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

            for (int i = 0; i < appList.size(); i++){
                appAdapter.addItem(appList.get(i).loadIcon(packagemanager), appList.get(i).loadLabel(packagemanager));
            }
=======
    private Cursor getURI() {
        // �ּҷ� URI
        Uri people = ContactsContract.Contacts.CONTENT_URI;

        // �˻��� �÷� ���ϱ�
        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};

        // ���� ������ Ŀ�� ���
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        // managedquery �� activity �޼ҵ��̹Ƿ� �Ʒ��� ���� ó����
        return getContentResolver().query(people, projection, null, selectionArgs, sortOrder);
        // return managedQuery(people, projection, null, selectionArgs, sortOrder);
    }
>>>>>>> origin/Isco

        }else if(menu == "phone") {

            String[] arrProjection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
            String[] arrPhoneProjection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

<<<<<<< HEAD
            // ID�� �̸� �޾ƿ��� Cursor
            Cursor clsCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, arrProjection
                    , ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1", null, null);
=======
        Cursor cursor = getURI();                    // ��ȭ��ȣ�� ��������
>>>>>>> origin/Isco

            while (clsCursor.moveToNext()) {
                String strContactId = clsCursor.getString(0);

                // PHoneNumber�� �޾ƿ��� Cursor
                Cursor clsPhoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrPhoneProjection
                        , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + strContactId, null, null);

                while (clsPhoneCursor.moveToNext()) {
                    // Adapter�� Item �߰�
                    phoneAdapter.addItem(getResources().getDrawable(R.mipmap.human), clsCursor.getString(1) + " / " + clsPhoneCursor.getString(0));
                }
                clsPhoneCursor.close();
            }
            clsCursor.close();
        }else if(menu == "addition"){

        }
    }

    private class ViewHolder {
        public ImageView popupIcon;
        public TextView popupText;
    }

    private class PUListAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<PopupListdata> mPopupListdata = new ArrayList<PopupListdata>();

        public PUListAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public PUListAdapter(Context mContext, ArrayList<String> mylist) {
            super();
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mPopupListdata.size();
        }

        @Override
        public Object getItem(int position) {
            return mPopupListdata.get(position);
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
                convertView = inflater.inflate(R.layout.listview_popup_item, null);

                holder.popupIcon = (ImageView) convertView.findViewById(R.id.popup_list_image);
                holder.popupText = (TextView) convertView.findViewById(R.id.popup_list_text);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PopupListdata mData = mPopupListdata.get(position);

            if (mData.mIcon != null) {
                holder.popupIcon.setVisibility(View.VISIBLE);
                holder.popupIcon.setImageDrawable(mData.mIcon);
            } else {
                holder.popupIcon.setVisibility(View.GONE);
            }

            holder.popupText.setText(mData.mTitle);

            return convertView;
        }

        public void addItem(Drawable icon, CharSequence mTitle) {
            PopupListdata addInfo = null;
            addInfo = new PopupListdata();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;

            mPopupListdata.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mPopupListdata.remove(position);
            dataChange();
        }


        public void dataChange() {
            appAdapter.notifyDataSetChanged();
        }

    }

}
