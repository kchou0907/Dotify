package kchou97.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kchou97.dotify.NavGraphDirections
import kchou97.dotify.R
import kchou97.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    val safeArgs: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)
        val receivedSong = safeArgs.song

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
        }

        return binding.root
    }
}