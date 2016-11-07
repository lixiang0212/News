package zhuoxin.com.viewpagerdemo.myview;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class aaa {
//    private ViewPager viewPager;
//    private List<View> views;
//    private View view_one,view_two,view_three;
//    private LayoutInflater inflater_one;
//    private TextView tv_one,tv_two,tv_three;
//    private ImageView iv;
//    private int offset = 0;// 动画图片偏移量
//    private int currIndex = 0;// 当前页卡编号
//    private int width;// 动画图片宽度
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        views = new ArrayList<>();
//        inflater_one=LayoutInflater.from(this);
//        initView();
//    }
//
//    private void initView() {
//        viewPager=(ViewPager)findViewById(R.id.view_pager);
//        view_one=inflater_one.inflate(R.layout.inflate_pager_one,null);
//        view_two=inflater_one.inflate(R.layout.inflate_pager_two,null);
//        view_three=inflater_one.inflate(R.layout.inflate_pager_three,null);
//        tv_one=(TextView)findViewById(R.id.tv_one);
//        tv_two=(TextView)findViewById(R.id.tv_two);
//        tv_three=(TextView)findViewById(R.id.tv_three);
//        iv=(ImageView)findViewById(R.id.iv_xia);
//        initImageView();
//        initData();
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(0);
//        tv_one.setBackground(getResources().getDrawable(R.drawable.select_is));
//    }
//    private void initData(){
//        views.add(view_one);
//        views.add(view_two);
//        views.add(view_three);
//        tv_one.setOnClickListener(new MyOnClick(0));
//        tv_two.setOnClickListener(new MyOnClick(1));
//        tv_three.setOnClickListener(new MyOnClick(2));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            //滑动偏移量
//            int one = offset * 2 + width;// 页卡1 -> 页卡2 偏移量
//            int two = one * 2;// 页卡1 -> 页卡3 偏移量
//
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//            //选中
//            public void onPageSelected(int position) {
//                Animation animation = null;
//                switch (position) {
//                    case 0:
//                        tv_one.setBackground(getResources().getDrawable(R.drawable.select_is));
//                        tv_two.setBackground(getResources().getDrawable(R.drawable.select_no));
//                        tv_three.setBackground(getResources().getDrawable(R.drawable.select_no));
//                        if (currIndex == 1) {
//                            animation = new TranslateAnimation(one,0,0,0);
//                        } else if (currIndex == 2) {
//                            animation = new TranslateAnimation(two,0,0,0);
//                        }
//                        break;
//                    case 1:
//                        tv_one.setBackground(getResources().getDrawable(R.drawable.select_no));
//                        tv_two.setBackground(getResources().getDrawable(R.drawable.select_is));
//                        tv_three.setBackground(getResources().getDrawable(R.drawable.select_no));
//                        if (currIndex == 0) {
//                            animation = new TranslateAnimation(offset,one,0,0);
//                        } else if (currIndex == 2) {
//                            animation = new TranslateAnimation(two,one,0,0);
//                        }
//                        break;
//                    case 2:
//                        tv_one.setBackground(getResources().getDrawable(R.drawable.select_no));
//                        tv_two.setBackground(getResources().getDrawable(R.drawable.select_no));
//                        tv_three.setBackground(getResources().getDrawable(R.drawable.select_is));
//                        if (currIndex == 0) {
//                            animation = new TranslateAnimation(offset,two,0,0);
//                        } else if (currIndex == 1) {
//                            animation = new TranslateAnimation(one,two,0,0);
//                        }
//                        break;
//                }
//                currIndex=position;
//                animation.setFillAfter(true);// True:图片停在动画结束位置
//                animation.setDuration(300);
//                iv.startAnimation(animation);
//            }
//            //滑动的状态
//            public void onPageScrollStateChanged(int state) {
//                Log.i("AAA", "A--" +state);
//            }
//        });
//    }
//
//    PagerAdapter adapter = new PagerAdapter() {
//
//        public int getCount() {
//            return views.size();
//        }
//
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
//
//        public Object instantiateItem(ViewGroup container, int position) {
//            container.addView(views.get(position),0);
//            return views.get(position);
//        }
//
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(views.get(position));
//        }
//
//    };
//    private void initImageView(){
//        width = BitmapFactory.decodeResource(getResources(), R.drawable.xia).getWidth();//图片宽度
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int phone_width= dm.widthPixels;
//        offset = ((phone_width/3)-width)/2;//偏移量
//        Matrix mt = new Matrix();
//        mt.postTranslate(offset,0);
//        iv.setImageMatrix(mt);//设置动画的初始位置
//    }
//
//
//    class MyOnClick implements View.OnClickListener{
//        int position;
//        public MyOnClick(int position) {
//            this.position = position;
//        }
//        public void onClick(View view) {
//            viewPager.setCurrentItem(position);
//        }
//    }
}
