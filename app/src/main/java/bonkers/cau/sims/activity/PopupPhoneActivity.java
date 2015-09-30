package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import bonkers.cau.sims.database.PopupListData;
import bonkers.cau.sims.R;

public class PopupPhoneActivity extends Activity {
    private ListView mListView = null;
    private PUListAdapter phoneAdapter= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_popup_phone);

        mListView = (ListView) findViewById(R.id.popup_phone_list);

        phoneAdapter = new PUListAdapter(this);


        String[] arrProjection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        String[] arrPhoneProjection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

        // ID와 이름 받아오는 Cursor
        Cursor clsCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, arrProjection
                , ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1", null, null);

        while (clsCursor.moveToNext()) {
            String strContactId = clsCursor.getString(0);

            // PHoneNumber를 받아오는 Cursor
            Cursor clsPhoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrPhoneProjection, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + strContactId, null, null);

            while (clsPhoneCursor.moveToNext()) {
                // Adapter에 Item 추가
                phoneAdapter.addItem(getResources().getDrawable(R.mipmap.human), clsCursor.getString(1) + " / " + clsPhoneCursor.getString(0));
            }
            clsPhoneCursor.close();
        }
        clsCursor.close();


        mListView.setAdapter(phoneAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Add edit Activity로 전달한 데이터 resultText Key 값의 "superdroid result" 문자열을
                //Extra로 Intent에 담았다.
                Intent intent = new Intent();
                PopupListData mData = phoneAdapter.mPopupListData.get(position);

                intent.putExtra("resultText", mData.mTitle);
                intent.putExtra("resultType", "phone");

                // 전달할 Intent를 설정하고 finish()함수를 통해
                //B Activity를 종료시킴과 동시에 결과로 Intent를 전달하였다.
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private class ViewHolder {
        public ImageView popupIcon;
        public TextView popupText;
    }

    private class PUListAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<PopupListData> mPopupListData = new ArrayList<PopupListData>();

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
            return mPopupListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mPopupListData.get(position);
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
                convertView = inflater.inflate(R.layout.listview_popup_phone_item, null);

                holder.popupIcon = (ImageView) convertView.findViewById(R.id.popup_phone_list_image);
                holder.popupText = (TextView) convertView.findViewById(R.id.popup_phone_list_text);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PopupListData mData = mPopupListData.get(position);

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
            PopupListData addInfo = null;
            addInfo = new PopupListData();

            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;

            mPopupListData.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mPopupListData.remove(position);
            dataChange();
        }

        public void dataChange() {
            phoneAdapter.notifyDataSetChanged();
        }

    }
}

