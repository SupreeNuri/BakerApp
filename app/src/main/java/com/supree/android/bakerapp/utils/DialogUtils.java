package com.supree.android.bakerapp.utils;


import android.app.Activity;
import android.app.Dialog;

public class DialogUtils {
    private Dialog dialog;

    public void showDialogIngredients(final Activity activity, final String question) {
        synchronized (activity) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //dialog = new DialogDropdown(activity, question, answers, listener, currentAnswer);
                    //dialog.show();
                }
            });
        }
    }

    public Dialog getDialog() {
        return dialog;
    }
}
