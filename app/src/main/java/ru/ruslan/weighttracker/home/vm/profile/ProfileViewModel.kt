package ru.ruslan.weighttracker.home.vm.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import ru.ruslan.weighttracker.data.repository.LocalProfileRepositoryImpl
import ru.ruslan.weighttracker.data.repository.ProfilePreferncesRepositoryImpl
import ru.ruslan.weighttracker.home.domain.usecase.SaveToProfileUseCase

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("PROFILE_PREFERENCES", Context.MODE_PRIVATE)
    private val saveToProfileUseCase: SaveToProfileUseCase =
        SaveToProfileUseCase(
            LocalProfileRepositoryImpl(
                ProfileLocalDBDataSource(AppRoomDatabase.getDatabase(getApplication()))
            ),
            ProfilePreferncesRepositoryImpl(ProfilePreferencesDataSource(sharedPreferences))
        )


    fun generateAndSetProfileData(){
        viewModelScope.launch(Dispatchers.IO){
            saveToProfileUseCase.insertProfile()
            saveToProfileUseCase.saveWeight()
            saveToProfileUseCase.savePhoto()
        }
    }
}