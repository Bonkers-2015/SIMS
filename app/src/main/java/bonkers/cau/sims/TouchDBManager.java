package bonkers.cau.sims;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by redpe_000 on 2015-08-03.
 */
public class TouchDBManager {
    // DB관련 상수 선언
    private static final String dbName = "SimsInfo2.db";
    private static final String tableName = "TouchData";
    public static final int dbVersion = 1;

    // DB관련 객체 선언
    private OpenHelper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context context;

    // 생성자
    public TouchDBManager(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    // Opener of DB and Table
    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
            super(context, name, null, version);
            // TODO Auto-generated constructor stub
        }

        // 생성된 DB가 없을 경우에 한번만 호출됨
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            // String dropSql = "drop table if exists " + tableName;
            // db.execSQL(dropSql);

            String createSql = "create table " + tableName + " (id integer primary key autoincrement," +
                    "touchName text, touchPath text);";

            arg0.execSQL(createSql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

    // 데이터 추가
    public void insertData(TouchData data) {
        String sql = "insert into " + tableName+ " values(NULL, " + data.getmTouchName() + ", '" + data.getmTouchPath() +"');";

        db.execSQL(sql);
    }

    // 데이터 갱신
    public void updateData(TouchData data, int id) {
        String sql = "update " + tableName + " set touchName = '" + data.getmTouchName()+ "', touchPath = '" + data.getmTouchPath()+ "' where id = " + id + ";";

        db.execSQL(sql);
    }

    // 데이터 삭제
    public void removeData(int id) {
        String sql = "delete from " + tableName + " where id = " + id + ";";
        db.execSQL(sql);
    }

    // 데이터 검색
    public TouchData selectData(int id) {
        String sql = "select * from " + tableName + " where id = " + id
                + ";";
        Cursor result = db.rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            TouchData data = new TouchData(result.getString(1),result.getString(2));
            result.close();
            return data;
        }
        result.close();
        return null;
    }

    // 데이터 전체 검색
    public ArrayList<TouchData> selectAll() {
        String sql = "select * from " + tableName + ";";
        Cursor results = db.rawQuery(sql, null);

        results.moveToFirst();
        ArrayList<TouchData> infos = new ArrayList<TouchData>();

        while (!results.isAfterLast()) {
            TouchData info = new TouchData(results.getInt(0),results.getString(1),results.getString(2));
            infos.add(info);
            results.moveToNext();
        }
        results.close();
        return infos;
    }
}
