package zhuoxin.com.viewpagerdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.LookActivity;
import zhuoxin.com.viewpagerdemo.info.ContentlistBean;
import zhuoxin.com.viewpagerdemo.manager.VolleyManager;

/**
 * Created by lixiang on 2016/10/18.
 */

public class MyFragment extends Fragment {
    private ImageView imageView;
    private TextView textView;
    private ContentlistBean bean;
    private VolleyManager manager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bean = (ContentlistBean)getArguments().getSerializable("datas");
        return inflater.inflate(R.layout.inflate_news_header,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = VolleyManager.getVolleyManager();
        imageView = (ImageView) view.findViewById(R.id.news_header_iv);
        textView = (TextView) view.findViewById(R.id.news_header_tv);
        textView.setText(bean.getTitle());
        manager.getImageLruCahe(bean.imageurls.get(0).getUrl(),imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LookActivity.class);
                intent.putExtra("url",bean);
                getContext().startActivity(intent);
            }
        });
    }
}
