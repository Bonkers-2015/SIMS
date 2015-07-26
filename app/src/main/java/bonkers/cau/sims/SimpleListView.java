package bonkers.cau.sims;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class SimpleListView extends ActionBarActivity {
    private ListView first_setting,second_setting,icon;
    private ArrayAdapter<String> listAdapter1,listAdapter2,listAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first_setting=(ListView)findViewById(R.id.first_setting);
        second_setting=(ListView)findViewById(R.id.second_setting);
        icon = (ListView)findViewById(R.id.icon);

        String[] firstsetting_string = new String[] {"상","하","상"};
        String[] secondsetting_string = new String[] {"하","상","상"};
        String[] iconsetting_string = new String[] {"KaKaoTalk","Line","Call"};

        ArrayList<String> firstsetting_List = new ArrayList<String>();
        ArrayList<String> secondsetting_List = new ArrayList<String>();
        ArrayList<String> icon_List = new ArrayList<String>();

        firstsetting_List.addAll(Arrays.asList(firstsetting_string ));
        secondsetting_List.addAll(Arrays.asList(secondsetting_string));
        icon_List.addAll(Arrays.asList(iconsetting_string));

        listAdapter1 = new ArrayAdapter<String>(this,R.layout.activity_eachrow,firstsetting_List);
        listAdapter2 = new ArrayAdapter<String>(this,R.layout.activity_eachrow,secondsetting_List);
        listAdapter3 = new ArrayAdapter<String>(this,R.layout.activity_eachrow,icon_List);

        listAdapter1.add("Touch");
        listAdapter2.add("하");
        listAdapter3.add("ScreenShot");

        first_setting.setAdapter(listAdapter1);
        second_setting.setAdapter(listAdapter2);
        icon.setAdapter(listAdapter3);
    }
}
