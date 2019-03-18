package com.douglas.myfoody.screen.login_signup;



import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import com.douglas.myfoody.screen.home.HomeActivity;
import com.douglas.myfoody.screen.main.MainActivity;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginFragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText email, password;
    private static Button loginBtn;
    private static TextView signUpBtn;
    private static FragmentManager fragmentManager;
    private UserViewModel mUserViewModel;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        email = view.findViewById(R.id.login_email);
        password = view.findViewById(R.id.login_password);
        loginBtn = view.findViewById(R.id.loginBtn);
        signUpBtn = view.findViewById(R.id.createAccount);

        //set default login as admin
        email.setText("admin@gmail.com");
        password.setText("123");
    }

    // Set Listeners
    private void setListeners() {
        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (checkValidation()) {
                    // TODO: login success then redirect to search screen for searching restaurants
                    //get user by email from email input
                    mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
                    mUserViewModel.getUserByEmail(email.getText().toString()).observe(this, new Observer<User>() {
                        @Override
                        public void onChanged(@Nullable User user) {
                            if (user == null) {
                                new MyToast().showToast(getActivity(), view,
                                        "Email is not exist.\nPlease try again!");
                            } else if (user.getPassword().equals(password.getText().toString()) == false) {
                                new MyToast().showToast(getActivity(), view,
                                        "Password is incorrect.\nPlease try again!");
                            } else {
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            }
                        }
                    });
                }
                break;
            case R.id.createAccount:
//                fragmentManager.beginTransaction()
//                               .replace(R.id.frameContainer, new SignUpFragment(), "SignUpFragment")
//                               .commit();
                MainActivity.signupFragment();
                break;
        }

    }

    // Check Validation before login
    private boolean checkValidation() {
        // Get email id and password
        String getEmailId = email.getText().toString();
        String getPassword = password.getText().toString();

        // Check for both field is empty or not
        if (getEmailId.equals("") || getPassword.equals("")) {
            new MyToast().showToast(getActivity(), view,
                    "Enter required credentials.");
            return false;
        }

        return true;
    }
}
