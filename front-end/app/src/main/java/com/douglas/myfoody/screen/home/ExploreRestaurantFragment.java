package com.douglas.myfoody.screen.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.douglas.myfoody.R;


public class ExploreRestaurantFragment extends Fragment implements OnClickListener {
    private static View view;

//    private static EditText email, password;
//    private static Button loginBtn;
//    private static TextView signUpBtn;
    private static FragmentManager fragmentManager;
//    private UserViewModel mUserViewModel;

    public ExploreRestaurantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.explore_restaurant_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
//        email = view.findViewById(R.id.login_email);
//        password = view.findViewById(R.id.login_password);
//        loginBtn = view.findViewById(R.id.loginBtn);
//        signUpBtn = view.findViewById(R.id.createAccount);
//
//        // create default login account
//        email.setText("admin@gmail.com");
//        password.setText("123");
        // end create default login account
    }

    // Set Listeners
    private void setListeners() {
//        loginBtn.setOnClickListener(this);
//        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.loginBtn:
//                if (checkValidation()) {
//                    // TODO: login success then redirect to search screen for searching restaurants
//                    //get user by email from email input
//                    mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//                    mUserViewModel.getUserByEmail(email.getText().toString()).observe(this, new Observer<User>() {
//                        @Override
//                        public void onChanged(@Nullable User user) {
//                            if(user == null){
//                                new MyToast().showToast(getActivity(), view,
//                                        "Email is not exist.\nPlease try again!");
//                            }else if(user.getPassword().equals(password.getText().toString()) == false)
//                                new MyToast().showToast(getActivity(), view,
//                                        "Password is incorrect.\nPlease try again!");
//                            else
//                                startActivity(new Intent(getActivity(), HomeActivity.class));
//                        }
//                    });
//                }
//                break;
//            case R.id.createAccount:
//                fragmentManager.beginTransaction()
//                               .replace(R.id.frameContainer, new SignUpFragment(), "SignUpFragment")
//                               .commit();
//                break;
//        }

    }

}
