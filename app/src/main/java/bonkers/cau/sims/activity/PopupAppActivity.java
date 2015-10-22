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

import bonkers.cau.sims.database.PopupListData;
import bonkers.cau.sims.R;

public class PopupAppActivity extends Activity {
    private ListView mListView = null;
    private PUListAdapter appAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_popup_app);

        mListView = (ListView) findViewById(R.id.popup_app_list);

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
            appAdapter.addItem(appList.get(i).loadIcon(packagemanager), appList.get(i).loadLabel(packagemanager));
        }

        mListView.setAdapter(appAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                //Extra�� Intent�� ��Ҵ�.
                Intent intent = new Intent();
                PopupListData mData = appAdapter.mPopupListData.get(position);
                intent.putExtra("resultText", mData.mTitle);
                intent.putExtra("resultType", "app");

                // ������ Intent�� �����ϰ� finish()�Լ��� ����
                //B Activity�� �����Ŵ�� ���ÿ� ����� Intent�� �����Ͽ���.
                setResult(RESULT_OK, intent);
                finish();
//sgdxggggggg
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
                convertView = inflater.inflate(R.layout.listview_popup_app_item, null);

                holder.popupIcon = (ImageView) convertView.findViewById(R.id.popup_app_list_image);
                holder.popupText = (TextView) convertView.findViewById(R.id.popup_app_list_text);

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
            appAdapter.notifyDataSetChanged();
        }

    }

}

