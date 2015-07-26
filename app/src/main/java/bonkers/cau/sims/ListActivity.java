package bonkers.cau.sims;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;


public class ListActivity extends ActionBarActivity {
    ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainListView=(ListView)findViewById(R.id.mainListView);
    }
}
