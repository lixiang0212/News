package zhuoxin.com.viewpagerdemo.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.base.BaseActivity;
import zhuoxin.com.viewpagerdemo.fragment.FragmentFind;
import zhuoxin.com.viewpagerdemo.fragment.FragmentHot;
import zhuoxin.com.viewpagerdemo.fragment.FragmentNews;

public class LogoActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_one,btn_two,btn_three;
    private FragmentNews fragment_one;
    private FragmentHot fragment_two;
    private FragmentFind fragment_three;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView aView;

    public void setContentLayout(){
        setContentView(R.layout.activity_logo);
    }
    public void initView() {
        btn_one=(Button)findViewById(R.id.logo_btn_one);
        btn_two=(Button)findViewById(R.id.logo_btn_two);
        btn_three=(Button)findViewById(R.id.logo_btn_three);
        manager=getSupportFragmentManager();
    }
    public void initListener(){
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        initToolBar();
    }
    private void initToolBar(){
        toolbar =(Toolbar)findViewById(R.id.logo_toolBar);
        toolbar.setTitle("");
//      toolbar.setLogo(getResources().getDrawable(R.drawable.icon_select));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_select));
        setSupportActionBar(toolbar);
        drawerLayout =(DrawerLayout)findViewById(R.id.logo_drawerLayout);
        aView=(NavigationView)findViewById(R.id.logo_NavigationView);
//      getSupportActionBar().setHomeButtonEnabled(true);//可以点击图标
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回图标
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name
        ,R.string.app_name);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        aView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logo_menu_collect:
                        Intent intent = new Intent(LogoActivity.this,CollectActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logo_menu_sheZhi:
                        Intent intent1 = new Intent(LogoActivity.this,SettingActivity.class);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logo_toolbar_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        showShare();
        return true;
    }

    public void initData() {
        fragment_one = new FragmentNews();
        fragment_two = new FragmentHot();
        fragment_three = new FragmentFind();
        transaction =manager.beginTransaction();
        transaction.add(R.id.logo_frameLayout,fragment_one);
        transaction.add(R.id.logo_frameLayout,fragment_two);
        transaction.add(R.id.logo_frameLayout,fragment_three);
        transaction.show(fragment_one);
        transaction.hide(fragment_two);
        transaction.hide(fragment_three);
        transaction.commit();
        btn_one.setBackground(getResources().getDrawable(R.drawable.new_selected));
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
    public void onClick(View view) {

        transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.logo_btn_one:
                if(fragment_one == null){
                    fragment_one = new FragmentNews();
                }
                transaction.show(fragment_one);
                transaction.hide(fragment_two);
                transaction.hide(fragment_three);
                btn_one.setBackground(getResources().getDrawable(R.drawable.new_selected));
                btn_two.setBackground(getResources().getDrawable(R.drawable.collect_unselected));
                btn_three.setBackground(getResources().getDrawable(R.drawable.find_defult));
                break;
            case R.id.logo_btn_two:
                if(fragment_two == null){
                    fragment_two = new FragmentHot();
                }
                transaction.hide(fragment_one);
                transaction.show(fragment_two);
                transaction.hide(fragment_three);
                btn_one.setBackground(getResources().getDrawable(R.drawable.new_unselected));
                btn_two.setBackground(getResources().getDrawable(R.drawable.collect_selected));
                btn_three.setBackground(getResources().getDrawable(R.drawable.find_defult));
                break;
            case R.id.logo_btn_three:
                if(fragment_three== null){
                    fragment_three = new FragmentFind();
                }
                transaction.hide(fragment_one);
                transaction.hide(fragment_two);
                transaction.show(fragment_three);
                btn_one.setBackground(getResources().getDrawable(R.drawable.new_unselected));
                btn_two.setBackground(getResources().getDrawable(R.drawable.collect_unselected));
                btn_three.setBackground(getResources().getDrawable(R.drawable.find_selected));
                break;
        }
        transaction.commit();
    }
}
