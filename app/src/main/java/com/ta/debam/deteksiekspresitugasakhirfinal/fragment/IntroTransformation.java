package com.ta.debam.deteksiekspresitugasakhirfinal.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Debam on 4/29/2016.
 */
public class IntroTransformation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int pagePosition = Integer.parseInt((String) page.getTag()) ;

        int pageWidth = page.getWidth();
        float pageWidthTimesPosition = pageWidth * position;
        float absPosition = Math.abs(position);

        if (position <= -1.0f || position >= 1.0f) {

        } else if (position == 0.0f) {

        } else{
          //NGODING DISINI
        }
    }
}
