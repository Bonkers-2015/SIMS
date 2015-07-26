package bonkers.cau.sims;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


public class AddEditActivity extends Activity {
    int phoneBtnCount,phoneMotionCount;

    private String mModelName;

    private Button[] mButtons;
    private RelativeLayout.LayoutParams[] mrlPramas;
    private RelativeLayout mRelativeLayout;

     Button btnTest;
     RelativeLayout.LayoutParams rlPramas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // (임시로) 모델 "A" 전송
        mModelName = "A";

        test();

//        setLayout();
//        init();

    }

    public void test(){

        btnTest = (Button)findViewById(R.id.btn_1);
        rlPramas = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rlPramas.addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
        rlPramas.addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);


        btnTest.setLayoutParams(rlPramas);

    }

    private void setLayout(){
        mButtons = new Button[]{
                (Button)findViewById(R.id.btn_1),
                (Button)findViewById(R.id.btn_2),
                (Button)findViewById(R.id.btn_3),
                new Button(AddEditActivity.this),
        };

        mrlPramas = new RelativeLayout.LayoutParams[]{
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT),
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT),
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT),
        };

    }

    private void init(){
        checkModel();

        for(int i=0;i<mButtons.length;i++) {
            mButtons[i].setLayoutParams(mrlPramas[i]);

        }
    }

    private void checkModel() {

        if (mModelName == "A") {

            // 폰 가로,세로 비율
            //버튼 갯수
            //모션 갯수
            //폰 이미지
            phoneBtnCount = 3;
            phoneMotionCount = 2;

            mrlPramas[0].addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
            mrlPramas[0].addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);
            mrlPramas[1].addRule(RelativeLayout.RIGHT_OF, R.id.btn_main);
            mrlPramas[1].addRule(RelativeLayout.ALIGN_TOP, R.id.btn_main);
            mrlPramas[2].addRule(RelativeLayout.LEFT_OF, R.id.btn_main);
            mrlPramas[2].addRule(RelativeLayout.ALIGN_BOTTOM, R.id.btn_main);


        } else if (mModelName == "B") {

        } else if (mModelName == "C") {

        } else {
            //데이터가 없습니다.
        }
    }


    private void Setting(){


    }
}
