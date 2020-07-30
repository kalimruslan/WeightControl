package ru.ruslan.weighttracker.data.datasource.sharedpreferences

import android.content.SharedPreferences
import javax.inject.Inject

class ProfilePreferencesDataSource (private val preferences: SharedPreferences) {
    companion object{
        const val KEY_PROFILE_ID = "PROFILE_ID"
        const val KEY_WEIGHT_MEASUER = "WEIGHT_MEASURE"
        const val KEY_HEIGHT_MEASURE = "HEIGHT_MEASUER"
    }

    fun storeProfileId(profileId: Int){
        preferences
            .edit()
            .putInt(KEY_PROFILE_ID, profileId)
            .apply()
    }

    fun retrieveProfileId() = preferences.getInt(KEY_PROFILE_ID, 0)

    fun storeWeightMeasure(unit: String) {
        preferences
            .edit()
            .putString(KEY_WEIGHT_MEASUER, unit)
            .apply()
    }

    fun retrieveWeightMeasure(): String? = preferences.getString(KEY_WEIGHT_MEASUER, "кг.")

    fun storeHeightMeasure(unit: String) {
        preferences
            .edit()
            .putString(KEY_HEIGHT_MEASURE, unit)
            .apply()
    }

    fun retrieveHeightMeasure() : String? = preferences.getString(KEY_WEIGHT_MEASUER, "см.")
}