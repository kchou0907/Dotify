package kchou97.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.ericchee.songdataprovider.SongDataProvider
import kchou97.dotify.DotifyApplication
import kchou97.dotify.R
import kchou97.dotify.activity.PlayerActivity
import kotlin.random.Random

private const val NEW_SONG_CHANNEL_ID = "NEW_SONG_CHANNEL_ID"

class SongNotificationManager(
    private val context: Context
    ) {

    private val notificationManager = NotificationManagerCompat.from(context)
    private val application by lazy {context.applicationContext as DotifyApplication }

    init {
        initNotificationChannels()
    }

    fun publishNewSongNotification() {

        val intent = Intent(context, PlayerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        var song = SongDataProvider.getAllSongs().random()
        application.currSong = song

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        var builder = NotificationCompat.Builder(context, NEW_SONG_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_song)
            .setContentTitle(context.getString(R.string.notif_title, song.artist))
            .setContentText(context.getString(R.string.notif_text, song.title))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(notificationManager) {
            val notificationID = Random.nextInt()
            notify(notificationID, builder.build())
        }
    }

    private fun initNotificationChannels() {
        initNewSongsChannel()
    }
    private fun initNewSongsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "New Uploaded Music"
            val descriptionText = context.getString(R.string.new_song_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(NEW_SONG_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            notificationManager.createNotificationChannel(channel)
        }
    }


}
