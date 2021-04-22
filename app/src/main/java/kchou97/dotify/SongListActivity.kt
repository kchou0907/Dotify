package kchou97.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kchou97.dotify.databinding.ActivitySongListBinding


class SongListActivity : AppCompatActivity() {

    lateinit var song: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            val adapter = SongListAdapter(SongDataProvider.getAllSongs())
            rvSongs.adapter = adapter

            adapter.onSongClickListener = { songDeets ->
                currSong.text = root.context.getString(R.string.song_playing_format, songDeets.title, songDeets.artist)
                song = songDeets
            }

            shuffleBtn.setOnClickListener{
                adapter.updateSongList(SongDataProvider.getAllSongs().shuffled())
            }
            miniPlayer.setOnClickListener {
                if(currSong.text.length > 0) {
                    navPlayerActivity(this@SongListActivity, song)
                }
            }
        }
    }
}