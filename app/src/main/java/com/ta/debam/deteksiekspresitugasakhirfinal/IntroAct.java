package com.ta.debam.deteksiekspresitugasakhirfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ta.debam.deteksiekspresitugasakhirfinal.adaper.IntroAdapter;
import com.ta.debam.deteksiekspresitugasakhirfinal.fragment.IntroTransformation;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.Constant;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.Utilities;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Debam on 4/29/2016.
 */
public class IntroAct extends AppCompatActivity {
    private ViewPager mViewPager;
    private IntroAdapter mIntroAdapter;
    private ImageView mImageView;
    private TextView mTextView;

    private static final int REQUEST_EXTERNAL=1;
    private static String[] PERMISSIONS ={
            android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        super.onCreate(savedInstanceState);


        if (Utilities.haveLooged(getApplicationContext())){
            Intent i = new Intent(this, MainAct.class);
            startActivity(i);
            this.finish();
        } else {
            Utilities.saveBoolean(getApplicationContext(), Constant.SharedPref.LOGGED,true);
        }
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_EXTERNAL);
        }

        setContentView(R.layout.intro_layout);

        mImageView  = (ImageView) findViewById(R.id.swipe);
        mTextView   = (TextView) findViewById(R.id.btn_go);

        mTextView.setVisibility(View.GONE);

        mIntroAdapter = new IntroAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mIntroAdapter);

        //mViewPager.setPageTransformer(false, new IntroTransformation());

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
//        int pos = indicator.getVerticalScrollbarPosition();
//        Log.e("pos", pos+"");

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==3){
                    mImageView.setVisibility(View.INVISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                } else{
                    mImageView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utilities.dariMain(getApplicationContext())){
            Utilities.saveBoolean(getApplicationContext(),Constant.SharedPref.BACK,false);
            finish();
        }
    }

    public void gotoMain(View v){
        Intent i = new Intent(this, MainAct.class);
        if (Utilities.dariMain(getApplicationContext())){
            Utilities.saveBoolean(getApplicationContext(),Constant.SharedPref.BACK,false);
            finish();
        } else {
            startActivity(i);
        }
    }

}
