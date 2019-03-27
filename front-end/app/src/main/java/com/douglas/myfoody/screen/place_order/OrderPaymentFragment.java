package com.douglas.myfoody.screen.place_order;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.models.Promotion;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.utilities.Utils;
import com.douglas.myfoody.screen.login_signup.MyToast;
import com.douglas.myfoody.screen.viewmodel.OrderViewModel;
import com.douglas.myfoody.screen.viewmodel.PromotionViewModel;

public class OrderPaymentFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static FragmentManager fragmentManager;

    private Order mOrder;
    private Promotion appliedPromotion;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_order_payment_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        if(loadOrderFromIntent()) setListeners();
        return view;
    }

    private void setListeners() {
        view.findViewById(R.id.buttonPayment).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPayment:
                if(processPayment() && saveOrder()) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.placeOrderFrameContainer, new OrderFinalizedFragment(), "Order_Finalized_Fragment").commit();
                }
                break;
            default:
                break;
        }
    }

    private boolean loadOrderFromIntent() {
        Intent intent = getActivity().getIntent();
        if(intent == null) {
            return false;
        }

        mOrder = intent.getParcelableExtra("order");
        appliedPromotion = intent.getParcelableExtra("promotion");
        currentUser = Utils.getLoggedInUser();

        TextView subTotal = view.findViewById(R.id.textViewShowSubTotal);
        subTotal.setText(subTotal.getText().toString() + String.format("%.2f", mOrder.getSubTotal()));

        TextView deliveryFee = view.findViewById(R.id.textViewShowDeliveryFee);
        deliveryFee.setText(deliveryFee.getText().toString() + String.format("%.2f", mOrder.getDeliveryFee()));

        TextView discount = view.findViewById(R.id.textViewShowDiscount);
        discount.setText(discount.getText().toString() + String.format("%.2f", mOrder.getDiscount()));

        TextView tax = view.findViewById(R.id.textViewShowTax);
        tax.setText(tax.getText().toString() + String.format("%.2f", mOrder.getTax()));

        TextView total = view.findViewById(R.id.textViewShowTotal);
        total.setText(total.getText().toString() + String.format("%.2f", mOrder.getTotal()));

        return true;
    }

    private boolean processPayment() {
        String cardName = ((EditText) view.findViewById(R.id.editTextCardName)).getText().toString();
        String cardNumber = ((EditText) view.findViewById(R.id.editTextCardNumber)).getText().toString();
        String cardExpiry = ((EditText) view.findViewById(R.id.editTextExpiry)).getText().toString();
        String cardSecurityCode = ((EditText) view.findViewById(R.id.editTextCardCode)).getText().toString();

        if(cardName.isEmpty() || cardNumber.isEmpty() || cardExpiry.isEmpty() || cardSecurityCode.isEmpty()) {
            MyToast.showToast(getActivity(), view, "Card details are required");
            return false;
        } else {
            // TODO: validate payment
            boolean paymentSuccess = true;

            if(!paymentSuccess) {
                MyToast.showToast(getActivity(), view, "Fail to pay using this card detail");
                return false;
            }
        }

        return true;
    }

    private boolean saveOrder() {
        // Save Order
        OrderViewModel ovm = ViewModelProviders.of(this).get(OrderViewModel.class);
        mOrder.setCreatedAt(Utils.formatDate(Utils.getToday()));
        if(!ovm.insert(mOrder)) {
            return false;
        }

        // Expire the applied promotion
        if(appliedPromotion != null) {
            PromotionViewModel pvm = ViewModelProviders.of(this).get(PromotionViewModel.class);
            if (!pvm.expireDiscount(appliedPromotion, currentUser.getEmail())) {
                return false;
            }
        }

        return true;
    }
}
