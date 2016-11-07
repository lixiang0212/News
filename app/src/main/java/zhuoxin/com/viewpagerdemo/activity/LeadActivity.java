package zhuoxin.com.viewpagerdemo.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.myview.leadView;

public class LeadActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<View> views;//视图的集合
    private View view_one,view_two,view_three;//要解析的视图
    private LayoutInflater inflater_one;//解析器
    private Button btn;
    private leadView circle_one,circle_two,circle_three;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        views = new ArrayList<>();
        inflater_one=LayoutInflater.from(this);
        initView();
        pagerListener();
    }
    private void initView() {

        viewPager=(ViewPager)findViewById(R.id.lead_view_pager);
        view_one=inflater_one.inflate(R.layout.inflate_pager_one,null);
        view_two=inflater_one.inflate(R.layout.inflate_pager_two,null);
        view_three=inflater_one.inflate(R.layout.inflate_pager_three,null);
        circle_one=(leadView)findViewById(R.id.circle_one);
        circle_two=(leadView)findViewById(R.id.circle_two);
        circle_three=(leadView)findViewById(R.id.circle_three);
        views.add(view_one);
        views.add(view_two);
        views.add(view_three);

        btn=(Button)view_three.findViewById(R.id.pager_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LeadActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);//设置进入时的选项
    }
    private void pagerListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            public void onPageSelected(int position) {
                int black = getResources().getColor(R.color.colorBlack);
                int white = getResources().getColor(R.color.colorWhite);
                if(position==0){
                    circle_one.setColor(black);
                    circle_two.setColor(white);
                    circle_three.setColor(white);
                    circle_one.invalidate();
                    circle_two.invalidate();
                    circle_three.invalidate();
                }
                else if(position==1){
                    circle_one.setColor(white);
                    circle_two.setColor(black);
                    circle_three.setColor(white);
                    circle_one.invalidate();
                    circle_two.invalidate();
                    circle_three.invalidate();
                }
                else {
                    circle_one.setColor(white);
                    circle_two.setColor(white);
                    circle_three.setColor(black);
                    circle_one.invalidate();
                    circle_two.invalidate();
                    circle_three.invalidate();
                }

            }
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
}
