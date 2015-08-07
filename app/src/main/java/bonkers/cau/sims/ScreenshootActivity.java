package bonkers.cau.sims;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ScreenshootActivity extends ActionBarActivity {
    Activity av = ScreenshootActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshoot);


        // 아래 line 을 수정 -> 지정된 버튼이 눌렸을때로
        Button button = (Button) findViewById(R.id.button1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    screenshot(av);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    public void screenshot(Activity av2)throws Exception {
        View view = av2.getWindow().getDecorView();

        view.setDrawingCacheEnabled(true);

        Bitmap screenshot = view.getDrawingCache();
        //Activity.getWindow().getDecorView()

        String filename = "screenshot.png";

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