package zhuoxin.com.viewpagerdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class sharedpres {
    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor editor;
    private static Set<String> sets = new TreeSet<>();
    private static Set<String> setAll = new TreeSet<>();
    private static boolean flag=true,flag1=true;
    public static void saveFlags(Context context,boolean flag){
        if(sp ==null){
            sp=context.getSharedPreferences("context",context.MODE_PRIVATE);
        }
        editor=sp.edit();
        editor.putBoolean("flag",flag);
        editor.commit();
    }

    public static Boolean getFlags(Context context){
        if(sp==null){
            sp = context.getSharedPreferences("context",context.MODE_APPEND);
        }
        editor = sp.edit();
        Boolean flag = sp.getBoolean("flag",true);
        return flag;
    }

    public static List<String> getLists(Context context){

        Set<String> datas = getSets(context);
        List<String> lists = new ArrayList<>();
        if(datas==null||datas.size()==0){
            lists.add("军事");
            lists.add("互联网");
            lists.add("房产");
            lists.add("娱乐");
            lists.add("游戏");
            lists.add("教育");
            lists.add("女人");
        }
        for (String s:datas) {
            lists.add(s);
        }
        return lists;
    }

    public  static Set<String> getSets(Context context){
        if(sp==null){
            sp = context.getSharedPreferences("sets",context.MODE_APPEND);
        }
        Set<String> datas = sp.getStringSet("data",sets);
        if(flag){
            datas.add("军事");
            datas.add("互联网");
            datas.add("房产");
            datas.add("娱乐");
            datas.add("游戏");
            flag=false;
        }
        return datas;
    }
    public  static Set<String> getSetAll(Context context){
        if(sp==null){
            sp = context.getSharedPreferences("setAll",context.MODE_APPEND);
        }
        Set<String> datas = sp.getStringSet("dataAll",setAll);
        if(flag1){
            datas.add("教育");
            datas.add("科技");
            datas.add("社会");
            datas.add("国内");
            datas.add("台湾");
            datas.add("国际");
            datas.add("汽车");
            datas.add("科技");
            datas.add("体育");
            datas.add("电影");
            datas.add("电视");
            flag1=flag;
        }
        return datas;
    }

    public static void saveLists(List<String> lists,Context context){
        if(sp==null){
            sp = context.getSharedPreferences("lists",context.MODE_APPEND);
        }
        Set<String> set = new HashSet<>();
        for (String s:lists) {
            set.add(s);
        }
        editor=sp.edit();
        editor.putStringSet("sets",set);
        editor.commit();
    }
    public static void saveSets(Set<String> sets,Context context){
        if(sp==null){
            sp = context.getSharedPreferences("sets",context.MODE_APPEND);
        }
        editor=sp.edit();
        editor.putStringSet("data",sets);
        editor.commit();
    }
    public static void saveSetAll(Set<String> setAll,Context context){
        if(sp==null){
            sp = context.getSharedPreferences("setAll",context.MODE_APPEND);
        }
        editor=sp.edit();
        editor.putStringSet("dataAll",setAll);
        editor.commit();
    }
}
