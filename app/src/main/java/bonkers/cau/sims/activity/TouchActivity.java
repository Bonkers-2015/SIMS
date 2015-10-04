package bonkers.cau.sims.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import bonkers.cau.sims.R;


public class TouchActivity extends Activity  {

    TouchView tv;
    FrameLayout mlayout;
    ImageButton saveBtn;
    String touchPath;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tv = new TouchView(this);
        setContentView(R.layout.activity_touch);
        mlayout = (FrameLayout) findViewById(R.id.frame_touch);
        mlayout.addView(tv, 0);
        saveBtn = (ImageButton) findViewById(R.id.btn_touch_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add edit Activity�� ������ ������ resultText Key ���� "superdroid result" ���ڿ���
                //Extra�� Intent�� ��Ҵ�.
                Intent intent = new Intent();

                intent.putExtra("resultText", touchPath);
                intent.putExtra("resultType", "touch");

                // ������ Intent�� �����ϰ� finish()�Լ��� ����
                //B Activity�� �����Ŵ�� ���ÿ� ����� Intent�� �����Ͽ���.
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }


    public class TouchView extends View {
        Paint pt;

        static final float pi = 3.1415926535f;
        static final float rtd = 57.29577951f;
        static final float sectionNum = 8;
        static final float roundMinAngle = 2 * pi * 11 / 12;
        ArrayList<Vertex> arVertex1; //����ڰ� ��ġ������
        ArrayList<Vertex> arVertex2; //1�� ������ ����
        ArrayList<Vertex> arVertex3; //

        Paint mPaint;
        TextView tv;

        Context c;

        public TouchView(Context context) {
            super(context);
            c = context;
            init_variable();
        }


        public void init_variable() {
            setBackgroundColor(Color.WHITE);
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


                //�ε巴�� �ϱ� ���ؼ� ���� �߰�
                canvas.drawCircle(arVertex1.get(i - 1).x, arVertex1.get(i - 1).y, 3, mPaint);
                canvas.drawLine(arVertex1.get(i - 1).x, arVertex1.get(i - 1).y, arVertex1.get(i).x, arVertex1.get(i).y, mPaint);
                canvas.drawCircle(arVertex1.get(i).x, arVertex1.get(i).y, 3, mPaint);

            }

            for (int i = 1; i < arVertex2.size(); i++) {
                //�ε巴�� �ϱ� ���ؼ� ���� �߰�
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
                //�ε巴�� �ϱ� ���ؼ� ���� �߰�
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

                    //���� ���ϱ�
                    float radian = getAngle(x2, y2);
                    //�Ÿ� ���ϱ�
                    float length = (float) Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));
                    //������ �������ϱ�
                    float tempang = (radian + (section / 2)) % (2 * pi);
                    int sec = (int) (tempang / section);

                    arVertex1.get(i).radian = radian;
                    arVertex1.get(i).length = length;
                    arVertex1.get(i).section = sec;

                    //���� �������� ������
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
                    Log.v("test", i + "������  ����  : " + sec + "  ���� : " + (int) (radian * rtd) +
                            " ������ : " + allLength + " �������� : " + (int) (allAngle * rtd) + "    " + (int) (allAngle1 * rtd));

                    if (allAngle > section * 3 / 2 || allAngle < -section * 3 / 2) {
                        Log.v("test", i + "��°" +
                                " ������ ���� : " + (int) (allAngle * rtd) +
                                " �� ���̴� " + allLength);

                        allAngleReset = false;
                        allAngle = 0;
                        arVertex2.add(arVertex1.get(i));
                    }
                }
                arVertex2.add(arVertex1.get(arVertex1.size() - 1));

                Log.v("test", "=========> �Ѱ��� : " + (int) (allAngle * rtd));

                if (allAngle1 > roundMinAngle) {
                    int round = (int) (allAngle1 / (2 * pi));
                    if (allAngle1 % (2 * pi) > roundMinAngle) {
                        round++;
                    }
                    touchPath = "circle<" + round;
                    return false;
                } else if (-allAngle1 > roundMinAngle) {
                    int round = (int) (-allAngle1 / (2 * pi));
                    if (-allAngle1 % (2 * pi) > roundMinAngle) {
                        round++;
                    }
                    touchPath = "circle>" + round;
                    return false;
                }
                float AllmoveAngle = 0;
                for (int i = 1; i < arVertex2.size(); i += 1) {
                    float x2, y2;
                    x2 = arVertex2.get(i).x - arVertex2.get(i - 1).x;
                    y2 = arVertex2.get(i - 1).y - arVertex2.get(i).y;


                    float length = (float) Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));
                    if (length < (allLength / (arVertex2.size()) / 2)) {
                        Log.v("test", "2�ܰ�   ==> ��ü ���� : " + allLength / (arVertex2.size()) + " �̺κб��� : " + length);
                        continue;
                    }
                    //���� ���ϱ�
                    float radian = getAngle(x2, y2);

                    //ù��° �������� ����
                    radian += AllmoveAngle;
                    //��Ī�Ǵ� ���� ����� ������ ���ϱ� 22.5 �� ȸ��
                    float tempang = (radian + (section / 2)) % (2 * pi);

                    float moveAngle = tempang % section;
                    moveAngle = (moveAngle < (section / 2) ? (section / 2) - moveAngle : (section / 2) - moveAngle);

                    if (i == 1)//ù��° ���������� ����.
                    {
                        AllmoveAngle = moveAngle;
                    }
                    //������ �������ϱ�
                    int sec = (int) (tempang / section);

                    Log.v("test", "2�ܰ�   ==> " + i + "������   ����  : " + sec + "  ���� : " + (int) ((radian + moveAngle) * rtd));

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
                //�ؽ�Ʈ ���
                String str = "";
                for (int i = 0; i < arVertex3.size(); i++) {
                    str = str + arVertex3.get(i).section;
                    if (i < arVertex3.size() - 1)
                        str = str + ">";
                }
                touchPath = str;

                return true;
            }
            return false;
        }

        //x = 0 ������ ���� ���� ������ ����Ѵ�
        float getAngle(float x2, float y2) {
            //���ؼ���
            float x1 = 1.f, y1 = 0.f;
            //0���� �����°� ����
            if (x2 == x1) {
                x2 *= 2;
                y2 *= 2;
            }
            float radian = -(float) Math.atan((y2 - y1) / (x2 - x1));

            //180��
            if (x2 < 0 && y2 == 0) {
                radian -= pi;
            }

            //��и麰 ���� ����
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

