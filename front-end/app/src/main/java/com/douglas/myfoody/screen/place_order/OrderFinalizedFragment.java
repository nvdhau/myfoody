package com.douglas.myfoody.screen.place_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.home.HomeActivity;

public class OrderFinalizedFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_order_finalized_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        setListeners();

        getActivity().setTitle("");
        return view;
    }

    private void setListeners() {
        view.findViewById(R.id.buttonPlaceOrderConfirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPlaceOrderConfirm:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
