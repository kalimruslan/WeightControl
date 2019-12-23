package ru.ruslan.weighttracker.data.datasource.sharedpreferences

import android.content.SharedPreferences
import javax.inject.Inject

class ProfilePreferencesDataSource (private val preferences: SharedPreferences) {
    companion object{
        const val KEY_PROFILE_ID = "PROFILE_ID"
    }

    fun storeProfileId(profileId: Int){
        preferences
            .edit()
            .putInt(KEY_PROFILE_ID, profileId)
            .apply()
    }

    fun retreiveProfileId() = preferences.getInt(KEY_PROFILE_ID, 0)
}