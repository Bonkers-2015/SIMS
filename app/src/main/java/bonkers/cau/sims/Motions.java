package bonkers.cau.sims;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by dongbin on 2015-07-31.
 */

public class Motions extends Button{

    public String name;
    public boolean onOff;
    public RelativeLayout.LayoutParams params;
    public int motioncode;
    public String iconName;

    public Motions(Context context) {
        super(context);


        this.name = new String("NULL");
        this.onOff = false;
        this.params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.motioncode=0;

    }

}
