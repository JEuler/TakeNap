package com.cloverrepublic.takenap

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

import java.util.Calendar

/**
 * Created by itere on 18.04.2016.
 */
class TakeNapBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED != intent.action) {
            //ALARM_ACTION
            val mBuilder = NotificationCompat.Builder(context)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(context.getString(R.string.app_name))
            // Creates an explicit intent for an Activity in your app
            val resultIntent = Intent(context, MainActivity::class.java)
            val notification_id = Calendar.getInstance().timeInMillis.toInt()
            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            val stackBuilder = TaskStackBuilder.create(context)
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity::class.java)
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            mBuilder.setContentIntent(resultPendingIntent)
            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // mId allows you to update the notification later on.
            mNotificationManager.notify(notification_id, mBuilder.build())
        }
    }
}

