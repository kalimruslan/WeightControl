package ru.ruslan.weighttracker.profile.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.repository.LocalProfileRepositoryImpl
import ru.ruslan.weighttracker.profile.domain.usecase.SaveToProfileUseCase

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val saveToProfileUseCase: SaveToProfileUseCase = SaveToProfileUseCase(
        LocalProfileRepositoryImpl(
            ProfileLocalDBDataSource(AppRoomDatabase.getDatabase(getApplication()))
        )
    )


    fun generateAndSetProfileData(){
        viewModelScope.launch(Dispatchers.IO){
            saveToProfileUseCase.insertProfile()
            saveToProfileUseCase.saveWeight()
            saveToProfileUseCase.savePhoto()
        }
    }
}