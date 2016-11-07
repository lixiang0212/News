package zhuoxin.com.viewpagerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.adapter.CollectAdapter;
import zhuoxin.com.viewpagerdemo.info.CollectInfo;
import zhuoxin.com.viewpagerdemo.interfaces.NewsReturnInterface;
import zhuoxin.com.viewpagerdemo.sql.MyCollectSQLiteUtil;

public class CollectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv;
    private CollectAdapter adapter;
    private List<CollectInfo> datas;
    private MyCollectSQLiteUtil sqlUtil;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
    }
    private void initView() {
        sqlUtil = new MyCollectSQLiteUtil(this);
        datas = new ArrayList<>();
        toolbar= (Toolbar) findViewById(R.id.collect_toolBar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.btn_return));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        lv= (ListView) findViewById(R.id.collect_listView);
        datas = sqlUtil.queryAll();
        adapter = new CollectAdapter(this,datas);
        lv.setAdapter(adapter);
    }
}
