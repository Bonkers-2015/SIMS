package bonkers.cau.sims;

import android.content.Context;
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
    public int keycode;
    public String iconName;

    public Buttons(Context context) {
        super(context);


        this.name = new String("NULL");
        this.onOff = false;
        this.params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.keycode=0;

    }

}
