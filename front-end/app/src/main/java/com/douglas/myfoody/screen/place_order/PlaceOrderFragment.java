package com.douglas.myfoody.screen.place_order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.login_signup.MyToast;

public class PlaceOrderFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_order_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        setListeners();
        return view;
    }

    private void setListeners() {
        view.findViewById(R.id.buttonPlaceOrder).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPlaceOrder:
                fragmentManager.beginTransaction()
                        .replace(R.id.homeFrameContainer, new OrderPaymentFragment(), "Order_Payment_Fragment").commit();
                break;
            default:
                break;
        }
    }
}
