package bonkers.cau.sims;

import android.graphics.drawable.Drawable;

/**
 * Created by ±è½Â¿í on 2015-07-30.
 */
public class ListData {
    private Drawable mIcon;
    private String mData1;
    private String mData2;
    private  int indexNum;
    private  int id;




    public ListData(int mIndexNum, String mData1, String mData2) {
        this.indexNum=mIndexNum;
        this.mData1 = mData1;
        this.mData2 = mData2;
    }
    public ListData(int mId,int mIndexNum, String mData1, String mData2) {
        this.indexNum=mIndexNum;
        this.id = mId;
        this.mData1 = mData1;
        this.mData2 = mData2;
    }
    public ListData(int mId,Drawable mIcon,String mData1,String mData2) {
        this.id = mId;
        this.mIcon = mIcon;
        this.mData1 = mData1;
        this.mData2 = mData2;
    }
        public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(int inexNum) {
        this.indexNum = inexNum;
    }
    public String getmData1() {
        return mData1;
    }

    public void setmData1(String mData1) {
        this.mData1 = mData1;
    }

    public String getmData2() {
        return mData2;
    }

    public void setmData2(String mData2) {
        this.mData2 = mData2;
    }
}

