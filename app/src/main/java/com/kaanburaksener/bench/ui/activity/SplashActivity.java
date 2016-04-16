package com.kaanburaksener.bench.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.os.Bundle;

import com.kaanburaksener.bench.R;

/**
 * Created by kaanburaksener on 16/04/16.
 */

public class SplashActivity extends AppCompatActivity {
    private ImageView splashBackground;
    private ActionBar actionBar;
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        actionBar = getSupportActionBar();
        actionBar.hide();
        initializer();

        Thread mSplashThread;
        mSplashThread = new Thread(){
            @Override public void run(){
                try {
                    synchronized(this){
                        wait(SPLASH_DISPLAY_LENGTH);
                    }
                }

                catch(InterruptedException ex){

                }

                finally {
                    Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        mSplashThread.start();
    }

    /**
     * This function is used to initialize the layout elements
     */

    private void initializer() {
        setStatusBarColor();
        splashBackground = (ImageView) findViewById(R.id.bg);
        splashBackground.setImageResource(R.drawable.splash_bg);
    }

    /**
     * This function is used to change the status bar color
     */

    private void setStatusBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.secondary_dark_color));
            }
        } catch (NumberFormatException e) {
            Log.d("Status bar set error: ", e.toString());
        }
    }
}
