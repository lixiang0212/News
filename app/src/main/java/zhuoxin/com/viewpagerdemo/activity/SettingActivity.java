package zhuoxin.com.viewpagerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.utils.MyNotification;
import zhuoxin.com.viewpagerdemo.utils.sharedAuto;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Switch s_auto;
    private Switch s_tz;
    private Toolbar toolbar;
    private static Boolean flag, flag1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        s_auto = (Switch) findViewById(R.id.setting_sw_auto);
        s_tz= (Switch) findViewById(R.id.setting_sw_tz);
        toolbar = (Toolbar) findViewById(R.id.setting_toolBar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.btn_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        flag = sharedAuto.get(this, "s_auto");
        flag1 = sharedAuto.get(this, "s_tz");
        s_auto.setChecked(flag);
        s_tz.setChecked(flag1);
        s_auto.setOnClickListener(this);
        s_tz.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_sw_auto:
                Log.i("AAA","sw_auto");
                flag = !flag;
                s_auto.setChecked(flag);
                sharedAuto.save(this, "s_auto", flag);
                break;
            case R.id.setting_sw_tz:
                Log.i("AAA","sw_tz");
                flag1 = !flag1;
                s_tz.setChecked(flag1);
                sharedAuto.save(this, "s_tz", flag1);
                if (flag1) {
                    Log.i("AAA","sw_tz+yes");
                    MyNotification.openNotification(this);
                } else {
                    Log.i("AAA","sw_tz_no");
                    MyNotification.closeNotification(this);
                }
                break;
        }
    }
}