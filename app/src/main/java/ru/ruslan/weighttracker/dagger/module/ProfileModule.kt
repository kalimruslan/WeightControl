package ru.ruslan.weighttracker.dagger.module

import android.content.SharedPreferences
import androidx.core.content.res.FontResourcesParserCompat
import dagger.Module
import dagger.Provides
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import ru.ruslan.weighttracker.data.repository.ProfileLocalRepositoryImpl
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ProfileModule {

    @Provides
    fun provideProfileLocalRepository(localDBDataSource: ProfileLocalDBDataSource,
                                      profilePreferencesDataSource: ProfilePreferencesDataSource)
            : ProfileLocalRepository =
        ProfileLocalRepositoryImpl(localDBDataSource, profilePreferencesDataSource)

    @Provides
    fun provideProfileLocalDBDataSource(roomDatabase: AppRoomDatabase): ProfileLocalDBDataSource =
        ProfileLocalDBDataSource(roomDatabase)

    @Provides
    fun provideProfilePreferencesDataSource(
        preferences: SharedPreferences): ProfilePreferencesDataSource =
        ProfilePreferencesDataSource(preferences)
}