package zhuoxin.com.viewpagerdemo.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import zhuoxin.com.viewpagerdemo.info.userInfo;

public class SQLiteAdd {
    private MySQLiteOpenHelper openHelper;
    public SQLiteAdd(Context context) {
        openHelper=new MySQLiteOpenHelper(context);
    }
    public void insert(userInfo info){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("insert into login(name,passwd) values(?,?)",
                new Object[]{info.getName(),info.getPasswd()});
        db.close();
    }
    public void delete(int id){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete * from login.db where id="+id);
        db.close();
    }
    public List<userInfo> queryAll(){

        SQLiteDatabase db =openHelper.getWritableDatabase();
        Cursor cursor = db.query("login",null,null,null,null,null,"id ASC");//DESC逆序
        List<userInfo> list = new ArrayList<userInfo>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pd = cursor.getString(cursor.getColumnIndex("passwd"));
            list.add(new userInfo(name,pd));
        }
        cursor.close();
        db.close();
        return list;
    }
}
