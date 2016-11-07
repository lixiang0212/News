package zhuoxin.com.viewpagerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public TabLayoutAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles){
        super(fm);
        this.fragments=fragments;
        this.titles=titles;

    }

    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position%titles.size());
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }
}
