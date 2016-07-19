package com.ta.debam.deteksiekspresitugasakhirfinal;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Debam on 4/28/2016.
 */
public class SplashAct extends AppCompatActivity{

//    private LinearLayout lay;
    private ImageView top, bot;
    private Animation anim;
    private Thread TrSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash2_layout);

//        lay = (LinearLayout) findViewById(R.id.linLay);
        top = (ImageView) findViewById(R.id.splash_img_top);
        bot = (ImageView) findViewById(R.id.splash_img_bot);

        startAnimation();

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

    }

    public void startAnimation(){
//        anim = AnimationUtils.loadAnimation(this, R.anim.slide_down);
//        anim.reset();
//
//        top.clearAnimation();
//        top.startAnimation(anim);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(bot, "alpha",  1f, .3f);
        fadeOut.setDuration(1000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(bot, "alpha", .3f, 1f);
        fadeIn.setDuration(1000);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);
        mAnimationSet.start();

//        anim = AnimationUtils.loadAnimation(this, R.anim.slide_up);
//        anim.reset();
//
//        bot.clearAnimation();
//        bot.startAnimation(anim);

        TrSplash = new Thread(){
            @Override
            public void run() {
                try {
                    int waited = 0;

                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashAct.this,
                            IntroAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashAct.this.finish();
                } catch (InterruptedException e) {

                } finally {
                    SplashAct.this.finish();
                }
            }
        };
        TrSplash.start();

    }
}
