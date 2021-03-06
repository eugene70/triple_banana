// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package org.triple.banana.secure_dns;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;

import androidx.core.app.NotificationCompat;

import org.banana.cake.interfaces.BananaApplicationUtils;
import org.triple.banana.R;

public enum SecureDnsNotificationManager {
    instance;

    private static final String CHANNEL_ID = "secure_dns";

    private SecureDnsNotificationManager() {}

    public static SecureDnsNotificationManager getInstance() {
        return instance;
    }

    public void showSecureDnsNotificationIfNeeded() {
        if (wasNotificationShown()) return;

        Context context = BananaApplicationUtils.get().getApplicationContext();
        if (context == null) return;

        final NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) return;

        NotificationCompat.Builder builder = createNotificationBuilder(context, manager);
        builder.setContentTitle(context.getResources().getString(R.string.secure_dns))
                .setContentText(context.getResources().getString(R.string.secure_dns_summary))
                .setSmallIcon(R.drawable.toolbar_button_banana)
                .setTimeoutAfter(3000)
                .setAutoCancel(true)
                .setNumber(0)
                .setSound(null)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        manager.notify(10000 /* id */, builder.build());
        markNotificationShown(true);

        // NOTE: The setTimeoutAfter() is not working on N- OS. So, the following code is a
        // workaround to remove the notification after 3 seconds. It works pretty well, but it may
        // not be canceled if the app exits before the post delayed task is not executed. Using the
        // AlarmManager may be better, but it's not worth the effort, so simply use postDelayed().
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            (new Handler()).postDelayed(() -> {
                manager.cancel(10000 /* id */);
            }, 3000);
        }
    }

    public void resetNotificationState() {
        markNotificationShown(false);
    }

    private void markNotificationShown(boolean value) {
        SharedPreferences.Editor editor =
                BananaApplicationUtils.get().getSharedPreferences().edit();
        editor.putBoolean("feature_secure_dns_notification_shown", value);
        editor.apply();
    }

    public boolean wasNotificationShown() {
        return BananaApplicationUtils.get().getSharedPreferences().getBoolean(
                "feature_secure_dns_notification_shown", false);
    }

    private NotificationCompat.Builder createNotificationBuilder(
            Context context, NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    context.getResources().getString(R.string.secure_dns),
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(false);
            channel.setSound(null, null);
            manager.createNotificationChannel(channel);
            return new NotificationCompat.Builder(context, CHANNEL_ID);
        }

        return new NotificationCompat.Builder(context).setPriority(
                NotificationCompat.PRIORITY_HIGH);
    }
}
