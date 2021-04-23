package kchou97.dotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kchou97.dotify.databinding.ActivityPlayerBinding
import kchou97.dotify.databinding.ActivitySongListBinding
import kchou97.dotify.databinding.ItemSongBinding.inflate
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }
        var submitIsVisible: Boolean = true;

        with (binding) {

            playCount.text = randomNumber.toString() + " plays"
            playNum = randomNumber
            val song: Song? = intent.getParcelableExtra<Song>(SONG_KEY)

            submitUser.visibility = View.GONE
            editUsername.visibility = View.GONE

            artist.text = song?.artist
            song?.largeImageID?.let { albumPic.setImageResource(it) }
            songTitle.text = song?.title


            prev.setOnClickListener {
                displayMessage("Skipping to previous track")
            }

            next.setOnClickListener {
                displayMessage("Skipping to next track")
            }

            play.setOnClickListener {
                incrementCounter(playCount)
            }

            changeUser.setOnClickListener {
                toggleEdit(submitUser, editUsername, changeUser, username, submitIsVisible)
                submitIsVisible = !submitIsVisible
            }

            submitUser.setOnClickListener {
                if (editUserName(editUsername, username)) {
                    toggleEdit(submitUser, editUsername, changeUser, username, !submitIsVisible)
                    submitIsVisible = !submitIsVisible
                } else {
                    displayMessage("Please submit a valid username")
                }
            }
        }
    }
    fun displayMessage(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }
    fun incrementCounter(playCounter: TextView) {
        playNum++
        playCounter.text = playNum.toString() + " plays"
    }
    fun editUserName(textInput: EditText, userfield: TextView): Boolean{
        var newUsername = textInput.editableText
        if (newUsername.length == 0) {
            return false
        } else {
            userfield.text = newUsername
            return true
        }

    }
    fun toggleEdit(submitBtn: Button, textInput: EditText, editBtn: Button, userfield: TextView, submitIsVisible: Boolean) {
        if (submitIsVisible) {
            editBtn.visibility = View.GONE
            userfield.visibility = View.GONE

            submitBtn.visibility = View.VISIBLE
            textInput.visibility = View.VISIBLE
        } else {
            editBtn.visibility = View.VISIBLE
            userfield.visibility = View.VISIBLE

            submitBtn.visibility = View.GONE
            textInput.visibility = View.GONE
        }
    }
}