package kchou97.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import kchou97.dotify.repository.DataRepository

class DotifyApplication: Application() {
    lateinit var currSong: Song
    lateinit var dataRepository: DataRepository
    var count = 0
    override fun onCreate() {
        super.onCreate()
        dataRepository = DataRepository()
    }
}