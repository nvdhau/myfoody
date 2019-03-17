package com.douglas.myfoody.screen.main;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.login_signup.LoginFragment;
public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        // if null we set login as default screen
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                           .replace(R.id.frameContainer, new LoginFragment(), "Login_Fragment")
                           .commit();
        }

        // close button
        findViewById(R.id.close_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loginFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, new LoginFragment(), "Login_Fragment")
                .commit();
    }
}
