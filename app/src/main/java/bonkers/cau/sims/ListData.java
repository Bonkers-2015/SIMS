package bonkers.cau.sims;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by ±è½Â¿í on 2015-07-30.
 */
public class ListData {
    private Drawable mIcon;
    private int indexNum;
    private String mData1;
    private String mData2;


    public ListData(Drawable mIcon, String mData1, String mData2) {
        this.mIcon = mIcon;
        this.mData1 = mData1;
        this.mData2 = mData2;
    }

    public ListData(int indexNum, String mData1, String mData2) {
        this.indexNum = indexNum;
       // PackageManager packagemanager = context.getPackageManager();
       // List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);
       // this.mIcon=appList.get(indexNum).loadIcon(packagemanager);
        this.mData1 = mData1;
        this.mData2 = mData2;
    }
    public ListData(Context context, int indexNum, String mData1, String mData2) {
        this.indexNum = indexNum;
        PackageManager packagemanager = context.getPackageManager();
        List<ApplicationInfo> appList = packagemanager.getInstalledApplications(0);
        this.mIcon=appList.get(indexNum).loadIcon(packagemanager);
        this.mData1 = mData1;
        this.mData2 = mData2;
    }
    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public int getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(int indexNum) {
        this.indexNum = indexNum;
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

