package bonkers.cau.sims;

/**
 * Created by ±è½Â¿í on 2015-07-30.
 */
public class ListData {
    private String mData1 = null;
    private String mData2 = null;
    private String mAppName = null;
    private String mPhoneName = null;
    private String mPhoneNumber = null;
    private  int indexNum;
    private  int id;

    public ListData(int mIndexNum, String mData1, String mData2, String mAppName, String mPhoneName, String mPhoneNumber) {
        this.indexNum=mIndexNum;
        this.mData1 = mData1;
        this.mData2 = mData2;
        this.mAppName = mAppName;
        this.mPhoneName = mPhoneName;
        this.mPhoneNumber = mPhoneNumber;
    }
    public ListData(int mId,int mIndexNum, String mData1, String mData2, String mAppName, String mPhoneName, String mPhoneNumber) {
        this.id = mId;
        this.indexNum=mIndexNum;
        this.mData1 = mData1;
        this.mData2 = mData2;
        this.mAppName = mAppName;
        this.mPhoneName = mPhoneName;
        this.mPhoneNumber = mPhoneNumber;
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

    public String getmAppName() {
        return mAppName;
    }

    public void setmAppName(String mAppName) {
        this.mAppName = mAppName;
    }

    public String getmPhoneName() {
        return mPhoneName;
    }

    public void setmPhoneName(String mPhoneName) {
        this.mPhoneName = mPhoneName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}

