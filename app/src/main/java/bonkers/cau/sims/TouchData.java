package bonkers.cau.sims;

/**
 * Created by ±è½Â¿í on 2015-07-30.
 */
public class TouchData {
    private String mTouchName = null;
    private String mTouchPath = null;
    private  int id;

    public TouchData(String mTouchName, String mTouchPath) {
        this.mTouchName = mTouchName;
        this.mTouchPath = mTouchPath;
    }

    public TouchData(int Id, String mTouchName, String mTouchPath) {
        this.id = Id;
        this.mTouchName = mTouchName;
        this.mTouchPath = mTouchPath;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmTouchName() {
        return mTouchName;
    }

    public void setmTouchName(String mTouchName) {
        this.mTouchName = mTouchName;
    }

    public String getmTouchPath() {
        return mTouchPath;
    }

    public void setmTouchPath(String mTouchPath) {
        this.mTouchPath = mTouchPath;
    }
}

