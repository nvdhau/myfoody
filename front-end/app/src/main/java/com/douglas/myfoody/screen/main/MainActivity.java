package com.douglas.myfoody.screen.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.database.DatabaseHelper;
import com.douglas.myfoody.screen.login_signup.LoginFragment;
import com.douglas.myfoody.core.view_model.UserViewModel;


public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    private UserViewModel mUserViewModel;

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


        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getUserByEmail("test@gmail.com").observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                System.out.println("DB INITIALIZING");
            }

        });


        com.douglas.myfoody.database.User user = new com.douglas.myfoody.database.User("nvdhau@gmail.com");
        user.setFirstName("Hau");
        user.setLastName("Nguyen");
        user.setPassword("123456");

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        if(databaseHelper.addUser(user)){
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();

    }

    //return to login fragment
    public static void loginFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, new LoginFragment(), "Login_Fragment")
                .commit();
    }
}
