package com.ta.debam.deteksiekspresitugasakhirfinal.adaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ta.debam.deteksiekspresitugasakhirfinal.fragment.IntroFrag;

/**
 * Created by Debam on 4/29/2016.
 */
public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return IntroFrag.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
