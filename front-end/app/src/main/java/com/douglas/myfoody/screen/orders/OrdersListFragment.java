package com.douglas.myfoody.screen.orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douglas.myfoody.R;

public class OrdersListFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_list_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
