package trannguyentanthuan.sqlite2903;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    // name ten sql , cursor con trỏ
    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy vấn không trả kết quả
    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    //truy vấn có trẻ kết quả
    public Cursor GetData(String sql){
        SQLiteDatabase database =getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
