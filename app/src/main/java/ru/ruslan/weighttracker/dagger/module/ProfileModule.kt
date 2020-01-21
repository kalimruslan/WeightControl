package ru.ruslan.weighttracker.dagger.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import ru.ruslan.weighttracker.data.repository.ProfileLocalRepositoryImpl
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class ProfileModule {

    @Provides
    fun provideProfileLocalRepository(localDBDataSource: ProfileLocalDBDataSource,
                                      profilePreferencesDataSource: ProfilePreferencesDataSource)
            : ProfileLocalRepository =
        ProfileLocalRepositoryImpl(localDBDataSource, profilePreferencesDataSource)

    @Provides
    fun provideProfileLocalDBDataSource(roomDatabase: AppRoomDatabase): ProfileLocalDBDataSource =
        ProfileLocalDBDataSource(roomDatabase)

    @Singleton
    @Provides
    fun provideProfilePreferencesDataSource(
        preferences: SharedPreferences): ProfilePreferencesDataSource =
        ProfilePreferencesDataSource(preferences)
}