package bonkers.cau.sims;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

<<<<<<< HEAD
/**
 * Created by ±è½Â¿í on 2015-07-30.
 */
=======
>>>>>>> db5829acd1c9d1f3c250030acd2a52fc94f5775b
public class ListData {
    public Drawable mIcon;
    public String mTitle;
    public String mData;

    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListData_1, ListData mListData_2) {
            return sCollator.compare(mListData_1.mTitle,mListData_2.mTitle);
        }
    };
<<<<<<< HEAD
}

=======
}
>>>>>>> db5829acd1c9d1f3c250030acd2a52fc94f5775b
