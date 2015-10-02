package bonkers.cau.sims;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dongbin on 2015-10-02.
 */
public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttr(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttr(context, attrs);
    }

    public MyTextView(Context context) {
        super(context);
    }

    private void setAttr(Context context, AttributeSet attrs){
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        String typefaceName = arr.getString(R.styleable.MyTextView_typeface);
        Typeface typeface = null;
        try{
            typeface = Typeface.createFromAsset(context.getAssets(), typefaceName);
            setTypeface(typeface);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}