package bonkers.cau.sims;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PhoneActivity extends ActionBarActivity {
    private ListView mListView = null;
    private PUListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup);





        mListView = (ListView) findViewById(R.id.popup_list);
        mAdapter = new PUListAdapter(this);
        mListView.setAdapter(mAdapter);

        mAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                "pakrbeomseok");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                "pakrbeomseok");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                "pakrbeomseok");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.human),
                "pakrbeomseok");

/*            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                    PopupListdata mData = mAdapter.mPopupListdata.get(position);
                    Toast.makeText(PopupActivity.this, mData.mTitle, Toast.LENGTH_SHORT).show();
                }
            });*/


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
            mAdapter.notifyDataSetChanged();
        }

    }
}
