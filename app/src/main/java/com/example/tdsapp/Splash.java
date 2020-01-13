package com.example.tdsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private ImageView cup;
    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        cup = findViewById(R.id.cupimage);
        welcome = findViewById(R.id.welcome);
        Animation myanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splashanim);
        cup.startAnimation(myanim);
        welcome.startAnimation(myanim);
        final Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                }


            }

        };
        timer.start();
    }
}
