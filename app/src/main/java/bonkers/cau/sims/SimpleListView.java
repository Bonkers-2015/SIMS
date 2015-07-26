package bonkers.cau.sims;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class SimpleListView extends ActionBarActivity {
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainListView = (ListView)findViewById(R.id.mainListView);

        String[] Applications = new String[] {"KaKaoTalk","Line","Call"};

        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll(Arrays.asList(Applications));

        listAdapter = new ArrayAdapter<String>(this,R.layout.activity_eachrow,planetList);

        listAdapter.add("ScreenShot");

        mainListView.setAdapter(listAdapter);
    }
}
