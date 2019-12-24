package ru.ruslan.weighttracker.dagger.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import ru.ruslan.weighttracker.data.repository.LocalProfileRepositoryImpl
import ru.ruslan.weighttracker.data.repository.ProfilePreferncesRepositoryImpl
import ru.ruslan.weighttracker.domain.repository.ProfilePrefencesRepository
import ru.ruslan.weighttracker.domain.repository.ProfileRepository
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class ProfileModule {

    @Provides
    fun provideProfileRepository(localDBDataSource: ProfileLocalDBDataSource): ProfileRepository =
        LocalProfileRepositoryImpl(localDBDataSource)

    @Provides
    fun provideProfileLocalDBDataSource(roomDatabase: AppRoomDatabase): ProfileLocalDBDataSource =
        ProfileLocalDBDataSource(roomDatabase)

    @Provides
    fun provideProfilePreferencesRepository(
        profilePreferencesDataSource: ProfilePreferencesDataSource): ProfilePrefencesRepository =
        ProfilePreferncesRepositoryImpl(profilePreferencesDataSource)

    @Singleton
    @Provides
    fun provideProfilePreferencesDataSource(
        preferences: SharedPreferences): ProfilePreferencesDataSource =
        ProfilePreferencesDataSource(preferences)
}