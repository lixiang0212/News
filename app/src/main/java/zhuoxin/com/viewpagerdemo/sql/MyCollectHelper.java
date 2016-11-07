package zhuoxin.com.viewpagerdemo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyCollectHelper extends SQLiteOpenHelper{

    public MyCollectHelper(Context context) {
        super(context, "collect.db", null, 5);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table collect(id integer primary key autoincrement,title varchar[20]," +
                "url varchar[50])");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int now) {
        db.execSQL("alter table person add account varchar[20]");
    }
}
