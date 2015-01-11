package io.northernlights.logcleaner.receiver;

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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.northernlights.logcleaner.Notifier;
import io.northernlights.logcleaner.R;
import io.northernlights.logcleaner.SettingsActivity;
import io.northernlights.logcleaner.StorageManager;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
//            Intent serviceIntent = new Intent(context, MySystemService.class);
//            context.startService(serviceIntent);

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

            Boolean enableBootTimeCleaning     = sharedPref.getBoolean(SettingsActivity.KEY_PREF_ENABLE_AT_BOOT_TIME, false);
            Boolean enableNotifications        = sharedPref.getBoolean(SettingsActivity.KEY_PREF_ENABLE_NOTIFICATIONS, false);
            Boolean enableBootTimeNotification = sharedPref.getBoolean(SettingsActivity.KEY_PREF_ENABLE_BOOT_TIME_NOTIFICATIONS, false);

            if ( enableBootTimeCleaning ) {
                if (StorageManager.performCleaningAtBoot()) {
                    if (enableNotifications && enableBootTimeNotification) {

                        String notificationTitle = context.getResources().getString(R.string.app_label);
                        String notificationText  = context.getResources().getString(R.string.i18n_clean_at_boot_time_notification_success);

                        Notifier.showNotification(notificationTitle, notificationText, context);
                    }
                }
            }
        }
    }
}