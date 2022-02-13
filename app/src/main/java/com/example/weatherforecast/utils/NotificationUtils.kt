package com.example.weatherforecast.utils

import android.R.attr.smallIcon
import android.R.id
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherforecast.R
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import com.example.weatherforecast.presentation.MainActivity
import java.util.*


object NotificationUtils {
    fun showNotification(context: Context, weatherEntity: CurrentWeatherEntity) {

        val title = "Today weather  Temp : ${weatherEntity.temp}"
        val text = "Wind : ${weatherEntity.wind}"
        val subText = "Humidity : ${weatherEntity.humidity}"

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri = getDefaultSound()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannels(context, manager)
        }

        val intent =
            Intent(context, MainActivity::class.java)
        val notificationId = Random().nextInt(60000)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notificationBuilder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setSubText(subText)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        manager.notify(
            notificationId,
            notificationBuilder.build()
        )
    }

    private fun getDefaultSound(): Uri {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannels(
        context: Context,
        notificationManager: NotificationManager
    ) {
        val adminChannelName = context.getString(R.string.channel_name)
        val adminChannelDescription = context.getString(R.string.channel_name)

        val adminChannel = NotificationChannel(
            context.getString(R.string.channel_id),
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        adminChannel.setSound(getDefaultSound(), audioAttributes)
        notificationManager.createNotificationChannel(adminChannel)
    }
}