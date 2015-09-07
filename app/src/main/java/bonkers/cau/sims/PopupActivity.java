package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
    private PUListAdapter puAdapter = null;
    private PUListAdapter pAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_popup);

        //addEdit���� intent�� ����
        Intent intent = getIntent();
        //intent extra�� ������ myName �� �ش��ϴ� ���� ������
        String receivedText = intent.getStringExtra("myName");

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



        mListView = (ListView) findViewById(R.id.popup_list);
        puAdapter = new PUListAdapter(this);
        pAdapter = new PUListAdapter(this);
        mListView.setAdapter(puAdapter);
        for (int i = 0; i < launchableInstalledApps.size(); i++) {
            puAdapter.addItem(launchableInstalledApps.get(i).loadIcon(packagemanager),
                    launchableInstalledApps.get(i).loadLabel(packagemanager));


        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PopupListdata mData = puAdapter.mPopupListdata.get(position);
                if ("Phone" == mData.mTitle) {
                    getList();
                    mListView.setAdapter(pAdapter);

                } else {
                    //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                    //Extra�� Intent�� ��Ҵ�.
                    Intent intent = new Intent();
                    intent.putExtra("resultText", mData.mTitle);

                    // ������ Intent�� �����ϰ� finish()�Լ��� ����
                    //B Activity�� �����Ŵ�� ���ÿ� ����� Intent�� �����Ͽ���.
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }

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

    public void getList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = getURI();                    // ��ȭ��ȣ�� ��������


        if (cursor.moveToFirst()) {
            do {
                pAdapter.addItem(getResources().getDrawable(R.mipmap.human), cursor.getString(1) + "/" + cursor.getString(0) + "/" + cursor.getString(2));
            } while (cursor.moveToNext());
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
            puAdapter.notifyDataSetChanged();
        }

    }

}
