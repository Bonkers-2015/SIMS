package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity implements View.OnClickListener {

    private ListView mListView = null;
    private ListViewAdapter mAdapter=null;
    private ListDBManager dbManager;
    private ArrayList<ListData> listDataArrList;
    private Button addBtn,editBtn;
    private int mSelectedPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        mListView=(ListView)findViewById(R.id.mlist);
        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        PackageManager packagemanager = getApplicationContext().getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);

        //DB를 받아온다
        dbManager= new ListDBManager(getApplicationContext());
        listDataArrList =dbManager.selectAll();

        for (ListData data:listDataArrList) {

            mAdapter.addItem(data.getId(),appList.get(data.getIndexNum()).loadIcon(packagemanager), data.getmData1(), data.getmData2());


        }
        PackageManager pm = this.getPackageManager();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo pack : packs) {

            Log.i("TAG", pack.applicationInfo.loadLabel(pm).toString());
            Log.i("TAG", pack.packageName);

        }

        addBtn =(Button)findViewById(R.id.list_addbtn);
        addBtn.setOnClickListener(this);
        editBtn =(Button)findViewById(R.id.list_editbtn);
        editBtn.setOnClickListener(this);


        String[] items = new String[20];
        for(int i=0; i<items.length;i++) {
            items[i] = "Item" + (i+1);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < mListView.getChildCount(); i++) {
                    if(position == i ){
                        mListView.getChildAt(i).setBackgroundColor(Color.GRAY);
                        mSelectedPosition=i;
                    }else{
                        mListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

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

                                    dbManager.removeData(mAdapter.getItem(position).getId());
                                    mAdapter.remove(position);

                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        mListView.setOnTouchListener(touchListner);
        mListView.setOnScrollListener(touchListner.makeScrollListener());
    }

    @Override
    public void onClick(View v) {

        // no select => mSelectedPosition = -1

        Intent intent = new Intent(ListActivity.this ,AddEditActivity.class);
        Bundle myData = new Bundle();
        if(v == addBtn) {
            myData.putInt("selectedPosition", -1);
            intent.putExtras(myData);
            startActivity(intent);

        }else if(v == editBtn && mSelectedPosition>-1){
            myData.putInt("selectedPosition", mSelectedPosition);
            intent.putExtras(myData);
            startActivity(intent);
        }

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
        public ListData getItem(int position) {
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

        public void addItem(int mId,Drawable icon,String mTitle,String mData) {
            ListData addInfo = null;
            addInfo = new ListData(mId,icon,mTitle,mData);
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
