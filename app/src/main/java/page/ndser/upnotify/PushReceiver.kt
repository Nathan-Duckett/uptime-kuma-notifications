package page.ndser.upnotify

import me.pushy.sdk.Pushy
import android.content.Intent
import android.graphics.Color
import android.content.Context
import android.app.PendingIntent
import android.media.RingtoneManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var notificationTitle = "Uptime Kuma"
        var notificationText = "No content - Check Uptime Kuma"

        // Attempt to extract the "message" property from the payload: {"message":"Hello World!"}
        if (intent.getStringExtra("message") != null) {
            notificationTitle = intent.getStringExtra("message")!!
        }

        // Attempt to extract the "description" property from the payload: {"message":"Hello World!"}
        if (intent.getStringExtra("body") != null) {
            notificationText = intent.getStringExtra("body")!!
        }

        // Prepare a notification with vibration, sound and lights
        val builder = NotificationCompat.Builder(context)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setLights(Color.RED, 1000, 1000)
            .setVibrate(longArrayOf(0, 400, 250, 400))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT))

        // Automatically configure a Notification Channel for devices running Android O+
        Pushy.setNotificationChannel(builder, context)

        // Get an instance of the NotificationManager service
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Build the notification and display it
        notificationManager.notify(1, builder.build())
    }
}