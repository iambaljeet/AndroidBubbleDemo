package com.app.androidbubbledemo

import android.graphics.Bitmap
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var notificationUtility: NotificationUtility
    private var notificationsCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationUtility = NotificationUtility(this)

        val drawable = ContextCompat.getDrawable(this, R.drawable.user_avatar)
        val bitmap = drawable?.toBitmap()
        val icon = IconCompat.createWithAdaptiveBitmap(bitmap)

        button_create_notification.setOnClickListener {
            val timer = object: CountDownTimer(10000, 2000) {
                override fun onFinish() {
                }

                override fun onTick(time: Long) {
                    buildAndShowNotification(icon, bitmap)
                }
            }
            timer.start()
        }
    }

    private fun buildAndShowNotification(
        icon: IconCompat,
        bitmap: Bitmap?
    ) {
        val autoExpand = notificationsCount == 1
        val bubble = notificationUtility.createBubble(icon = icon, autoExpand = autoExpand)
        val person = notificationUtility.createPerson(true, "Baljeet")
        val notification = notificationUtility.createNotification(
            message = "Message $notificationsCount",
            smallIcon = R.drawable.ic_android_icon,
            largerIcon = bitmap,
            person = "Baljeet",
            bubbleData = bubble,
            personObject = person
        )
        notificationUtility.showNotification(notification)
        notificationsCount += 1
    }
}