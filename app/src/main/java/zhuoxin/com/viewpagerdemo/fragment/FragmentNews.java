package zhuoxin.com.viewpagerdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.activity.TitleActivity;
import zhuoxin.com.viewpagerdemo.adapter.TabStateLayoutAdapter;
import zhuoxin.com.viewpagerdemo.utils.sharedpres;

public class FragmentNews extends Fragment{
    private ViewPager pager;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabLayout tabLayout;
    private TabStateLayoutAdapter adapter;
    private ImageView iv_add;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        addData();
    }

    public void initView() {
        pager =(ViewPager)getActivity().findViewById(R.id.fragment_one_view_pager);
        tabLayout=(TabLayout)getActivity().findViewById(R.id.fragment_one_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
            }

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        iv_add=(ImageView)getActivity().findViewById(R.id.fragment_one_add);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//模式//填充//滑动
        iv_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TitleActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

    private void addData(){
        fragments=new ArrayList<>();
        if(titles!=null)
        titles.clear();
        fragments.clear();
        titles=sharedpres.getLists(getActivity());
        for (int i=0;i<titles.size();i++){
            FragmentDemo fragmentDemo = new FragmentDemo();
            Bundle bundle = new Bundle();
            bundle.putString("title",titles.get(i));
            fragmentDemo.setArguments(bundle);
            fragments.add(fragmentDemo);
        }
        adapter=new TabStateLayoutAdapter(getChildFragmentManager(),fragments,titles);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){if(resultCode==101){if(requestCode==100){

        boolean flag = data.getBooleanExtra("flag",false);
        if(flag){
        addData();

    }}}}}
}
