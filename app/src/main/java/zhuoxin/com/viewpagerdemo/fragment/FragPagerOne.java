package zhuoxin.com.viewpagerdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.LookActivity;
import zhuoxin.com.viewpagerdemo.adapter.FragRecyclerViewAdapter;
import zhuoxin.com.viewpagerdemo.info.sportsInfo;
import zhuoxin.com.viewpagerdemo.interfaces.RecyclerViewInterface;
import zhuoxin.com.viewpagerdemo.manager.ConnectionManager;

public class FragPagerOne extends Fragment{
    private RecyclerView recyclerView;
    private List<sportsInfo> lists;
    private FragRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x01){
              adapter.notifyDataSetChanged();
            }
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo,container,false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("AAA","ViewCreated"+"");
        lists = new ArrayList<>();
        initView();
        initData();
    }
    private void initView() {
        recyclerView =(RecyclerView)getActivity().findViewById(R.id.fragment_demo_recycle_view);
        manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new FragRecyclerViewAdapter(getActivity(),lists);
        adapter.setViewInterface(new RecyclerViewInterface() {
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(getActivity(), LookActivity.class);
                intent.putExtra("url",lists.get(position).url);
                startActivity(intent);
            }
            public void OnLongClickListener(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Log.i("AAA","initData"+"");
        new Thread(new Runnable() {
            public void run() {
                String httpUrl = "http://apis.baidu.com/txapi/keji/keji";
                String httpArg = "num=30&page=1";
                String jsonResult = ConnectionManager.request(httpUrl+"?"+httpArg);
                Log.i("AAA",jsonResult+"");
                List<sportsInfo> list =new ArrayList<>();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
         try {
                JSONObject jsonObject = new JSONObject(jsonResult);
                int code = jsonObject.getInt("code");
                String msg = jsonObject.getString("msg");
                if(code==200) {
                    Log.i("AAA",msg);
                    JSONObject object;
                    sportsInfo info;
                    JSONArray array = jsonObject.getJSONArray("newslist");
                for (int i = 0; i < array.length(); i++) {
                      object = array.getJSONObject(i);
//                    String time = object.getString("ctime");
//                    String title = object.getString("title");
//                    String description = object.getString("description");
//                    String picUrl = object.getString("picUrl");
//                    String url = object.getString("url");
//                    lists.add(new sportsInfo(time,title,description,picUrl,url));
                    info = gson.fromJson(object.toString(),sportsInfo.class);
                    lists.add(info);
                    Log.i("AAA","解析Json添加到List-"+i+"-"+info.toString());
                    Log.i("AAA",lists.toString());
                    }
                }
                    Message msges = new Message();
                    msges.what=0x01;
                    msges.obj=lists;
                    handler.sendMessage(msges);
                    Log.i("AAA","成功发送Handler");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
