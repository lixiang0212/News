package zhuoxin.com.viewpagerdemo.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.LookActivity;
import zhuoxin.com.viewpagerdemo.info.CollectInfo;
import zhuoxin.com.viewpagerdemo.interfaces.NewsReturnInterface;
import zhuoxin.com.viewpagerdemo.interfaces.OnClickInterface;
import zhuoxin.com.viewpagerdemo.sql.MyCollectSQLiteUtil;

public class CollectAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<CollectInfo> datas;
    private MyCollectSQLiteUtil util;
    public CollectAdapter(Context context, final List<CollectInfo> datas) {
        this.context = context;
        this.datas = datas;
        inflater=LayoutInflater.from(context);
        util = new MyCollectSQLiteUtil(context);
    }
    private class  Temp{
        private TextView tv;
        private ImageButton bt_go,bt_del;
    }
    public int getCount() {
        return datas.size();
    }

    public CollectInfo getItem(int position) {
        return datas.get(position);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Temp temp ;
        if(view==null){
            temp =new Temp();
            view =inflater.inflate(R.layout.inflate_collect_item,null);
            temp.tv= (TextView) view.findViewById(R.id.collect_item_tv);
            temp.bt_go= (ImageButton) view.findViewById(R.id.collect_item_go);
            temp.bt_del= (ImageButton) view.findViewById(R.id.collect_item_del);
            view.setTag(temp);
        }else {
            temp= (Temp) view.getTag();
        }
        final View dialogView =inflater.inflate(R.layout.inflate_collect_dialog_view,null);
//        TextView now_title = (TextView) dialogView.findViewById(R.id.collect_dialog_title);
//        TextView now_title1 = (TextView) dialogView.findViewById(R.id.collect_dialog_title1);
        TextView now_et = (TextView) dialogView.findViewById(R.id.collect_dialog_et);
        final TextView now_et1 = (TextView) dialogView.findViewById(R.id.collect_dialog_et1);
        final CollectInfo info = datas.get(position);
        temp.tv.setText(info.getTitle());
        now_et.setText(info.getTitle());
        temp.tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, LookActivity.class);
                intent.putExtra("title",info.getTitle());
                intent.putExtra("url",info.getUrl());
                context.startActivity(intent);
            }
        });
        temp.bt_go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               new AlertDialog.Builder(context)
                        .setIcon(R.drawable.dddss)
                        .setTitle("标题")
                        .setView(dialogView)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title =now_et1.getText().toString();
                                if (title.length()>0){
                                    util.update(new CollectInfo(title,info.getUrl()),info.getId());
                                    temp.tv.setText(title);
                                    datas=util.queryAll();
                                    notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(context,"请输入有效的标题",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
                notifyDataSetChanged();
            }
        });
        temp.bt_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.dddss)
                        .setTitle("标题")
                        .setMessage("您真的要删除？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                datas.remove(position);
                                util.delete(info.id);
                                notifyDataSetChanged();
                            }
                        }).show();
            }
        });
        return view;
    }
}
