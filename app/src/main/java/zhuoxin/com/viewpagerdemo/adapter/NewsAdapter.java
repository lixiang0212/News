package zhuoxin.com.viewpagerdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.fragment.FragmentDemo;
import zhuoxin.com.viewpagerdemo.fragment.FragmentTemp;
import zhuoxin.com.viewpagerdemo.fragment.MyFragment;
import zhuoxin.com.viewpagerdemo.interfaces.NewsInterface;
import zhuoxin.com.viewpagerdemo.manager.VolleyManager;
import zhuoxin.com.viewpagerdemo.info.ContentlistBean;

import static java.lang.Thread.sleep;

public class NewsAdapter extends BaseLoadingAdapter {

    private List<ContentlistBean> datas;
    private VolleyManager manager;
    private Context context;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions options;
    private NewsInterface newsInterface;
    private FragmentManager fragmentManager;
    private MyFragmentPagerAdapter adapter;
    private Handler handler;
    private Runnable runnable;
    private ExecutorService service;
    private int CurrentPage = 10;
    private HeaderTemp temp;
    private HeaderTemp headerTemp;

    public void setNewsInterface(NewsInterface newsInterface) {
        this.newsInterface = newsInterface;
    }

    public NewsAdapter(List<ContentlistBean> datas, Context context, RecyclerView recyclerView, FragmentManager fragmentManager) {
        super(datas, context, recyclerView);
        this.datas = datas;
        this.context = context;
        this.fragmentManager = fragmentManager;
        manager = VolleyManager.getVolleyManager();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_news_lodinglose)
                .showImageOnFail(R.drawable.img_news_lodinglose)
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        layoutInflater = LayoutInflater.from(context);
    }

    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup viewGroup) {
       if(temp==null) {
           temp = new HeaderTemp(inflater.inflate(R.layout.inflate_news_view_pager, viewGroup, false));
       }
        return temp;
    }

    public void onBindHeaderHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if(headerTemp ==null){
            headerTemp = (HeaderTemp) viewHolder;
        }
        final List<Fragment> views = new ArrayList<>();
        final List<ContentlistBean> data = datas.subList(0, 5);
        for (int i = 0; i < data.size(); i++) {
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("datas", data.get(i));
            fragment.setArguments(bundle);
            views.add(fragment);
        }
        Log.i("AAA", data.size() + "data.size");
        adapter = new MyFragmentPagerAdapter(fragmentManager, views);
        temp.viewPager.setAdapter(adapter);
        temp.viewPager.setCurrentItem(10, false);
        //////////////////////////////
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x01:
                        temp.viewPager.setCurrentItem(msg.arg1);
                        break;
                }
            }
        };
        temp.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                CurrentPage = position;
                temp.textView.setText((position % 5 + 1) + "/5");
                Log.i("AAA", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        if (service ==null) {
            service = Executors.newFixedThreadPool(1);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = 0x01;
                    message.arg1 = CurrentPage + 1;
                    handler.sendMessage(message);
                }
            }
        };
        service.submit(runnable);
    }


    public RecyclerView.ViewHolder onCreateBodyHolder(ViewGroup viewGroup) {
        BodyTemp temp = new BodyTemp(inflater.inflate(R.layout.inflate_news_body_item,viewGroup,false));
        return temp;
    }

    public void onBindBodyHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        BodyTemp temp = (BodyTemp)viewHolder;
        ContentlistBean contentlistBean = datas.get(position);
        if(contentlistBean!=null) {
            temp.title.setText(contentlistBean.getTitle());
            temp.from.setText(contentlistBean.getSource());
            temp.time.setText(contentlistBean.getPubDate());
            manager.getImageLruCahe(contentlistBean.imageurls.get(0).getUrl(), temp.iv);
            temp.relativeLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    newsInterface.OnItemClickListener(view, position);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateBodysHolder(ViewGroup viewGroup) {
        BodysTemp temp = new BodysTemp(inflater.inflate(R.layout.inflate_news_bodys,viewGroup,false));
        return temp;
    }

    @Override
    public void onBindBodysHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        BodysTemp temp = (BodysTemp)viewHolder;
        ContentlistBean contentlistBean = datas.get(position);
        if(contentlistBean!=null) {
            temp.title.setText(contentlistBean.getTitle());
            manager.getImageLruCahe(contentlistBean.imageurls.get(0).getUrl(), temp.iv1);
            manager.getImageLruCahe(contentlistBean.imageurls.get(1).getUrl(), temp.iv2);
            manager.getImageLruCahe(contentlistBean.imageurls.get(2).getUrl(), temp.iv3);
            temp.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsInterface.OnItemClickListener(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    ////////////////////////////////ViewHolder////////////////
    private class BodyTemp extends RecyclerView.ViewHolder{
        public TextView title,time,from;
        public ImageView iv;
        public RelativeLayout relativeLayout;
        public BodyTemp(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.include_item_title);
            time=(TextView)itemView.findViewById(R.id.include_item_time);
            from=(TextView)itemView.findViewById(R.id.include_item_from);
            iv=(ImageView) itemView.findViewById(R.id.include_item_iv);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.news_body_relative);
        }
    }
    private class BodysTemp extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView iv1,iv2,iv3;
        public LinearLayout layout;
        public RelativeLayout relativeLayout;
        public BodysTemp(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.news_bods_title);
            iv1= (ImageView) itemView.findViewById(R.id.news_bods_iv1);
            iv2= (ImageView) itemView.findViewById(R.id.news_bods_iv2);
            iv3= (ImageView) itemView.findViewById(R.id.news_bods_iv3);
            layout = (LinearLayout) itemView.findViewById(R.id.news_bods_ll);
        }
    }
    public class HeaderTemp extends RecyclerView.ViewHolder{
        private ViewPager viewPager;
        private TextView textView;
        public HeaderTemp(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.inflate_news_view_pager);
            textView = (TextView) itemView.findViewById(R.id.header_page);
        }
    }
    //////////////////////////////end///////////////////////
    public void addAll(List<ContentlistBean> list){
            this.datas.addAll(list);
            notifyDataSetChanged();
    }
    public void clearAll(){
        if (this.datas!=null){
            this.datas.clear();
            notifyDataSetChanged();
        }
    }
}
