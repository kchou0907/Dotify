package kchou97.dotify.manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kchou97.dotify.DotifyApplication

class SongSyncWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val application by lazy {context.applicationContext as DotifyApplication}
    private val songNotificationManager by lazy {application.songNotificationManager}

    override suspend fun doWork(): Result {
        songNotificationManager.publishNewSongNotification()
        return Result.success()
    }

}
