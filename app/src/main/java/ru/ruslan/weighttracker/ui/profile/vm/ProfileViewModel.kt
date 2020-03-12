package ru.ruslan.weighttracker.ui.profile.vm

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.usecase.GetFromProfileUseCase
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import ru.ruslan.weighttracker.ui.ProfileEntityToUIMapper
import ru.ruslan.weighttracker.ui.ProfileUIToEntityMapper
import ru.ruslan.weighttracker.ui.profile.vm.model.ProfileUI
import ru.ruslan.weighttracker.ui.util.EnumCommand
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val saveToProfileUseCase: SaveToProfileUseCase,
                                           private val getFromProfileUseCase: GetFromProfileUseCase) :
    ViewModel() {

    private val profileMutableLiveData: MutableLiveData<ProfileUI> = MutableLiveData()
    private val isUserExistMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isProfileCreatedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isProfileEditedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val isProfileCreatedLiveData: LiveData<Boolean>
        get() = isProfileCreatedMutableLiveData

    val isProfileEditedLiveData: LiveData<Boolean>
        get() = isProfileEditedMutableLiveData

    init {
        checkIfUserExist()
    }

    fun checkIfUserExist(): LiveData<Boolean> {
        isUserExistMutableLiveData.value = getFromProfileUseCase.checkIfUserExist()
        return isUserExistMutableLiveData
    }

    fun userIsExist(): Boolean = getFromProfileUseCase.checkIfUserExist()

    fun getProfile(): LiveData<ProfileUI> {
        viewModelScope.launch(Dispatchers.IO) {
            getFromProfileUseCase.getCurrentProfile(object :
                GetFromProfileUseCase.Callback.GetProfile {
                override fun getProfileSuccess(profileEntity: ProfileEntity?) {
                    val profileUI = ProfileEntityToUIMapper.map(profileEntity)
                    profileMutableLiveData.postValue(profileUI)
                }

                override fun getProfileError() {}
            })
        }

        return profileMutableLiveData
    }

    fun manageProfile(command: EnumCommand, name: String, sex: String, height: String,
                      weight: String) {
        val profileUI = ProfileUI(
            name,
            weight.split(" ")[0].toDouble(),
            height.split(" ")[0].toDouble(),
            sex,
            "",
            0.0,
            null
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (command) {
                EnumCommand.CREATE_PROFILE -> {
                    saveToProfileUseCase.insertProfile(ProfileUIToEntityMapper.map(profileUI), object : SaveToProfileUseCase.Callback.Profile {
                            override fun profileCreateSuccess() {
                                isProfileCreatedMutableLiveData.postValue(true)
                            }
                            override fun profileCreateError() {
                                isProfileCreatedMutableLiveData.postValue(false)
                            }
                            override fun profileEditSuccess() {}
                        })
                }
                EnumCommand.EDIT_PROFILE -> {
                    saveToProfileUseCase.editProfile(ProfileUIToEntityMapper.map(profileUI), object : SaveToProfileUseCase.Callback.Profile {
                            override fun profileCreateSuccess() {}
                            override fun profileCreateError() {}
                            override fun profileEditSuccess() {
                                isProfileEditedMutableLiveData.postValue(true)
                            }
                        })
                }
            }

        }
    }

    fun checkDataValidation(name: String, sex: String, height: String, weight: String): Boolean =
        name.isNotEmpty() && sex.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()

}