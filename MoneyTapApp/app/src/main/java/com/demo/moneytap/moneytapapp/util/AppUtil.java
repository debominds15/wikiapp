package com.demo.moneytap.moneytapapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.demo.moneytap.moneytapapp.R;

/**
 * Util class to carry out common task
 */
public class AppUtil {

    private static AlertDialog sProgressAlertDialog;

    public static void show(Context context, String message) {
        if (sProgressAlertDialog != null && sProgressAlertDialog.isShowing()) {
            sProgressAlertDialog.dismiss();
            sProgressAlertDialog = null;
        }

        AlertDialog.Builder progressBuilder = new AlertDialog.Builder(context, R.style.ProgressDialogTheme);

        View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_bar, null);
        progressBuilder.setView(view);
        progressBuilder.setCancelable(false);

        TextView progressText = view.findViewById(R.id.progress_text);
        progressText.setText(message);
        sProgressAlertDialog = progressBuilder.create();
        sProgressAlertDialog.show();
    }

    public static void dismiss() {
        if (sProgressAlertDialog != null && sProgressAlertDialog.isShowing()) {
            sProgressAlertDialog.dismiss();
            sProgressAlertDialog = null;
        }
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
