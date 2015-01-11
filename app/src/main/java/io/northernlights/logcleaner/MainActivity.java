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
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity implements OnClickListener {

    private ArrayList<String> logFoldersPath;

    private long internalStorageTotalSpace;
    private long internalStorageUsedSpace;
    private long internalStorageFreeSpace;

    private String i18n_perform_cleaning_title;
    private String i18n_perform_cleaning_question;

    private String i18n_clean_operation_failed_title;
    private String i18n_clean_operation_failed_text;
    private String i18n_clean_operation_failed_no_root_access;
    private String i18n_clean_operation_failed_execution_error_as_root;
    private String i18n_clean_operation_failed_execution_error;

    private String i18n_clean_operation_success_title;
    private String i18n_clean_operation_success_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clean_command);

        // Init the log folders list
        logFoldersPath = new ArrayList<String>();
        logFoldersPath.add("/data/log_other_mode");
        logFoldersPath.add("/data/log");

        // Load i18n strings
        loadI18nStrings();

        // Add the listener to the buttons
        Button cleanBtn = (Button)findViewById(R.id.cleanBtn);
        ImageButton refreshBtn = (ImageButton)findViewById(R.id.refreshBtn);

        cleanBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);

        // Set internal storage space size
        TextView internalStorageText = (TextView)findViewById(R.id.internalStorageTextView);
        internalStorageTotalSpace = StorageInfo.TotalMemory();
        internalStorageText.append(" (" + StorageInfo.printHuman(internalStorageTotalSpace, "GB") + ")");

        // Set internal storage used and free space
        updateInternalStorageSpaceText();

        // Set internal storage progress bar
        updateInternalStorageProgressBar();

        // Set log total size
        updateLogFileSizeText();

    }

    private void loadI18nStrings() {
        i18n_perform_cleaning_title    = getResources().getString(R.string.i18n_perform_cleaning_title);
        i18n_perform_cleaning_question = getResources().getString(R.string.i18n_perform_cleaning_question);

        i18n_clean_operation_failed_title                   = getResources().getString(R.string.i18n_clean_operation_failed_title);
        i18n_clean_operation_failed_text                    = getResources().getString(R.string.i18n_clean_operation_failed_text);
        i18n_clean_operation_failed_no_root_access          = getResources().getString(R.string.i18n_clean_operation_failed_no_root_access);
        i18n_clean_operation_failed_execution_error_as_root = getResources().getString(R.string.i18n_clean_operation_failed_execution_error_as_root);
        i18n_clean_operation_failed_execution_error         = getResources().getString(R.string.i18n_clean_operation_failed_execution_error);

        i18n_clean_operation_success_title = getResources().getString(R.string.i18n_clean_operation_success_title);
        i18n_clean_operation_success_text  = getResources().getString(R.string.i18n_clean_operation_success_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clean_command, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cleanBtn:
                askPermissionToPerformCleaning();
                break;
            case R.id.refreshBtn:
                updateInternalStorageSpaceText();
                updateInternalStorageProgressBar();
                updateLogFileSizeText();
                break;
        }
    }

    private void askPermissionToPerformCleaning() {
        new AlertDialog.Builder(this)
                .setTitle(i18n_perform_cleaning_title)
                .setMessage(i18n_perform_cleaning_question)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        performCleaning();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void performCleaning() {
        if ( cleanLogs() ) {
            Dialogs.success(i18n_clean_operation_success_title, i18n_clean_operation_success_text, this);
            updateInternalStorageSpaceText();
            updateInternalStorageProgressBar();
            updateLogFileSizeText();
        } else {
            Dialogs.failure(i18n_clean_operation_failed_title, i18n_clean_operation_failed_text, this);
        }
    }

    private Boolean cleanLogs() {
        Boolean result = false;

        try {
            Process suProcess = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

            for(String logFolderPath : logFoldersPath) {
                File logDirectory = new File(logFolderPath);
                if (logDirectory.exists() && logDirectory.isDirectory()) {
                    String cmd = "rm -f " + logFolderPath + "/*\n";

                    os.writeBytes(cmd);
                    os.flush();
                }
            }

            os.writeBytes("exit\n");
            os.flush();

            try {
                int suProcessRetval = suProcess.waitFor();
                if ( suProcessRetval != 255) {
                    // Root Access granted
                    result = true;
                } else {
                    // Root Access denied
                    result = false;
                }
            } catch (Exception ex) {
                Dialogs.failure(i18n_clean_operation_failed_title, i18n_clean_operation_failed_execution_error_as_root, this);
            }

        } catch (IOException ex) {
            Dialogs.failure(i18n_clean_operation_failed_title, i18n_clean_operation_failed_no_root_access, this);
        } catch (SecurityException ex) {
            Dialogs.failure(i18n_clean_operation_failed_title, i18n_clean_operation_failed_no_root_access, this);
        } catch (Exception ex) {
            Dialogs.failure(i18n_clean_operation_failed_title, i18n_clean_operation_failed_execution_error, this);
        }

        return result;
    }

    /*
     *  User interface update methods
     */
    private void updateLogFileSizeText() {
        long logFolderSize = 0L;

        for(String logFolderPath : logFoldersPath) {
            File logDirectory = new File(logFolderPath);
            if (logDirectory.exists() && logDirectory.isDirectory()) {
                logFolderSize += StorageInfo.folderSize(logDirectory);
            }
        }

        TextView logFileSizeText = (TextView)findViewById(R.id.logFileSizeText);
        logFileSizeText.setText(StorageInfo.printHuman(logFolderSize, "MB"));
    }

    private void updateInternalStorageSpaceText() {
        TextView internalStorageUsedSpaceText = (TextView)findViewById(R.id.internalStorageUsedSpaceTextView);
        internalStorageUsedSpace = StorageInfo.UsedMemory();
        internalStorageUsedSpaceText.setText(StorageInfo.printHuman(internalStorageUsedSpace, "MB"));

        TextView internalStorageFreeSpaceText = (TextView)findViewById(R.id.internalStorageFreeSpaceTextView);
        internalStorageFreeSpace = StorageInfo.FreeMemory();
        internalStorageFreeSpaceText.setText(StorageInfo.printHuman(internalStorageFreeSpace, "MB"));
    }

    private void updateInternalStorageProgressBar() {
        ProgressBar internalStorageProgressBar = (ProgressBar)findViewById(R.id.internalStorageProgressBar);

        double internalStorageUsedSpaceMB  = ( internalStorageUsedSpace  / 1024 ) / 1024;
        double internalStorageTotalSpaceMB = ( internalStorageTotalSpace / 1024 ) / 1024;

        long usedPercent = Math.round( (internalStorageUsedSpaceMB / internalStorageTotalSpaceMB) * 100);

        internalStorageProgressBar.setProgress((int)usedPercent);
        if ( usedPercent <= 50 ) {
            internalStorageProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));
        } else if( usedPercent > 50 && usedPercent < 80 ) {
            internalStorageProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_warning));
        } else {
            internalStorageProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_danger));
        }
    }

}
