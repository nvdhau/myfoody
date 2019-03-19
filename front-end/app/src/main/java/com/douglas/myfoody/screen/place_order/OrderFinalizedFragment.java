package com.douglas.myfoody.screen.place_order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douglas.myfoody.R;

public class OrderFinalizedFragment extends Fragment {
    private static View view;
    private static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_order_finalized_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        return view;
    }
}
