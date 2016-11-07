package zhuoxin.com.viewpagerdemo.activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.base.BaseActivity;
import zhuoxin.com.viewpagerdemo.interfaces.DeleteInterface;
import zhuoxin.com.viewpagerdemo.myview.FlowAddLayout;
import zhuoxin.com.viewpagerdemo.myview.FlowLayout;
import zhuoxin.com.viewpagerdemo.utils.sharedpres;

public class TitleActivity extends BaseActivity{

    private Toolbar toolbar;
    private TextView btn_add;
    private FlowLayout flowLayout;
    private FlowAddLayout flow_add;
    private boolean flag = false;

    public void setContentLayout() {
        setContentView(R.layout.activity_title);
    }

    public void initView() {
        toolbar=(Toolbar)findViewById(R.id.title_tool_bar);
        toolbar.setNavigationIcon(R.drawable.btn_return);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        flowLayout=(FlowLayout)findViewById(R.id.title_flow_layout);
        flow_add=(FlowAddLayout)findViewById(R.id.title_flow_add);
        btn_add=(TextView) findViewById(R.id.activity_title_add);
    }

    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("flag",flag);
                setResult(101,intent);
                finish();
            }
        });
    }
    public void initData() {
        flowLayout.getSetData(sharedpres.getSets(this));
        flowLayout.setDeleteInterface(new DeleteInterface() {
            public void deleteData() {
                flowLayout.getSetData(sharedpres.getSets(TitleActivity.this));
                flow_add.getSetData(sharedpres.getSetAll(TitleActivity.this));
                flag=true;
            }
        });
        flow_add.getSetData(sharedpres.getSetAll(this));
        flow_add.setDeleteInterface(new DeleteInterface() {
            public void deleteData() {
                flowLayout.getSetData(sharedpres.getSets(TitleActivity.this));
                flow_add.getSetData(sharedpres.getSetAll(TitleActivity.this));
                flag=true;
            }
        });
    }

}
