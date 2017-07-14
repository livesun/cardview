package livesun.cardpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29028 on 2017/7/14.
 */

public class PageAdapter extends FragmentPagerAdapter {
    private  List<Fragment> fragments=new ArrayList<>();

    public PageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }
}
