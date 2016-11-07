package zhuoxin.com.viewpagerdemo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import zhuoxin.com.viewpagerdemo.info.CollectInfo;

public class MyCollectSQLiteUtil {
    private MyCollectHelper openHelper;
    private Context context;
    public MyCollectSQLiteUtil(Context context) {
        this.context=context;
        openHelper=new MyCollectHelper(context);
    }
    public void insert(CollectInfo info){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",info.getTitle());
        values.put("url",info.getUrl());
        long id =db.insert("collect",null,values);//表名
        info.setId(id);
        db.close();
    }
    public void delete(long id){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        //db.execSQL("delete * from collect where url="+url);
        db.delete("collect","id=?",new String[]{id+""});
        db.close();
    }

    public void update(CollectInfo info,long id){

        SQLiteDatabase db =openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",info.getTitle());
        values.put("url",info.url);
        db.update("collect",values,"id=?",new String[]{id+""});
        db.close();

    }
    public List<CollectInfo> select(String titles){
        SQLiteDatabase db =openHelper.getWritableDatabase();
        Cursor c =db.rawQuery("select * from collect where title like ?",new String[]{'%'+titles+'%'});
        List<CollectInfo> list = new ArrayList<CollectInfo>();
        CollectInfo info;
        while (c.moveToNext()){
            String title = c.getString(c.getColumnIndex("title"));
            String url = c.getString(c.getColumnIndex("url"));
            long id =c.getLong(c.getColumnIndex("id"));
            info = new CollectInfo(title,url);
            info.setId(id);
            list.add(info);
        }
        c.close();
        db.close();
        return list;
    }
    public List<CollectInfo> queryAll(){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query("collect",null,null,null,null,null,"id ASC");//DESC逆序
        List<CollectInfo> list = new ArrayList<CollectInfo>();
        CollectInfo info;
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            long id =cursor.getLong(cursor.getColumnIndex("id"));
            info = new CollectInfo(title,url);
            info.setId(id);
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }
}
