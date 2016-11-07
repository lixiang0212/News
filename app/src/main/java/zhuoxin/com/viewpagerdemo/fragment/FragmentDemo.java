package zhuoxin.com.viewpagerdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.LookActivity;
import zhuoxin.com.viewpagerdemo.adapter.NewsAdapter;
import zhuoxin.com.viewpagerdemo.info.ContentlistBean;
import zhuoxin.com.viewpagerdemo.interfaces.NewsInterface;
import zhuoxin.com.viewpagerdemo.interfaces.NewsReturnInterface;
import zhuoxin.com.viewpagerdemo.manager.MyApplication;
import zhuoxin.com.viewpagerdemo.manager.ConnectionManager;
import zhuoxin.com.viewpagerdemo.info.AllNewsInfo;

public class FragmentDemo extends Fragment{

    private Gson gson;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager managers;
    private String title;
    private RequestQueue queue;
    private List<ContentlistBean> contentlistBeans;
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayout;
    private TextView tv_title;
    private ProgressBar progressBar;
    private int AllPager;
    private int CurrentPager=1;
    private final int Pull_up = 0;
    private final int Pull_down =1;
    private ViewPager viewPager;
    private List<NewsAdapter.HeaderTemp> views;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gson =MyApplication.getInstance().getGson();
        queue = MyApplication.getInstance().getQueue();
        title=getArguments().getString("title");
        Log.i("AAA",title);
        return inflater.inflate(R.layout.fragment_demo,container,false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        contentlistBeans = new ArrayList<>();
        views = new ArrayList<>();
        recyclerView =(RecyclerView)view.findViewById(R.id.fragment_demo_recycle_view);
        managers=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(managers);
       // viewPager= (ViewPager) view.findViewById(R.id.fragment_demo_view_pager);
        relativeLayout= (RelativeLayout) view.findViewById(R.id.fragment_demo_relative);
        tv_title= (TextView) view.findViewById(R.id.fragment_demo_title);
        progressBar= (ProgressBar) view.findViewById(R.id.fragment_demo_progressBar);
        adapter= new NewsAdapter(contentlistBeans,getActivity(),recyclerView,getChildFragmentManager());
        adapter.setNewsInterface(new NewsInterface() {
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(getActivity(), LookActivity.class);
                intent.putExtra("url",contentlistBeans.get(position));
                getActivity().startActivity(intent);
            }
        });
        adapter.setAnInterface(new NewsReturnInterface() {
            public void Return() {
                Log.i("AAA","Return");
                if(CurrentPager>AllPager){
                    CurrentPager=1;
                    getStringRequest(Pull_down,CurrentPager);
                }else{
                    getStringRequest(Pull_up,CurrentPager);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.fragment_demo_swl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorDeepSkyBlue,R.color.colorGainsboro);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                CurrentPager--;
                if(CurrentPager<=1){
                    CurrentPager=1;
                }
                getStringRequest(Pull_down,CurrentPager);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!ViewCompat.canScrollVertically(recyclerView,1)){
                    adapter.notifyLoading();
                    CurrentPager++;
                    if(CurrentPager>AllPager){
                        Log.i("AAA",CurrentPager+"");
                        adapter.setLoadingNoMore();
                    }
                    else {
                        getStringRequest(Pull_up, CurrentPager);
                    }
                }
            }
        });
        getStringRequest(Pull_up,CurrentPager);
        super.onViewCreated(view, savedInstanceState);
    }

    public void getStringRequest(final int type, int pager){

        StringRequest request =new StringRequest(ConnectionManager.setName(title,pager), new Response.Listener<String>() {
            public void onResponse(String jsonResult) {
                Log.i("AAA",jsonResult);
                AllNewsInfo allNewsInfo =gson.fromJson(jsonResult,AllNewsInfo.class);
                contentlistBeans= allNewsInfo.getShowapi_res_body().getPagebean().getContentlist();
                AllPager =allNewsInfo.getShowapi_res_body().getPagebean().getAllPages();
                onGetDataSuccess(type);
                relativeLayout.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("AAA",volleyError.toString());
                progressBar.setVisibility(View.INVISIBLE);
                tv_title.setText("加载失败，点击重新加载！");
                tv_title.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        tv_title.setText("拼命加载中！");
                        getStringRequest(Pull_up,CurrentPager);
                    }
                });
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> apikey = new HashMap<>();
                apikey.put("apikey",ConnectionManager.API_KEY);
                return apikey;
            }
        };
        queue.add(request);
    }
    private void onGetDataSuccess(int type) {
        if (contentlistBeans == null || contentlistBeans.size() == 0)
            return;
        Iterator<ContentlistBean> iterator = contentlistBeans.iterator();
        ContentlistBean temp = null;
        // 遍历数据源 并且把所有的没有图片地址的信息删除掉
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (temp == null || temp.getImageurls().size() == 0 || temp.getImageurls().get(0).getUrl() == null)
                iterator.remove();
        }

        switch (type){
            case Pull_up:
                adapter.setLoadingComplete();
                if(contentlistBeans!=null) {
                    adapter.addAll(contentlistBeans);
                }
                break;
            case Pull_down:
                if (contentlistBeans != null) {
                    adapter.clearAll();
                    adapter.addAll(contentlistBeans);
                }
                break;
        }

    }
}

