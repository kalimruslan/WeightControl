package ru.ruslan.weighttracker.profile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.profile.vm.ProfileViewModel
import ru.ruslan.weighttracker.videos.list.ui.VideosFragment

class ProfileActivity : AppCompatActivity() {

    private var profileViewModel: ProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initVars()
        observerLiveData()
    }

    private fun initVars() {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    private fun observerLiveData() {
        profileViewModel?.generateAndSetProfileData()
    }
}
