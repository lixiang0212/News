package zhuoxin.com.viewpagerdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.LookActivity;
import zhuoxin.com.viewpagerdemo.adapter.CollectAdapter;
import zhuoxin.com.viewpagerdemo.info.CollectInfo;
import zhuoxin.com.viewpagerdemo.sql.MyCollectSQLiteUtil;

public class FragmentFind extends Fragment {

    private EditText et;
    private Button button;
    private ListView lv;
    private CollectAdapter adapter;
    private List<CollectInfo> datas;
    private MyCollectSQLiteUtil util;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x01:
                    String s = (String)msg.obj;
                    if(s.length()>0){datas.clear();}
                    datas=util.select(s);
                    adapter = new CollectAdapter(getActivity(),datas);
                    lv.setAdapter(adapter);
                    break;
            }}};
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find,container,false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        util = new MyCollectSQLiteUtil(getActivity());
        datas = new ArrayList<>();
        datas= util.queryAll();
        adapter =new CollectAdapter(getActivity(),datas);
        et= (EditText) view.findViewById(R.id.fragment_find_et);
        et.addTextChangedListener(new MyText());
        button= (Button) view.findViewById(R.id.fragment_find_btn);
        lv= (ListView) view.findViewById(R.id.fragment_find_listView);
        lv.setAdapter(adapter);
        initView();
        super.onViewCreated(view, savedInstanceState);
    }
    private void initView() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title =et.getText().toString();
                if(datas.size()>0) {
                    for (int i = 0; i < datas.size(); i++) {
                        if(title.equals(datas.get(i).getTitle())){
                            String url = datas.get(i).getUrl();
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), LookActivity.class);
                            intent.putExtra("title",title);
                            intent.putExtra("url",url);
                            getActivity().startActivity(intent);
                            return;
                        }
                        else{
                            Toast.makeText(getActivity(),"请输入正确的标题",Toast.LENGTH_SHORT).show();
                            et.setText("");
                        }
                    }
                }else {
                    Toast.makeText(getActivity(),"请输入正确的标题",Toast.LENGTH_SHORT).show();
                    et.setText("");
                }
            }
        });
    }
    class MyText implements TextWatcher{
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            Message msg = Message.obtain();
            if(charSequence.length()>0){
                String s = charSequence.toString();
                msg.obj =s;
                msg.what=0x01;
                handler.sendMessage(msg);
            }
        }
        public void afterTextChanged(Editable editable) {
        }
    }
}
