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

        // 현재 시간 입력
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        // 날짜 표시 형식 설정
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat CurSecondFormat = new SimpleDateFormat("ss");

        // String type 저장
        String strCurYear = CurYearFormat.format(date);
        String strCurMonth = CurMonthFormat.format(date);
        String strCurDay = CurDayFormat.format(date);
        String strCurHour = CurHourFormat.format(date);
        String strCurMinute = CurMinuteFormat.format(date);
        String strCurSecond = CurSecondFormat.format(date);

        view.setDrawingCacheEnabled(true);
        Bitmap screenshot = view.getDrawingCache();

        // 파일명을 날짜로 지정
        String filename = strCurYear + strCurMonth + strCurDay + strCurHour + strCurMinute + strCurSecond;

        // 전체화면 캡쳐 : Activity.getWindow().getDecorView()

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
