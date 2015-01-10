package io.northernlights.logcleaner;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.Activity;

public class Dialogs {

    public static void success(String title, String content, Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do something here or just don't do anything
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public static void failure(String title, String content, Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do something here or just don't do anything
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
