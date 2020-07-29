package ru.ruslan.weighttracker.ui.profile

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.dagger.scope.ProfileScope
import ru.ruslan.weighttracker.domain.contract.ProfileContract
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.usecase.GetFromProfileUseCase
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import ru.ruslan.weighttracker.ui.ProfileEntityToUIMapper
import ru.ruslan.weighttracker.ui.ProfileUIToEntityMapper
import javax.inject.Inject


@ProfileScope
class ProfilePresenter @Inject constructor(private val saveToProfileUseCase: SaveToProfileUseCase,
                                           private val getFromProfileUseCase: GetFromProfileUseCase) : ProfileContract.Presenter {

    private lateinit var view: ProfileContract.View
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    override fun setView(view: ProfileContract.View) {
        this.view = view
        this.view.initViews()
    }

    override fun getCurrentProfile() {
        coroutineScope.launch {
            getFromProfileUseCase.getCurrentProfile(object :
                GetFromProfileUseCase.Callback.GetProfile {
                override fun getProfileSuccess(profileEntity: ProfileEntity?) {
                    val profileUI = ProfileEntityToUIMapper.map(profileEntity)
                    launch(Dispatchers.Main) {
                        view.populateProfileViews(profileUI)
                    }
                }

                override fun getProfileError() {}
            })
        }
    }

    override fun accountIfExist(): Boolean =
        getFromProfileUseCase.checkIfUserExist()


    override fun buttonCreateProfileClicked(name: String, sex: String, height: String,
                                            weight: String) {
        if (checkDataValidation(name, sex, height, weight)) {
            val profileUI = getProfileUI(name, sex, height, weight)
            createNewProfile(profileUI)
        }
    }

    private fun createNewProfile(profileUI: ProfileUI) {
        coroutineScope.launch {
            saveToProfileUseCase.insertProfile(
                ProfileUIToEntityMapper.map(profileUI),
                object : SaveToProfileUseCase.Callback.Profile {
                    override fun profileCreateSuccess() {
                        launch(Dispatchers.Main) {
                            view.showToastProfileCreatedSuccess()
                            view.hasItAccount(accountIfExist())
                        }
                    }

                    override fun profileCreateError() {
                        launch(Dispatchers.Main) {
                            view.showToastProfileCreatedError()
                        }
                    }

                    override fun profileEditSuccess() {}
                })
        }
    }

    override fun buttonEditProfileClicked(name: String, sex: String, height: String,
                                          weight: String) {
        if (checkDataValidation(name, sex, height, weight)) {
            val profileUI = getProfileUI(name, sex, height, weight)
            editCurrentProfile(profileUI)
        }
    }

    private fun editCurrentProfile(profileUI: ProfileUI) {
        coroutineScope.launch {
            saveToProfileUseCase.editProfile(
                ProfileUIToEntityMapper.map(profileUI),
                object : SaveToProfileUseCase.Callback.Profile {
                    override fun profileCreateSuccess() {}
                    override fun profileCreateError() {}
                    override fun profileEditSuccess() {
                        launch(Dispatchers.Main) {
                            view.showToastProfileEditedSuccess()
                        }
                    }
                })
        }
    }

    private fun getProfileUI(name: String, sex: String, height: String,
                             weight: String): ProfileUI {
        return ProfileUI(
            name,
            weight.split(" ")[0].toDouble(),
            height.split(" ")[0].toDouble(),
            sex,
            "",
            0.0,
            null
        )
    }

    private fun checkDataValidation(name: String, sex: String, height: String,
                                    weight: String): Boolean =
        name.isNotEmpty() && sex.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()
}