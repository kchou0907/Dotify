package kchou97.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val randomNumber = Random.nextInt(1000, 10000)
    private var playNum = 0
    private lateinit var playCount: TextView
    private lateinit var prevBtn: ImageButton
    private lateinit var nextBtn: ImageButton
    private lateinit var playBtn: ImageButton
    private lateinit var editBtn: Button
    private lateinit var submitBtn: Button
    private lateinit var userfield: TextView
    private lateinit var textInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playCount = findViewById(R.id.playCount)
        prevBtn = findViewById(R.id.prev)
        nextBtn = findViewById(R.id.next)
        playBtn = findViewById(R.id.play)
        editBtn = findViewById(R.id.changeUser)
        submitBtn = findViewById(R.id.submitUser)
        userfield = findViewById(R.id.username)
        textInput = findViewById(R.id.editUsername)
        var submitIsVisible: Boolean = true;

        playCount.text = randomNumber.toString() + " plays"
        playNum = randomNumber

        submitBtn.visibility = View.GONE
        textInput.visibility = View.GONE

        prevBtn.setOnClickListener{
            displayMessage("Skipping to previous track")
        }

        nextBtn.setOnClickListener{
            displayMessage("Skipping to next track")
        }

        playBtn.setOnClickListener{
            incrementCounter(playCount)
        }

        editBtn.setOnClickListener{
            toggleEdit(submitBtn, textInput, editBtn, userfield, submitIsVisible)
            submitIsVisible = !submitIsVisible
        }

        submitBtn.setOnClickListener{
            if (editUserName(textInput, userfield)) {
                toggleEdit(submitBtn, textInput, editBtn, userfield, !submitIsVisible)
                submitIsVisible = !submitIsVisible
            } else {
                displayMessage("Please submit a valid username")
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