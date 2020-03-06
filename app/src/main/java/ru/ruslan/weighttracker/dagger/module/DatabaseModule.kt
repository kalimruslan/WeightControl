package ru.ruslan.weighttracker.dagger.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.ruslan.weighttracker.data.datasource.localdb.dao.ProfileLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.database.AppRoomDatabase
import ru.ruslan.weighttracker.data.utils.Constants
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppRoomDatabase::class.java,
            Constants.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Reusable
    internal fun provideProfileLocalDao(appRoomDatabase: AppRoomDatabase): ProfileLocalDao{
        return appRoomDatabase.profileLocalDao()
    }
}