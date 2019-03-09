package com.douglas.myfoody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.douglas.myfoody.screen.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // release resourse
                finish();

                // start Login screen
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };

        Timer opening = new Timer();
        opening.schedule(task, 4000);
    }
}