package bonkers.cau.sims;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// KGM 150810
public class ScreenshotActivity extends ActionBarActivity {
    public void screenshot(View view)throws Exception {

        // ���� �ð� �Է�
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        // ��¥ ǥ�� ���� ����
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat CurSecondFormat = new SimpleDateFormat("ss");

        // String type ����
        String strCurYear = CurYearFormat.format(date);
        String strCurMonth = CurMonthFormat.format(date);
        String strCurDay = CurDayFormat.format(date);
        String strCurHour = CurHourFormat.format(date);
        String strCurMinute = CurMinuteFormat.format(date);
        String strCurSecond = CurSecondFormat.format(date);

        view.setDrawingCacheEnabled(true);
        Bitmap screenshot = view.getDrawingCache();

        // ���ϸ��� ��¥�� ����
        String filename = strCurYear + strCurMonth + strCurDay + strCurHour + strCurMinute + strCurSecond;

        // ��üȭ�� ĸ�� : Activity.getWindow().getDecorView()

        try {
            File f = new File(Environment.getExternalStorageDirectory(), filename);
            f.createNewFile();
            OutputStream outStream = new FileOutputStream(f);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setDrawingCacheEnabled(false);
    }
}
