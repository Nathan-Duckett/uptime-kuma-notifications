package page.ndser.upnotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.pushy.sdk.Pushy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Pushy.isRegistered(this)) {
            RegisterForPushNotificationsAsync(this).execute()
        }

        Pushy.listen(this)
    }
}