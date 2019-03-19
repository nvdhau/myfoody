package com.douglas.myfoody.core.utilities;

import android.graphics.Color;
import android.widget.EditText;

public class UIHelper {
    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
//        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
//        editText.setKeyListener(editText.getKeyListener());
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setFocusableInTouchMode(true);
    }
}
