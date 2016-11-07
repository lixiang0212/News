package zhuoxin.com.viewpagerdemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.info.ContentlistBean;

/**
 * Created by lixiang on 2016/10/18.
 */

public class MyPagerAdapter extends PagerAdapter {

    private List<View> views;
    private List<ContentlistBean> datas;
    private Context context;
    private LayoutInflater inflater;

    public MyPagerAdapter(List<View> views, List<ContentlistBean> datas, Context context) {
        this.views = views;
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v =views.get(position);
        TextView tv= (TextView) v.findViewById(R.id.news_header_tv);
        ImageView iv = (ImageView) v.findViewById(R.id.news_header_iv);
        tv.setText(datas.get(position).getTitle());

        return v;
    }
}
