package com.example.newscops.util

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.newscops.R
import java.lang.Exception


object HeadlineNotification {

    private val NOTIFICATION_TAG = "Headline"
    private val CHANNEL_ID = "headlines_channel_id"

    fun notify(
        context: Context,
        url: String,
        content: String
    ) {
        val res = context.resources

        val builder = NotificationCompat.Builder(context)

            .setDefaults(Notification.DEFAULT_ALL)

            .setSmallIcon(R.drawable.ic_stat_headline)
            .setContentTitle("Today's headline")
            .setContentText(content)

            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .setChannelId(CHANNEL_ID)

            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(Intent.ACTION_VIEW, Uri.parse(url)),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content)
                    .setBigContentTitle("Today's headline")
                    .setSummaryText("Today's headline")
            )

            .setAutoCancel(true)

        notify(context, builder.build())
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification) {
        try {
            val nm = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Headline"
                val description = "Notifies top news everry morning"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description
                nm.createNotificationChannel(channel)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                nm.notify(NOTIFICATION_TAG, 0, notification)
            } else {
                nm.notify(NOTIFICATION_TAG.hashCode(), notification)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        val nm = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0)
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode())
        }
    }
}
