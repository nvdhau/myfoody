package com.douglas.myfoody.screen.place_order;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.MenuItem;
import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.models.Promotion;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.utilities.JSONHelper;
import com.douglas.myfoody.core.utilities.UIHelper;
import com.douglas.myfoody.core.utilities.Utils;
import com.douglas.myfoody.screen.login_signup.MyToast;
import com.douglas.myfoody.screen.viewmodel.PromotionViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceOrderFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static FragmentManager fragmentManager;

    private Restaurant mRestaurant;
    private ArrayList<MenuItem> mItems;
    private Order mOrder;
    private List<Promotion> promotions;
    private Promotion appliedPromotion;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_order_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        if(loadOrderFromIntent())
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
                if(readUserInputsToOrder()) {
                    Intent intent = getActivity().getIntent();
                    intent.putExtra("order", mOrder);
                    intent.putExtra("promotion", appliedPromotion);

                    fragmentManager.beginTransaction()
                            .replace(R.id.placeOrderFrameContainer, new OrderPaymentFragment(), "Order_Payment_Fragment").commit();
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

        mRestaurant = intent.getParcelableExtra("restaurant");
        mItems = intent.getParcelableArrayListExtra("items");
        currentUser = Utils.getLoggedInUser();

        mOrder = new Order();
        mOrder.setUserEmail(currentUser.getEmail());
        mOrder.setRestaurantId(mRestaurant.getID());

        mOrder.setDeliveryAddress(currentUser.getAddress());
        EditText edAddress = view.findViewById(R.id.editTextDeliveryTo);
        edAddress.setText(mOrder.getDeliveryAddress());

        mOrder.setSpecialInstruction("");
        mOrder.setDeliveryFee(0.0);
        mOrder.setDiscount(0.0);

        double subTotal = 0.0;
        ArrayList<MenuItem> nonZeroItems = new ArrayList<>();

        TableLayout itemsTable = (TableLayout)view.findViewById(R.id.items_table);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);

        // Set new table row layout parameters.
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.width = TableRow.LayoutParams.MATCH_PARENT;
        layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
        layoutParams.bottomMargin = 16;


        for(int i=0; i<mItems.size(); i++) {
            MenuItem currentItem = mItems.get(i);
            if(currentItem.getQuantity() > 0) {
                nonZeroItems.add(currentItem);
                subTotal += currentItem.getQuantity() * currentItem.getPrice();

                //display items order details
                // Create a new table row and set params
                TableRow tableRow = new TableRow(getContext());
                tableRow.setLayoutParams(layoutParams);

                //add row cell data
                tableRow.addView(UIHelper.createItemDetailsTextView(getContext(),
                        18, "#FFFFFF", currentItem.getItemName(), Gravity.LEFT, 0), 0);

                tableRow.addView(UIHelper.createItemDetailsTextView(getContext(),
                        18, "#FFFFFF", "x" + currentItem.getQuantity(), Gravity.LEFT, 100), 1);

                tableRow.addView(UIHelper.createItemDetailsTextView(getContext(),
                        18, "#FFFFFF", format.format(currentItem.getPrice()), Gravity.RIGHT, 0), 2);

                itemsTable.addView(tableRow);
            }
        }


        mOrder.setSubTotal(subTotal);
        mItems = nonZeroItems;
        mOrder.setItems(JSONHelper.parseMenuItemsListToJSON(mItems));

        mOrder.setTax(0.0);
        mOrder.setTotal(0.0);

        TextView tvSubTotal = view.findViewById(R.id.textViewSubTotal);
        tvSubTotal.setText(format.format(subTotal));

        PromotionViewModel pvm = ViewModelProviders.of(this).get(PromotionViewModel.class);
        promotions = pvm.getUserDiscounts(currentUser.getEmail());
        List<String> sPromotions = new ArrayList<String>();
        for(int i=0; i<promotions.size(); i++) {
            sPromotions.add(promotions.get(i).getPromotionCode()
                    + " - " + promotions.get(i).getDiscountAmount()
                    + (Promotion.DISCOUNT_TYPE_PERCENT.equals(promotions.get(i).getDiscountType()) ? "%" : " CAD"));
        }
        sPromotions.add("None");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, sPromotions);
        ((Spinner) view.findViewById(R.id.spinnerDiscountCodes)).setAdapter(adapter);

        return true;
    }

    private boolean readUserInputsToOrder() {
        String specialInstructions = ((EditText) view.findViewById(R.id.editTextInstructions)).getText().toString();
        mOrder.setSpecialInstruction(specialInstructions);

        String deliveryOption = (String) ((Spinner) view.findViewById(R.id.spinnerDeliveryOptions)).getSelectedItem();
        if("Delivery".equals(deliveryOption)) {
            String deliveryAddress = ((EditText) view.findViewById(R.id.editTextDeliveryTo)).getText().toString();

            if(deliveryAddress.isEmpty()) {
                MyToast.showToast(getActivity(), view, "Address is required if you choose delivery");
                return false;
            } else {
                mOrder.setDeliveryAddress(deliveryAddress);
                // TODO: calculate delivery fee
                mOrder.setDeliveryFee(10.0);
            }
        } else {
            mOrder.setDeliveryAddress("Pick Up");
            mOrder.setDeliveryFee(0.0);
        }

        String promotionOption = (String) ((Spinner) view.findViewById(R.id.spinnerDiscountCodes)).getSelectedItem();
        if(!"None".equals(promotionOption)) {
            appliedPromotion = promotions.get(((Spinner) view.findViewById(R.id.spinnerDiscountCodes)).getSelectedItemPosition());
            double discountAmount = appliedPromotion.getDiscountAmount(mOrder.getSubTotal());
            mOrder.setDiscount(discountAmount);
        }

        double total = mOrder.getSubTotal() + mOrder.getDeliveryFee() - mOrder.getDiscount();
        double tax = total * 12.0 / 100.0;
        total += tax;
        mOrder.setTax(tax);
        mOrder.setTotal(total);

        return true;
    }
}
