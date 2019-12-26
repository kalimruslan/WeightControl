package ru.ruslan.weighttracker.ui.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private var isFabOpen: Boolean = false
    private val openCloseFabMld = MutableLiveData<Boolean>()
    private val profileMutableLiveData = MutableLiveData<HomeUI>()
    val openCloseFabLd: LiveData<Boolean>
        get() = openCloseFabMld

    val profileLiveData: LiveData<HomeUI>
        get() = profileMutableLiveData

    init {
        updateProfileViews()
    }

    private fun updateProfileViews() {
        val profile = HomeUI(
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