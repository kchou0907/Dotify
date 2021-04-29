package kchou97.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kchou97.dotify.databinding.FragmentSettingsBinding
import kchou97.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {
    private val safeArgs: SettingsFragmentArgs by navArgs()

    private lateinit var song: SongDetails
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentStatisticsBinding.inflate(inflater)
        song = safeArgs.song

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                song = this.getParcelable("songDetails")!!
            }
        }
        with(binding) {
            var blurb = root.context.getString(R.string.song_count, song.song?.title, song.count)
            song.song?.largeImageID?.let { albumArt.setImageResource(it) }
            count.text = blurb
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putParcelable("songDetails", song)
        super.onSaveInstanceState(outState)
    }

}
