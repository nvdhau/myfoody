package com.douglas.myfoody.core.utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UIHelper {
    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setFocusableInTouchMode(true);
    }

    public static void setItemDetailsTextView(View v, int textViewId, String content){
        TextView textView = v.findViewById(textViewId);
        textView.setText(content);
    }

    public static TextView createItemDetailsTextView(Context context, int textSize, String textColor, String content, int gravity, int paddingRight){
        TextView textView = new TextView(context);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.parseColor(textColor));
        textView.setGravity(gravity);
        textView.setText(content);
        textView.setPadding(0,0,paddingRight ,0);
        return textView;
    }
}
