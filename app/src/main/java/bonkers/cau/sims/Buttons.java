package bonkers.cau.sims;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dongbin on 2015-07-31.
 */

public class Buttons{

    public String name, title;
    public boolean onOff;
//    public RelativeLayout.LayoutParams params;
    public int onImage,offImage, color;
    public Button button;
    public TextView textView = null;

    public Buttons(String title, String name, Button btn, TextView textView) {

        this.button = btn;
        this.textView = textView;
        this.name = name;
        this.onOff = false;
        onImage = R.mipmap.ic_launcher;
        offImage = R.mipmap.ic_launcher;
        color = Color.LTGRAY;
    }
    public Buttons(String title, String name, Button btn) {

        this.button = btn;
        this.name = name;
        this.onOff = false;
        onImage = R.mipmap.ic_launcher;
        offImage = R.mipmap.ic_launcher;
        color = Color.LTGRAY;
    }

}
