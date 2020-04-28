package com.douglas.myfoody.screen.home.my_orders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.MenuItem;
import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.utilities.JSONHelper;
import com.douglas.myfoody.core.utilities.UIHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MyOrderDetailFragment extends Fragment {
    private static View view;

    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_order_detail_layout, container, false);

        Bundle bundle = this.getArguments();

        if(bundle != null){

            getActivity().setTitle("Order Details");
            Order parcelableOrder = bundle.getParcelable("order");

            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);

            UIHelper.setItemDetailsTextView(view, R.id.restaurant_name, parcelableOrder.getRestaurantName());
            UIHelper.setItemDetailsTextView(view, R.id.order_date, "Date: " + parcelableOrder.getCreatedAt());
            UIHelper.setItemDetailsTextView(view, R.id.textViewShowSubTotal, format.format(parcelableOrder.getSubTotal()));
            UIHelper.setItemDetailsTextView(view, R.id.textViewShowDeliveryFee, format.format(parcelableOrder.getDeliveryFee()));
            UIHelper.setItemDetailsTextView(view, R.id.textViewShowTax, format.format(parcelableOrder.getTax()));
            UIHelper.setItemDetailsTextView(view, R.id.textViewShowDiscount, format.format(parcelableOrder.getDiscount()));
            UIHelper.setItemDetailsTextView(view, R.id.textViewShowTotal, format.format(parcelableOrder.getTotal()));
            UIHelper.setItemDetailsTextView(view, R.id.textViewDeliveryTo, parcelableOrder.getDeliveryAddress());
            UIHelper.setItemDetailsTextView(view, R.id.textViewInstruction, parcelableOrder.getSpecialInstruction());


            TableLayout itemsTable = (TableLayout)view.findViewById(R.id.items_table);

            // Set new table row layout parameters.
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT;
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
            layoutParams.bottomMargin = 16;

            List<MenuItem> listPurchasedItems = JSONHelper.getItemListFromOrderDetail(parcelableOrder.getItems());

            for (MenuItem item :
                    listPurchasedItems) {
                // Create a new table row and set params
                TableRow tableRow = new TableRow(getContext());
                tableRow.setLayoutParams(layoutParams);

                //add row cell data
                tableRow.addView(UIHelper.createItemDetailsTextView(getContext(),
                        18, "#FFFFFF", item.getItemName(), Gravity.LEFT, 0), 0);

                tableRow.addView(UIHelper.createItemDetailsTextView(getContext(),
                        18, "#FFFFFF", "x" + item.getQuantity(), Gravity.LEFT, 100), 1);

                tableRow.addView(UIHelper.createItemDetailsTextView(getContext(),
                        18, "#FFFFFF", format.format(item.getPrice()), Gravity.RIGHT, 0), 2);

                itemsTable.addView(tableRow);
            }
        }
        return view;
    }

}
