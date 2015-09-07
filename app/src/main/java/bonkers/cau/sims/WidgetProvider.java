package bonkers.cau.sims;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;


public class WidgetProvider extends AppWidgetProvider {


    private static final String ACTION_SIMS_ON = "com.sims.widget.On";
    private static final String ACTION_SIMS_OFF = "com.sims.widget.Off";
    private ComponentName simsWidget;
    private RemoteViews views = null;

    int widgetControl = 0;
    private Intent intent;

    //갱신 주기에 따라서 위젯 갱신할 때 호출.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        //TODO Auto-getnerated method stub
        Log.e("Widget State","onUpdate");

        simsWidget = new ComponentName(context, WidgetProvider.class );
        views = new RemoteViews(context.getPackageName(),R.layout.activity_sims_widget);

        if(widgetControl==0){

            views.setImageViewResource(R.id.sims_btn, R.mipmap.off);
            intent = new Intent(ACTION_SIMS_ON);

        }else if(widgetControl==1){

            views.setImageViewResource(R.id.sims_btn, R.mipmap.on);
            intent = new Intent(ACTION_SIMS_OFF);
        }

        PendingIntent onPendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.sims_btn, onPendingIntent);

        appWidgetManager.updateAppWidget(simsWidget,views);

    }

    @Override
    public void onReceive(Context context, Intent intent){
        //TODO Auto-generated method stub
        Log.e("Widget State","onReceive");

        String action = intent.getAction();

        if(action.equals(ACTION_SIMS_ON)){
            Log.e("Flash state", intent.getAction());
            try{
                    widgetControl = 1;

                Intent i = new Intent(context, MainService.class);
                context.startService(i);

                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class)));

            }catch(Exception e){
                //TODO: handle exception
                Log.e("Flash state", "Flash On Exception");
            }
        }else if(action.equals(ACTION_SIMS_OFF)){
            Log.e("Flash state",intent.getAction());
            try{
                widgetControl = 0;

                Intent i = new Intent(context, MainService.class);
                context.stopService(i);



                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                this.onUpdate(context, manager,manager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class)));

            }catch(Exception e){
                //TODO: handle exception
                Log.e("Flash state", "Flash OFF Exception");
            }
        }else{
            super.onReceive(context, intent);
        }
    }

    //위젯이 처름생성될 때 호출.
    @Override
    public void onEnabled(Context context){

        super.onEnabled(context);
    }

    //위젯이 마지막 인스턴스가 제거될 때 호출.
    @Override
    public void onDisabled(Context context){

        super.onDisabled(context);
    }
/*
    public static void updateAppWidget(Context context,
                                       AppWidgetManager appWidgetManager, int appWidgetId) {

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.KOREA);


        //  RemoteViews를 이용해 Text설정

        RemoteViews updateViews = new RemoteViews(context.getPackageName(),
                R.layout.activity_sims_widget);

        updateViews.setTextViewText(R.id.mText,
                mFormat.format(mCalendar.getTime()));



         // 위젯 업데이트

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);

    } */

}

