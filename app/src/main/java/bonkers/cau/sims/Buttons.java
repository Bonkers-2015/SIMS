package bonkers.cau.sims;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by dongbin on 2015-07-31.
 */

public class Buttons extends Button{

    public String name;
    public boolean onOff;
    public RelativeLayout.LayoutParams params;
    public int onImage,offImage, color;

    public Buttons(Context context) {
        super(context);

        this.name = new String("");
        this.onOff = false;
        this.params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        onImage = R.mipmap.ic_launcher;
        offImage = R.mipmap.ic_launcher;
        color = Color.LTGRAY;
    }

}
