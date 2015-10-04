package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bonkers.cau.sims.R;
import bonkers.cau.sims.database.ExcludedListData;

public class ExcludedApp extends Activity {
    private ListView mListView = null;
    private PUListAdapter appAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_ecluded_app);

        mListView = (ListView) findViewById(R.id.listview_excluded);

        appAdapter = new PUListAdapter(this);

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
            appAdapter.addItem(appList.get(i).loadIcon(packagemanager), appList.get(i).loadLabel(packagemanager),appList.get(i).packageName);
        }

        mListView.setAdapter(appAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                //Extra�� Intent�� ��Ҵ�.
                Intent intent = new Intent();
                ExcludedListData mData = appAdapter.mExcludedListData.get(position);

                intent.putExtra("resultText", mData.mPackageName);
                intent.putExtra("resultType", "app");

                // ������ Intent�� �����ϰ� finish()�Լ��� ����
                //B Activity�� �����Ŵ�� ���ÿ� ����� Intent�� �����Ͽ���.
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    private class ViewHolder {
        public ImageView icon;
        public TextView textView;
        public ImageView check;
    }

    private class PUListAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ExcludedListData> mExcludedListData = new ArrayList<ExcludedListData>();

        public PUListAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public PUListAdapter(Context mContext, ArrayList<CharSequence> mylist) {
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
                holder.check.setImageDrawable(mData.mIcon);
            }else{
                holder.check.setVisibility(View.GONE);;
            }

//            if (mData.mIcon != null) {
//                holder.icon.setVisibility(View.VISIBLE);
//                holder.icon.setImageDrawable(mData.mIcon);
//            } else {
//                holder.icon.setVisibility(View.GONE);
//            }

            holder.textView.setText(mData.mPackageName);

            return convertView;
        }

        public void addItem(Drawable icon, CharSequence mTitle, CharSequence mPackageName) {
            ExcludedListData addInfo = null;
            addInfo = new ExcludedListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mPackageName = mPackageName;

            mExcludedListData.add(addInfo);
            dataChange();
        }

        public void remove(int position) {
            mExcludedListData.remove(position);
            dataChange();
        }


        public void dataChange() {
            appAdapter.notifyDataSetChanged();
        }

    }

}

