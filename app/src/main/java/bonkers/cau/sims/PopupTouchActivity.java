package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
            touchAdapter.addItem(data.getId(),data.getmTouchName(),data.getmTouchPath());
        }

        mListView.setAdapter(touchAdapter);
        SwipeDismissListViewTouchListener touchListner =
                new SwipeDismissListViewTouchListener (mListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView,int[] reverseSortedPositions) {
                                for (int position:reverseSortedPositions) {
                                    Log.d("mylog",Integer.toString(touchAdapter.getItem(position).getId()));
                                    dbManager.removeData(touchAdapter.getItem(position).getId());
                                    touchAdapter.remove(position);

                                }
                                touchAdapter.notifyDataSetChanged();
                            }
                        });
        mListView.setOnTouchListener(touchListner);
        mListView.setOnScrollListener(touchListner.makeScrollListener());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Add edit Activity로 전달한 데이터 resultText Key 값의 "superdroid result" 문자열을
                //Extra로 Intent에 담았다.
                Intent intent = new Intent();
                TouchData mData;
                mData = touchAdapter.mTouchListdata.get(position);

                intent.putExtra("resultText", mData.getmTouchName());
                intent.putExtra("resultPath", mData.getmTouchPath());
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
        private ArrayList<TouchData> mTouchListdata = new ArrayList<TouchData>();

        public PUListAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public PUListAdapter(Context mContext, ArrayList<TouchData> mylist) {
            super();
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mTouchListdata.size();
        }

        @Override
        public TouchData getItem(int position) {
            return mTouchListdata.get(position);
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

            TouchData mData = mTouchListdata.get(position);

            holder.popupText.setText(mData.getmTouchName());

            return convertView;
        }

        public void addItem(int mId,String mTitle,String mPath) {
            TouchData addInfo = null;
            addInfo = new TouchData(mId,mTitle,mPath);
            mTouchListdata.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mTouchListdata.remove(position);
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

