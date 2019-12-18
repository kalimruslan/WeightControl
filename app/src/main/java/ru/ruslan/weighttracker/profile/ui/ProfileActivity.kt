package ru.ruslan.weighttracker.profile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.profile.vm.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


    }
}
