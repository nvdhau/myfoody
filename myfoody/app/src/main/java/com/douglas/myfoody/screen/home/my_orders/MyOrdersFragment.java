package com.douglas.myfoody.screen.home.my_orders;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.screen.viewmodel.OrderViewModel;
import com.douglas.myfoody.screen.viewmodel.RestaurantViewModel;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

import java.util.List;

public class MyOrdersFragment extends Fragment {
    private static View view;
    private User currentUser;

    private OrderViewModel mOrderViewModel;
    private UserViewModel mUserViewModel;
    private RestaurantViewModel mRestaurantViewModel;

    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_orders_layout, container, false);

        //create viewModels
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mRestaurantViewModel = ViewModelProviders.of(getActivity()).get(RestaurantViewModel.class);
        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        // get current user info and store in UserViewModel
        currentUser = mUserViewModel.getUser().getValue();

        RecyclerView recyclerView = view.findViewById(R.id.my_orders_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        return view;
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        final MyOrdersAdapter adapter = new MyOrdersAdapter(getContext());
        recyclerView.setAdapter(adapter);

        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        List<Order> myOrders = mOrderViewModel.findByUserEmail(currentUser.getEmail());

        for (Order order :
                myOrders) {
            order.setRestaurantName(mRestaurantViewModel
                    .getRestaurantByID(order.getRestaurantId())
                    .getName());
        }

        if(myOrders.isEmpty()) {
            Order emptyOrder = new Order();
            emptyOrder.setID(-1);
            myOrders.add(emptyOrder);
        }

        adapter.setOrders(myOrders);
    }
}
