package com.douglas.myfoody.screen.main.login_signup;

import com.douglas.myfoody.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {

    // custom toast method
    public static void showToast(Context context, View view, String error) {

        // set layout for custom view
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.my_toast, (ViewGroup) view.findViewById(R.id.toast_root));
        TextView textView = (TextView) layout.findViewById(R.id.toast_error);
        textView.setText(error);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
