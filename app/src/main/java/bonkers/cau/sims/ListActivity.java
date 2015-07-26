package bonkers.cau.sims;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class ListActivity extends ActionBarActivity {
    EditText nameSIMS;
    ListView first_setting,second_setting,icon;
    Button addbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameSIMS=(EditText)findViewById(R.id.nameSIMS);
        first_setting=(ListView)findViewById(R.id.first_setting);
        second_setting=(ListView)findViewById(R.id.second_setting);
        icon=(ListView)findViewById(R.id.icon);
        addbutton= (Button)findViewById(R.id.addbutton);
    }
}
