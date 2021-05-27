package kchou97.dotify.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.load
import kchou97.dotify.DotifyApplication
import kchou97.dotify.databinding.FragmentAboutBinding
import kchou97.dotify.databinding.FragmentProfileBinding
import kchou97.dotify.repository.DataRepository
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var myApp: DotifyApplication
    private lateinit var dataRepository: DataRepository
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myApp = context.applicationContext as DotifyApplication
        dataRepository = myApp.dataRepository
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileBinding.inflate(inflater)

        with(binding) {
            lifecycleScope.launch {
                runCatching {
                    val user = dataRepository.getUser()

                    username.text = user.username
                    firstname.text = "First Name: " + user.firstName
                    lastname.text = "Last Name: " + user.lastName
                    profilePic.load(user.profilePicURL)

                }.onFailure {
                    username.text = "failed to get user"
                }
            }

            swipeToRefresh.setOnClickListener{
                lifecycleScope.launch {
                    runCatching {
                        val user = dataRepository.getUser()

                        username.text = user.username
                        firstname.text = "First Name: " + user.firstName
                        lastname.text = "Last Name: " + user.lastName
                        profilePic.load(user.profilePicURL)

                    }.onFailure {
                        username.text = "failed to get user"
                    }
                }
                swipeToRefresh.isRefreshing = false
            }
        }

        return binding.root
    }

}