package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bonkers.cau.sims.R;
import bonkers.cau.sims.database.ExcludedListData;

public class ExcludedAppActivity extends Activity implements View.OnClickListener {
    private ListView mListView = null;
    private ListAdapter mAdapter = null;
    private Button btnSave,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_excluded_app);

        btnSave = (Button)findViewById(R.id.btn_excluded_save);
        btnCancel = (Button)findViewById(R.id.btn_excluded_cancle);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.listview_excluded);
        mAdapter = new ListAdapter(this);

        PackageManager packagemanager = this.getPackageManager();
        List<ApplicationInfo> installedApps = getApplicationContext().getPackageManager().getInstalledApplications(PackageManager.PERMISSION_GRANTED);
        List<ApplicationInfo> appList = new ArrayList<ApplicationInfo>();
        for(int i =0; i < installedApps.size(); i++){
            if(getApplicationContext().getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packageName) != null){
                //If you're here, then this is a launch-able app
                appList.add(installedApps.get(i));
            }
        }

        for (int i = 0; i < appList.size(); i++){
            mAdapter.addItem(appList.get(i).loadIcon(packagemanager), appList.get(i).loadLabel(packagemanager),appList.get(i).packageName);
        }


        setListView(this);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ExcludedListData mData = mAdapter.mExcludedListData.get(position);

                if(mData.mOnOff){
                    mData.mOnOff = false;
                }else{
                    mData.mOnOff = true;
                }

                mAdapter.dataChange();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v==btnSave){

            ArrayList<String> checkedAppList = new ArrayList<String>();

            for (int i=0; i<mAdapter.mExcludedListData.size();i++){
                if(mAdapter.mExcludedListData.get(i).mOnOff){
                    checkedAppList.add(mAdapter.mExcludedListData.get(i).mPackageName.toString());
                }
            }

            SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            Set<String> stringSet = new HashSet<>();
            stringSet.addAll(checkedAppList);

            editor.putStringSet("excludedAppList", stringSet);
            editor.commit();


//

            finish();

        }else if(v==btnCancel){
            finish();
        }


    }

    public void setListView(Context context){
        SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        ArrayList<String> excludedAppList = new ArrayList<String>();
        if(myPrefs.getStringSet("excludedAppList",null)!=null){
            excludedAppList.addAll(myPrefs.getStringSet("excludedAppList",null));
            for(int i=0;i<excludedAppList.size();i++){
                for(int j=0;j<mAdapter.mExcludedListData.size();j++){
                    if (excludedAppList.get(i).equals(mAdapter.mExcludedListData.get(j).mPackageName)){
                        mAdapter.mExcludedListData.get(j).mOnOff = true;
                    }
                }
            }
        }
        mAdapter.dataChange();
    }


    private class ViewHolder {
        public ImageView icon;
        public TextView textView;
        public ImageView check;
    }

    private class ListAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ExcludedListData> mExcludedListData = new ArrayList<ExcludedListData>();

        public ListAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public ListAdapter(Context mContext, ArrayList<CharSequence> mylist) {
            super();
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mExcludedListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mExcludedListData.get(position);
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
                convertView = inflater.inflate(R.layout.listview_excluded_app_item, null);

                holder.icon = (ImageView) convertView.findViewById(R.id.excluded_app_list_image);
                holder.textView = (TextView) convertView.findViewById(R.id.excluded_app_list_text);
                holder.check = (ImageView)convertView.findViewById((R.id.excluded_app_list_check));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            ExcludedListData mData = mExcludedListData.get(position);

            if(mData.mOnOff == true){
                holder.check.setVisibility(View.VISIBLE);
                holder.check.setImageDrawable(mData.mCheck);
            }else{
                holder.check.setVisibility(View.GONE);;
            }

            holder.icon.setImageDrawable(mData.mIcon);
            holder.textView.setText(mData.mTitle);

            return convertView;
        }

        public void addItem(Drawable icon, CharSequence mTitle, CharSequence mPackageName) {
            ExcludedListData addInfo = null;
            addInfo = new ExcludedListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mPackageName = mPackageName;
            addInfo.mOnOff = false;
            addInfo.mCheck = getResources().getDrawable(R.mipmap.excluded_check);

            mExcludedListData.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mExcludedListData.remove(position);
            dataChange();
        }


        public void dataChange() {
            mAdapter.notifyDataSetChanged();
        }

    }

}

