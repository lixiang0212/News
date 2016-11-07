package zhuoxin.com.viewpagerdemo.activity.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout();
        initView();
        initListener();
        initData();

    }
    public abstract void setContentLayout();
    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
    public void startActivity(Class<?> a){
        Intent intent = new Intent();
        intent.setClass(this,a);
        startActivity(intent);
    }
    public String getAppVersionName(){
        String name ="";
        try {
            name=getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
    public String getAppVersionCode(){
       int Code =0;
        try {
            Code=getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Code+"";
    }
}
