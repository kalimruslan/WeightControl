package ru.ruslan.weighttracker.ui.profile.vm

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ruslan.weighttracker.domain.usecase.GetFromProfileUseCase
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import ru.ruslan.weighttracker.ui.ProfileEntityToUIMapper
import ru.ruslan.weighttracker.ui.profile.vm.model.ProfileUI
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val saveToProfileUseCase: SaveToProfileUseCase,
                                           private val getFromProfileUseCase: GetFromProfileUseCase) :
    ViewModel() {

    private val profileMutableLiveData: MutableLiveData<ProfileUI> = MutableLiveData()
    private val isUserExistMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        checkIfUserExist()
    }

    fun checkIfUserExist(): LiveData<Boolean> {
        isUserExistMutableLiveData.value = getFromProfileUseCase.checkIfUserExist()
        return isUserExistMutableLiveData
    }

    fun getProfile(): LiveData<ProfileUI> {
        viewModelScope.launch(Dispatchers.IO) {
            val profileUI = ProfileEntityToUIMapper.map(getFromProfileUseCase.getCurrentProfile())
            withContext(Dispatchers.Main){
                profileMutableLiveData.value = profileUI
            }
        }

        return profileMutableLiveData
    }

    fun generateAndSetProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            saveToProfileUseCase.insertProfile()
            saveToProfileUseCase.saveWeight()
            saveToProfileUseCase.savePhoto()
        }
    }
}