package bonkers.cau.sims;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    TextView textView ;
    Button button;

    //������� ���� ����Ǵ�
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        textView= (TextView)findViewById(R.id.bumseok);
        textView.setText("ok?");
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, PopupActivity.class);
                        startActivity(i);


                    }
                }
        );

    }


}
