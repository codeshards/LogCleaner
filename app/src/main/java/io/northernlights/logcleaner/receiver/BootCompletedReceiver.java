package io.northernlights.logcleaner.receiver;

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