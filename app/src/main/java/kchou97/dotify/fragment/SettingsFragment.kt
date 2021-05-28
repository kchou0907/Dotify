package kchou97.dotify.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kchou97.dotify.DotifyApplication
import kchou97.dotify.NavGraphDirections
import kchou97.dotify.R
import kchou97.dotify.databinding.FragmentSettingsBinding
import kchou97.dotify.manager.RefreshSongManager
import kchou97.dotify.manager.SongNotificationManager

private val SONG_NOTIFS_KEY = "SONG_NOTIFS_KEY"

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    lateinit var dotApp: DotifyApplication
    val safeArgs: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)
        val receivedSong = safeArgs.song
        dotApp = this.requireActivity().applicationContext as DotifyApplication
        val preferences = this.requireActivity().getSharedPreferences(SONG_NOTIFS_KEY, Context.MODE_PRIVATE)
        val refreshSongManager: RefreshSongManager by lazy { dotApp.refreshSongManager }

        with(binding) {
            profileBtn.setOnClickListener{
                navController.navigate(R.id.profileFragment)
            }

            statBtn.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalStatisticsFragment(receivedSong))
            }

            aboutBtn.setOnClickListener{
                navController.navigate(R.id.aboutFragment)
            }

            notificationToggle.isChecked = preferences.getBoolean(SONG_NOTIFS_KEY, false)

            if (notificationToggle.isChecked) {
                refreshSongManager.refreshSongPeriodically()
            } else {
                refreshSongManager.stopRefreshSongPeriodically()
            }

            notificationToggle.setOnCheckedChangeListener{ _, isChecked ->
                preferences.edit {
                    putBoolean(SONG_NOTIFS_KEY, isChecked)
                }

                if(isChecked) {
                    refreshSongManager.refreshSong()
                } else {
                    refreshSongManager.stopRefreshSongPeriodically()
                }
            }
        }

        return binding.root
    }
}