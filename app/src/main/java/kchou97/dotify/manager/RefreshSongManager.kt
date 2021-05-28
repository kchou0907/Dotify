package kchou97.dotify.manager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit


private const val SONG_UPDATE_TAG = "SONG_UPDATE_TAG"

class RefreshSongManager(
    private val context: Context
    ) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun refreshSong() {

        val request = OneTimeWorkRequestBuilder<SongSyncWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager.enqueue(request)
    }

    fun refreshSongPeriodically() {
        if (isRefreshSongRunning()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<SongSyncWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(SONG_UPDATE_TAG)
            .build()

        workManager.enqueue(request)
    }

    fun stopRefreshSongPeriodically() {
        workManager.cancelAllWorkByTag(SONG_UPDATE_TAG)
    }

    private fun isRefreshSongRunning(): Boolean {
        return workManager.getWorkInfosByTag(SONG_UPDATE_TAG).get().any{
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}