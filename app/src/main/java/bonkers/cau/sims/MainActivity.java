package bonkers.cau.sims;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    String model = Build.MODEL;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LayoutInflater inflater = (LayoutInflater) getApplicationContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View myView;
        myView = inflater.inflate(R.layout.activity_main, null);
        myView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        setContentView(myView);
/*
Service 부분. 실행되었을 때 backgroud 에서 실행유지
*/
        Intent myIntent = new Intent(getBaseContext(), MainService.class);
        startService(myIntent);


        textView = (TextView) findViewById(R.id.model);
        textView.setText(model);

        packageList();



    }

     //package list
    public void packageList() {

        long keyPressedTime = 0;
        Toast toast;
        Context context = null;

        PackageManager pm = this.getPackageManager();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo pack : packs) {

            Log.i("TAG", pack.applicationInfo.loadLabel(pm).toString());

            Log.i("TAG", pack.packageName);

        }
    }

        //버튼 클릭시 어플실행
        @Override
        public boolean dispatchKeyEvent(KeyEvent event){

            Context context = getApplicationContext();

            //버튼선택
            if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {

                //버튼이눌렸을 때
                if(event.getAction() == KeyEvent.ACTION_DOWN){


                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
                    startActivity(intent);

                }
                return true;
            }
            return super.dispatchKeyEvent(event);

    }



}
