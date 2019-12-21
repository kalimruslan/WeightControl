package ru.ruslan.weighttracker.ui.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var isFabOpen: Boolean = false
    private val openCloseFabMld = MutableLiveData<Boolean>()
    private val profileMutableLiveData = MutableLiveData<ProfileUI>()
    val openCloseFabLd: LiveData<Boolean>
        get() = openCloseFabMld

    val profileLiveData: LiveData<ProfileUI>
        get() = profileMutableLiveData

    init {
        updateProfileViews()
    }

    private fun updateProfileViews() {
        val profile = ProfileUI(
            "1 января 2019",
            "120 кг",
            "1 февраля 2019",
            "110 кг"
        )
        profileMutableLiveData.value = profile
    }

    fun handleFabMain() {
        isFabOpen = if (isFabOpen) {
            openCloseFabMld.value = false
            false
        } else {
            openCloseFabMld.value = true
            true
        }
    }
}