package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PopupTouchActivity extends Activity {
    private ListView mListView = null;
    private PUListAdapter touchAdapter = null;
    private TouchDBManager dbManager;
    private ArrayList<TouchData> touchDataArrList;
    private Button mBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_popup_touch);

        mListView = (ListView) findViewById(R.id.popup_touch_list);
        mBtnAdd = (Button)findViewById(R.id.popup_touch_add_btn);


        touchAdapter = new PUListAdapter(this);

        //DB를 받아온다
        dbManager= new TouchDBManager(getApplicationContext());
        touchDataArrList = dbManager.selectAll();

        for (TouchData data:touchDataArrList) {
            touchAdapter.addItem(data.getmTouchName());
        }

        mListView.setAdapter(touchAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Add edit Activity로 전달한 데이터 resultText Key 값의 "superdroid result" 문자열을
                //Extra로 Intent에 담았다.
                Intent intent = new Intent();
                PopupTouchListdata mData;
                mData = touchAdapter.mPopupTouchListdata.get(position);

                intent.putExtra("resultText", mData.mTitle);
                intent.putExtra("resultType", "touch");

                // 전달할 Intent를 설정하고 finish()함수를 통해
                //B Activity를 종료시킴과 동시에 결과로 Intent를 전달하였다.
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PopupTouchActivity.this,TouchActivity.class);
                startActivity(i);

            }
        });

    }

    private class ViewHolder {
        public TextView popupText;
    }

    private class PUListAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<PopupTouchListdata> mPopupTouchListdata = new ArrayList<PopupTouchListdata>();

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
            return mPopupTouchListdata.size();
        }

        @Override
        public Object getItem(int position) {
            return mPopupTouchListdata.get(position);
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
                convertView = inflater.inflate(R.layout.listview_popup_touch_item, null);

                holder.popupText = (TextView) convertView.findViewById(R.id.popup_touch_list_text);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PopupTouchListdata mData = mPopupTouchListdata.get(position);

            holder.popupText.setText(mData.mTitle);

            return convertView;
        }

        public void addItem(CharSequence mTitle) {
            PopupTouchListdata addInfo = null;
            addInfo = new PopupTouchListdata();

            addInfo.mTitle = mTitle;

            mPopupTouchListdata.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mPopupTouchListdata.remove(position);
            dataChange();
        }


        public void dataChange() {
            touchAdapter.notifyDataSetChanged();
        }

    }

    private class ViewHolder2 {
        //        public ImageView popupIcon;
        public TextView popupText;
    }

}

