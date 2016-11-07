package zhuoxin.com.viewpagerdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.gui.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.viewpagerdemo.R;

public class FragmentHot extends Fragment {

    private EditText et;
    private Button btn;
    private ViewPager pager;
    private MyAdapter adapter;
    private String title;
    private List<FragmentDemo> fragments;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot,container,false);
    }
    
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        et= (EditText) view.findViewById(R.id.fragment_hot_et);
        btn= (Button) view.findViewById(R.id.fragment_hot_btn);
        pager= (ViewPager) view.findViewById(R.id.fragment_hot_view_pager);
        title ="女人";
        initView();
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               title=et.getText().toString();
                Log.i("AAA","onClick");
                Log.i("AAA",title);
                if(title.length()>0){
                    Log.i("AAA","notify");
                    initView();
                }else {
                    et.setText("");
                    Toast.makeText(getActivity(),"请输入有效的关键字!",Toast.LENGTH_SHORT).show();
                }}});
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        if(fragments.size()>0){
            fragments.clear();
        }
        FragmentDemo fragmentDemo = new FragmentDemo();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        Log.i("AAA",title);
        fragmentDemo.setArguments(bundle);
        fragments.add(fragmentDemo);
        adapter = new MyAdapter(getChildFragmentManager(), fragments);
        adapter.notifyDataSetChanged();
        pager.setAdapter(adapter);
    }
    class MyAdapter extends FragmentStatePagerAdapter{
        private List<FragmentDemo> fragmentDemo;
        public MyAdapter(FragmentManager fm,List<FragmentDemo> fragmentDemo) {
            super(fm);
            this.fragmentDemo=fragmentDemo;
        }

        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public int getCount() {
            return 1;
        }
    }
}
