package io.northernlights.logcleaner;

/*
 * LogCleaner - Helps you to save Internal Storage space deleting log folders
 * Copyright (C) 2014 Alessandro Garzaro
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.

 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
