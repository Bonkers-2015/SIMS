package bonkers.cau.sims;

import android.graphics.drawable.Drawable;
import android.widget.Button;

/**
 * Created by dongbin on 2015-07-31.
 */

public class Buttons{

    private String name;
    private boolean onOff;
    private Drawable onImage,offImage;
    private Button button;

    public Buttons(String name, Button btn) {

        this.button = btn;
        this.name = name;
        this.onOff = false;

    }

    public void setName(String name ){
        this.name = name;
    }
    public void setOnOFF(Boolean onOff){
        this.onOff = onOff;

        if(onOff == true)
            button.setBackground(onImage);
        else
            button.setBackground(offImage);
    }

    public void setImage(Drawable onIcon, Drawable offIcon){
        onImage = onIcon;
        offImage = offIcon;
    }

    public String getName(){
        return name;
    }
    public boolean getOnOFF(){
        return onOff;
    }
    public Button getButton(){
        return button;
    }

}
