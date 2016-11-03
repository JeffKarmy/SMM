package com.jeffrkarmy.sheetmetalreference.Class;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jeffrkarmy.sheetmetalreference.R;


public class Utility {

    public void alertMessage(Activity activity, String message, String button) {

        final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton(button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}
