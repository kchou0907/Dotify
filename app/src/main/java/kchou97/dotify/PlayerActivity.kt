package kchou97.dotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kchou97.dotify.databinding.ActivityPlayerBinding
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

private const val SONG_KEY = "song"

/*
* Navigates to the Player Activity screen
* @Param context Context - provides information on whatever activity the user is coming from
* @Param songDeets Song - song object containing all the song details
* */
fun navPlayerActivity(context: Context, songDeets: Song) {
    val intent = Intent(context, PlayerActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(SONG_KEY, songDeets)
        }
        putExtras(bundle)
    }
    context.startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {
    private val randomNumber = Random.nextInt(1000, 10000)
    private var playNum = 0
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }
        val song: Song? = intent.getParcelableExtra<Song>(SONG_KEY)

        with (binding) {

            playNum = randomNumber
            if (savedInstanceState != null) {
                with(savedInstanceState) {
                    playNum = getInt("playCount")
                }
            }
            playCount.text = root.context.getString(R.string.play_count, playNum)



            var songDetails: SongDetails = SongDetails(
                song = song,
                count = playNum
            )

            artist.text = song?.artist
            song?.largeImageID?.let { albumPic.setImageResource(it) }
            songTitle.text = song?.title

            settings.setOnClickListener{
                navSettingsActivity(this@PlayerActivity, songDetails)
            }

            prev.setOnClickListener {
                displayMessage("Skipping to previous track")
            }

            next.setOnClickListener {
                displayMessage("Skipping to next track")
            }

            play.setOnClickListener {
                incrementCounter(playCount)
                songDetails.count++
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putInt("playCount", playNum)
        super.onSaveInstanceState(outState)
    }

    fun displayMessage(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }
    fun incrementCounter(playCounter: TextView) {
        playNum++
        playCounter.text = playNum.toString() + " plays"
    }
}

@Parcelize
data class SongDetails(
    val song: Song?,
    var count: Int
): Parcelable