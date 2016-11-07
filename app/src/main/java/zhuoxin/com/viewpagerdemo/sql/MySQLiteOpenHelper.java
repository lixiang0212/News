package zhuoxin.com.viewpagerdemo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    public MySQLiteOpenHelper(Context context) {
        super(context, "login.db", null, 5);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table login(id integer primary key autoincrement,name varchar[20]," +
                "passwd varchar[20])");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int now) {
        db.execSQL("alter table person add account varchar[20]");
    }
}
