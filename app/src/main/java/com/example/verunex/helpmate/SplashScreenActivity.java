package com.example.verunex.helpmate;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView mImageView, mImageView2;
    private FirebaseAuth mFirebaseAuth;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mImageView = (ImageView) findViewById(R.id.logoUp);
        mImageView2 = (ImageView) findViewById(R.id.dot);


        final Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.scale_dot);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.move_up_down);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(mFirebaseAuth.getInstance().getCurrentUser() == null){
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImageView2.startAnimation(anim2);
        mImageView.startAnimation(anim);

    }

}





