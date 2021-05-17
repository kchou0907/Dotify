package kchou97.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kchou97.dotify.adapter.SongListAdapter
import kchou97.dotify.databinding.ActivitySongListBinding
import kotlinx.coroutines.launch


class SongListActivity : AppCompatActivity() {

    // the song that is currently in question
    lateinit var song: Song
    // the title of the activity
    val TITLE = "All Songs"
    var miniplayerText: String = ""
    private val dotApp: DotifyApplication by lazy { application as DotifyApplication }
    private val dataRepository by lazy { dotApp.dataRepository }

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

            if (savedInstanceState != null) {
                with(savedInstanceState) {
                    miniplayerText = getString("miniplayer")!!
                }
                currSong.text = miniplayerText
            }

            adapter.onSongClickListener = { songDeets ->
                miniplayerText = root.context.getString(R.string.song_playing_format, songDeets.title, songDeets.artist)
                currSong.text = miniplayerText
                dotApp.currSong = songDeets
                song = dotApp.currSong
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
                    navPlayerActivity(this@SongListActivity)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("miniplayer", miniplayerText)
        }
        super.onSaveInstanceState(outState)
    }

    fun showRemovedSong(song: Song) {
        var title = song.title
        Toast.makeText(this,"$title was removed from the song list" ,Toast.LENGTH_SHORT).show()
    }
}