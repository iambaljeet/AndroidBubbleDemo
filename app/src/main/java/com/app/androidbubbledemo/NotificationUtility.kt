package com.app.androidbubbledemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import java.lang.System.currentTimeMillis

class NotificationUtility(private val context: Context) {
    private val NOTIFICATION_CHANNEL_ID = "101"

    fun showNotification(notification: Notification?) {
        notification?.let {
            NotificationManagerCompat.from(context).apply {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "TestChannel", NotificationManager.IMPORTANCE_HIGH)
                    createNotificationChannel(channel)
                }
                notify(100, notification)
            }
        }
    }

    fun createNotification(message: String, smallIcon: Int, largerIcon: Bitmap?, person: String, personObject: Person, bubbleData: NotificationCompat.BubbleMetadata):
            Notification? {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setLargeIcon(largerIcon)
            .setSmallIcon(smallIcon)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setStyle(NotificationCompat.MessagingStyle(personObject)
                .setGroupConversation(false)
                .addMessage(message, currentTimeMillis(), personObject))
            .addPerson(person)
            .setShowWhen(true)
            .setBubbleMetadata(bubbleData)
            .build()
    }

    fun createBubble(icon: IconCompat, autoExpand: Boolean): NotificationCompat.BubbleMetadata {
        val target = Intent(context, FloatingActivity::class.java)
        val bubbleIntent = PendingIntent.getActivity(context, 0, target, 0)

        return NotificationCompat.BubbleMetadata.Builder()
            .setIcon(icon)
            .apply {
                if (autoExpand) {
                    setAutoExpandBubble(true)
                    setSuppressNotification(true)
                }
            }
            .setDesiredHeight(500)
            .setIntent(bubbleIntent)
            .build()
    }

    fun createPerson(isBot: Boolean, personName: String): Person {
        return Person.Builder()
            .setBot(isBot)
            .setName(personName)
            .setImportant(true)
            .build()
    }
}