package ru.ruslan.weighttracker.ui.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val saveToProfileUseCase: SaveToProfileUseCase) :
    ViewModel() {

    fun generateAndSetProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            saveToProfileUseCase.insertProfile()
            saveToProfileUseCase.saveWeight()
            saveToProfileUseCase.savePhoto()
        }
    }
}