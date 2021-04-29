package kchou97.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.ericchee.songdataprovider.Song
import kchou97.dotify.databinding.ActivitySettingsBinding

private const val SONG_DETAILS_KEY = "song"
/*
* Navigates to the Settings Activity screen
* @Param context Context - provides information on whatever activity the user is coming from
* @Param songDeets Song - song object containing all the song details
* */
fun navSettingsActivity(context: Context, song: SongDetails) {

    val intent = Intent(context, SettingsActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(SONG_DETAILS_KEY, song)
        }
        putExtras(bundle)
    }
    context.startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            navController.setGraph(R.navigation.nav_graph, intent.extras)
        }

    }
}