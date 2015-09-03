package bonkers.cau.sims;

import android.graphics.Color;
import android.widget.Button;

/**
 * Created by dongbin on 2015-07-31.
 */

public class Buttons{

    public String name, title;
    public boolean onOff;
//    public RelativeLayout.LayoutParams params;
    public int onImage,offImage, color;
    public Button button;

    public Buttons(String title, String name, Button btn) {

        this.button = btn;
//        this.title = title;
        this.name = name;
        this.onOff = false;
        onImage = R.mipmap.ic_launcher;
        offImage = R.mipmap.ic_launcher;
        color = Color.LTGRAY;
    }

}
