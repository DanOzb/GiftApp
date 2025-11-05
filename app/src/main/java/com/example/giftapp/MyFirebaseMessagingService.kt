package com.example.giftapp
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("FCM", "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM", "Message received from: ${message.from}")

        message.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")
            //TODO: Show notification
        }
    }
}