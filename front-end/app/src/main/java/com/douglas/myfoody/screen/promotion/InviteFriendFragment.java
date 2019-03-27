package com.douglas.myfoody.screen.promotion;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.Promotion;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.screen.viewmodel.PromotionViewModel;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

public class InviteFriendFragment extends Fragment implements View.OnClickListener {
    private static final String INVITE_CODE = "INVITE10";
    private static final float DISCOUNT_AMOUNT = 10;
    private static final String DISCOUNT_TYPE = Promotion.DISCOUNT_TYPE_PERCENT;
    private static final int MAX_DISCOUNT_CODE = 1;

    private static View view;
    private static FragmentManager fragmentManager;

    private static PromotionViewModel pvm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.invite_friend_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        view.findViewById(R.id.buttonInviteFriend).setOnClickListener(this);

        pvm = ViewModelProviders.of(this).get(PromotionViewModel.class);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonInviteFriend) {
            TextView tvInviteResult = view.findViewById(R.id.textViewInviteResult);

            // Get input emails list
            EditText edFriendEmails = view.findViewById(R.id.editTextFriendEmails);
            String[] emails = edFriendEmails.getText().toString().trim().split("\n");
            for(int i=0; i<emails.length; i++) {
                if(!Patterns.EMAIL_ADDRESS.matcher(emails[i]).matches()) {
                    tvInviteResult.setText(emails[i] + " is not a valid email.");
                    return;
                }
            }
            // TODO: Handle invite emails list

            // Check if this user is eligible for INVITE10 code
            UserViewModel uvm = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
            User user = uvm.getUser().getValue();

            if(isEligibleForDiscountCode(user)) {
                // Add Invite discount code for user
                Promotion p = new Promotion();
                p.setPromotionCode(INVITE_CODE);
                p.setDiscountAmount(DISCOUNT_AMOUNT);
                p.setDiscountType(DISCOUNT_TYPE);

                if(pvm.addDiscount(p, user.getEmail(), "9999-12-31 23:59:59")) {
                    tvInviteResult.setText("Invitations Sent! You will be able to apply INVITE10 code for 10% off on the next order.");
                } else {
                    tvInviteResult.setText("Invitations Not Sent! Please try again later.");
                }
            } else {
                tvInviteResult.setText("Invitations Sent! You have already had INVITE10 discount code.");
            }
        }
    }

    private boolean isEligibleForDiscountCode(User user) {
        int numberOfDiscounts = pvm.getSpecificUserDiscounts(INVITE_CODE, user.getEmail()).size();
        return numberOfDiscounts < MAX_DISCOUNT_CODE;
    }
}
