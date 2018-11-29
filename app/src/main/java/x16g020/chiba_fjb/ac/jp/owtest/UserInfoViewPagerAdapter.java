package x16g020.chiba_fjb.ac.jp.owtest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by naoi on 2017/04/24.
 */

public class UserInfoViewPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_NUM = 2;

    public UserInfoViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new WeatherFragment();
                break;
            default:
                fragment = new WeatherInfoFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
