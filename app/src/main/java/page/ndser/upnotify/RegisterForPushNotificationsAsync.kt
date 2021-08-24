package page.ndser.upnotify

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import me.pushy.sdk.Pushy

class RegisterForPushNotificationsAsync(activity: Activity) : AsyncTask<Void, Void, Any>() {
    var activity: Activity = activity;

    override fun doInBackground(vararg params: Void): Any {
        try {
            // Register the device for notifications
            val deviceToken = Pushy.register(activity)

            // Registration succeeded, log token to logcat
            Log.d("Pushy", "Pushy device token: " + deviceToken)
            Pushy.subscribe("uptime", activity)

            // Provide token to onPostExecute()
            return deviceToken
        } catch (exc: Exception) {
            // Registration failed, provide exception to onPostExecute()
            return exc
        }
    }

    override fun onPostExecute(result: Any) {
        var message: String

        // Registration failed?
        if (result is Exception) {
            // Log to console
            result.message?.let { Log.e("Pushy", it) }

            // Display error in alert
            message = result.message.toString()
        }
        else {
            // Registration success, result is device token
            message = "Pushy device token: " + result.toString() + "\n\n(copy from logcat)"
        }

        // Display dialog
        android.app.AlertDialog.Builder(activity)
            .setTitle("Pushy")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}