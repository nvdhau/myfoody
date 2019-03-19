package com.douglas.myfoody.screen.place_order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douglas.myfoody.R;

public class OrderPaymentFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_order_payment_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        setListeners();
        return view;
    }

    private void setListeners() {
        view.findViewById(R.id.buttonPayment).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPayment:
                fragmentManager.beginTransaction()
                        .replace(R.id.homeFrameContainer, new OrderFinalizedFragment(), "Order_Finalized_Fragment").commit();
                break;
            default:
                break;
        }
    }
}
