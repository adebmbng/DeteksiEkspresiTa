package com.ta.debam.deteksiekspresitugasakhirfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Debam on 4/28/2016.
 */
public class HomeAct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
    }

    public void gotoIntro(View v){
        Intent i = new Intent(this, IntroAct.class);
        startActivity(i);
    }
}
