package bonkers.cau.sims;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;


public class Popup extends ActionBarActivity {
    TextView popup;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup);
        popup=(TextView)findViewById(R.id.Popup);
        popup.setText("Success");

     }


}
