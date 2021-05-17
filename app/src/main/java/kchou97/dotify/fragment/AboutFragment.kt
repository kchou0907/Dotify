package kchou97.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kchou97.dotify.BuildConfig
import kchou97.dotify.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        with(binding) {
            version.text = BuildConfig.VERSION_NAME
        }
        return binding.root
    }
}