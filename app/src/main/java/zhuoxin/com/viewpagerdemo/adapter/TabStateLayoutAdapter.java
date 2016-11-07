package zhuoxin.com.viewpagerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by lixiang on 2016/9/17.
 */
public class TabStateLayoutAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public TabStateLayoutAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        if (titles.size() > position)
            return titles.get(position);
        else
            return "";
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }
}
