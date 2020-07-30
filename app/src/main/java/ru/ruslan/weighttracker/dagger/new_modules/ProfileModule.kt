package ru.ruslan.weighttracker.dagger.new_modules

import android.content.SharedPreferences
import androidx.core.content.res.FontResourcesParserCompat
import dagger.Module
import dagger.Provides
import ru.ruslan.weighttracker.dagger.scope.CameraScope
import ru.ruslan.weighttracker.dagger.scope.ProfileScope
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import ru.ruslan.weighttracker.data.repository.ProfileLocalRepositoryImpl
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import javax.inject.Singleton

@Module
class ProfileModule {

    @Provides
    fun provideProfileLocalRepository(localDBDataSource: ProfileLocalDBDataSource,
                                      profilePreferencesDataSource: ProfilePreferencesDataSource): ProfileLocalRepository =
        ProfileLocalRepositoryImpl(localDBDataSource, profilePreferencesDataSource)

    @Provides
    fun provideProfileLocalDBDataSource(roomDatabase: AppRoomDatabase): ProfileLocalDBDataSource =
        ProfileLocalDBDataSource(roomDatabase)

    @Provides
    fun provideProfilePreferencesDataSource(
        preferences: SharedPreferences): ProfilePreferencesDataSource =
        ProfilePreferencesDataSource(preferences)
}