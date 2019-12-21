package ru.ruslan.weighttracker.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.profile.vm.ProfileViewModel

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
