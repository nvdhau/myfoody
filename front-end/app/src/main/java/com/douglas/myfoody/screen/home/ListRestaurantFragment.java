package com.douglas.myfoody.screen.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.screen.viewmodel.RestaurantViewModel;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

import java.util.List;


public class ListRestaurantFragment extends Fragment implements OnClickListener {
    private static View view;
    private static Button exploreBtn;
    private static FragmentManager fragmentManager;
    private UserViewModel mUserViewModel;
    private RestaurantViewModel mRestaurantViewModel;
    private User user;


    public ListRestaurantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_restaurant_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview_list_restaurant);
        final RestaurantAdapter adapter = new RestaurantAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        mRestaurantViewModel.findAllRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurantList) {
                 adapter.setRestaurants(restaurantList);
            }
        });
    }

    // Set Listeners
    private void setListeners() {
//        exploreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.exploreBtn:
//                // get address and display restaurants
//
//                break;
//        }

    }

}
