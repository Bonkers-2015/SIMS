package bonkers.cau.sims;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ±è½Â¿í on 2015-07-31.
 */
public class DismissActivity extends Activity{
    ListView mListView;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_list);

        mListView = (ListView)findViewById(R.id.listView1);

        String[] items = new String[20];
        for(int i=0; i<items.length;i++) {
            items[i] = "Item" + (i+1);
        }
        mAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_2,android.R.id.text2,
                new ArrayList<String>(Arrays.asList(items)));
        mListView.setAdapter(mAdapter);

        SwipeDismissListViewTouchListener touchListner =
                new SwipeDismissListViewTouchListener (mListView,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int positioin) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView,int[] reverseSortedPositions) {
                        for (int position:reverseSortedPositions) {
                            mAdapter.remove(mAdapter.getItem(position));
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
        mListView.setOnTouchListener(touchListener);
        mListView.setOnScrollListener(touchListener.makeScrollListener());
    }
}
