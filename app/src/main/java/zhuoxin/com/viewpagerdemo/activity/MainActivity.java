package zhuoxin.com.viewpagerdemo.activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.viewpagerdemo.myview.MyTextView;
import zhuoxin.com.viewpagerdemo.interfaces.OnClickInterface;
import zhuoxin.com.viewpagerdemo.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<View> views;//视图的集合
    private View view_one,view_two,view_three;//要解析的视图
    private LayoutInflater inflater_one;//解析器
    private MyTextView tv_one,tv_two,tv_three;//自定义的TextView
    private ImageView iv;//动画图片
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int width;// 动画图片宽度
    private Button btn;
    private View.OnClickListener MyOnClick = new View.OnClickListener(){

        public void onClick(View view) { //Text的点击监听
            viewPager.setCurrentItem(((MyTextView)view).getPosition());
            ((MyTextView)view).setSelect(true);
        }
    };
    private OnClickInterface face = new OnClickInterface() {
        public void OnClickChanged(boolean flag, View view) { //接口的实现
            if(flag){view.setBackground(getResources().getDrawable(R.drawable.select_is));}
            else {view.setBackground(getResources().getDrawable(R.drawable.select_no));}
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        views = new ArrayList<>();
        inflater_one=LayoutInflater.from(this);
        initView();
    }
    private void initView() {
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        view_one=inflater_one.inflate(R.layout.inflate_pager_one,null);
        view_two=inflater_one.inflate(R.layout.inflate_pager_two,null);
        view_three=inflater_one.inflate(R.layout.inflate_pager_three,null);
        tv_one=(MyTextView)findViewById(R.id.tv_one);
        tv_two=(MyTextView)findViewById(R.id.tv_two);
        tv_three=(MyTextView)findViewById(R.id.tv_three);
        iv=(ImageView)findViewById(R.id.iv_xia);
        btn=(Button)view_three.findViewById(R.id.pager_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        initImageView();
        initData();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);//设置进入时的选项
        tv_one.setBackground(getResources().getDrawable(R.drawable.select_is));
    }
    private void initData(){ //设置position,接口，点击监听
        tv_one.setPosition(0);
        tv_two.setPosition(1);
        tv_three.setPosition(2);
        tv_one.setFace(face);
        tv_two.setFace(face);
        tv_three.setFace(face);
        tv_one.setOnClickListener(MyOnClick);
        tv_two.setOnClickListener(MyOnClick);
        tv_three.setOnClickListener(MyOnClick);
        views.add(view_one);
        views.add(view_two);
        views.add(view_three);
        //ViewPager的监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动偏移量
            int one = offset * 2 + width;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            //选中
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        tv_one.setSelect(true);
                        tv_two.setSelect(false);
                        tv_three.setSelect(false);
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one,0,0,0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two,0,0,0);
                    }
                        break;
                    case 1:
                        tv_one.setSelect(false);
                        tv_two.setSelect(true);
                        tv_three.setSelect(false);
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(offset,one,0,0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two,one,0,0);
                        }
                        break;
                    case 2:
                        tv_one.setSelect(false);
                        tv_two.setSelect(false);
                        tv_three.setSelect(true);
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(offset,two,0,0);
                        } else if (currIndex == 1) {
                            animation = new TranslateAnimation(one,two,0,0);
                        }
                        break;
                }
                currIndex=position;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(100);
                iv.startAnimation(animation);
            }
            //滑动的状态
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    PagerAdapter adapter = new PagerAdapter() {
        //总的页数
        public int getCount() {
            return views.size();
        }
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //初始化
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position),0);
            return views.get(position);
        }
        //销毁
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

    };
    private void initImageView(){
        width = BitmapFactory.decodeResource(getResources(), R.drawable.xia).getWidth();//获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int phone_width= dm.widthPixels;//获取手机分辨率的宽度
        offset = ((phone_width/3)-width)/2;//偏移量
        Matrix mt = new Matrix();
        mt.postTranslate(offset,0);//位移动画
        iv.setImageMatrix(mt);//设置动画的初始位置
    }
}
