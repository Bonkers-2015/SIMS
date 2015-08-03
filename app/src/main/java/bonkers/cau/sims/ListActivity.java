package bonkers.cau.sims;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity {

    private ListView mListView = null;
    private ListViewAdapter mAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mListView=(ListView)findViewById(R.id.mlist);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.addItem(getResources().getDrawable(R.mipmap.phone), "Vol-up", "Vol-down");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.kakao),"Vol-down","Vol-up");
        Button btn =(Button)findViewById(R.id.list_addbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddEditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String[] items = new String[20];
        for(int i=0; i<items.length;i++) {
            items[i] = "Item" + (i+1);
        }

        mListView.setAdapter(mAdapter);

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
                                    mAdapter.remove(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        mListView.setOnTouchListener(touchListner);
        mListView.setOnScrollListener(touchListner.makeScrollListener());
    }

    private class ViewHolder {
        public ImageView mIcon;
        public TextView mFirst;
        public TextView mSecond;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext=null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position,View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_list_item, null);

                holder.mIcon = (ImageView)convertView.findViewById(R.id.list_icon);
                holder.mFirst = (TextView)convertView.findViewById(R.id.first_setting);
                holder.mSecond = (TextView)convertView.findViewById(R.id.second_setting);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if(mData.getmIcon()!=null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.getmIcon());
            }

            else {
                holder.mIcon.setVisibility(View.VISIBLE);
            }

            holder.mFirst.setText(mData.getmData1());
            holder.mSecond.setText(mData.getmData2());

            return convertView;
        }

        public void addItem(Drawable icon,String mTitle,String mData) {
            ListData addInfo = null;
            addInfo = new ListData(icon,mTitle,mData);
            mListData.add(addInfo);

        }

        public void remove(int positon) {
            mListData.remove(positon);
            dataChange();
        }

        public void dataChange() {
            mAdapter.notifyDataSetChanged();

        }
    }
}
