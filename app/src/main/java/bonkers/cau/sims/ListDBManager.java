package bonkers.cau.sims;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by redpe_000 on 2015-08-03.
 */
public class ListDBManager {
    // DB���� ��� ����
    private static final String dbName = "SimsInfo.db";
    private static final String tableName = "ListData";
    public static final int dbVersion = 1;

    // DB���� ��ü ����
    private OpenHelper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // �ΰ����� ��ü��
    private Context context;

    // ������
    public ListDBManager(Context context) {
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

        // ������ DB�� ���� ��쿡 �ѹ��� ȣ���
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            // String dropSql = "drop table if exists " + tableName;
            // db.execSQL(dropSql);

            String createSql = "create table " + tableName + " (id integer primary key autoincrement," +
                    "indexnum integer, data1 text, data2 text, appName text,appPackage text, phoneName text, phoneNumber text);";

            arg0.execSQL(createSql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

    // ������ �߰�
    public void insertAppData(ListData data) {
        String sql = "insert into " + tableName+ " values(NULL, "
                + data.getIndexNum() + ", '" + data.getmData1() +"', '" + data.getmData2() + "', '"
                + data.getmAppName() +"', '"+data.getmAppPackage()+ "', NULL,NULL);";

        db.execSQL(sql);
    }
    public void insertPhoneData(ListData data) {
        String sql = "insert into " + tableName+ " values(NULL, "
                + data.getIndexNum() + ", '" + data.getmData1() +"', '" + data.getmData2() + "', NULL, '"
                +", NULL, '"+ data.getmPhoneName() + "', '" + data.getmPhoneNumber() + "');";

        db.execSQL(sql);
    }

    // ������ ����
    public void updateAppData(ListData data, int index) {
        String sql = "update " + tableName + " set indexnum = " + data.getIndexNum()
                + ", data1 = '" + data.getmData1()+ "', data2 = '" + data.getmData2()+ "', appName = '" + data.getmAppName()
                + "', appPackage = '" +data.getmAppPackage()+ "', phoneName = NULL, phoneNumber = NULL where id = " + index + ";";

        db.execSQL(sql);
    }
    public void updatePhoneData(ListData data, int index) {
        String sql = "update " + tableName + " set indexnum = " + data.getIndexNum()
                + ", data1 = '" + data.getmData1()+ "', data2 = '" + data.getmData2()+ "', appName = NULL, phoneName = '"
                + data.getmPhoneName()+ "', phoneNumber = '" + data.getmPhoneNumber() + "' where id = " + index + ";";

        db.execSQL(sql);
    }

    // ������ ����
    public void removeData(int index) {
        String sql = "delete from " + tableName + " where id = " + index + ";";
        db.execSQL(sql);
    }

    // ������ �˻�
    public ListData selectData(int index) {
        String sql = "select * from " + tableName + " where id = " + index
                + ";";
        Cursor result = db.rawQuery(sql, null);

        // result(Cursor ��ü)�� ��� ������ false ����
        if (result.moveToFirst()) {
            ListData data = new ListData(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7));
            result.close();
            return data;
        }
        result.close();
        return null;
    }

    // ������ ��ü �˻�
    public ArrayList<ListData> selectAll() {
        String sql = "select * from " + tableName + ";";
        Cursor results = db.rawQuery(sql, null);

        results.moveToFirst();
        ArrayList<ListData> infos = new ArrayList<ListData>();

        while (!results.isAfterLast()) {
            ListData info = new ListData(results.getInt(0),results.getInt(1),results.getString(2),results.getString(3)
                    ,results.getString(4),results.getString(5),results.getString(6),results.getString(7));
            infos.add(info);
            results.moveToNext();
        }
        results.close();
        return infos;
    }
}
