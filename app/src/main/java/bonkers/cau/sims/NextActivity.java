/* Shortcuts in my style */

package bonkers.cau.sims;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;


public class NextActivity extends ActionBarActivity {

    String model = Build.MODEL;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Log.d("MODEL", model);

        textView=(TextView)findViewById(R.id.model);
        textView.setText(model);
    }
}
