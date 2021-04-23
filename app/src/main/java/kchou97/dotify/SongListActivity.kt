package kchou97.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kchou97.dotify.databinding.ActivitySongListBinding


class SongListActivity : AppCompatActivity() {

    // the song that is currently in question
    lateinit var song: Song
    // the title of the activity
    val TITLE = "All Songs"

    /*
    * Initializes the activity. This is the stuff that will happen when the activity first pops up
    * @Param savedInstanceState Bundle? - information relating to a previous state
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            val adapter = SongListAdapter(SongDataProvider.getAllSongs())
            rvSongs.adapter = adapter

            title = TITLE

            adapter.onSongClickListener = { songDeets ->
                currSong.text = root.context.getString(R.string.song_playing_format, songDeets.title, songDeets.artist)
                song = songDeets
            }

            adapter.onSongLongClickListener = { song ->
                adapter.removeFromList(song)
                showRemovedSong(song)
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

    fun showRemovedSong(song: Song) {
        var title = song.title
        Toast.makeText(this,"$title was removed from the song list" ,Toast.LENGTH_SHORT).show()
    }
}