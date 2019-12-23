package ru.ruslan.weighttracker.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.profile.vm.ProfileViewModel
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {

    private var profileViewModel: ProfileViewModel? = null
    @Inject lateinit var viewModelFatory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initVars()
        observerLiveData()
    }

    private fun initVars() {
        profileViewModel = ViewModelProviders.of(this, viewModelFatory).get(ProfileViewModel::class.java)
    }

    private fun observerLiveData() {
        profileViewModel?.generateAndSetProfileData()
    }
}
