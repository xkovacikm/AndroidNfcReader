package com.example.demeterovci.androidnfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends Activity {

    // Sets splash screen time in miliseconds
    private static int SPLASH_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                // run() method will be executed when 3 seconds have passed

                //Time to start MainActivity
                Intent intent = new Intent(SplashScreen.this, FirstActivity.class);
                startActivity(intent );

                finish();
            }
        }, SPLASH_TIME);
    }
}
