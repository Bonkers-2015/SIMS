package bonkers.cau.sims.boadcast;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bonkers.cau.sims.database.ListDBManager;
import bonkers.cau.sims.database.ListData;

public class TouchService extends Service {
    String touchPath;
    TouchTopView mView;
    private WindowManager.LayoutParams mParams;  //layout params 객체. 뷰의 위치 및 크기
    private WindowManager mWindowManager;          //윈도우 매니저
    private ListDBManager lDbManager;

    String data1;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mView = new TouchTopView(this);
        mParams = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,//항상 최 상위. 터치 이벤트 받을 수 있음.
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  //포커스를 가지지 않음
                PixelFormat.TRANSLUCENT);                                        //투명
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);  //윈도우 매니저
        mWindowManager.addView(mView, mParams);      //윈도우에 뷰 넣기. permission 필요.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data1 = intent.getStringExtra("data1");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(mWindowManager != null) {        //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
            if(mView != null) mWindowManager.removeView(mView);
        }
        super.onDestroy();
    }

    void lauchApp(Context context,String data1, String data2) {
        ListDBManager dbManager = new ListDBManager(context);
        ArrayList<ListData> listDataArrList = dbManager.selectAll();

        PackageManager packagemanager = context.getPackageManager();
        for (ListData list : listDataArrList) {
            if (list.getmData1().equals(data1)) {
                if (list.getmData2().equals(data2)) {

                    //어플 정보 받아오기
                    String appPackageName = list.getmAppPackage();
                    //앱실행
                    Intent i = packagemanager.getLaunchIntentForPackage(appPackageName);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    context.startActivity(i);

                }
            }
        }
    }

    public class TouchTopView extends View {
        Paint pt;

        static final float pi = 3.1415926535f;
        static final float rtd = 57.29577951f;
        static final float sectionNum = 8;
        static final float roundMinAngle = 2 * pi * 11 / 12;
        ArrayList<Vertex> arVertex1; //사용자가 터치한직선
        ArrayList<Vertex> arVertex2; //1차 보간된 선분
        ArrayList<Vertex> arVertex3; //

        Paint mPaint;
        TextView tv;

        Context c;

        public TouchTopView(Context context) {
            super(context);
            c = context;
            init_variable();
        }


        public void init_variable() {
            setBackgroundColor(Color.TRANSPARENT);
            arVertex1 = new ArrayList<Vertex>();
            arVertex2 = new ArrayList<Vertex>();
            arVertex3 = new ArrayList<Vertex>();

            mPaint = new Paint();

            pt = new Paint();
            mPaint = new Paint();

            tv = new TextView(this.getContext());

        }

        @Override
        public void onDraw(Canvas canvas) {

            mPaint.setStrokeWidth(6);

            for (int i = 1; i < arVertex1.size(); i++) {
                if (i == 1) {
                    mPaint.setColor(Color.BLACK);
                    mPaint.setAlpha(255);
                } else if (i == arVertex1.size() - 1) {
                    mPaint.setColor(Color.RED);
                    mPaint.setAlpha(255);
                } else {
                    mPaint.setColor(Color.BLUE);
                    mPaint.setAlpha(100);
                }


                //부드럽게 하기 위해서 원을 추가
                canvas.drawCircle(arVertex1.get(i - 1).x, arVertex1.get(i - 1).y, 3, mPaint);
                canvas.drawLine(arVertex1.get(i - 1).x, arVertex1.get(i - 1).y, arVertex1.get(i).x, arVertex1.get(i).y, mPaint);
                canvas.drawCircle(arVertex1.get(i).x, arVertex1.get(i).y, 3, mPaint);

            }

            for (int i = 1; i < arVertex2.size(); i++) {
                //부드럽게 하기 위해서 원을 추가
                float x1 = arVertex2.get(i - 1).x;
                float y1 = arVertex2.get(i - 1).y;
                float x2 = arVertex2.get(i).x;
                float y2 = arVertex2.get(i).y;
                float movePos = 25.f;
                x1 += movePos;
                y1 += movePos;
                x2 += movePos;
                y2 += movePos;
                mPaint.setColor(Color.GREEN);
                mPaint.setAlpha(120);
                canvas.drawLine(x1, y1, x2, y2, mPaint);
                mPaint.setAlpha(250);
                canvas.drawCircle(x2, y2, 3, mPaint);
                mPaint.setAlpha(250);
                canvas.drawCircle(x1, y1, 3, mPaint);

            }

            for (int i = 0; i < arVertex3.size(); i++) {
                //부드럽게 하기 위해서 원을 추가
                mPaint.setAlpha(250);
                float x1 = arVertex3.get(i).x;
                float y1 = arVertex3.get(i).y;
                float x2 = x1 + arVertex3.get(i).length * (float) Math.cos(arVertex3.get(i).radian);
                float y2 = y1 + (arVertex3.get(i).length * (float) Math.sin(arVertex3.get(i).radian));
                float movePos = 50.f;
                x1 += movePos;
                y1 += movePos;
                x2 += movePos;
                y2 += movePos;

                mPaint.setColor(Color.GRAY);
                canvas.drawLine(x1, y1, x2, y2, mPaint);
                mPaint.setColor(Color.RED);
                canvas.drawCircle(x2, y2, 3, mPaint);
                mPaint.setColor(Color.BLACK);
                canvas.drawCircle(x1, y1, 3, mPaint);

            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                arVertex1.removeAll(arVertex1);
                arVertex2.removeAll(arVertex2);
                arVertex3.removeAll(arVertex3);
                arVertex1.add(new Vertex(event.getX(), event.getY()));
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                arVertex1.add(new Vertex(event.getX(), event.getY()));
                invalidate();

                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float section = 2 * pi / sectionNum;
                float allAngle = 0, allAngle1 = 0, allLength = 0;
                boolean allAngleReset = true;
                arVertex2.add(arVertex1.get(0));
                for (int i = 1; i < arVertex1.size(); i += 1) {
                    float x2, y2;
                    x2 = arVertex1.get(i).x - arVertex1.get(i - 1).x;
                    y2 = arVertex1.get(i - 1).y - arVertex1.get(i).y;

                    //각도 구하기
                    float radian = getAngle(x2, y2);
                    //거리 구하기
                    float length = (float) Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));
                    //각도로 구역구하기
                    float tempang = (radian + (section / 2)) % (2 * pi);
                    int sec = (int) (tempang / section);

                    arVertex1.get(i).radian = radian;
                    arVertex1.get(i).length = length;
                    arVertex1.get(i).section = sec;

                    //이전 직선과의 각도차
                    if (!allAngleReset) {
                        float AngGap = arVertex1.get(i - 1).radian - arVertex1.get(i).radian;
                        if (AngGap > pi) {
                            AngGap -= 2 * pi;
                        } else if (AngGap < -pi) {
                            AngGap += 2 * pi;
                        }
                        allAngle += AngGap;
                        allAngle1 += AngGap;
                    } else {
                        allAngleReset = false;
                    }

                    allLength += length;

                    if (allAngle > section * 3 / 2 || allAngle < -section * 3 / 2) {

                        allAngleReset = false;
                        allAngle = 0;
                        arVertex2.add(arVertex1.get(i));
                    }
                }
                arVertex2.add(arVertex1.get(arVertex1.size() - 1));


                if (allAngle1 > roundMinAngle) {
                    int round = (int) (allAngle1 / (2 * pi));
                    if (allAngle1 % (2 * pi) > roundMinAngle) {
                        round++;
                    }
                    Toast.makeText(this.getContext(), "circle(countercolckwise) " + round + "round", Toast.LENGTH_SHORT).show();
                    touchPath = "circle<" + round;

                    lauchApp(getContext(),data1,"touch : "+touchPath);

                    return false;
                } else if (-allAngle1 > roundMinAngle) {
                    int round = (int) (-allAngle1 / (2 * pi));
                    if (-allAngle1 % (2 * pi) > roundMinAngle) {
                        round++;
                    }
                    Toast.makeText(this.getContext(), "circle(clockwise) " + round + "round ", Toast.LENGTH_SHORT).show();
                    touchPath = "circle>" + round;

                    lauchApp(getContext(),data1, "touch : "+touchPath);

                    return false;
                }
                float AllmoveAngle = 0;
                for (int i = 1; i < arVertex2.size(); i += 1) {
                    float x2, y2;
                    x2 = arVertex2.get(i).x - arVertex2.get(i - 1).x;
                    y2 = arVertex2.get(i - 1).y - arVertex2.get(i).y;


                    float length = (float) Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));
                    if (length < (allLength / (arVertex2.size()) / 2)) {
                        continue;
                    }
                    //각도 구하기
                    float radian = getAngle(x2, y2);

                    //첫번째 직선으로 보정
                    radian += AllmoveAngle;
                    //매칭되는 가장 가까운 직선각 구하기 22.5 도 회전
                    float tempang = (radian + (section / 2)) % (2 * pi);

                    float moveAngle = tempang % section;
                    moveAngle = (moveAngle < (section / 2) ? (section / 2) - moveAngle : (section / 2) - moveAngle);

                    if (i == 1)//첫번째 직선에대해 보정.
                    {
                        AllmoveAngle = moveAngle;
                    }
                    //각도로 구역구하기
                    int sec = (int) (tempang / section);


                    if (arVertex3.size() > 0) {
                        if (arVertex3.get(arVertex3.size() - 1).section == sec) {
                            arVertex3.get(arVertex3.size() - 1).length += length;
                            continue;
                        }
                    }
                    Vertex vertex = new Vertex(arVertex2.get(i - 1).x, arVertex2.get(i - 1).y);
                    vertex.radian = (radian + moveAngle);
                    vertex.length = length;
                    vertex.section = sec;
                    arVertex3.add(vertex);
                }

                invalidate();
                //텍스트 출력
                String str = "";
                for (int i = 0; i < arVertex3.size(); i++) {
                    str = str + arVertex3.get(i).section;
                    if (i < arVertex3.size() - 1)
                        str = str + ">";
                }

                Toast.makeText(this.getContext(), str, Toast.LENGTH_SHORT).show();
                touchPath = str;

                lauchApp(getContext(),data1, "touch : "+touchPath);

                return true;
            }
            return false;
        }

        //x = 0 직선에 대한 점의 각도를 계산한다
        float getAngle(float x2, float y2) {
            //기준선문
            float x1 = 1.f, y1 = 0.f;
            //0으로 나누는거 방지
            if (x2 == x1) {
                x2 *= 2;
                y2 *= 2;
            }
            float radian = -(float) Math.atan((y2 - y1) / (x2 - x1));

            //180도
            if (x2 < 0 && y2 == 0) {
                radian -= pi;
            }

            //사분면별 각도 조정
            if (y2 < y1 && x2 > x1) {
            } else if ((y2 < y1 && x2 < x1) ||
                    (y2 > y1 && x2 < x1)) {
                radian += pi;
            } else {
                radian += 2 * pi;
            }
            return radian;
        }

        public class Vertex {
            Vertex(float ax, float ay) {
                x = ax;
                y = ay;
            }

            float x;
            float y;
            float radian;
            float length;
            int section;
        }
    }

}
