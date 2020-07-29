package ru.ruslan.weighttracker.domain.contract

import ru.ruslan.weighttracker.ui.profile.ProfileUI

interface ProfileContract {
    interface View: BaseView{
        fun initViews()
        fun setListeners()
        fun hasItAccount(isHas: Boolean)
        fun populateProfileViews(profileUI: ProfileUI?)
        fun showToastProfileCreatedSuccess()
        fun showToastProfileCreatedError()
        fun showToastProfileEditedSuccess()
        fun showToastProfileEditedError()
    }

    interface Presenter: BasePresenter<View>{
        fun getCurrentProfile()
        fun accountIfExist(): Boolean
        fun buttonCreateProfileClicked(name: String, sex: String, height: String, weight: String)
        fun buttonEditProfileClicked(name: String, sex: String, height: String, weight: String)
    }
}