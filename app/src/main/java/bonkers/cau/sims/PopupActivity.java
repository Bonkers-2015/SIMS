package bonkers.cau.sims;

import android.app.Activity;
import android.content.Context;
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





            mListView = (ListView) findViewById(R.id.popup_list);
            puAdapter = new PUListAdapter(this);
            pAdapter = new PUListAdapter(this);
            mListView.setAdapter(puAdapter);

            puAdapter.addItem(getResources().getDrawable(R.mipmap.kakao),
                    "kakao");
            puAdapter.addItem(getResources().getDrawable(R.mipmap.knights),
                    "7knights");
            puAdapter.addItem(getResources().getDrawable(R.mipmap.line),
                    "line");
            puAdapter.addItem(null,
                    "이미지가 null이면...");

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                    PopupListdata mData = puAdapter.mPopupListdata.get(position);
                    if("line"==mData.mTitle){
                        pAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                                "pakrbeomseok");
                        pAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                                "pakrbeomseok");
                        pAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                                "pakrbeomseok");
                        pAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                                "pakrbeomseok");
                        mListView.setAdapter(pAdapter);
                    }

                }
            });


    /*        //make same height and width in imageview
            ImageView iv = (ImageView)this.findViewById(R.id.popup_list_image);
            ViewGroup.LayoutParams lp = iv.getLayoutParams();
            lp.height = getResources().getDisplayMetrics().heightPixels;
            lp.width = lp.height;*/




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
        public void addItem(Drawable icon, String mTitle){
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
