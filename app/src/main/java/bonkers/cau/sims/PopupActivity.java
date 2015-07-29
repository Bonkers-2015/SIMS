package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
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



            PackageManager packagemanager = this.getPackageManager();
            List<ApplicationInfo> appList = packagemanager.getInstalledApplications( 0 );

            mListView = (ListView) findViewById(R.id.popup_list);
            puAdapter = new PUListAdapter(this);
            pAdapter = new PUListAdapter(this);
            mListView.setAdapter(puAdapter);
            puAdapter.addItem(getResources().getDrawable(R.mipmap.phone),"Phone");
            for (int i = 0; i < appList.size(); i++)
                puAdapter.addItem(appList.get(i).loadIcon(packagemanager),
                        appList.get(i).loadLabel(packagemanager));



            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    PopupListdata mData = puAdapter.mPopupListdata.get(position);
                    if ("Phone" == mData.mTitle) {
                        getList();
                        mListView.setAdapter(pAdapter);
                    }

                }
            });

        }
    private Cursor getURI()
    {
        // 주소록 URI
        Uri people =  ContactsContract.Contacts.CONTENT_URI;

        // 검색할 컬럼 정하기
        String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER };

        // 쿼리 날려서 커서 얻기
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        // managedquery 는 activity 메소드이므로 아래와 같이 처리함
        return getContentResolver().query(people, projection, null, selectionArgs, sortOrder);
        // return managedQuery(people, projection, null, selectionArgs, sortOrder);
    }
    public void getList(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = getURI();                    // 전화번호부 가져오기


        if(cursor.moveToFirst()){
            do{
                pAdapter.addItem(getResources().getDrawable(R.mipmap.human),cursor.getString(1) + "/" + cursor.getString(0)+ "/" + cursor.getString(2));
            }while(cursor.moveToNext());
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
        public PUListAdapter(Context mContext,ArrayList<String> mylist) {
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
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            PopupListdata mData = mPopupListdata.get(position);

            if (mData.mIcon != null) {
                holder.popupIcon.setVisibility(View.VISIBLE);
                holder.popupIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.popupIcon.setVisibility(View.GONE);
            }

            holder.popupText.setText(mData.mTitle);

            return convertView;
        }
        public void addItem(Drawable icon, CharSequence mTitle){
            PopupListdata addInfo = null;
            addInfo = new PopupListdata();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;

            mPopupListdata.add(addInfo);
            dataChange();
        }

        public void remove(int position){
            mPopupListdata.remove(position);
            dataChange();
        }


        public void dataChange(){
            puAdapter.notifyDataSetChanged();
        }

    }

}
