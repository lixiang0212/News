package zhuoxin.com.viewpagerdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.utils.sharedpres;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView iv;
    private Boolean flag;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getFlag();
        initView();
    }
    private void getFlag() {
        flag= sharedpres.getFlags(this);
        if(flag){
            flag=false;
            sharedpres.saveFlags(this,flag);
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this,LeadActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void initView() {
        iv=(ImageView)findViewById(R.id.welcome_iv);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_welcome);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,LogoActivity.class);
                startActivity(intent);
                finish();
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
