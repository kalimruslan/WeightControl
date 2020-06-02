package ru.ruslan.weighttracker.ui.firebase

import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.util.showToast

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        val handler = Handler(Looper.getMainLooper())

        //2
        handler.post(Runnable {
            "Получен ПУШ".showToast(baseContext)
        })
    }


}